package Geometry;

public class Triangle {
    public Point3D[] points;


    public Triangle() {
        points = new Point3D[] {
                new Point3D(),
                new Point3D(),
                new Point3D()
        };
    }


    public Triangle(Triangle triangle) {
        points = triangle.points;
    }


    public Triangle(Point3D[] points) {
        if (points.length == 3) {
            this.points = points;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }


    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        points = new Point3D[] {p1, p2, p3};
    }


    public Triangle shiftZ(float zOffset) {
        Triangle newTriangle = new Triangle();

        for (int i = 0; i < points.length; i++) {
            newTriangle.points[i].x = this.points[i].x;
            newTriangle.points[i].y = this.points[i].y;
            newTriangle.points[i].z = this.points[i].z + zOffset;
        }

        return newTriangle;
    }


    @Override
    public String toString() {
        return points[0] + ", \n" + points[1] + ", \n" + points[2] + "\n";
    }
}
