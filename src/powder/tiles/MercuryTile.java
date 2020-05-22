package powder.tiles;

import powder.World;
import powder.util.ColorRandomizer;

import java.awt.*;

public class MercuryTile extends LiquidTile {
    public MercuryTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(70, 70, 70), 10));
    }

    @Override
    public double getBuoyancy() {
        return 0.5;
    }
}
