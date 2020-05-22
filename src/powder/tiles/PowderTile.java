package powder.tiles;

import powder.World;
import powder.util.Randomizer;

public abstract class PowderTile extends Tile {
    public PowderTile(World world) {
        super(world);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean hasGravity() {
        return true;
    }

    private boolean canDisplace(Tile tile) {
        return !tile.isSolid();
    }

    private boolean canDisplaceDiagonally(Tile tile) {
        return canDisplace(tile) && !(tile instanceof LiquidTile);
    }

    private boolean isPushingDown(Tile tile) {
        return tile.isSolid() && tile.hasGravity();
    }

    @Override
    public void update() {
//        Always move down, if possible.
        if (canDisplace(getWorld().getTile(getPosition().down()))) {
            getWorld().swapTiles(getPosition(), getPosition().down());
            return;
        }

//        Always move diagonally, if possible.
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

//        Move to the side if we are being pushed from above, if possible.
        if (isPushingDown(getWorld().getTile(getPosition().up()))) {
            if (canDisplaceDiagonally(getWorld().getTile(getPosition().left()))) {
                getWorld().swapTiles(getPosition(), getPosition().left());
                return;
            }
            if (canDisplaceDiagonally(getWorld().getTile(getPosition().right()))) {
                getWorld().swapTiles(getPosition(), getPosition().right());
                return;
            }
        }

//        If we have gotten to this point, we should become dormant.
        setActive(false);
    }
}
