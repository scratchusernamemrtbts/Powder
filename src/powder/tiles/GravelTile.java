package powder.tiles;

import powder.World;
import powder.util.ColorRandomizer;

import java.awt.*;

public class GravelTile extends PowderTile {
    public GravelTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(127, 127, 127), 30));
    }
}
