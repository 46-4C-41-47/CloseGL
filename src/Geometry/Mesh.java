package Geometry;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private final List<Triangle> triangles;

    public Mesh() {
        triangles = new ArrayList<>();
    }


    public Mesh(Mesh inputMesh) {
        triangles = new ArrayList<>();
        triangles.addAll(inputMesh.getTriangles());
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


    @Override
    public String toString() {
        String string = "";

        for (Triangle triangle: triangles) {
            string += triangle + "\n";
        }

        return string;
    }
}
