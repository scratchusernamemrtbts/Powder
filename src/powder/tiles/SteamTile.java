package powder.tiles;

import powder.util.ColorRandomizer;
import powder.World;

import java.awt.*;

public class SteamTile extends GasTile {
    public SteamTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(200, 200, 200), 25));
    }
}
