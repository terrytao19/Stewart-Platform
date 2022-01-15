import org.decimal4j.util.DoubleRounder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Stewart {
    public static void main(String[] args) throws IOException {
        //Angle between adjacent platform vertices / 2
        final double THETA_R = 30;

        //Angle between adjacent servos / 2
        final double THETA_P = 30;

        //Servo plane angles
        final double[] THETA_S = {180,-120,60,120,-60,0};

        //Platform radius
        final double RD = 67;

        //Base radius
        final double PD = 67;

        //Servo arm length
        final double L1 = 16;

        //Leg length
        final double L2 = 61;

        //Height when all arms horizontal
        final double Z_HOME = 60;

        Solver STEWART = new Solver(THETA_R, THETA_P, THETA_S, RD, PD, L1, L2, Z_HOME);

        ArrayList<double[]> BRUTE = new ArrayList<>();

        for(double i = 0; i <= Math.PI * 2; i += Math.PI * 2 / (30)) {
//            double sway = Math.cos(i) * 8;
//            double surge = Math.sin(i) * 8;
            double sway = 0.0;
            double surge = 0.0;
            double heave = 0.0;
            double pitch = Math.cos(i) * 3;
            double roll = Math.sin(i) * 3;
            double yaw = 0.0;
            BRUTE.add(STEWART.solve(sway,surge,heave,pitch,roll,yaw));
        }

        PrintWriter writer = new PrintWriter("BRUTE.txt", StandardCharsets.UTF_8);

        String writeAllAngs = "";

        int rows = 0;

        for(double[] angs : BRUTE) {
            rows ++;
            String writeAngs = "{";
            for (double ang : angs) {
                ang = DoubleRounder.round(ang, 1);
                writeAngs = writeAngs + ang + ", ";
            }
            writeAngs = writeAngs.substring(0, writeAngs.length() - 2);
            writeAngs = writeAngs + "}";
            writeAllAngs = writeAllAngs + writeAngs + ", ";
        }
        writeAllAngs = writeAllAngs.substring(0, writeAllAngs.length() - 2);
        writer.write(writeAllAngs);
        writer.close();
        System.out.println(rows);
    }
}