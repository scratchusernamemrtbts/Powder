package powder.tiles;

import powder.util.ColorRandomizer;
import powder.World;

import java.awt.*;

public class WaterTile extends LiquidTile {
    public WaterTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(0, 0, 200), 20));
    }

    @Override
    public double getBuoyancy() {
        return 1.0;
    }
}
