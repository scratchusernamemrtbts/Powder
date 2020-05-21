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

    @Override
    public void update() {
//        Always move down, if possible.
        if (!getWorld().getTile(getPosition().down()).isSolid()) {
            getWorld().swapTiles(getPosition(), getPosition().down());
            return;
        }

//        Always move diagonally, if possible.
        if (!getWorld().getTile(getPosition().down().right()).isSolid()) {
            getWorld().swapTiles(getPosition(), getPosition().down().right());
            return;
        }
        if (!getWorld().getTile(getPosition().down().left()).isSolid()) {
            getWorld().swapTiles(getPosition(), getPosition().down().left());
            return;
        }

//        Move to the side if we are being pushed from above, if possible.
        if (getWorld().getTile(getPosition().up()).hasGravity() && getWorld().getTile(getPosition().up()).isSolid()) {
            if (!getWorld().getTile(getPosition().left()).isSolid()) {
                getWorld().swapTiles(getPosition(), getPosition().left());
                return;
            }
            if (!getWorld().getTile(getPosition().right()).isSolid()) {
                getWorld().swapTiles(getPosition(), getPosition().right());
                return;
            }
        }

//        If we have gotten to this point, we should become dormant.
        setActive(false);
    }
}
