package powder.tiles;

import powder.World;
import powder.util.ColorRandomizer;

import java.awt.*;

public class MethaneTile extends GasTile {
    public MethaneTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(203, 255, 0), 20));
    }

    @Override
    public double getFlammability() {
        return 1;
    }

    @Override
    public double getDeathChance() {
        return 0;
    }
}
