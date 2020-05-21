package powder.tiles;

import powder.util.ColorRandomizer;
import powder.World;

import java.awt.*;

public class SmokeTile extends GasTile {
    public SmokeTile(World world) {
        super(world);
        setColor(ColorRandomizer.randomize(new Color(69, 69, 69), 10, 10, 10));
    }

    @Override
    public double getDeathChance() {
        return 0.1;
    }
}
