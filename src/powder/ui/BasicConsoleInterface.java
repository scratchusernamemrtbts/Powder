package powder.ui;

import powder.Powder;
import powder.World;
import powder.tiles.Tile;

import javax.xml.bind.helpers.AbstractUnmarshallerImpl;
import java.awt.*;

public class BasicConsoleInterface implements Interface {
    private final Powder powder;

    public BasicConsoleInterface(Powder powder) {
        this.powder = powder;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        World world = powder.getWorld();
        StringBuilder output = new StringBuilder();
        for (int y = world.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < world.getWidth(); x++) {
                Tile tile = world.getTile(x, y);
                output.append(tile.getChar());
            }
            output.append("\n");
        }
        System.out.print(output);
        System.out.flush();
    }

    @Override
    public boolean isTurbo() {
        return false;
    }

    @Override
    public boolean isPaused() {
        return false;
    }
}
