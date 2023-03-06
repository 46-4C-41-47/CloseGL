package Geometry;

public class ParametricLine {
    private final Vector3D vector;
    private final Point3D point;


    public ParametricLine(Point3D a, Point3D b) {
        vector = new Vector3D(b.x - a.x, b.y - a.y, b.z - a.z);
        point = a;
    }


    public Point3D getPoint() {
        return point;
    }


    public Vector3D getVector() {
        return vector;
    }
}
