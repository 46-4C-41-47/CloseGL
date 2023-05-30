package App;

import App.ControlHandlers.KeyEventHandler;
import App.J3D.Camera;
import App.Math.*;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;


public class Frame extends JFrame {
    private final Canvas canvas;
    private DrawTask task;


    public static void main(String[] args) {
        new Frame();
    }


    public Frame() {
        super();
        canvas = new Canvas(Parameters.FRAME_SIZE);

        setSize(Parameters.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        createTask();
        addKeyListener(new KeyEventHandler(task, KeyEvent.VK_Z));
        addKeyListener(new KeyEventHandler(task, KeyEvent.VK_Q));
        addKeyListener(new KeyEventHandler(task, KeyEvent.VK_S));
        addKeyListener(new KeyEventHandler(task, KeyEvent.VK_D));
        addKeyListener(new KeyEventHandler(task, KeyEvent.VK_SPACE));
        addKeyListener(new KeyEventHandler(task, KeyEvent.VK_SHIFT));
        addKeyListener(new KeyEventHandler(task, KeyEvent.VK_ESCAPE));
        add(canvas);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        getContentPane().setCursor(blankCursor);

        setVisible(true);
    }


    private void createTask() {
        Timer timer = new Timer(true);
        ObjReader objReader = new ObjReader(Parameters.pathToObj);
        task = new DrawTask(this, objReader.getObject());
        timer.scheduleAtFixedRate(task, 0, 1000 / Parameters.FRAME_RATE);
    }


    public Canvas getCanvas() {
        return canvas;
    }


    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
}
