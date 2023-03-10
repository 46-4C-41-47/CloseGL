package Application;

import Geometry.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


public class DrawTask extends TimerTask {
    private final Canvas canvas;
    private final Mesh cube;
    private final Screen screen;
    //private final Matrix projMatrix = Main.initProjectionMatrix();
    private float elapsedTime = (float) ((1000 / (float) Parameters.FRAME_RATE) * 0.001) * 72f;


    public DrawTask(Canvas canvas, Mesh cube, Screen screen) {
        this.canvas = canvas;
        this.cube   = cube;
        this.screen = screen;
    }


    private Point3D projectPoint(Point3D point) {
        ParametricLine line = new ParametricLine(screen.cam(), point);
        Vector3D normalVector = screen.screenPlane().getNormalVector();
        float a = normalVector.x, b = normalVector.y, c = normalVector.z, d = screen.screenPlane().getD();
        float t =
                (-(a * point.x) -(b * point.x) -(c * point.x) -d)
                /
                (a * line.getVector().x + b * line.getVector().y + c * line.getVector().z);
        //System.out.print((-(a * point.x) -(b * point.x) -(c * point.x) -d) + " / ");
        //System.out.println(a * line.getVector().x + b * line.getVector().y + c * line.getVector().z);
        //System.out.println(t);

        float x = line.getPoint().x + line.getVector().x * t;
        float y = line.getPoint().y + line.getVector().y * t;

        return new Point3D(x, y, 0f);
    }


    private Triangle projectTriangle(Triangle triangleToProject) {
        Triangle projectedTriangle = new Triangle();

        for (int i = 0; i < triangleToProject.points.length; i++) {
            projectedTriangle.points[i] = projectPoint(triangleToProject.points[i]);
        }

        return projectedTriangle;
    }


    private Mesh computeProjection(Mesh object) {
        Mesh projectedMesh = new Mesh();

        for (Triangle triangle : object.getTriangles()) {
            Triangle projectedTriangle = projectTriangle(triangle);
            projectedTriangle = scalePoints(projectedTriangle);
            projectedMesh.add(projectedTriangle);
        }

        return projectedMesh;
    }


    private Mesh shiftMesh(Mesh mesh, float offset) {
        Mesh shiftedMesh = new Mesh();

        for (Triangle triangle : mesh.getTriangles()) {
            shiftedMesh.add(triangle.shiftZ(offset));
        }

        return shiftedMesh;
    }


    private Mesh rotateX(Mesh object, float theta) {
        Mesh rotatedObject = new Mesh();
        Matrix rotationMatrixX = new Matrix();

        rotationMatrixX.matrix[0][0] = 1;
        rotationMatrixX.matrix[1][1] = (float)  Math.cos(theta);
        rotationMatrixX.matrix[1][2] = (float)  Math.sin(theta);
        rotationMatrixX.matrix[2][1] = (float) -Math.sin(theta);
        rotationMatrixX.matrix[2][2] = (float)  Math.cos(theta);
        rotationMatrixX.matrix[3][3] = 1;

        for (Triangle triangle : object.getTriangles()) {
            Triangle rotatedTriangle = new Triangle(
                    Main.multiplyMatrixPoint(triangle.points[0], rotationMatrixX),
                    Main.multiplyMatrixPoint(triangle.points[1], rotationMatrixX),
                    Main.multiplyMatrixPoint(triangle.points[2], rotationMatrixX)
            );

            rotatedObject.add(rotatedTriangle);
        }

        return rotatedObject;
    }


    private Mesh rotateY(Mesh object, float theta) {
        Mesh rotatedObject = new Mesh();
        Matrix rotationMatrixY = new Matrix();

        rotationMatrixY.matrix[0][0] = (float)  Math.cos(theta);
        rotationMatrixY.matrix[1][1] = 1;
        rotationMatrixY.matrix[2][0] = (float)  Math.sin(theta);
        rotationMatrixY.matrix[0][2] = (float) -Math.sin(theta);
        rotationMatrixY.matrix[2][2] = (float)  Math.cos(theta);
        rotationMatrixY.matrix[3][3] = 1;

        for (Triangle triangle : object.getTriangles()) {
            Triangle rotatedTriangle = new Triangle(
                    Main.multiplyMatrixPoint(triangle.points[0], rotationMatrixY),
                    Main.multiplyMatrixPoint(triangle.points[1], rotationMatrixY),
                    Main.multiplyMatrixPoint(triangle.points[2], rotationMatrixY)
            );

            rotatedObject.add(rotatedTriangle);
        }

        return rotatedObject;
    }


    private Mesh rotateZ(Mesh object, float theta) {
        Mesh rotatedObject = new Mesh();
        Matrix rotationMatrixZ = new Matrix();

        rotationMatrixZ.matrix[0][0] = (float)  Math.cos(theta);
        rotationMatrixZ.matrix[0][1] = (float)  Math.sin(theta);
        rotationMatrixZ.matrix[1][0] = (float) -Math.sin(theta);
        rotationMatrixZ.matrix[1][1] = (float)  Math.cos(theta);
        rotationMatrixZ.matrix[2][2] = 1;
        rotationMatrixZ.matrix[3][3] = 1;

        for (Triangle triangle : object.getTriangles()) {
            Triangle rotatedTriangle = new Triangle(
                    Main.multiplyMatrixPoint(triangle.points[0], rotationMatrixZ),
                    Main.multiplyMatrixPoint(triangle.points[1], rotationMatrixZ),
                    Main.multiplyMatrixPoint(triangle.points[2], rotationMatrixZ)
            );

            rotatedObject.add(rotatedTriangle);
        }

        return rotatedObject;
    }


    public Triangle scalePoints(Triangle triangle) {
        for (Point3D point: triangle.points) {
            point.x *= (float) Parameters.FRAME_SIZE.width  / Parameters.SCALE_FACTOR;
            point.x += (float) Parameters.FRAME_SIZE.width  / 2;
            point.y *= (float) Parameters.FRAME_SIZE.height / Parameters.SCALE_FACTOR;
            point.y += (float) Parameters.FRAME_SIZE.height / 2;
        }

        return triangle;
    }


    @Override
    public void run() {
        elapsedTime += (1000 / (float) Parameters.FRAME_RATE) * 0.0005;
        //System.out.println(cube);
        Mesh modifiedCube = new Mesh(cube);
        modifiedCube = shiftMesh(modifiedCube,3);
        modifiedCube = rotateX(modifiedCube, elapsedTime);
        modifiedCube = rotateY(modifiedCube, elapsedTime * 0.33f);
        modifiedCube = rotateZ(modifiedCube, elapsedTime * 0.66f);

        List<Mesh> projectedObjects = new ArrayList<>();
        projectedObjects.add(computeProjection(modifiedCube));

        canvas.draw(projectedObjects);
        canvas.paintComponent(canvas.getGraphics());
    }
}