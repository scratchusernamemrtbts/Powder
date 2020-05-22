package powder.tiles;

import powder.World;
import powder.util.ColorRandomizer;

import java.awt.*;

public class OilTile extends LiquidTile {
    public OilTile(World world) {
        super(world);
        setColor(ColorRandomizer.uniform(new Color(20, 20, 20), 20));
    }

    @Override
    public void evaporate() {
        getWorld().setTile(getPosition(), new MethaneTile(getWorld()));
    }

    @Override
    public double getEvaporationChance() {
        return 0.01;
    }

    @Override
    public double getBuoyancy() {
        return 1.5;
    }

    @Override
    protected boolean floatsOn(Tile tile) {
        return tile instanceof WaterTile;
    }

    @Override
    public double getFlammability() {
        return 1;
    }
}
