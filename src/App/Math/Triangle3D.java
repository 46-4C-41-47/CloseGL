package App.Math;


public class Triangle3D {
    private static final int POINTS_SIZE = 3;
    private CartesianVector normal;
    private Vertex3D[] points;
    public double exposition = 0;


    public Triangle3D(Triangle3D triangle) {
        points = triangle.points;
    }


    public Triangle3D(Vertex3D[] points) {
        if (points.length == POINTS_SIZE) {
            this.points = points;

        } else {
            throw new ArrayIndexOutOfBoundsException("A triangle is represented by 3 points");
        }
    }


    public Triangle3D(Vertex3D p1, Vertex3D p2, Vertex3D p3) {
        points = new Vertex3D[] {p1, p2, p3};
    }


    public Vertex3D[] getPoints() {
        return points;
    }


    private CartesianVector computeNormal() {
        CartesianVector A = new CartesianVector(points[0], points[1]);
        CartesianVector B = new CartesianVector(points[0], points[2]);

        return new CartesianVector(
                (A.y * B.z) - (A.z * B.y),
                (A.z * B.x) - (A.x * B.z),
                (A.x * B.y) - (A.y * B.x)
        );
    }


    public CartesianVector getNormal() {
        if (normal == null) {
            normal = computeNormal();
        }

        return normal;
    }


    public double getExposition(CartesianVector lightDirection) {
        return (getNormal().getNormalize().dotProduct(lightDirection) + 1) / 2;
    }


    public Triangle3D rotateX(double theta) {
        return new Triangle3D(
                new Vertex3D(DoubleMatrix2D.rotateX(points[0].toMatrix(), theta)),
                new Vertex3D(DoubleMatrix2D.rotateX(points[1].toMatrix(), theta)),
                new Vertex3D(DoubleMatrix2D.rotateX(points[2].toMatrix(), theta))
        );
    }


    public Triangle3D rotateY(double theta) {
        return new Triangle3D(
                new Vertex3D(DoubleMatrix2D.rotateY(points[0].toMatrix(), theta)),
                new Vertex3D(DoubleMatrix2D.rotateY(points[1].toMatrix(), theta)),
                new Vertex3D(DoubleMatrix2D.rotateY(points[2].toMatrix(), theta))
        );
    }


    @Override
    public String toString() {
        return points[0] + ", \n" + points[1] + ", \n" + points[2] + "\n";
    }
}
