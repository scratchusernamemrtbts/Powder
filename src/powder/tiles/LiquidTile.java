package powder.tiles;

import powder.position.Direction;
import powder.position.Position;
import powder.World;
import powder.util.Randomizer;

public abstract class LiquidTile extends Tile {
    private int extraneousTicks = 0;
    private Direction randomDirection = null;

    public LiquidTile(World world) {
        super(world);
    }

    public abstract double getBuoyancy();

    public double getEvaporationChance() {
        return 0.02;
    }

    public void evaporate() {
        getWorld().setTile(getPosition(), new SteamTile(getWorld()));
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }

    protected boolean floatsOn(Tile tile) {
        return tile instanceof LiquidTile && ((LiquidTile) tile).getBuoyancy() < getBuoyancy();
    }

    protected boolean canDisplace(Tile tile) {
        return tile.isAir() || (tile instanceof LiquidTile && ((LiquidTile) tile).getBuoyancy() > getBuoyancy());
    }

    protected boolean canPush(Tile tile) {
        return tile.isAir() || (tile instanceof LiquidTile && ((LiquidTile) tile).getBuoyancy() > getBuoyancy());
    }

    private boolean isRowFull(int x, int y) {
        for (int i = x; i > 0; i--) {
            Tile tile = getWorld().getTile(new Position(i, y));
            if (!isSimilarLiquid(tile)) {
                if (tile.isAir() || isDifferentLiquid(tile)) {
                    return false;
                }
                break;
            }
        }
        for (int i = x; i < getWorld().getWidth() - 1; i++) {
            Tile tile = getWorld().getTile(new Position(i, y));
            if (!isSimilarLiquid(tile)) {
                if (tile.isAir() || isDifferentLiquid(tile)) {
                    return false;
                }
                break;
            }
        }
        return true;
    }

    private boolean rowHasLiquid(int x, int y) {
        for (int i = x; i > 0; i--) {
            Tile tile = getWorld().getTile(new Position(i, y));
            if (isSimilarLiquid(tile)) {
                return true;
            }
            if (tile.isSolid()) {
                break;
            }
        }
        for (int i = x; i < getWorld().getWidth() - 1; i++) {
            Tile tile = getWorld().getTile(new Position(i, y));
            if (isSimilarLiquid(tile)) {
                return true;
            }
            if (tile.isSolid()) {
                break;
            }
        }
        return false;
    }

    private boolean isExtraneous() {
//        We are considered extraneous if:
//         - our row is not full
//         - row below us is full
//         - no liquid above us

        return !rowHasLiquid(getPosition().getX(), getPosition().getY() + 1) &&
                !isRowFull(getPosition().getX(), getPosition().getY()) &&
                isRowFull(getPosition().getX(), getPosition().getY() - 1);
    }

    private Direction getFlowingDirection() {
        Tile leftTile = null;
        Tile rightTile = null;

        for (int x = getPosition().getX(); x > 0; x--) {
            Tile tile = getWorld().getTile(new Position(x, getPosition().getY() - 1));
            if (!isSimilarLiquid(tile)) {
                if (!tile.isSolid()) {
                    leftTile = tile;
                }
                break;
            }
        }

        for (int x = getPosition().getX(); x < getWorld().getWidth() - 1; x++) {
            Tile tile = getWorld().getTile(new Position(x, getPosition().getY() - 1));
            if (!isSimilarLiquid(tile)) {
                if (!tile.isSolid()) {
                    rightTile = tile;
                }
                break;
            }
        }

        if (leftTile == null && rightTile == null) return null;

        if (rightTile == null) return Direction.Left;
        if (leftTile == null) return Direction.Right;

        int leftDistance = Math.abs(leftTile.getPosition().getX() - getPosition().getX());
        int rightDistance = Math.abs(rightTile.getPosition().getX() - getPosition().getX());

        if (rightDistance < leftDistance) return Direction.Right;
        return Direction.Left;
    }

    private boolean isSimilarLiquid(Tile tile) {
        return tile instanceof LiquidTile && ((LiquidTile) tile).getBuoyancy() == getBuoyancy();
    }

    private boolean isDifferentLiquid(Tile tile) {
        return tile instanceof LiquidTile && ((LiquidTile) tile).getBuoyancy() != getBuoyancy();
    }

    private void push(Direction direction) {
//        Look for the edge
        Position position = getPosition().direction(direction);
        while (isSimilarLiquid(getWorld().getTile(position))) {
            position = position.direction(direction);
        }
        position = position.direction(Direction.opposite(direction));

        if (position.equals(getPosition())) {
            getWorld().swapTiles(position.direction(direction), getPosition());
            return;
        }

//        Swap tiles, moving backwards
        while (!position.equals(getPosition())) {
//            if (!(isSimilarLiquid(getWorld().getTile(position.direction(direction))) || getWorld().getTile(position.direction(direction)).isAir())) {
//                return;
//            }
            if (!canPush(getWorld().getTile(position.direction(direction)))) {
                position = position.direction(Direction.opposite(direction));
                continue;
            }
            getWorld().swapTiles(position.direction(direction), position);
            position = position.direction(Direction.opposite(direction));
        }
    }

    @Override
    public void update() {
        if (isExtraneous()) {
            extraneousTicks++;
            if (extraneousTicks > 100) {
                if (Randomizer.nextDouble() < getEvaporationChance()) {
                    evaporate();
                    return;
                }
            }
        } else {
            extraneousTicks = 0;
        }

//        Always move down, if possible.
        if (canDisplace(getWorld().getTile(getPosition().down()))) {
            getWorld().swapTiles(getPosition(), getPosition().down());
            return;
        }

//        Diagonal movement, if possible.
        Tile downRight = getWorld().getTile(getPosition().down().right());
        Tile downLeft = getWorld().getTile(getPosition().down().left());
        if (canDisplace(downRight) && !canDisplace(downLeft)) {
            getWorld().swapTiles(getPosition(), downRight.getPosition());
            return;
        }
        if (canDisplace(downLeft) && !canDisplace(downRight)) {
            getWorld().swapTiles(getPosition(), downLeft.getPosition());
            return;
        }
        if (canDisplace(downLeft) && canDisplace(downRight)) {
            if (Randomizer.nextBoolean()) {
                getWorld().swapTiles(getPosition(), downRight.getPosition());
            } else {
                getWorld().swapTiles(getPosition(), downLeft.getPosition());
            }
            return;
        }

//        Detect flow direction
        if (isSimilarLiquid(getWorld().getTile(getPosition().down())) && !isSimilarLiquid(getWorld().getTile(getPosition().up()))) {
            Direction d = getFlowingDirection();
            if (d != null) {
                if (getWorld().getTile(getPosition().direction(d).down()).isAir()) {
                    getWorld().swapTiles(getPosition(), getPosition().direction(d).down());
                } else {
                    push(d);
                }
                return;
            }
        }

        if (canDisplace(getWorld().getTile(getPosition().right()))) {
            getWorld().swapTiles(getPosition(), getPosition().right());
            return;
        }
        if (canDisplace(getWorld().getTile(getPosition().left()))) {
            getWorld().swapTiles(getPosition(), getPosition().left());
            return;
        }

//        Random horizontal movement
        if (randomDirection != null) {
            if (canDisplace(getWorld().getTile(getPosition().direction(randomDirection)))) {
                getWorld().swapTiles(getPosition(), getPosition().direction(randomDirection));
                return;
            }
        }
        Tile right = getWorld().getTile(getPosition().right());
        Tile left = getWorld().getTile(getPosition().left());
        if (right.isAir() && left.isAir()) {
            if (Randomizer.nextBoolean()) {
                randomDirection = Direction.Right;
                getWorld().swapTiles(getPosition(), right.getPosition());
            } else {
                randomDirection = Direction.Left;
                getWorld().swapTiles(getPosition(), left.getPosition());
            }
            return;
        }
        if (right.isAir() && !left.isAir()) {
            randomDirection = Direction.Right;
            getWorld().swapTiles(getPosition(), right.getPosition());
            return;
        }
        if (left.isAir() && !right.isAir()) {
            randomDirection = Direction.Left;
            getWorld().swapTiles(getPosition(), left.getPosition());
            return;
        }

//        If we have gotten to this point, we should become dormant.
//        This means that we are completely enclosed in water, both sides and at least bottom
//        If we need to evaporate we will be awoken by a neighbor
        setActive(false);
    }
}
