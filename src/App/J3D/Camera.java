package App.J3D;

import App.Math.*;
import App.Parameters;


public class Camera {
    private static final PolarVector DEFAULT_CAMERA_DIRECTION = new PolarVector(0, 0);
    private static final Vertex DEFAULT_CAMERA_POSITION = new Vertex(0, 0, 0);
    private Vertex cameraPosition = DEFAULT_CAMERA_POSITION;
    private PolarVector cameraDirection = DEFAULT_CAMERA_DIRECTION;
    private Vector upVector = new Vector(0, 1, 0);
    private Vector sidewayVector = upVector.cross(cameraDirection.toCartesian());


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


    public Vertex getPosition() {
        return cameraPosition;
    }


    public void moveForward(double offset) {
        cameraPosition.translate(cameraDirection.toCartesian(), offset);
    }


    public void moveSideways(double offset) {
        cameraPosition.translate(sidewayVector, offset);
    }


    public void moveUpDown(double offset) {
        cameraPosition.translate(upVector, offset);
    }


    public void moveTo(double x, double y, double z) {
        cameraPosition = new Vertex(x, y, z);
    }


    public void resetCamera() {
        cameraPosition = DEFAULT_CAMERA_POSITION;
        cameraDirection = DEFAULT_CAMERA_DIRECTION;
    }
}
