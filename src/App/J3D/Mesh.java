package App.J3D;

import App.Math.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Mesh {
    private static final int NO_DIMENSION = -1;
    private List<Triangle> triangles;
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
        triangles.add(triangle);
    }


    public void addAll(List<Triangle> triangles) {
        this.triangles.addAll(triangles);
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


    public void translateX(double offset) {
        for (int i = 0; i < triangles.size(); i++) {
            triangles.set(i,
                    new Triangle(
                            triangles.get(i).getPoints()[0].translateX(offset),
                            triangles.get(i).getPoints()[1].translateX(offset),
                            triangles.get(i).getPoints()[2].translateX(offset)
                    )
            );
        }
    }


    public void translateY(double offset) {
        for (int i = 0; i < triangles.size(); i++) {
            triangles.set(i,
                    new Triangle(
                            triangles.get(i).getPoints()[0].translateY(offset),
                            triangles.get(i).getPoints()[1].translateY(offset),
                            triangles.get(i).getPoints()[2].translateY(offset)
                    )
            );
        }
    }


    public void translateZ(double offset) {
        for (int i = 0; i < triangles.size(); i++) {
            triangles.set(i,
                    new Triangle(
                            triangles.get(i).getPoints()[0].translateZ(offset),
                            triangles.get(i).getPoints()[1].translateZ(offset),
                            triangles.get(i).getPoints()[2].translateZ(offset)
                    )
            );
        }
    }


    private Triangle project(DoubleMatrix2D projectionMatrix, Triangle triangle) {
        Vertex[] points = new Vertex[3];

        for (int i = 0; i < points.length; i++) {
            DoubleMatrix2D vertex = DoubleMatrix2D.multiplyMatrix(
                    triangle.getPoints()[i].toMatrix(),
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


    public void rotateX(double angle) {
        for (int i = 0; i < triangles.size(); i++) {
            triangles.set(i,
                    new Triangle(
                        new Vertex(DoubleMatrix2D.rotateX(triangles.get(i).getPoints()[0].toMatrix(), angle)),
                        new Vertex(DoubleMatrix2D.rotateX(triangles.get(i).getPoints()[1].toMatrix(), angle)),
                        new Vertex(DoubleMatrix2D.rotateX(triangles.get(i).getPoints()[2].toMatrix(), angle))
                    )
            );
        }
    }


    public void rotateY(double angle) {
        for (int i = 0; i < triangles.size(); i++) {
            triangles.set(i,
                    new Triangle(
                            new Vertex(DoubleMatrix2D.rotateY(triangles.get(i).getPoints()[0].toMatrix(), angle)),
                            new Vertex(DoubleMatrix2D.rotateY(triangles.get(i).getPoints()[1].toMatrix(), angle)),
                            new Vertex(DoubleMatrix2D.rotateY(triangles.get(i).getPoints()[2].toMatrix(), angle))
                    )
            );
        }
    }


    public void rotateZ(double angle) {
        for (int i = 0; i < triangles.size(); i++) {
            triangles.set(i,
                    new Triangle(
                            new Vertex(DoubleMatrix2D.rotateZ(triangles.get(i).getPoints()[0].toMatrix(), angle)),
                            new Vertex(DoubleMatrix2D.rotateZ(triangles.get(i).getPoints()[1].toMatrix(), angle)),
                            new Vertex(DoubleMatrix2D.rotateZ(triangles.get(i).getPoints()[2].toMatrix(), angle))
                    )
            );
        }
    }


    private Triangle move(Vertex camPosition, Triangle triangle) {
        Vertex[] points = new Vertex[3];

        for (int i = 0; i < triangle.getPoints().length; i++) {
            points[i] = triangle.getPoints()[i].plus(camPosition);
        }

        return new Triangle(points);
    }


    public Mesh render(Vector lightDirection, DoubleMatrix2D projectionMatrix, Dimension frameSize, Camera cam) {
        Mesh newMesh = new Mesh();

        for (Triangle rawTriangle : triangles) {
            Triangle movedTriangle = move(cam.getPosition(), rawTriangle);
            Triangle cleanTriangle = move(cam.getPosition(), movedTriangle.rotateY(cam.getYaw()).rotateX(cam.getPitch()));

            Vector camToPoint = new Vector(cam.getPosition(), cleanTriangle.getPoints()[0]);
            double dotProduct = cleanTriangle.getNormal().dot(camToPoint);

            if (dotProduct < 0) {
                List<Triangle> choppedTriangle = new ArrayList<>();

                choppedTriangle.addAll(cleanTriangle.clip(
                        new Plane(new Vector(0, 0, -1), new Vertex(0, 0, -0.1)))
                );

                double exposition = cleanTriangle.getExposition(lightDirection);

                for (Triangle tri : choppedTriangle) {
                    tri.exposition = exposition;
                    newMesh.add(toScreen(frameSize, project(projectionMatrix, tri)));
                }
            }
        }

        newMesh.sortTriangles();

        return newMesh;
    }


    public Mesh clipOnScreen(Plane[] frustumBorder) {
        Mesh newMesh = new Mesh();

        for (Triangle rawTriangle : triangles) {
            List<Triangle> clippedTriangles = new ArrayList<>();
            clippedTriangles.add(rawTriangle);

            for (Plane plane : frustumBorder) {
                List<Triangle> temp = new ArrayList<>();

                for (Triangle triangle : clippedTriangles) {
                    temp.addAll(triangle.clip(plane));
                }

                clippedTriangles = temp;
            }

            newMesh.addAll(clippedTriangles);
        }

        return newMesh;
    }


    /*public Mesh render(Vector lightDirection, DoubleMatrix2D projectionMatrix, Dimension frameSize, Camera cam) {
        Mesh newMesh = new Mesh();

        for (Triangle triangle : triangles) {
            Triangle movedTriangle = move(cam.getPosition(), triangle);
            Triangle rotatedTriangle = move(cam.getPosition(), movedTriangle.rotateY(cam.getYaw()).rotateX(cam.getPitch()));

            Vector camToPoint = new Vector(cam.getPosition(), rotatedTriangle.getPoints()[0]);
            double dotProduct = rotatedTriangle.getNormal().dot(camToPoint);

            if (dotProduct < 0) {
                rotatedTriangle.exposition = rotatedTriangle.getExposition(lightDirection);
                newMesh.add(toScreen(frameSize, project(projectionMatrix, rotatedTriangle)));
            }
        }

        newMesh.sortTriangles();

        return newMesh;
    }*/


    /*public Mesh render(Vector lightDirection, DoubleMatrix2D projectionMatrix, Dimension frameSize, Camera cam) {
        Mesh newMesh = new Mesh();

        for (Triangle triangle : triangles) {
            Triangle movedTriangle = move(cam.getPosition(), triangle);
            Triangle rotatedTriangle = move(cam.getPosition(), movedTriangle.rotateY(cam.getYaw()).rotateX(cam.getPitch()));

            Vector camToPoint = new Vector(cam.getPosition(), rotatedTriangle.getPoints()[0]);
            double dotProduct = rotatedTriangle.getNormal().dot(camToPoint);

            if (dotProduct < 0 && !rotatedTriangle.isBehindNearPlane(0.1)) {
                rotatedTriangle.exposition = rotatedTriangle.getExposition(lightDirection);
                newMesh.add(toScreen(frameSize, project(projectionMatrix, rotatedTriangle)));
            }
        }

        newMesh.sortTriangles();

        return newMesh;
    }*/


    private void sortTriangles() {
        triangles.sort(new Comparator<Triangle>() {
            @Override
            public int compare(Triangle o1, Triangle o2) {
                double o1Distance = (
                        o1.getPoints()[0].z +
                        o1.getPoints()[1].z +
                        o1.getPoints()[2].z) / 3;

                double o2Distance = (
                        o2.getPoints()[0].z +
                        o2.getPoints()[1].z +
                        o2.getPoints()[2].z) / 3;

                return Double.compare(o1Distance, o2Distance);
            }
        });
    }


    private Triangle toScreen(Dimension screenSize, Triangle triangle) {
        Triangle newTriangle = new Triangle(
                new Vertex(
                        ((triangle.getPoints()[0].x + 1) * (double) screenSize.width / 2),
                        ((triangle.getPoints()[0].y + 1) * (double) screenSize.height / 2),
                        triangle.getPoints()[0].z
                ),
                new Vertex(
                        ((triangle.getPoints()[1].x + 1) * (double) screenSize.width / 2),
                        ((triangle.getPoints()[1].y + 1) * (double) screenSize.height / 2),
                        triangle.getPoints()[1].z
                ),
                new Vertex(
                        ((triangle.getPoints()[2].x + 1) * (double) screenSize.width / 2),
                        ((triangle.getPoints()[2].y + 1) * (double) screenSize.height / 2),
                        triangle.getPoints()[2].z
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
