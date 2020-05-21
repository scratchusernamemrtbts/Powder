package powder;

import powder.position.Position;
import powder.tiles.*;
import powder.ui.Keys;
import powder.ui.Renderer;
import powder.ui.WindowFrame;

import java.awt.*;

public class Powder {

    private final World world = new World();
    private final WindowFrame windowFrame = new WindowFrame();
    private final Renderer renderer = new Renderer(this);
    private int frame = 0;

    private long timer = System.currentTimeMillis();

    public Powder() {
//        ThreadManager.runLoop(renderer::render);
//        ThreadManager.runLoop(this::update);
    }

    public void start() {
        windowFrame.setVisible(true);
        while (true) {
            try {
                update();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            if (!windowFrame.isKeyPressed(Keys.SPACE)) {
                try {
                    Thread.sleep(14);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void placeTile(Position position) {
        if (position.getX() <= 0 || position.getY() <= 0 || position.getX() >= world.getWidth() - 1 || position.getY() >= world.getHeight() - 1) {
            return;
        }
        Tile tile;
        if (windowFrame.isKeyPressed(Keys.A)) {
            tile = new WaterTile(world);
        } else if (windowFrame.isKeyPressed(Keys.B)) {
            tile = new BedrockTile(world);
        } else if (windowFrame.isKeyPressed(Keys.C)) {
            tile = new FuseTile(world);
        } else if (windowFrame.isKeyPressed(Keys.D)) {
            tile = new GunpowderTile(world);
        } else if (windowFrame.isKeyPressed(Keys.F)) {
            tile = new FireTile(world);
        } else if (windowFrame.isKeyPressed(Keys.G)) {
            tile = new DirtTile(world);
        } else if (windowFrame.isKeyPressed(Keys.H)) {
            tile = new GrassTile(world);
        } else if (windowFrame.isKeyPressed(Keys.I)) {
            tile = new GravelTile(world);
        } else if (windowFrame.isKeyPressed(Keys.J)) {
            tile = new OilTile(world);
        } else {
            tile = new SandTile(world);
        }
        world.setTile(position, tile);
    }

    public void update() {
        frame++;

        Point mousePosition = windowFrame.getMousePosition();
        if (mousePosition != null) {
            if (windowFrame.isMouseDown()) {
                Position p = new Position(mousePosition.x / renderer.getTileWidth(), (windowFrame.getSize().height - mousePosition.y) / renderer.getTileHeight());
                placeTile(p);
                placeTile(p.left());
                placeTile(p.left().down());
                placeTile(p.down());
            }
        }

//        long start = System.currentTimeMillis();
        world.update();
//        long startRender = System.currentTimeMillis();
        renderer.render();
//        long end = System.currentTimeMillis();

        if (System.currentTimeMillis() - timer > 1000) {
            timer = System.currentTimeMillis();
            windowFrame.setTitle(frame + " fps");
            frame = 0;
        }

//        System.out.println("update in: " + (startRender - start));
//        System.out.println("render in: " + (end - startRender));
    }

    public World getWorld() {
        return world;
    }

    public WindowFrame getWindowFrame() {
        return windowFrame;
    }
}
