package helper;
//points stored as Arraylist of 6 indices: [x,y,z,i,j,k]
//translations in mm and rotations in degrees

import java.net.SocketOption;
import java.util.ArrayList;

public class Point {

    private ArrayList<Double> point = new ArrayList<>();

    public Point() {
        for(int i = 0; i < 6; i++) {
            point.add(0.0);
        }
    }

    public Point(double x, double y, double z) {
        point.add(x);
        point.add(y);
        point.add(z);
        for(int i = 0; i < 3; i++) {
            point.add(0.0);
        }
    }

    public Point(double x, double y, double z, double i, double k, double j) {
        point.add(x);
        point.add(y);
        point.add(z);
        point.add(i);
        point.add(k);
        point.add(j);
    }

    //returns point array, for debugging
    public ArrayList<Double> getPoint() {
        return(point);
    }

    public void translateTo(double x, double y, double z) {
        point.set(0,x);
        point.set(1,y);
        point.set(2,z);
    }

    public void translateBy(double x, double y, double z) {
        translateTo(point.get(0) + x,point.get(1) + y,point.get(2) + z);
    }

    //returns 3 index array with translations
    public ArrayList<Double> translationTo(Point toPoint) {

        double dX = toPoint.point.get(0) - point.get(0);
        double dY = toPoint.point.get(1) - point.get(1);
        double dZ = toPoint.point.get(2) - point.get(2);

        ArrayList<Double> translation = new ArrayList<>();

        translation.add(dX);
        translation.add(dY);
        translation.add(dZ);

        return(translation);

    }

    //returns 3 index array with rotations
    public ArrayList<Double> rotationTo(Point toPoint) {

        double dX = toPoint.point.get(3) - point.get(3);
        double dY = toPoint.point.get(4) - point.get(4);
        double dZ = toPoint.point.get(5) - point.get(5);

        ArrayList<Double> rotation = new ArrayList<>();

        rotation.add(dX);
        rotation.add(dY);
        rotation.add(dZ);

        return(rotation);

    }

    //returns scalar distance to other point
    public double distanceTo(Point toPoint) {
        ArrayList<Double> translation = translationTo(toPoint);
        return(Math.sqrt(Math.pow(translation.get(0), 2) + Math.pow(translation.get(1), 2) + Math.pow(translation.get(2), 2)));
    }

    //rotates point around axis
    //it works don't question it
    //ACCEPTS DEGREES
    public void rotateAboutZ(double angle) {
        point.set(5, (angle + point.get(5)) % 360);
        angle = Math.toRadians(angle % 360);
        double hypot = this.distanceTo(new Point(0, 0, point.get(2)));
        double curAngle = Math.atan2(point.get(1), point.get(0));
        angle = (angle + curAngle) % (2 * Math.PI);
        point.set(0, Math.cos(angle) * hypot);
        point.set(1, Math.sin(angle) * hypot);
    }

    //rotate to angle about Z axis
    public void rotateToZ(double desAngle) {
        desAngle = Math.toRadians(desAngle % 360);
        double curAngle = Math.atan2(point.get(1), point.get(0));
        rotateAboutZ(Math.toDegrees(desAngle - curAngle));
    }

    //returns angle to desired angle
    public double getAngleTo(double desAngle) {
        desAngle = Math.toRadians(desAngle % 360);
        double curAngle = Math.atan2(point.get(1), point.get(0));
        return(desAngle - curAngle);
    }
}