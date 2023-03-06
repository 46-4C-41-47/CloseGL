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
    private float elapsedTime = 0f;


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
        float z = 0;

        return new Point3D(x, y, z);
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

/*
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
*/

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

            /*rotatedTriangle = new Triangle(
                    Main.multiplyMatrixPoint(rotatedTriangle.points[0], rotMatX),
                    Main.multiplyMatrixPoint(rotatedTriangle.points[1], rotMatX),
                    Main.multiplyMatrixPoint(rotatedTriangle.points[2], rotMatX)
            );*/

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
        //System.out.println(cube);
        Mesh modifiedCube = new Mesh(cube);
        modifiedCube = shiftMesh(modifiedCube,2);
        modifiedCube = computeRotation(modifiedCube);

        List<Mesh> projectedObjects = new ArrayList<>();
        projectedObjects.add(computeProjection(modifiedCube));

        canvas.draw(projectedObjects);
        canvas.paintComponent(canvas.getGraphics());
    }
}