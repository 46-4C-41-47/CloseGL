package App.ControlHandlers;

import App.DrawTask;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyEventHandler implements KeyListener {
    private final int key;
    private final DrawTask task;


    public KeyEventHandler(DrawTask task, int key) {
        this.task = task;
        this.key = key;
    }


    private void setValue(boolean value) {
        switch (key) {
            case KeyEvent.VK_Z:
                task.zKeyPressed = value;
                break;

            case KeyEvent.VK_Q:
                task.qKeyPressed = value;
                break;

            case KeyEvent.VK_S:
                task.sKeyPressed = value;
                break;

            case KeyEvent.VK_D:
                task.dKeyPressed = value;
                break;

            case KeyEvent.VK_SPACE:
                task.spaceKeyPressed = value;
                break;

            case KeyEvent.VK_SHIFT:
                task.shiftKeyPressed = value;
                break;

            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;

            default:
                System.err.println("A key as an event but no action");
                break;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == key) {
            setValue(true);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == key) {
            setValue(false);
        }
    }
}
