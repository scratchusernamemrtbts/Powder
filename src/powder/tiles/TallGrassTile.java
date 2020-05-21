package powder.tiles;

import powder.World;
import powder.util.ColorRandomizer;
import powder.util.Randomizer;

import java.awt.*;

public class TallGrassTile extends SolidTile {
    private int life;
    private boolean grown = false;

    public TallGrassTile(World world, int life) {
        super(world);
        setColor(ColorRandomizer.randomize(new Color(0, 200, 0), 0, 50, 0));
        this.life = life;
    }

    private boolean canGrow() {
        return !grown && life > 0;
    }

    @Override
    public boolean blocksSunlight() {
        return false;
    }

    @Override
    public double getFlammability() {
        return 0.25;
    }

    private void destroy() {
        getWorld().setTile(getPosition(), new AirTile(getWorld()));

//        if (getWorld().getTile(getPosition().up()) instanceof TallGrassTile) {
    }

    private boolean toleratesAsSide(Tile tile) {
        return tile.isAir() || toleratesAsBottom(tile);
    }

    private boolean toleratesAsBottom(Tile tile) {
        return tile instanceof GrassTile || tile instanceof TallGrassTile;
    }

    @Override
    public void update() {
        if (!toleratesAsSide(getWorld().getTile(getPosition().left())) ||
                !toleratesAsSide(getWorld().getTile(getPosition().right())) ||
                !toleratesAsBottom(getWorld().getTile(getPosition().down())) ||
                !toleratesAsSide(getWorld().getTile(getPosition().up()))
        ) {
            destroy();
            return;
        }


        if (canGrow() && Randomizer.nextDouble() < 0.1) {
            if (getWorld().getTile(getPosition().up()).isAir()) {
                getWorld().setTile(getPosition().up(), new TallGrassTile(getWorld(), life - 1));
                grown = true;
            }
        }
        setActive(canGrow());
    }
}
