package powder.tiles;

import powder.World;

import java.awt.*;

public class BedrockTile extends SolidTile {
    public BedrockTile(World world) {
        super(world);
        setColor(new Color(0, 0, 0));
    }
}