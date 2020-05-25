package powder.tiles;

import powder.util.Randomizer;
import powder.World;

public abstract class GasTile extends Tile {
    public GasTile(World world) {
        super(world);
    }

    @Override
    public boolean hasGravity() {
        return false;
    }

    @Override
    public boolean isAir() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    private boolean canDisplace(Tile t) {
        return !t.isSolid();
    }

    public double getDeathChance() {
        return 0.002;
    }

    @Override
    public void update() {
//        Random death
        if (Randomizer.nextDouble() < getDeathChance()) {
            getWorld().setTile(getPosition(), new AirTile(getWorld()));
            return;
        }

//        Try to move up
        if (canDisplace(getWorld().getTile(getPosition().up()))) {
            getWorld().swapTiles(getPosition(), getPosition().up());
        }

//        Try to move to a side
        if (Randomizer.nextBoolean()) {
            if (canDisplace(getWorld().getTile(getPosition().right()))) {
                getWorld().swapTiles(getPosition(), getPosition().right());
            }
        } else {
            if (canDisplace(getWorld().getTile(getPosition().left()))) {
                getWorld().swapTiles(getPosition(), getPosition().left());
            }
        }
    }

    @Override
    public char getChar() {
        return '*';
    }
}
