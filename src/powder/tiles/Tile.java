package powder.tiles;

import powder.World;
import powder.position.Position;

import java.awt.*;

public abstract class Tile {
    private final World world;
    private Position position;
    private boolean active = true;
    private Color color = new Color(255, 0, 255);

    public Tile(World world) {
        this.world = world;
    }

    public abstract void update();

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isSolid() {
        return true;
    }

    public boolean isAir() {
        return false;
    }

    public boolean hasGravity() {
        return false;
    }

    public double getFlammability() {
        return 0.0;
    }

    public boolean blocksSunlight() {
        return true;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public World getWorld() {
        return world;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
