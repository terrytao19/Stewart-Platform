import java.util.Arrays;

public class Stewart {
    public static void main(String[] args) {
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

        Solver INVERSE_KINEMATICS = new Solver(THETA_R,THETA_P,THETA_S,RD,PD,L1,L2,Z_HOME);

        double[] angs = INVERSE_KINEMATICS.solve(5,5,0,5,0,0);

        System.out.println(Arrays.toString(angs));
    }
}