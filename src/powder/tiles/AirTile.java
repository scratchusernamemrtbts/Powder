package powder.tiles;

import powder.World;

import java.awt.*;

public class AirTile extends Tile {
    public AirTile(World world) {
        super(world);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isAir() {
        return true;
    }

    @Override
    public Color getColor() {
        return new Color(255, 255, 255);
    }

    @Override
    public boolean hasGravity() {
        return false;
    }

    @Override
    public boolean blocksSunlight() {
        return false;
    }

    @Override
    public void update() {
        setActive(false);
    }

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public char getChar() {
        return ' ';
    }
}
