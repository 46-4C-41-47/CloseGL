package Application;

import Geometry.Matrix;
import Geometry.Mesh;
import Geometry.Point3D;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame();
        Timer timer = new Timer(true);
        DrawTask drawTask = new DrawTask(frame.getCanvas(), initCube());
        timer.scheduleAtFixedRate(drawTask, 0, 1000 / Parameters.FRAME_RATE);
    }


    public static Point3D multiplyMatrixPoint(Point3D p, Matrix m) {
        Point3D outputPoint = new Point3D(
                p.x * m.matrix[0][0] + p.y * m.matrix[1][0] + p.z * m.matrix[2][0] + m.matrix[3][0],
                p.x * m.matrix[0][1] + p.y * m.matrix[1][1] + p.z * m.matrix[2][1] + m.matrix[3][1],
                p.x * m.matrix[0][2] + p.y * m.matrix[1][2] + p.z * m.matrix[2][2] + m.matrix[3][2]
        );

        float w = p.x * m.matrix[0][3] + p.y * m.matrix[1][3] + p.z * m.matrix[2][3] + m.matrix[3][3];

        if (w != 0f) {
            outputPoint.x /= w;
            outputPoint.y /= w;
            outputPoint.z /= w;
        }

        return outputPoint;
    }


    public static Matrix initProjectionMatrix() {
        Matrix m = new Matrix();

        float screenRatio = (float) (Parameters.FRAME_SIZE.width / Parameters.FRAME_SIZE.height);
        float fovRad = 1 / (float) Math.tan(Parameters.FOV * 0.5f / 180.0f * 3.14159f);

        m.matrix[0][0] = screenRatio * fovRad;
        m.matrix[1][1] = fovRad;
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