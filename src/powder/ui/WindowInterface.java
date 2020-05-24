package powder.ui;

import powder.Powder;
import powder.World;
import powder.position.Position;
import powder.tiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WindowInterface extends JFrame implements MouseListener, KeyListener, Interface {
    private boolean mouseDown = false;
    private boolean mouseOver = false;
    private final boolean[] keys = new boolean[256];
    private final Powder powder;
    private final World world;
    private final FrameRenderer renderer;
    private long timer = System.currentTimeMillis();
    private int frame = 0;
    private final String title;

    public WindowInterface(Powder powder) {
        super("Powder");
        this.title = getTitle();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        addKeyListener(this);
        setResizable(false);
        this.powder = powder;
        this.world = powder.getWorld();
        this.renderer = new FrameRenderer(this, powder);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseOver = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseOver = false;
    }

    public boolean isMouseDown() {
        return mouseDown;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public boolean isKeyPressed(int key) {
        return keys[key];
    }

    @Override
    public void render() {
        frame++;

        renderer.render();

        if (System.currentTimeMillis() - timer > 1000) {
            setTitle(title + " (" + frame + " fps)");
            timer = System.currentTimeMillis();
            frame = 0;
        }
    }

    @Override
    public boolean isTurbo() {
        return isKeyPressed(Keys.SPACE);
    }

    private void placeTile(Position position) {
        if (position.getX() <= 0 || position.getY() <= 0 || position.getX() >= world.getWidth() - 1 || position.getY() >= world.getHeight() - 1) {
            return;
        }
        Tile tile;
        if (isKeyPressed(Keys.A)) {
            tile = new WaterTile(world);
        } else if (isKeyPressed(Keys.B)) {
            tile = new BedrockTile(world);
        } else if (isKeyPressed(Keys.C)) {
            tile = new FuseTile(world);
        } else if (isKeyPressed(Keys.D)) {
            tile = new GunpowderTile(world);
        } else if (isKeyPressed(Keys.F)) {
            tile = new FireTile(world);
        } else if (isKeyPressed(Keys.G)) {
            tile = new DirtTile(world);
        } else if (isKeyPressed(Keys.H)) {
            tile = new GrassTile(world);
        } else if (isKeyPressed(Keys.I)) {
            tile = new GravelTile(world);
        } else if (isKeyPressed(Keys.J)) {
            tile = new OilTile(world);
        } else if (isKeyPressed(Keys.V)) {
            tile = new VirusTile(world);
        } else if (isKeyPressed(Keys.M)) {
            tile = new MercuryTile(world);
        } else if (isKeyPressed(Keys.N)) {
            tile = new BlueFireTile(world);
        } else if (isKeyPressed(Keys.O)) {
            tile = new MethaneTile(world);
        } else if (isKeyPressed(Keys.P)) {
            tile = new BleachTile(world);
        } else if (isKeyPressed(Keys.E)) {
            tile = new AirTile(world);
        } else {
            tile = new SandTile(world);
        }
        world.setTile(position, tile);
    }

    @Override
    public void start() {
        setVisible(true);
    }

    @Override
    public void update() {
        Point mousePosition = getMousePosition();
        if (mousePosition != null) {
            if (isMouseDown()) {
                Position p = new Position(mousePosition.x / renderer.getTileWidth(), (getSize().height - mousePosition.y) / renderer.getTileHeight());
                placeTile(p);
                placeTile(p.left());
                placeTile(p.left().down());
                placeTile(p.down());
            }
        }
    }
}
