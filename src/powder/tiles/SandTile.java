package powder.tiles;

import powder.util.ColorRandomizer;
import powder.World;

import java.awt.*;

public class SandTile extends PowderTile {
    public SandTile(World world) {
        super(world);
        setColor(ColorRandomizer.randomize(new Color(255, 237, 149), 0, 25, 25));
    }

    @Override
    public char getChar() {
        return '@';
    }
}
