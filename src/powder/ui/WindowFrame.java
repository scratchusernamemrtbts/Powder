package powder.ui;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WindowFrame extends JFrame implements MouseListener, KeyListener {
    private boolean mouseDown = false;
    private boolean mouseOver = false;
    private final boolean[] keys = new boolean[256];

    public WindowFrame() {
        super("powder.Powder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        addKeyListener(this);
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
}
