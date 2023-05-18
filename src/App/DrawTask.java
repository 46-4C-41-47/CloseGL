package App;

import App.Math.Mesh;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


public class DrawTask extends TimerTask {
    private static final double ROTATION_SPEED = 2;
    private final Frame frame;
    private final Canvas canvas;
    private double startingTime = 0, elapsedTime = 0;
    private double fps = Parameters.FRAME_RATE, avgFps = Parameters.FRAME_RATE;
    private Mesh object;
    private List<Mesh> objectsToDraw = new ArrayList<>();


    public DrawTask(Frame frame, Mesh object) {
        this.frame = frame;
        this.canvas = frame.getCanvas();
        this.object = object.rotateX(45).rotateY(45);
        objectsToDraw.add(this.object);
    }


    public void rotateForward() {
        object = object.rotateX(ROTATION_SPEED);
    }


    public void rotateBackward() {
        object = object.rotateX(-ROTATION_SPEED);
    }


    public void rotateLeft() {
        object = object.rotateY(ROTATION_SPEED);
    }


    public void rotateRight() {
        object = object.rotateY(-ROTATION_SPEED);
    }


    public void rotateClockwise() {
        object = object.rotateZ(ROTATION_SPEED);
    }


    public void rotateTrigonometric() {
        object = object.rotateZ(-ROTATION_SPEED);
    }


    @Override
    public void run() {
        startingTime = System.currentTimeMillis();

        canvas.draw(object
                .translate(2, 8)
                .render(Parameters.Camera, Parameters.Light, Parameters.PROJECTION_MATRIX, Parameters.FRAME_SIZE)
        );

        avgFps = (avgFps + fps) / 2;
        fps = 1000 / (System.currentTimeMillis() - startingTime);

        if (1000 < fps) {
            fps = 1000;
        }

        frame.setTitle("FPS : " + String.format("%.2f", fps) + "     AVG : " + String.format("%.2f", avgFps));
    }
}