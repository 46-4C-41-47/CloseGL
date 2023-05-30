package App.Math;

import App.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Mesh {
    private static final int NO_DIMENSION = -1;
    private List<Triangle3D> triangles;


    public Mesh() {
        triangles = new ArrayList<>();
    }


    public Mesh(Mesh inputMesh) {
        triangles = new ArrayList<>();
        triangles.addAll(inputMesh.getTriangles());
    }


    public Mesh(List<Triangle3D> inTriangles) {
        triangles = inTriangles;
    }


    public void add(Triangle3D triangle) {
        triangles.add(triangle);
    }


    public void add(Vertex3D[] points) {
        Triangle3D triangle = new Triangle3D(points);
        triangles.add(triangle);
    }


    public void remove(int index) {
        triangles.remove(index);
    }


    public Mesh scale(double scaleFactor) {
        Mesh newMesh = new Mesh();

        for (Triangle3D triangle : triangles) {
            Triangle3D newTriangle = new Triangle3D(
                    triangle.getPoints()[0].scaleVertex(scaleFactor),
                    triangle.getPoints()[1].scaleVertex(scaleFactor),
                    triangle.getPoints()[2].scaleVertex(scaleFactor)
            );

            newMesh.add(newTriangle);
        }

        return newMesh;
    }


    public void translateX(double shiftValue) {
        for (Triangle3D triangle : triangles) {
            triangle.getPoints()[0].x += shiftValue;
            triangle.getPoints()[1].x += shiftValue;
            triangle.getPoints()[2].x += shiftValue;
        }
    }


    public void translateY(double shiftValue) {
        for (Triangle3D triangle : triangles) {
            triangle.getPoints()[0].y += shiftValue;
            triangle.getPoints()[1].y += shiftValue;
            triangle.getPoints()[2].y += shiftValue;
        }
    }


    public void translateZ(double shiftValue) {
        for (Triangle3D triangle : triangles) {
            triangle.getPoints()[0].z += shiftValue;
            triangle.getPoints()[1].z += shiftValue;
            triangle.getPoints()[2].z += shiftValue;
        }
    }


    private Triangle3D project(DoubleMatrix2D projectionMatrix, Triangle3D triangle) {
        Vertex3D[] points = new Vertex3D[3];

        for (int i = 0; i < points.length; i++) {
            DoubleMatrix2D vertex = DoubleMatrix2D.multiplyMatrix(
                    triangle.getPoints()[i].toMatrix(),
                    projectionMatrix
            );

            if (vertex.matrix[0][3] != 0) {
                points[i] = new Vertex3D(
                        vertex.matrix[0][0] / vertex.matrix[0][3],
                        vertex.matrix[0][1] / vertex.matrix[0][3],
                        vertex.matrix[0][2] / vertex.matrix[0][3],
                        vertex.matrix[0][3]
                );

            } else {
                points[i] = new Vertex3D(vertex);
            }
        }

        Triangle3D projectedTriangle = new Triangle3D(points);
        projectedTriangle.exposition = triangle.exposition;


        return projectedTriangle;
    }


    public void rotateX(double angle) {
        for (int i = 0; i < triangles.size(); i++) {
            triangles.set(i,
                    new Triangle3D(
                        new Vertex3D(DoubleMatrix2D.rotateX(triangles.get(i).getPoints()[0].toMatrix(), angle)),
                        new Vertex3D(DoubleMatrix2D.rotateX(triangles.get(i).getPoints()[1].toMatrix(), angle)),
                        new Vertex3D(DoubleMatrix2D.rotateX(triangles.get(i).getPoints()[2].toMatrix(), angle))
                    )
            );
        }
    }


    public void rotateY(double angle) {
        for (int i = 0; i < triangles.size(); i++) {
            triangles.set(i,
                    new Triangle3D(
                            new Vertex3D(DoubleMatrix2D.rotateY(triangles.get(i).getPoints()[0].toMatrix(), angle)),
                            new Vertex3D(DoubleMatrix2D.rotateY(triangles.get(i).getPoints()[1].toMatrix(), angle)),
                            new Vertex3D(DoubleMatrix2D.rotateY(triangles.get(i).getPoints()[2].toMatrix(), angle))
                    )
            );
        }
    }


    public void rotateZ(double angle) {
        for (int i = 0; i < triangles.size(); i++) {
            triangles.set(i,
                    new Triangle3D(
                            new Vertex3D(DoubleMatrix2D.rotateZ(triangles.get(i).getPoints()[0].toMatrix(), angle)),
                            new Vertex3D(DoubleMatrix2D.rotateZ(triangles.get(i).getPoints()[1].toMatrix(), angle)),
                            new Vertex3D(DoubleMatrix2D.rotateZ(triangles.get(i).getPoints()[2].toMatrix(), angle))
                    )
            );
        }
    }


    private Triangle3D move(Vertex3D camPosition, Triangle3D triangle) {
        Vertex3D[] points = new Vertex3D[3];

        for (int i = 0; i < triangle.getPoints().length; i++) {
            points[i] = new Vertex3D(
                triangle.getPoints()[i].x - camPosition.x,
                triangle.getPoints()[i].y - camPosition.y,
                triangle.getPoints()[i].z - camPosition.z
            );
        }

        return new Triangle3D(points);
    }


    public Mesh render(CartesianVector lightDirection, DoubleMatrix2D projectionMatrix, Dimension frameSize, Camera camera) {
        Mesh finalMesh = new Mesh();

        for (Triangle3D triangle : triangles) {
            CartesianVector camToPoint = new CartesianVector(camera.getPosition(), triangle.getPoints()[0]);
            Triangle3D rotatedTriangle = triangle.rotateY(camera.getYaw()).rotateX(camera.getPitch());
            double dotProduct = rotatedTriangle.getNormal().dotProduct(camToPoint);

            if (dotProduct < 0) {
                rotatedTriangle.exposition = triangle.getExposition(lightDirection);
                finalMesh.add(toScreen(frameSize, project(projectionMatrix, move(camera.getPosition(), rotatedTriangle))));
            }
        }

        finalMesh.sortTriangles();

        return finalMesh;
    }


    private void sortTriangles() {
        triangles.sort(new Comparator<Triangle3D>() {
            @Override
            public int compare(Triangle3D o1, Triangle3D o2) {
                double o1Distance = (
                        o1.getPoints()[0].z +
                        o1.getPoints()[0].z +
                        o1.getPoints()[0].z) / 3;

                double o2Distance = (
                        o2.getPoints()[0].z +
                        o2.getPoints()[0].z +
                        o2.getPoints()[0].z) / 3;

                return Double.compare(o1Distance, o2Distance);
            }
        });
    }


    private Triangle3D toScreen(Dimension screenSize, Triangle3D triangle) {
        Triangle3D newTriangle = new Triangle3D(
                new Vertex3D(
                        ((triangle.getPoints()[0].x + 1) * (double) screenSize.width / 2),
                        ((triangle.getPoints()[0].y + 1) * (double) screenSize.height / 2),
                          triangle.getPoints()[0].z
                ),
                new Vertex3D(
                        ((triangle.getPoints()[1].x + 1) * (double) screenSize.width / 2),
                        ((triangle.getPoints()[1].y + 1) * (double) screenSize.height / 2),
                          triangle.getPoints()[1].z
                ),
                new Vertex3D(
                        ((triangle.getPoints()[2].x + 1) * (double) screenSize.width / 2),
                        ((triangle.getPoints()[2].y + 1) * (double) screenSize.height / 2),
                          triangle.getPoints()[2].z
                )
        );

        newTriangle.exposition = triangle.exposition;

        return newTriangle;
    }


    public List<Triangle3D> getTriangles() {
        return triangles;
    }


    @Override
    public String toString() {
        String string = "";

        for (Triangle3D triangle : triangles) {
            string += triangle + "\n";
        }

        return string;
    }
}
