package helper;
//points stored as Arraylist of 6 indices: [x,y,z,i,j,k]
//translations in mm and rotations in degrees

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
        double intermediate = Math.hypot(translation.get(0), translation.get(1));
        return(Math.hypot(intermediate, translation.get(2)));
    }
}