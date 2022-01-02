package helper;

import java.util.ArrayList;

public class Stewart {
    private Plane A = new Plane();
    private Plane B = new Plane();
    private Plane C = new Plane();

    //measurements for the platform
    private double radius = 15;
    private double spacing = 10;
    private double height = 120;

    //measurements for the base/servos
    private double servoArmLength = 20;
    private double legLength = 110;
    //from middle of y-axis to servo center
    private double servoDistX = 5;
    //from servo plane to center of base
    private double servoDistY = 25;

    private double translationX;
    private double translationY;
    private double translationZ;

    private Point PA = new Point(-spacing/2,-radius,height);
    private Point PB = new Point(spacing/2,-radius,height);
    private Point PC = new Point(-spacing/2,-radius,height);
    private Point PD = new Point(spacing/2,-radius,height);
    private Point PE = new Point(-spacing/2,-radius,height);
    private Point PF = new Point(spacing/2,-radius,height);

    public Stewart() {
        PC.rotateAboutZ(120);
        PD.rotateAboutZ(120);
        PE.rotateAboutZ(240);
        PF.rotateAboutZ(240);

        A.setConstants(new Point(0,servoDistY,0,90,0,0),servoArmLength,legLength,servoDistX);
        B.setConstants(new Point(0,servoDistY,0,90,0,120),servoArmLength,legLength,servoDistX);
        C.setConstants(new Point(0,servoDistY,0,90,0,-120),servoArmLength,legLength,servoDistX);
    }

    private void updateVertices() {
        PA.translateBy(translationX, translationY, translationZ);
        PB.translateBy(translationX, translationY, translationZ);
        PC.translateBy(translationX, translationY, translationZ);
        PD.translateBy(translationX, translationY, translationZ);
        PE.translateBy(translationX, translationY, translationZ);
        PF.translateBy(translationX, translationY, translationZ);
    }

    private void updateCircles() {
        A.drawSphereCircle(PA, legLength, 2);
        A.drawSphereCircle(PB, legLength, 0);

        B.drawSphereCircle(PC, legLength, 2);
        B.drawSphereCircle(PD, legLength, 0);

        C.drawSphereCircle(PE, legLength, 2);
        C.drawSphereCircle(PF, legLength, 0);
    }

    public ArrayList<Double> solveIK(double x, double y, double z) {
        translationX = x;
        translationY = y;
        translationZ = z;
        updateVertices();
        updateCircles();
        ArrayList<Double> angles = new ArrayList<>();
        angles.add(A.getCorrectServoAngle("left"));
        angles.add(A.getCorrectServoAngle("right"));
        angles.add(B.getCorrectServoAngle("left"));
        angles.add(B.getCorrectServoAngle("right"));
        angles.add(C.getCorrectServoAngle("left"));
        angles.add(C.getCorrectServoAngle("right"));
        return(angles);
    }
}
