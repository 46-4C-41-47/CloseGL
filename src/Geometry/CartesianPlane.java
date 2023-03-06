package Geometry;

public class CartesianPlane {
    private final Vector3D normalVector;
    private final float d;


    public CartesianPlane(Vector3D normalVector, float d) {
        this.normalVector = normalVector;
        this.d = d;
    }


    public Vector3D getNormalVector() {
        return normalVector;
    }


    public float getD() {
        return d;
    }
}
