package App;

import App.Math.Mesh;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


public class DrawTask extends TimerTask {
    private static final double MOVE_SPEED = Parameters.MOVEMENT_SPEED * (1000f / Parameters.FRAME_RATE);
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int HALF_SCREEN_WIDTH = (SCREEN_SIZE.width) / 2, HALF_SCREEN_HEIGHT = (SCREEN_SIZE.height) / 2;
    private final Frame frame;
    private final Canvas canvas;
    private double startingTime = 0, fps = Parameters.FRAME_RATE, fpsSum = 0, thetaX = 0d, thetaY = 0d;
    private int frameCount = 0;
    private Robot robot;
    private Mesh object;
    private List<Mesh> objectsToDraw = new ArrayList<>();
    public boolean
            zKeyPressed = false,
            qKeyPressed = false,
            sKeyPressed = false,
            dKeyPressed = false,
            spaceKeyPressed = false,
            shiftKeyPressed = false;


    public DrawTask(Frame frame, Mesh object) {
        this.frame = frame;
        this.canvas = frame.getCanvas();
        this.object = object;
        this.object.translate(2, -15);

        try {
            robot = new Robot();
            robot.mouseMove(HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT);

        } catch (Exception e) {
            System.err.println("Low level mouse control is not allowed");
        }

        objectsToDraw.add(this.object);
    }


    private void handleKeyEvent() {
        if (zKeyPressed) {
            object.translate(2, MOVE_SPEED);
        }

        if (qKeyPressed) {
            object.translate(0, -MOVE_SPEED);
        }

        if (sKeyPressed) {
            object.translate(2, -MOVE_SPEED);
        }

        if (dKeyPressed) {
            object.translate(0, MOVE_SPEED);
        }

        if (spaceKeyPressed) {
            object.translate(1, -MOVE_SPEED);
        }

        if (shiftKeyPressed) {
            object.translate(1, MOVE_SPEED);
        }
    }


    private void handleMouseMotion() {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point mousePosition = pointerInfo.getLocation();

        thetaX -= Parameters.VERTICAL_MOUSE_SENSITIVITY
                * 2 * Math.PI
                * ((double) (mousePosition.y - HALF_SCREEN_HEIGHT) / HALF_SCREEN_HEIGHT);
        thetaY += Parameters.HORIZONTAL_MOUSE_SENSITIVITY
                * 2 * Math.PI
                * ((double) (mousePosition.x - HALF_SCREEN_WIDTH) / HALF_SCREEN_WIDTH);

        robot.mouseMove(HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT);
    }


    private void printFrameRate() {
        frameCount += 1;

        if (Parameters.FRAME_RATE * 0.5 <= frameCount) {
            fps = fpsSum / Parameters.FRAME_RATE * 0.5;

            if (10000 < fps) {
                fps = 10000;
            }

            frameCount = 0;
            fpsSum = 0;
        }

        fpsSum += 1000 / (System.currentTimeMillis() - startingTime);

        frame.setTitle("FPS : " + String.format("%.2f", fps));
    }


    @Override
    public void run() {
        startingTime = System.currentTimeMillis();

        if (frame.isActive()) {
            handleKeyEvent();
            handleMouseMotion();
        }

        canvas.draw(object.render(
                Parameters.Camera,
                Parameters.Light,
                Parameters.PROJECTION_MATRIX,
                Parameters.FRAME_SIZE,
                Parameters.Z_NEAR,
                thetaX,
                thetaY)
        );

        printFrameRate();
    }
}