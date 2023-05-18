package App.Math;


public class Triangle {
    private static final int POINTS_SIZE = 3;
    private Vector normal;
    private Vertex[] points;
    public double exposition = 0;


    public Triangle() {
        points = new Vertex[] {
                new Vertex(0, 0),
                new Vertex(0, 0),
                new Vertex(0, 0)
        };
    }


    public Triangle(Triangle triangle) {
        points = triangle.points;
    }


    public Triangle(Vertex[] points) {
        if (points.length == POINTS_SIZE) {
            if (points[0].getDimension() == points[1].getDimension() && points[0].getDimension() == points[2].getDimension()) {
                this.points = points;

            } else {
                throw new UnsupportedOperationException("All points of a triangle should have the same dimension");
            }

        } else {
            throw new ArrayIndexOutOfBoundsException("A triangle is represented by 3 points");
        }
    }


    public Triangle(Vertex p1, Vertex p2, Vertex p3) {
        if (p1.getDimension() == p2.getDimension() && p1.getDimension() == p3.getDimension()) {
            points = new Vertex[] {p1, p2, p3};

        } else {
            throw new UnsupportedOperationException("All points of a triangle should have the same dimension");
        }
    }


    public Vertex[] getPoints() {
        return points;
    }


    public int getDimension() {
        return points[0].getDimension();
    }


    private Vector computeNormal() {
        Vector A = new Vector(points[0], points[1]);
        Vector B = new Vector(points[0], points[2]);

        return new Vector(
                (A.getCoordinates()[1] * B.getCoordinates()[2]) - (A.getCoordinates()[2] * B.getCoordinates()[1]),
                (A.getCoordinates()[2] * B.getCoordinates()[0]) - (A.getCoordinates()[0] * B.getCoordinates()[2]),
                (A.getCoordinates()[0] * B.getCoordinates()[1]) - (A.getCoordinates()[1] * B.getCoordinates()[0])
        );
    }


    public Vector getNormal() {
        if (normal == null) {
            normal = computeNormal();
        }

        return normal;
    }


    public double getExposition(Vector lightDirection) {
        return (getNormal().getNormalize().dotProduct(lightDirection) + 1) / 2;
    }


    @Override
    public String toString() {
        return points[0] + ", \n" + points[1] + ", \n" + points[2] + "\n";
    }
}
