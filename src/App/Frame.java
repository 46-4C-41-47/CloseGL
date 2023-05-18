package App;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;


public class Frame extends JFrame {
    private final Canvas canvas;
    private DrawTask task;


    public static void main(String[] args) {
        new Frame();
        //ObjReader objReader = new ObjReader(Parameters.pathToObj);
        //System.out.println(objReader.getObject());

        /*String s = "2909 2921 2939";

        for (String str : s.split(" ")) {
            System.out.println(Integer.parseInt(str));
        }*/
    }


    public Frame() {
        super();
        canvas = new Canvas(Parameters.FRAME_SIZE);

        setSize(Parameters.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        createTask();
        addKeyListener(createKeyListener());
        setTitle("title");
        add(canvas);

        setVisible(true);
    }


    private KeyListener createKeyListener() {
        return new KeyListener() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_Z) {
                    task.rotateForward();

                } else if (event.getKeyCode() == KeyEvent.VK_Q) {
                    task.rotateLeft();

                } else if (event.getKeyCode() == KeyEvent.VK_S) {
                    task.rotateBackward();

                } else if (event.getKeyCode() == KeyEvent.VK_D) {
                    task.rotateRight();

                } else if (event.getKeyCode() == KeyEvent.VK_A) {
                    task.rotateClockwise();

                } else if (event.getKeyCode() == KeyEvent.VK_E) {
                    task.rotateTrigonometric();
                }
            }

            @Override
            public void keyReleased(KeyEvent event) {}

            @Override
            public void keyTyped(KeyEvent event) {}
        };
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
}
