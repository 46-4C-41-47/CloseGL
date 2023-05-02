package Math;

import App.Parameters;

import java.util.ArrayList;
import java.util.List;


public class Mesh {
    private static final int NO_DIMENSION = -1;
    private final List<Triangle> triangles;
    private int dimension = NO_DIMENSION;


    public Mesh() {
        triangles = new ArrayList<>();
    }


    public Mesh(Mesh inputMesh) {
        triangles = new ArrayList<>();
        triangles.addAll(inputMesh.getTriangles());
    }


    public void add(Triangle triangle) {
        if (getDimension() == NO_DIMENSION) {
            triangles.add(triangle);

        } else if (this.getDimension() == triangle.getDimension()) {
            triangles.add(triangle);

        } else {
            System.out.println(this.getDimension() + ", " + triangle.getDimension());
            throw new UnsupportedOperationException("All triangle of a mesh should have the same dimension");
        }
    }


    public void add(Vertex[] points) {
        Triangle triangle = new Triangle(points);
        triangles.add(triangle);
    }


    public int getDimension() {
        if (triangles.size() == 0) {
            return NO_DIMENSION;

        } else {
            if (dimension == NO_DIMENSION) {
                int dimension = triangles.get(0).getDimension();

                for (Triangle triangle : triangles) {
                    if (triangle.getDimension() != dimension) {
                        throw new UnsupportedOperationException("All points of a triangle should have the same dimension");
                    }
                }
            }

            return dimension;
        }

    }


    public void remove(int index) {
        triangles.remove(index);

        if (triangles.size() == 0) {
            dimension = NO_DIMENSION;
        }
    }


    public Mesh scaleMesh(double scaleFactor) {
        Mesh newMesh = new Mesh();

        for (Triangle triangle : triangles) {
            Triangle newTriangle = new Triangle(
                    triangle.getPoints()[0].scaleVertex(scaleFactor),
                    triangle.getPoints()[1].scaleVertex(scaleFactor),
                    triangle.getPoints()[2].scaleVertex(scaleFactor)
            );

            newMesh.add(newTriangle);
        }

        return newMesh;
    }


    public Mesh translate(int dimension, double shiftValue) {
        Mesh newMesh = new Mesh();

        for (Triangle triangle : triangles) {
            Triangle newTriangle = new Triangle(
                    triangle.getPoints()[0].translate(dimension, shiftValue),
                    triangle.getPoints()[1].translate(dimension, shiftValue),
                    triangle.getPoints()[2].translate(dimension, shiftValue)
            );

            newMesh.add(newTriangle);
        }

        return newMesh;
    }


    public Mesh rotateX(double angle) {
        Mesh rotatedMesh = new Mesh();

        for (Triangle triangle : triangles) {
            Triangle newTriangle = new Triangle(
                    new Vertex(DoubleMatrix2D.rotateX(triangle.getPoints()[0].toMatrix(3), angle)),
                    new Vertex(DoubleMatrix2D.rotateX(triangle.getPoints()[1].toMatrix(3), angle)),
                    new Vertex(DoubleMatrix2D.rotateX(triangle.getPoints()[2].toMatrix(3), angle))
            );

            rotatedMesh.add(newTriangle);
        }

        return rotatedMesh;
    }


    public Mesh rotateY(double angle) {
        Mesh rotatedMesh = new Mesh();

        for (Triangle triangle : triangles) {
            Triangle newTriangle = new Triangle(
                    new Vertex(DoubleMatrix2D.rotateY(triangle.getPoints()[0].toMatrix(3), angle)),
                    new Vertex(DoubleMatrix2D.rotateY(triangle.getPoints()[1].toMatrix(3), angle)),
                    new Vertex(DoubleMatrix2D.rotateY(triangle.getPoints()[2].toMatrix(3), angle))
            );

            rotatedMesh.add(newTriangle);
        }

        return rotatedMesh;
    }


    public Mesh rotateZ(double angle) {
        Mesh rotatedMesh = new Mesh();

        for (Triangle triangle : triangles) {
            Triangle newTriangle = new Triangle(
                    new Vertex(DoubleMatrix2D.rotateZ(triangle.getPoints()[0].toMatrix(3), angle)),
                    new Vertex(DoubleMatrix2D.rotateZ(triangle.getPoints()[1].toMatrix(3), angle)),
                    new Vertex(DoubleMatrix2D.rotateZ(triangle.getPoints()[2].toMatrix(3), angle))
            );

            rotatedMesh.add(newTriangle);
        }

        return rotatedMesh;
    }


    public Mesh projectMesh(DoubleMatrix2D projectionMatrix) {
        Mesh projectedMesh = new Mesh();

        for (Triangle triangle : triangles) {
            Triangle newTriangle = new Triangle(
                    new Vertex(DoubleMatrix2D.multiplyMatrix(triangle.getPoints()[0].toMatrix(4), projectionMatrix)),
                    new Vertex(DoubleMatrix2D.multiplyMatrix(triangle.getPoints()[1].toMatrix(4), projectionMatrix)),
                    new Vertex(DoubleMatrix2D.multiplyMatrix(triangle.getPoints()[2].toMatrix(4), projectionMatrix))
            );

            projectedMesh.add(newTriangle);
        }

        return projectedMesh;
    }


    public List<Triangle> getTriangles() {
        return triangles;
    }


    @Override
    public String toString() {
        String string = "";

        for (Triangle triangle : triangles) {
            string += triangle + "\n";
        }

        return string;
    }
}
