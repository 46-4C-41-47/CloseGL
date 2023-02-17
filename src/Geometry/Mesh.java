package Geometry;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private List<Triangle> triangles;

    public Mesh() {
        triangles = new ArrayList<>();
    }


    public void add(Triangle triangle) {
        triangles.add(triangle);
    }


    public void add(Point3D[] points) {
        Triangle triangle = new Triangle(points);
        triangles.add(triangle);
    }


    public void remove(int index) {
        triangles.remove(index);
    }


    public List<Triangle> getTriangles() {
        return triangles;
    }
}
