package App.Math;


public class PolarVector {
    private double phi = 0, theta = 0;
    private double length = 1;


    public PolarVector(double phi, double theta, double length) {
        setPhi(phi);
        setTheta(theta);
        this.length = length;
    }


    public PolarVector(double phi, double theta) {
        setPhi(phi);
        setTheta(theta);
    }


    public void setPhi(double phi) {
        this.phi = phi;
        /*double res = phi % (2 * Math.PI);

        if (res < 0) {
            this.phi = (2 * Math.PI) - res;

        } else {
            this.phi = res;
        }*/
    }


    public void setTheta(double theta) {
        this.theta = theta;
        /*double res = theta % Math.PI;

        if (res < 0) {
            this.theta = Math.PI - res;

        } else {
            this.theta = res;
        }*/
    }


    public void setLength(double length) {
        if (length <= 0) {
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


    public Vector toCartesian() {
        return new Vector(0, 0, 1).rotateY(-phi).rotateX(-theta);
    }


    @Override
    public String toString() {
        return "Polar vector : ( theta : " + theta + ", phi : " + phi + ",  length : " + length + ")";
    }
}
