package powder.ui;

import powder.Powder;
import powder.World;
import powder.tiles.Tile;

import java.awt.*;

public class ConsoleInterface implements Interface {
    private final Powder powder;

    public ConsoleInterface(Powder powder) {
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
        int COLOR_MASK = 0b111100001111000011110000;

        World world = powder.getWorld();
        StringBuilder output = new StringBuilder();
        for (int y = world.getHeight() - 1; y >= 0; y--) {
            Color lastColor = null;
            for (int x = 0; x < world.getWidth(); x++) {
                Tile tile = world.getTile(x, y);
                if (tile.isAir()) {
                    if (lastColor == null) {
                        output.append(" ");
                    } else {
                        output.append("\u001B[0m ");
                    }
                    lastColor = null;
                } else {
                    Color color = new Color(tile.getColor().getRGB() & COLOR_MASK);
                    if (color.equals(lastColor)) {
                        output.append(" ");
                    } else {
                        output.append(String.format("\u001B[48;2;%d;%d;%dm ", color.getRed(), color.getGreen(), color.getBlue()));
                    }
                    lastColor = color;
                }
            }
            output.append("\u001B[0m\n");
        }
        System.out.print(output);
        System.out.flush();
    }

    @Override
    public boolean isTurbo() {
        return false;
    }
}
