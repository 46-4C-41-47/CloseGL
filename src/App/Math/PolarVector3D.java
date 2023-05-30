package App.Math;


public class PolarVector3D {
    private double phi = 0, theta = 0;
    private double length = 1;


    public PolarVector3D(double phi, double theta, double length) {
        this.phi = phi;
        this.theta = theta;
        this.length = length;
    }


    public PolarVector3D(double phi, double theta) {
        this.phi = phi;
        this.theta = theta;
    }


    public void setPhi(double phi) {
        this.phi = phi % (2 * Math.PI);
    }


    public void setTheta(double theta) {
        this.theta = theta % (2 * Math.PI);
    }


    public void setLength(double length) {
        if (length < 0) {
            throw new IllegalArgumentException("The length of a vector is always positive");
        }

        this.length = length;
    }


    public double getPhi() {
        return phi;
    }


    public double getTheta() {
        return theta;
    }


    public double getLength() {
        return length;
    }


    public CartesianVector toCartesian() {
        return new CartesianVector(
                length * Math.sin(theta) * Math.cos(phi),
                length * Math.sin(theta) * Math.sin(phi),
                length * Math.cos(theta)
        );
    }
}
