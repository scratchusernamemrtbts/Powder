package powder.tiles;

import powder.position.Direction;
import powder.util.Randomizer;
import powder.World;

public abstract class LiquidTile extends Tile {
    private int horizontalMovementTicks = 0;
    private Direction preferredHorizontalDirection = null;

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

    private boolean isSolidAndHasGravity(Tile t) {
        return t.isSolid() && t.hasGravity() && !(t instanceof LiquidTile);
    }

    private boolean isNotSolidAndNotWater(Tile t) {
        return !t.isSolid() && !(t instanceof LiquidTile);
    }

    private void resetHorizontal() {
        horizontalMovementTicks = 0;
        preferredHorizontalDirection = null;
    }

    protected boolean floatsOn(Tile tile) {
        return tile instanceof LiquidTile && ((LiquidTile) tile).getBuoyancy() < getBuoyancy();
    }

    protected boolean canDisplace(Tile tile) {
        return tile.isAir() || (tile instanceof OilTile && ((LiquidTile) tile).getBuoyancy() > getBuoyancy());
    }

    protected boolean isPushingDown(Tile tile) {
        return tile.hasGravity();
    }

    @Override
    public void update() {
//        Check evaporation
        if (horizontalMovementTicks > 100) {
            if (getEvaporationChance() > 0 && Randomizer.nextDouble() < getEvaporationChance()) {
                evaporate();
                return;
            }
        }

//        Check for floating
        if (floatsOn(getWorld().getTile(getPosition().up()))) {
            getWorld().swapTiles(getPosition(), getPosition().up());
            return;
        }

//        Always move down, if possible.
        if (canDisplace(getWorld().getTile(getPosition().down()))) {
            if (preferredHorizontalDirection == null) {
//                Check for being pushed from above
                if (isPushingDown(getWorld().getTile(getPosition().up()))) {
                    if (canDisplace(getWorld().getTile(getPosition().left()))) {
                        getWorld().swapTiles(getPosition(), getPosition().left());
                        preferredHorizontalDirection = Direction.Left;
                        return;
                    }
                    if (canDisplace(getWorld().getTile(getPosition().right()))) {
                        getWorld().swapTiles(getPosition(), getPosition().right());
                        preferredHorizontalDirection = Direction.Right;
                        return;
                    }
//                    if (moved) {
//                        for (int i = 0; i < 2; i++) {
//                            if (canDisplace(getWorld().getTile(getPosition().direction(preferredHorizontalDirection)))) {
//                                getWorld().swapTiles(getPosition(), getPosition().direction(preferredHorizontalDirection));
//                            }
//                        }
//                        return;
//                    }
                }
            } else {
                if (isPushingDown(getWorld().getTile(getPosition().up()))) {
                    if (canDisplace(getWorld().getTile(getPosition().down().direction(preferredHorizontalDirection)))) {
                        getWorld().swapTiles(getPosition(), getPosition().down().direction(preferredHorizontalDirection));
                    } else {
                        preferredHorizontalDirection = null;
                    }
                }
            }
            getWorld().swapTiles(getPosition(), getPosition().down());
            resetHorizontal();
            return;
        }

//        Always move diagonally, if possible.
        if (canDisplace(getWorld().getTile(getPosition().down().right()))) {
            getWorld().swapTiles(getPosition(), getPosition().down().right());
            resetHorizontal();
            return;
        }
        if (canDisplace(getWorld().getTile(getPosition().down().left()))) {
            getWorld().swapTiles(getPosition(), getPosition().down().left());
            resetHorizontal();
            return;
        }

//        Move horizontally if being pushed from above
        if (isPushingDown(getWorld().getTile(getPosition().up()))) {
            if (canDisplace(getWorld().getTile(getPosition().left()))) {
                getWorld().swapTiles(getPosition(), getPosition().left());
                preferredHorizontalDirection = Direction.Left;
                return;
            }
            if (canDisplace(getWorld().getTile(getPosition().right()))) {
                getWorld().swapTiles(getPosition(), getPosition().right());
                preferredHorizontalDirection = Direction.Right;
                return;
            }
        }

//        Move horizontally on own, if possible.
        if (preferredHorizontalDirection != null) {
            if (canDisplace(getWorld().getTile(getPosition().direction(preferredHorizontalDirection)))) {
                getWorld().swapTiles(getPosition(), getPosition().direction(preferredHorizontalDirection));
                horizontalMovementTicks++;
                return;
            }
        }
        if (Randomizer.nextBoolean()) {
            if (canDisplace(getWorld().getTile(getPosition().right()))) {
                getWorld().swapTiles(getPosition(), getPosition().right());
                preferredHorizontalDirection = Direction.Right;
                horizontalMovementTicks++;
                return;
            }
            if (canDisplace(getWorld().getTile(getPosition().left()))) {
                getWorld().swapTiles(getPosition(), getPosition().left());
                preferredHorizontalDirection = Direction.Left;
                horizontalMovementTicks++;
                return;
            }
        } else {
            if (canDisplace(getWorld().getTile(getPosition().left()))) {
                getWorld().swapTiles(getPosition(), getPosition().left());
                preferredHorizontalDirection = Direction.Left;
                horizontalMovementTicks++;
                return;
            }
            if (canDisplace(getWorld().getTile(getPosition().right()))) {
                getWorld().swapTiles(getPosition(), getPosition().right());
                preferredHorizontalDirection = Direction.Right;
                horizontalMovementTicks++;
                return;
            }
        }

//        If we have gotten to this point, we should become dormant.
        setActive(false);
    }
}
