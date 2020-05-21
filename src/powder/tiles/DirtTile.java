package powder.tiles;

import powder.World;
import powder.util.ColorRandomizer;

import java.awt.*;

public class DirtTile extends PowderTile {
    public DirtTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(141, 91, 65), 30));
    }
}
