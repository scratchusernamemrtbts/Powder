package powder.ui;

import powder.Powder;
import powder.World;
import powder.tiles.Tile;

import javax.swing.*;
import java.awt.*;

public class FrameRenderer extends JPanel {
    private final Powder powder;

    private final int TILE_WIDTH = 5;
    private final int TILE_HEIGHT = 5;

    public FrameRenderer(JFrame ui, Powder powder) {
        this.powder = powder;
        ui.add(this);
        ui.pack();
    }

    public Dimension getPreferredSize() {
        return new Dimension(powder.getWorld().getWidth() * TILE_WIDTH, powder.getWorld().getHeight() * TILE_HEIGHT);
    }

    public void paintComponent(Graphics g) {
        World world = powder.getWorld();
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                Tile tile = world.getTile(x, y);
                g.setColor(tile.getColor());
//                if (!tile.isActive()) {
//                    g.setColor(new Color(0, 0, 0));
//                }
                g.fillRect(x * TILE_WIDTH, (world.getHeight() - y - 1) * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
            }
        }
    }

    public void render() {
        repaint();
    }

    public int getTileWidth() {
        return TILE_WIDTH;
    }

    public int getTileHeight() {
        return TILE_HEIGHT;
    }
}
