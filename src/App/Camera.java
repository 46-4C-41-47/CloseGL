package App;

import App.Math.PolarVector3D;
import App.Math.Vertex3D;


public class Camera {
    private static final PolarVector3D DEFAULT_CAMERA_DIRECTION = new PolarVector3D(0, 0);
    private static final Vertex3D DEFAULT_CAMERA_POSITION = new Vertex3D(0, 0, 0);
    private Vertex3D cameraPosition = DEFAULT_CAMERA_POSITION;
    private PolarVector3D cameraDirection = DEFAULT_CAMERA_DIRECTION;


    public void yaw(double offsetAngle) {
        cameraDirection.setPhi(cameraDirection.getPhi() + offsetAngle);
    }


    public void pitch(double offsetAngle) {
        cameraDirection.setTheta(cameraDirection.getTheta() + offsetAngle);
    }


    public double getYaw() {
        return cameraDirection.getPhi();
    }


    public double getPitch() {
        return cameraDirection.getTheta();
    }


    public Vertex3D getPosition() {
        return cameraPosition;
    }

    public void forward(double offsetValue) {
        cameraPosition = cameraDirection.toCartesian().translate(cameraPosition, offsetValue);
    }


    public void sideways(double offsetValue) {

    }


    public void moveTo(double x, double y, double z) {
        cameraPosition = new Vertex3D(x, y, z);
    }


    public void resetCamera() {
        cameraPosition = DEFAULT_CAMERA_POSITION;
        cameraDirection = DEFAULT_CAMERA_DIRECTION;
    }
}
