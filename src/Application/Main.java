package Application;

import Geometry.Matrix;
import Geometry.Mesh;
import Geometry.Point3D;
import Geometry.Triangle;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame();
        Timer timer = new Timer(true);
        DrawTask drawTask = new DrawTask(frame.getCanvas());
        timer.scheduleAtFixedRate(drawTask, 0, 1000 / Parameters.FRAME_RATE);
    }


    private static Matrix initProjectionMatrix() {
        Matrix m = new Matrix();

        float screenRatio = (float) (Parameters.FRAME_SIZE.width / Parameters.FRAME_SIZE.height);

        m.matrix[0][0] = screenRatio * (float) Math.toRadians(Parameters.FOV);
        m.matrix[1][1] = (float) Math.toRadians(Parameters.FOV);
        m.matrix[2][2] = Parameters.ZF / (Parameters.ZF - Parameters.ZN);
        m.matrix[3][2] = 1f;
        m.matrix[2][3] = (-Parameters.ZF) * Parameters.ZN / (Parameters.ZF - Parameters.ZN);

        return m;
    }


    private static Mesh initCube() {
        Mesh cube = new Mesh();

        // SOUTH
        cube.add(new Point3D[] {
                new Point3D(0.0f, 0.0f, 0.0f),
                new Point3D(0.0f, 1.0f, 0.0f),
                new Point3D(1.0f, 1.0f, 0.0f)
        });
        cube.add(new Point3D[] {
                new Point3D(0.0f, 0.0f, 0.0f),
                new Point3D(1.0f, 1.0f, 0.0f),
                new Point3D(1.0f, 0.0f, 0.0f)
        });

        // EAST
        cube.add(new Point3D[] {
                new Point3D(1.0f, 0.0f, 0.0f),
                new Point3D(1.0f, 1.0f, 0.0f),
                new Point3D(1.0f, 1.0f, 1.0f)
        });
        cube.add(new Point3D[] {
                new Point3D(1.0f, 0.0f, 0.0f),
                new Point3D(1.0f, 1.0f, 1.0f),
                new Point3D(1.0f, 0.0f, 1.0f)
        });

        // NORTH
        cube.add(new Point3D[] {
                new Point3D(1.0f, 0.0f, 1.0f),
                new Point3D(1.0f, 1.0f, 1.0f),
                new Point3D(0.0f, 1.0f, 1.0f)
        });
        cube.add(new Point3D[] {
                new Point3D(1.0f, 0.0f, 1.0f),
                new Point3D(0.0f, 1.0f, 1.0f),
                new Point3D(0.0f, 0.0f, 1.0f)
        });

        // WEST
        cube.add(new Point3D[] {
                new Point3D(0.0f, 0.0f, 1.0f),
                new Point3D(0.0f, 1.0f, 1.0f),
                new Point3D(0.0f, 1.0f, 0.0f)
        });
        cube.add(new Point3D[] {
                new Point3D(0.0f, 0.0f, 1.0f),
                new Point3D(0.0f, 1.0f, 0.0f),
                new Point3D(0.0f, 0.0f, 0.0f)
        });

        // TOP
        cube.add(new Point3D[] {
                new Point3D(0.0f, 1.0f, 0.0f),
                new Point3D(0.0f, 1.0f, 1.0f),
                new Point3D(1.0f, 1.0f, 1.0f)
        });
        cube.add(new Point3D[] {
                new Point3D(0.0f, 1.0f, 0.0f),
                new Point3D(1.0f, 1.0f, 1.0f),
                new Point3D(1.0f, 1.0f, 0.0f)
        });

        // BOTTOM
        cube.add(new Point3D[] {
                new Point3D(1.0f, 0.0f, 1.0f),
                new Point3D(0.0f, 0.0f, 1.0f),
                new Point3D(0.0f, 0.0f, 0.0f)
        });
        cube.add(new Point3D[] {
                new Point3D(1.0f, 0.0f, 1.0f),
                new Point3D(0.0f, 0.0f, 0.0f),
                new Point3D(1.0f, 0.0f, 0.0f)
        });

        return cube;
    }
}