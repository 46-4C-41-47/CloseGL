package App.Math;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Triangle {
    private static final int POINTS_SIZE = 3;
    private Vector normal;
    private Vertex[] points;
    public double exposition = 0;


    public Triangle(Triangle triangle) {
        points = triangle.points;
    }


    public Triangle(Vertex[] points) {
        if (points.length == POINTS_SIZE) {
            this.points = points;

        } else {
            throw new ArrayIndexOutOfBoundsException("A triangle is represented by 3 points");
        }
    }


    public Triangle(Vertex p1, Vertex p2, Vertex p3) {
        points = new Vertex[] {p1, p2, p3};
    }


    public Vertex[] getPoints() {
        return points;
    }


    private Vector computeNormal() {
        Vector A = new Vector(points[0], points[1]);
        Vector B = new Vector(points[0], points[2]);

        return A.cross(B);
    }


    public Vector getNormal() {
        if (normal == null) {
            normal = computeNormal();
        }

        return normal;
    }


    public double getExposition(Vector lightDirection) {
        return (getNormal().normalize().dot(lightDirection) + 1) / 2;
    }


    public Triangle rotateX(double theta) {
        return new Triangle(
                new Vertex(DoubleMatrix2D.rotateX(points[0].toMatrix(), theta)),
                new Vertex(DoubleMatrix2D.rotateX(points[1].toMatrix(), theta)),
                new Vertex(DoubleMatrix2D.rotateX(points[2].toMatrix(), theta))
        );
    }


    public Triangle rotateY(double theta) {
        return new Triangle(
                new Vertex(DoubleMatrix2D.rotateY(points[0].toMatrix(), theta)),
                new Vertex(DoubleMatrix2D.rotateY(points[1].toMatrix(), theta)),
                new Vertex(DoubleMatrix2D.rotateY(points[2].toMatrix(), theta))
        );
    }


    public boolean isBehindNearPlane(double planeOffset) {
        return  -planeOffset < points[0].z &&
                -planeOffset < points[1].z &&
                -planeOffset < points[2].z;
    }


    public List<Triangle> clip(Plane plane) {
        List<Triangle> choppedTriangle = new ArrayList<>();

        List<Vertex> insidePoints = new ArrayList<>();
        List<Vertex> outsidePoints = new ArrayList<>();

        for (Vertex point : getPoints()) {
            if (0 <= plane.getDistance(point)) {
                insidePoints.add(new Vertex(point));

            } else {
                outsidePoints.add(new Vertex(point));
            }
        }

        if (insidePoints.size() == 3) {
            choppedTriangle.add(this);

        } else if (insidePoints.size() == 2) {
            Vertex intersection1 = plane.lineIntersection(insidePoints.get(1), outsidePoints.get(0));

            Triangle a = new Triangle(insidePoints.get(0), insidePoints.get(1), intersection1);
            a.exposition = exposition;

            Triangle b = new Triangle(
                    insidePoints.get(0),
                    plane.lineIntersection(insidePoints.get(0), outsidePoints.get(0)),
                    intersection1
            );
            b.exposition = exposition;

            choppedTriangle.add(a);
            choppedTriangle.add(b);

        } else if (insidePoints.size() == 1) {
            Triangle a = new Triangle(
                    insidePoints.get(0),
                    plane.lineIntersection(insidePoints.get(0), outsidePoints.get(0)),
                    plane.lineIntersection(insidePoints.get(0), outsidePoints.get(1))
            );
            a.exposition = exposition;

            choppedTriangle.add(a);
        }

        return choppedTriangle;
    }


    public List<Triangle> clipLeft() {
        List<Triangle> choppedTriangle = new ArrayList<>();

        List<Vertex> insidePoints = new ArrayList<>();
        List<Vertex> outsidePoints = new ArrayList<>();

        for (Vertex point: points) {
            if (0 <= point.x) {
                insidePoints.add(new Vertex(point));

            } else {
                outsidePoints.add(new Vertex(point));
            }
        }

        if (insidePoints.size() == 3) {
            choppedTriangle.add(this);

        } else if (insidePoints.size() == 1) {
            Line P1ToP2 = new Line(insidePoints.get(0), outsidePoints.get(0));
            Line P1ToP3 = new Line(insidePoints.get(0), outsidePoints.get(1));

            Triangle a = new Triangle(
                    insidePoints.get(0),
                    new Vertex(0, P1ToP2.getY(0), 0),
                    new Vertex(0, P1ToP3.getY(0), 0)
            );
            a.exposition = exposition;
            choppedTriangle.add(a);

        } else if (insidePoints.size() == 2) {
            Line P1ToP3 = new Line(insidePoints.get(0), outsidePoints.get(0));
            Line P2ToP3 = new Line(insidePoints.get(1), outsidePoints.get(0));

            Triangle a = new Triangle(
                    insidePoints.get(0),
                    insidePoints.get(1),
                    new Vertex(0, P1ToP3.getY(0), 0)
            );

            Triangle b = new Triangle(
                    insidePoints.get(1),
                    new Vertex(0, P2ToP3.getY(0), 0),
                    new Vertex(0, P1ToP3.getY(0), 0)
            );
            a.exposition = exposition;
            b.exposition = exposition;

            choppedTriangle.add(a);
            choppedTriangle.add(b);
        }

        return choppedTriangle;
    }


    @Override
    public String toString() {
        return points[0] + ", \n" + points[1] + ", \n" + points[2] + "\n";
    }
}
