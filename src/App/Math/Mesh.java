package App.Math;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
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


    public Mesh(List<Triangle> inTriangles) {
        triangles = inTriangles;
    }


    public void add(Triangle triangle) {
        if (getDimension() == NO_DIMENSION) {
            triangles.add(triangle);
            dimension = triangle.getDimension();

        } else if (this.getDimension() == triangle.getDimension()) {
            triangles.add(triangle);

        } else {
            throw new UnsupportedOperationException("All triangle of a mesh should have the same dimension");
        }
    }


    public void add(Vertex[] points) {
        Triangle triangle = new Triangle(points);
        triangles.add(triangle);
    }


    public int getDimension() {
        return dimension;
    }


    public void remove(int index) {
        triangles.remove(index);

        if (triangles.size() == 0) {
            dimension = NO_DIMENSION;
        }
    }


    public Mesh scale(double scaleFactor) {
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


    public Triangle project(DoubleMatrix2D projectionMatrix, Triangle triangle) {
        Vertex[] points = new Vertex[3];

        for (int i = 0; i < points.length; i++) {
            DoubleMatrix2D vertex = DoubleMatrix2D.multiplyMatrix(
                    triangle.getPoints()[i].toMatrix(4),
                    projectionMatrix
            );

            if (vertex.matrix[0][3] != 0) {
                points[i] = new Vertex(
                        vertex.matrix[0][0] / vertex.matrix[0][3],
                        vertex.matrix[0][1] / vertex.matrix[0][3],
                        vertex.matrix[0][2] / vertex.matrix[0][3],
                        vertex.matrix[0][3]
                );

            } else {
                points[i] = new Vertex(vertex);
            }
        }

        Triangle projectedTriangle = new Triangle(points);
        projectedTriangle.exposition = triangle.exposition;


        return projectedTriangle;
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


    public Mesh render(Vertex CameraPosition, Vector lightDirection, DoubleMatrix2D projectionMatrix, Dimension frameSize) {
        Mesh newMesh = new Mesh();

        for (Triangle triangle : triangles) {
            Vector camToPoint = new Vector(CameraPosition, triangle.getPoints()[0]);
            double dotProduct = triangle.getNormal().dotProduct(camToPoint);

            if (dotProduct < 0) {
                Triangle newTriangle = new Triangle(triangle);
                newTriangle.exposition = triangle.getExposition(lightDirection);
                newMesh.add(toScreen(frameSize, project(projectionMatrix, newTriangle)));
            }
        }

        newMesh.sortTriangles();

        return newMesh;
    }


    private void sortTriangles() {
        triangles.sort(new Comparator<Triangle>() {
            @Override
            public int compare(Triangle o1, Triangle o2) {
                double o1Distance = (
                        o1.getPoints()[0].getCoordinates()[2] +
                        o1.getPoints()[0].getCoordinates()[2] +
                        o1.getPoints()[0].getCoordinates()[2]) / 3;

                double o2Distance = (
                        o2.getPoints()[0].getCoordinates()[2] +
                        o2.getPoints()[0].getCoordinates()[2] +
                        o2.getPoints()[0].getCoordinates()[2]) / 3;

                return Double.compare(o2Distance, o1Distance);
            }
        });
    }


    public Triangle toScreen(Dimension screenSize, Triangle triangle) {
        Triangle newTriangle = new Triangle(
                new Vertex(
                        ((triangle.getPoints()[0].getCoordinates()[0] + 1) * (double) screenSize.width / 2),
                        ((triangle.getPoints()[0].getCoordinates()[1] + 1) * (double) screenSize.height / 2),
                        triangle.getPoints()[0].getCoordinates()[2]
                ),
                new Vertex(
                        ((triangle.getPoints()[1].getCoordinates()[0] + 1) * (double) screenSize.width / 2),
                        ((triangle.getPoints()[1].getCoordinates()[1] + 1) * (double) screenSize.height / 2),
                        triangle.getPoints()[1].getCoordinates()[2]
                ),
                new Vertex(
                        ((triangle.getPoints()[2].getCoordinates()[0] + 1) * (double) screenSize.width / 2),
                        ((triangle.getPoints()[2].getCoordinates()[1] + 1) * (double) screenSize.height / 2),
                        triangle.getPoints()[2].getCoordinates()[2]
                )
        );

        newTriangle.exposition = triangle.exposition;

        return newTriangle;
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
