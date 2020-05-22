package powder.tiles;

import powder.World;
import powder.util.ColorRandomizer;

import java.awt.*;

public class BleachTile extends LiquidTile {
    public BleachTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(200, 200, 200), 20));
    }

    @Override
    public double getBuoyancy() {
        return 0.9;
    }

    @Override
    public void update() {
        super.update();

        if (getWorld().getTile(getPosition().up()) instanceof VirusTile) {
            getWorld().setTile(getPosition().up(), new AirTile(getWorld()));
            getWorld().setTile(getPosition(), new WaterTile(getWorld()));
        }
        if (getWorld().getTile(getPosition().down()) instanceof VirusTile) {
            getWorld().setTile(getPosition().down(), new AirTile(getWorld()));
            getWorld().setTile(getPosition(), new WaterTile(getWorld()));
        }
        if (getWorld().getTile(getPosition().left()) instanceof VirusTile) {
            getWorld().setTile(getPosition().left(), new AirTile(getWorld()));
            getWorld().setTile(getPosition(), new WaterTile(getWorld()));
        }
        if (getWorld().getTile(getPosition().right()) instanceof VirusTile) {
            getWorld().setTile(getPosition().right(), new AirTile(getWorld()));
            getWorld().setTile(getPosition(), new WaterTile(getWorld()));
        }
    }
}
