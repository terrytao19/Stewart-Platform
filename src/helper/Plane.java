package helper;

import java.util.ArrayList;

public class Plane {

    //represents translation / rotation from master plane
    private final Point fromBase;

    //indexes: 0 = sphere right, 1 = servo right, 2 = sphere left, 3 = servo left
    private ArrayList<ArrayList<Double>> circleList = new ArrayList<>();
    private final ArrayList<Double> zero = new ArrayList<>();

    public Plane() {
        fromBase = new Point(0.0,0.0,0.0,0.0,0.0,0.0);
        circleList.add(zero);
        circleList.add(zero);
        circleList.add(zero);
        circleList.add(zero);
    }

    public Plane(Point thisFromBase) {
        fromBase = thisFromBase;
        circleList.add(zero);
        circleList.add(zero);
        circleList.add(zero);
        circleList.add(zero);
    }

    public double getNormalDistance(Point toPoint) {
        toPoint.rotateAboutZ(Math.toDegrees(fromBase.getAngleTo(270)));
        double curBaseZAngle = fromBase.getPoint().get(5);
        System.out.println(curBaseZAngle);
        fromBase.rotateToZ(270);
        double baseY = fromBase.getPoint().get(1);
        System.out.println(fromBase.getPoint().get(5));
        fromBase.rotateToZ(curBaseZAngle);
        System.out.println(fromBase.getPoint());
        return(Math.abs(baseY - toPoint.getPoint().get(1)));
    }

    //returns arraylist with [x,y,r], to be used in drawCircle
    public ArrayList<Double> getSphereIntersect(Point sphereCenter, double legLength) {
        double normDist = this.getNormalDistance(sphereCenter);
        double radius = Math.sqrt(Math.pow(legLength, 2) - Math.pow(normDist, 2));
        ArrayList<Double> circle = new ArrayList<>();
        sphereCenter.rotateAboutZ(Math.toDegrees(fromBase.getAngleTo(270)));
        //Adds the x dimension
        circle.add(sphereCenter.getPoint().get(0));
        //Adds the y dimension (is z for sphere point)
        circle.add(sphereCenter.getPoint().get(2));
        //radius, duh
        circle.add(radius);
        return(circle);
    }

    //circle: [x,y,r]
    public void drawCircle(ArrayList<Double> circle, double legLength, int index) {
        circleList.add(index, circle);
    }

    public void drawSphereCircle(Point sphereCenter, double legLength, int index) {
        ArrayList<Double> circle = this.getSphereIntersect(sphereCenter, legLength);
        circleList.add(index, circle);
        System.out.println(circleList);
    }
}