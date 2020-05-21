package powder.tiles;

import powder.World;

public abstract class SolidTile extends Tile {
    public SolidTile(World world) {
        super(world);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean hasGravity() {
        return false;
    }

    @Override
    public void update() {

    }
}
