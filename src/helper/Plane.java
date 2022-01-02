package helper;

import java.util.ArrayList;
import java.util.Arrays;

public class Plane {

    private double servoArmLength;
    private double legLength;
    //Distance from center of 2 servos to servo center
    private double servoDistX;

    //represents translation / rotation from master plane
    private Point fromBase;

    //indexes: 0 = sphere right, 1 = servo right, 2 = sphere left, 3 = servo left
    private ArrayList<ArrayList<Double>> circleList = new ArrayList<>();
    private final ArrayList<Double> zero = new ArrayList<>();

    public Plane() {
        fromBase = new Point(0.0,0.0,0.0,0.0,0.0,0.0);
        ArrayList<Double> temp = new ArrayList<>();
        temp.add(0.0);
        temp.add(0.0);
        temp.add(0.0);
        circleList.add(temp);
        circleList.add(temp);
        circleList.add(temp);
        circleList.add(temp);
        servoArmLength = 0;
        legLength = 0;
        servoDistX = 0;
    }

    public void setConstants(Point thisFromBase, double thisServoArmLength, double thisLegLength, double thisServoDistX) {
        fromBase = thisFromBase;
        servoArmLength = thisServoArmLength;
        legLength = thisLegLength;
        servoDistX = thisServoDistX;
    }

    public double getNormalDistance(Point toPoint) {
        toPoint.rotateAboutZ(Math.toDegrees(fromBase.getAngleTo(270)));
        double curBaseZAngle = fromBase.getPoint().get(5);
        fromBase.rotateToZ(270);
        double baseY = fromBase.getPoint().get(1);
        fromBase.rotateToZ(curBaseZAngle);
        return(Math.abs(baseY - toPoint.getPoint().get(1)));
    }

    //returns arraylist with [x,y,r], to be used in drawCircle
    public ArrayList<Double> getSphereIntersect(Point sphereCenter, double legLength) {
        double normDist = this.getNormalDistance(sphereCenter);
        //TODO: if sphere center too far, this will take sqrt of negative value, should have exception
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
    public void drawCircle(ArrayList<Double> circle, int index) {
        circleList.set(index, circle);
    }

    //used in wrapper
    public void drawSphereCircle(Point sphereCenter, double legLength, int index) {
        ArrayList<Double> circle = this.getSphereIntersect(sphereCenter, legLength);
        drawCircle(circle, index);
    }

    public double getDistanceBetweenCircles(int cirPlatform, int cirBase) {
        double dist = Math.hypot(circleList.get(cirPlatform).get(0)-circleList.get(cirBase).get(0),circleList.get(cirPlatform).get(1)-circleList.get(cirBase).get(1));
        return(dist);
    }

    //Returns angle between a and b in rad
    public double lawCosine(double a, double b, double c) {
        double angle = Math.acos((Math.pow(a,2)+Math.pow(b,2)-Math.pow(c,2))/(2*a*b));
        return(angle);
    }

    public double getTheta2(int cirPlatform, int cirBase) {
        double theta2 = lawCosine(getDistanceBetweenCircles(cirPlatform,cirBase),servoArmLength,legLength);
        return(theta2);
    }

    public double getTheta1(int cirPlatform, int cirBase) {
        //This is horrible.
        //NOT GENERALIZED AT ALL!!! WHY 500?!?!?!?!?!
        //TODO:getTheta1 not work, getTheta2 does. Hypothesis: horrible code.
        double theta1 = lawCosine(getDistanceBetweenCircles(cirPlatform,cirBase),100,Math.hypot(circleList.get(cirPlatform).get(1)-circleList.get(cirBase).get(1),Math.abs(circleList.get(cirPlatform).get(0)-circleList.get(cirBase).get(0))));
        return(theta1);
    }

    public ArrayList<Double> translatePointByAngle(double x, double y, double r, double ang) {
        return new ArrayList<>(Arrays.asList(r * Math.cos(ang) + x, r * Math.sin(ang) + y));
    }

    //does both t1+t2 and t1-t2 and solves for resulting points, compares distances to adjacent platform vertex, takes farthest, and returns servo
    //angle (polar) in deg
    //I hate the if's as well just deal with it.
    public double getCorrectServoAngle(String servoSide) {
        if(servoSide.equals("right")) {
            double t1 = getTheta1(0,1);
            double t2 = getTheta2(0,1);
            double angA = t1-t2;
            double angB = t1+t2;
            ArrayList<Double> A = translatePointByAngle(circleList.get(1).get(0),circleList.get(1).get(1),servoArmLength,angA);
            ArrayList<Double> B = translatePointByAngle(circleList.get(1).get(0),circleList.get(1).get(1),servoArmLength,angB);
            //This is not the right way to do this, I'm just lazy atm.
            //if A is farther
            if(Math.hypot(Math.abs(circleList.get(2).get(0)-A.get(0)),Math.abs(circleList.get(2).get(1)-A.get(1))) > Math.hypot(Math.abs(circleList.get(2).get(0)-B.get(0)),Math.abs(circleList.get(2).get(1)-B.get(1)))) {
                return(Math.toDegrees(angA));
            }
            else {
                return(Math.toDegrees(angB));
            }
        }

        //Code for left side
        else {
            double t1 = getTheta1(2,3);
            double t2 = getTheta2(2,3);
            double angA = t1-t2;
            double angB = t1+t2;
            ArrayList<Double> A = translatePointByAngle(circleList.get(3).get(0),circleList.get(3).get(1),servoArmLength,angA);
            ArrayList<Double> B = translatePointByAngle(circleList.get(3).get(0),circleList.get(3).get(1),servoArmLength,angB);
            //This is not the right way to do this, I'm just lazy atm.
            //if A is farther
            if(Math.hypot(Math.abs(circleList.get(0).get(0)-A.get(0)),Math.abs(circleList.get(0).get(1)-A.get(1))) > Math.hypot(Math.abs(circleList.get(0).get(0)-B.get(0)),Math.abs(circleList.get(0).get(1)-B.get(1)))) {
                return(Math.toDegrees(angA));
            }
            //if B is farther
            else {
                return(Math.toDegrees(angB));
            }
        }
    }
}