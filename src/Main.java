import helper.*;

public class Main {
    public static void main(String[] args) {

        double originToSetA = -10.0;

        Point origin = new Point(0.0,0.0,0.0,0.0,0.0,0.0);
        Point test = new Point(3,6,2.0,0.0,0.0,0);
        Point BPlane = new Point(0.0,originToSetA,0.0,90.0,0.0, 0.0);
        BPlane.rotateAboutZ(120);
        Plane B = new Plane(BPlane);
        System.out.println(B.getNormalDistance(test));
    }
}