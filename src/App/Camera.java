package App;

import App.Math.Vector;
import App.Math.Vertex;


public class Camera {
    private static final Vector DEFAULT_CAMERA_DIRECTION = new Vector(0, 0, -1);
    private static final Vertex DEFAULT_CAMERA_POSITION = new Vertex(0, 0, 0);
    private Vertex cameraPosition = DEFAULT_CAMERA_POSITION;
    private Vector cameraDirection = DEFAULT_CAMERA_DIRECTION;


    public Camera() {}


    public void resetCamera() {
        cameraPosition = DEFAULT_CAMERA_POSITION;
        cameraDirection = DEFAULT_CAMERA_DIRECTION;
    }
}
