package powder.tiles;

import powder.util.ColorRandomizer;
import powder.World;

import java.awt.*;

public class FuseTile extends SolidTile {
    public FuseTile(World world) {
        super(world);
        setColor(ColorRandomizer.randomize(new Color(199, 110, 17), 25, 25, 25));
    }

    @Override
    public double getFlammability() {
        return 1;
    }
}
