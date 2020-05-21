package powder.tiles;

import powder.util.ColorRandomizer;
import powder.World;

import java.awt.*;

public class GunpowderTile extends PowderTile {
    public GunpowderTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(123, 123, 123), 10));
    }

    @Override
    public double getFlammability() {
        return 0.5;
    }
}
