package Application;

import Geometry.Matrix;
import Geometry.Mesh;
import Geometry.Point3D;
import Geometry.Triangle;

import java.util.ArrayList;
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


    /*private Mesh computeProjection(Mesh object) {
        Mesh projectedObject = new Mesh();

        for (Triangle triangle : object.getTriangles()) {
            Triangle projectedTriangle = new Triangle(
                    Main.multiplyMatrixPoint(triangle.points[0], projMatrix),
                    Main.multiplyMatrixPoint(triangle.points[1], projMatrix),
                    Main.multiplyMatrixPoint(triangle.points[2], projMatrix)
            );

            projectedTriangle = scalePoints(projectedTriangle);
            projectedObject.add(projectedTriangle);
        }

        return projectedObject;
    }*/


    private Mesh computeProjection(Mesh object) {
        Mesh projectedObject = new Mesh();

        for (Triangle triangle : object.getTriangles()) {
            Triangle projectedTriangle = new Triangle(
                    triangle.points[0],
                    triangle.points[1],
                    triangle.points[2]
            );

            projectedTriangle = scalePoints(projectedTriangle);
            projectedObject.add(projectedTriangle);
        }

        return projectedObject;
    }


    private Mesh computeRotation(Mesh object) {
        Mesh rotatedObject = new Mesh();
        Matrix rotMatZ = new Matrix(), rotMatX = new Matrix();

        // Rotation Z
        rotMatZ.matrix[0][0] = (float)  Math.cos(elapsedTime);
        rotMatZ.matrix[0][1] = (float)  Math.sin(elapsedTime);
        rotMatZ.matrix[1][0] = (float) -Math.sin(elapsedTime);
        rotMatZ.matrix[1][1] = (float)  Math.cos(elapsedTime);
        rotMatZ.matrix[2][2] = 1;
        rotMatZ.matrix[3][3] = 1;

        // Rotation X
        rotMatX.matrix[0][0] = 1;
        rotMatX.matrix[1][1] = (float)  Math.cos(elapsedTime * 0.5f);
        rotMatX.matrix[1][2] = (float)  Math.sin(elapsedTime * 0.5f);
        rotMatX.matrix[2][1] = (float) -Math.sin(elapsedTime * 0.5f);
        rotMatX.matrix[2][2] = (float)  Math.cos(elapsedTime * 0.5f);
        rotMatX.matrix[3][3] = 1;

        for (Triangle triangle : object.getTriangles()) {
            Triangle rotatedTriangle = new Triangle(
                    Main.multiplyMatrixPoint(triangle.points[0], rotMatZ),
                    Main.multiplyMatrixPoint(triangle.points[1], rotMatZ),
                    Main.multiplyMatrixPoint(triangle.points[2], rotMatZ)
            );

            rotatedTriangle = new Triangle(
                    Main.multiplyMatrixPoint(rotatedTriangle.points[0], rotMatX),
                    Main.multiplyMatrixPoint(rotatedTriangle.points[1], rotMatX),
                    Main.multiplyMatrixPoint(rotatedTriangle.points[2], rotMatX)
            );

            rotatedObject.add(rotatedTriangle);
        }

        return rotatedObject;
    }


    public Triangle scalePoints(Triangle triangle) {
        for (Point3D point: triangle.points) {
            point.x += 2;
            point.x *= (float) Parameters.FRAME_SIZE.width  / Parameters.SCALE_FACTOR;
            point.y += 2;
            point.y *= (float) Parameters.FRAME_SIZE.width / Parameters.SCALE_FACTOR;
        }

        return triangle;
    }


    @Override
    public void run() {
        elapsedTime += (1000 / (float) Parameters.FRAME_RATE) * 0.001;
        Mesh modifiedCube = new Mesh(cube);
        modifiedCube = computeRotation(modifiedCube);

        List<Mesh> projectedObjects = new ArrayList<>();
        projectedObjects.add(computeProjection(modifiedCube));

        canvas.draw(projectedObjects);
        canvas.paintComponent(canvas.getGraphics());
    }
}