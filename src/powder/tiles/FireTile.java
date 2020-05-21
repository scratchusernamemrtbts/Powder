package powder.tiles;

import powder.util.ColorRandomizer;
import powder.util.Randomizer;
import powder.World;
import powder.position.Position;

import java.awt.*;

public class FireTile extends Tile {
    private boolean removed = false;
    private int airNearby = 0;

    public FireTile(World world) {
        super(world);
        setColor(ColorRandomizer.randomize(new Color(230, 133, 0), 25, 25, 25));
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    private void checkNeighbor(Position position) {
        Tile tile = getWorld().getTile(position);

        if (tile instanceof WaterTile) {
            getWorld().setTile(getPosition(), new AirTile(getWorld()));
            removed = true;
            return;
        }

        if (tile instanceof AirTile) {
            airNearby++;
        }

        if (removed) {
            return;
        }

        if (tile.getFlammability() > 0) {
            if (Randomizer.nextDouble() < tile.getFlammability()) {
                getWorld().setTile(position, new FireTile(getWorld()));
            }
        }
    }

    @Override
    public void update() {
//        random death
        if (Randomizer.nextDouble() < 0.1) {
            getWorld().setTile(getPosition(), new SmokeTile(getWorld()));
        }

//        check up neighbors
        airNearby = 0;
        checkNeighbor(getPosition().up());
        checkNeighbor(getPosition().down());
        checkNeighbor(getPosition().left());
        checkNeighbor(getPosition().right());
        if (removed) {
            return;
        }

//        smothering
        if (airNearby == 0) {
            getWorld().setTile(getPosition(), new AirTile(getWorld()));
        }

//        try to move around
        if (getWorld().getTile(getPosition().up()).isAir()) {
            getWorld().swapTiles(getPosition(), getPosition().up());
        }
        if (Randomizer.nextBoolean()) {
            if (getWorld().getTile(getPosition().right()).isAir()) {
                getWorld().swapTiles(getPosition(), getPosition().right());
            }
        } else {
            if (getWorld().getTile(getPosition().left()).isAir()) {
                getWorld().swapTiles(getPosition(), getPosition().left());
            }
        }
    }
}
