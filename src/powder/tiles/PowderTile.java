package powder.tiles;

import powder.World;

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
        if (canDisplace(getWorld().getTile(getPosition().down().right()))) {
            getWorld().swapTiles(getPosition(), getPosition().down().right());
            return;
        }
        if (canDisplace(getWorld().getTile(getPosition().down().left()))) {
            getWorld().swapTiles(getPosition(), getPosition().down().left());
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
