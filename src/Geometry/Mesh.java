package Geometry;

import java.util.ArrayList;

public class Mesh {
    private ArrayList<Triangle> triangles;

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
}
