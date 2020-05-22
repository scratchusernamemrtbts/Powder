package powder.tiles;

import powder.World;
import powder.position.Position;
import powder.util.ColorRandomizer;

import java.awt.*;

public class BlueFireTile extends FireTile {
    public BlueFireTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(81, 185, 255), 20));
    }

    @Override
    protected double effectiveFlammability(Tile tile) {
        if (tile.isImmutable()) {
            return 0;
        }
        if (tile.getFlammability() == 0) {
            return 0.5;
        }
        return 0;
    }

    @Override
    protected void spreadFire(Position position) {
        getWorld().setTile(position, new BlueFireTile(getWorld()));
    }

    @Override
    protected boolean smotheredBy(Tile tile) {
        return tile.getFlammability() == 1;
    }
}
