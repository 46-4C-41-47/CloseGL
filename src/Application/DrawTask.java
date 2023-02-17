package Application;

import Geometry.Matrix;
import Geometry.Mesh;
import Geometry.Point3D;
import Geometry.Triangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

public class DrawTask extends TimerTask {
    private final Canvas canvas;
    private final Mesh cube;
    private final Matrix projMatrix = Main.initProjectionMatrix();
    private float elapsedTime = 0f;


    public DrawTask(Canvas ca, Mesh cu) {
        canvas = ca;
        cube = cu;
    }


    private Mesh computeProjection(Mesh object) {
        Mesh projectedObject = new Mesh();

        for (Triangle triangle : object.getTriangles()) {
            Point3D[] projPoints = new Point3D[]{
                    Main.multiplyMatrixPoint(triangle.points[0], projMatrix),
                    Main.multiplyMatrixPoint(triangle.points[1], projMatrix),
                    Main.multiplyMatrixPoint(triangle.points[2], projMatrix)
            };

            projPoints = scalePoints(projPoints);
            projectedObject.add(projPoints);
        }

        return projectedObject;
    }


    public Point3D[] scalePoints(Point3D[] projPoints) {
        for (Point3D point: projPoints) {
            point.x += 1;
            point.x *= (float) Parameters.FRAME_SIZE.width  / Parameters.SCALE_FACTOR;
            point.y += 1;
            point.y *= (float) Parameters.FRAME_SIZE.height / Parameters.SCALE_FACTOR;
        }

        return projPoints;
    }


    @Override
    public void run() {
        elapsedTime += 1000 / (float) Parameters.FRAME_RATE;

        List<Mesh> projectedObjects = new ArrayList<>();
        projectedObjects.add(computeProjection(cube));

        canvas.draw(projectedObjects);
        canvas.paintComponent(canvas.getGraphics());
    }
}