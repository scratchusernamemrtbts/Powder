package powder.ui;

import powder.*;
import powder.position.Position;
import powder.tiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

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
    private boolean paused = false;
    private boolean turbo = false;
    private final Map<String, JMenuItem> menuItemKeyMap = new HashMap<>();
    private TileDatabase.TileInfo currentTileInfo;

    public WindowInterface(Powder powder) {
        super("Powder");
        this.title = getTitle();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        addKeyListener(this);
        setResizable(false);
        setIconImage(ImageLoader.loadImage("icon.png"));
        this.powder = powder;
        this.world = powder.getWorld();
        this.renderer = new FrameRenderer(this, powder);
        addMenuBar();
        pack();
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu world = new JMenu("World");
        menuBar.add(world);

        JMenuItem pause = new JCheckBoxMenuItem("Pause");
        pause.addActionListener(e -> this.paused = pause.isSelected());
        world.add(pause);

        JMenuItem turbo = new JCheckBoxMenuItem("Turbo Mode");
        turbo.addActionListener(e -> this.turbo = turbo.isSelected());
        world.add(turbo);

        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to reset the world?",
                    "Are you sure?",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirmation == 0) {
                this.world.reset();
            }
        });
        world.add(reset);

        JMenu tilesMenu = new JMenu("Tiles");
        menuBar.add(tilesMenu);

        ButtonGroup tileGroup = new ButtonGroup();
        for (TileDatabase.TileInfo tileInfo : TileDatabase.tiles) {
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(tileInfo.getName());
            tileGroup.add(menuItem);
            tilesMenu.add(menuItem);

            Color color = tileInfo.getColor();
            if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
                menuItem.setForeground(new Color(0, 0, 0));
            } else {
                menuItem.setForeground(new Color(255, 255, 255));
            }
            menuItem.setBackground(color);

            menuItem.addActionListener(new SelectTileAction(tileInfo, this));

            if (tileInfo.getKey() != null) {
                if (menuItemKeyMap.containsKey(tileInfo.getKey())) {
                    throw new RuntimeException("Reused key " + tileInfo.getKey());
                }
                menuItemKeyMap.put(tileInfo.getKey(), menuItem);
            }

            if (tileInfo.getName().equals("Sand")) {
                menuItem.setSelected(true);
                currentTileInfo = tileInfo;
            }
        }

        JMenu help = new JMenu("Help");
        JMenuItem shortcuts = new JMenuItem("Keyboard Shortcuts");
        shortcuts.addActionListener(e -> JOptionPane.showMessageDialog(this, generateHelpText()));
        help.add(shortcuts);
        menuBar.add(help);

        setJMenuBar(menuBar);
    }

    private String generateHelpText() {
        StringBuilder result = new StringBuilder();
        result.append("space = Turbo\n");
        for (TileDatabase.TileInfo tileInfo : TileDatabase.tiles) {
            if (tileInfo.getKey() != null) {
                result.append(tileInfo.getKey()).append(" = ").append(tileInfo.getName()).append("\n");
            }
        }
        return result.toString();
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
        String key = String.valueOf(e.getKeyChar());
        if (menuItemKeyMap.containsKey(key)) {
            JMenuItem menuItem = menuItemKeyMap.get(key);
            if (!menuItem.isSelected()) {
                menuItem.doClick();
            }
        }
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
        return turbo || isKeyPressed(KeyEvent.VK_SPACE);
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    private void placeTile(Position position) {
        if (position.getX() <= 0 || position.getY() <= 0 || position.getX() >= world.getWidth() - 1 || position.getY() >= world.getHeight() - 1) {
            return;
        }
        Tile tile = currentTileInfo.construct(world);
        world.setTile(position, tile);
    }

    @Override
    public void start() {
        setVisible(true);
    }

    @Override
    public void update() {
        if (isMouseOver() && isMouseDown()) {
            Point mousePosition = renderer.getMousePosition();
            if (mousePosition != null) {
                Position p = new Position(mousePosition.x / renderer.getTileWidth(), (renderer.getSize().height - mousePosition.y) / renderer.getTileHeight());
                p = p.right();
                p = p.up();
//                p = p.up();
//                p = p.up();
//                p = p.up();
                placeTile(p);
                placeTile(p.left());
                placeTile(p.left().down());
                placeTile(p.down());
            }
        }
    }

    public void setCurrentTileInfo(TileDatabase.TileInfo currentTileInfo) {
        this.currentTileInfo = currentTileInfo;
    }

    public TileDatabase.TileInfo getCurrentTileInfo() {
        return currentTileInfo;
    }
}
