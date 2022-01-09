import java.util.Arrays;
public class Solver {
    //Angle between adjacent platform vertices / 2
    private double THETA_R;

    //Angle between adjacent servos / 2
    private double THETA_P;

    //Servo plane angles
    private double[] THETA_S;

    //Platform radius
    private double RD;

    //Base radius
    private double PD;

    //Servo arm length
    private double L1;

    //Leg length
    private double L2;

    //Height when all arms horizontal
    private double Z_HOME;

    private final double[][] COORDS = new double[12][5];

    public Solver(double THETA_R, double THETA_P, double[] THETA_S, double RD, double PD, double L1, double L2, double Z_HOME) {
        this.THETA_R = THETA_R;
        this.THETA_P = THETA_P;
        this.THETA_S = THETA_S;
        this.RD = RD;
        this.PD = PD;
        this.L1 = L1;
        this.L2 = L2;
        this.Z_HOME = Z_HOME;

        COORDS[0][0] = PD;
        COORDS[1][0] = PD;
        COORDS[2][0] = PD;
        COORDS[3][0] = -1*PD;
        COORDS[4][0] = -1*PD;
        COORDS[5][0] = -1*PD;
        COORDS[6][0] = PD;
        COORDS[7][0] = PD;
        COORDS[8][0] = PD;
        COORDS[9][0] = PD;
        COORDS[10][0] = PD;
        COORDS[11][0] = PD;

        COORDS[0][1] = Math.PI/6;
        COORDS[1][1] = Math.PI/6;
        COORDS[2][1] = -1*Math.PI/2;
        COORDS[3][1] = -1*Math.PI/2;
        COORDS[4][1] = Math.PI/6;
        COORDS[5][1] = Math.PI/6;
        COORDS[6][1] = Math.PI/6;
        COORDS[7][1] = Math.PI/6;
        COORDS[8][1] = -1*Math.PI/2;
        COORDS[9][1] = -1*Math.PI/2;
        COORDS[10][1] = Math.PI/6;
        COORDS[11][1] = Math.PI/6;

        COORDS[0][2] = COORDS[0][1]+Math.toRadians(THETA_R);
        COORDS[1][2] = COORDS[1][1]-Math.toRadians(THETA_R);
        COORDS[2][2] = COORDS[2][1]+Math.toRadians(THETA_R);
        COORDS[3][2] = COORDS[3][1]+Math.toRadians(THETA_R);
        COORDS[4][2] = COORDS[4][1]-Math.toRadians(THETA_R);
        COORDS[5][2] = COORDS[5][1]+Math.toRadians(THETA_R);
        COORDS[6][2] = COORDS[6][1]+Math.toRadians(THETA_R);
        COORDS[7][2] = COORDS[7][1]-Math.toRadians(THETA_R);
        COORDS[8][2] = COORDS[8][1]+Math.toRadians(THETA_R);
        COORDS[9][2] = COORDS[9][1]+Math.toRadians(THETA_R);
        COORDS[10][2] = COORDS[10][1]-Math.toRadians(THETA_R);
        COORDS[11][2] = COORDS[11][1]+Math.toRadians(THETA_R);

        COORDS[0][3] = COORDS[0][0]*Math.cos(COORDS[0][2]);
        COORDS[1][3] = COORDS[1][0]*Math.cos(COORDS[1][2]);
        COORDS[2][3] = COORDS[2][0]*Math.cos(COORDS[2][2]);
        COORDS[3][3] = COORDS[3][0]*Math.cos(COORDS[3][2]);
        COORDS[4][3] = COORDS[4][0]*Math.cos(COORDS[4][2]);
        COORDS[5][3] = COORDS[5][0]*Math.cos(COORDS[5][2]);
        COORDS[6][3] = COORDS[6][0]*Math.sin(COORDS[6][2]);
        COORDS[7][3] = COORDS[7][0]*Math.sin(COORDS[7][2]);
        COORDS[8][3] = COORDS[8][0]*Math.sin(COORDS[8][2]);
        COORDS[9][3] = COORDS[9][0]*Math.sin(COORDS[9][2]);
        COORDS[10][3] = COORDS[10][0]*Math.sin(COORDS[10][2]);
        COORDS[11][3] = COORDS[11][0]*Math.sin(COORDS[11][2]);

        COORDS[0][4] = Math.toDegrees(COORDS[0][2]);
        COORDS[1][4] = Math.toDegrees(COORDS[1][2]);
        COORDS[2][4] = Math.toDegrees(COORDS[2][2]);
        COORDS[3][4] = Math.toDegrees(COORDS[3][2]);
        COORDS[4][4] = Math.toDegrees(COORDS[4][2]);
        COORDS[5][4] = Math.toDegrees(COORDS[5][2]);
        COORDS[6][4] = Math.toDegrees(COORDS[6][2]);
        COORDS[7][4] = Math.toDegrees(COORDS[7][2]);
        COORDS[8][4] = Math.toDegrees(COORDS[8][2]);
        COORDS[9][4] = Math.toDegrees(COORDS[9][2]);
        COORDS[10][4] = Math.toDegrees(COORDS[10][2]);
        COORDS[11][4] = Math.toDegrees(COORDS[11][2]);

//        System.out.println(Arrays.deepToString(COORDS).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }
    public double[] solve(double sway, double surge, double heave, double pitch, double roll, double yaw) {
        pitch *= Math.PI/180;
        roll *= Math.PI/180;
        yaw *= Math.PI/180;

        double[][] platform_pivots = new double[6][3];

        //v1 saves compute time
        double v1 = Math.sin(pitch) * Math.sin(roll) * Math.cos(roll)-Math.cos(pitch)*Math.sin(yaw);
        platform_pivots[0][0] = COORDS[0][3] * Math.cos(roll) * Math.cos(yaw) + COORDS[6][4] * v1 + sway;
        platform_pivots[1][0] = COORDS[1][3] * Math.cos(roll) * Math.cos(yaw) + COORDS[7][4] * v1 + sway;
        platform_pivots[2][0] = COORDS[2][3] * Math.cos(roll) * Math.cos(yaw) + COORDS[8][4] * v1 + sway;
        platform_pivots[3][0] = COORDS[3][3] * Math.cos(roll) * Math.cos(yaw) + COORDS[9][4] * v1 + sway;
        platform_pivots[4][0] = COORDS[4][3] * Math.cos(roll) * Math.cos(yaw) + COORDS[10][4] * v1 + sway;
        platform_pivots[5][0] = COORDS[5][3] * Math.cos(roll) * Math.cos(yaw) + COORDS[11][4] * v1 + sway;

        //v2 saves compute time
        double v2 = Math.cos(pitch) * Math.cos(yaw) + Math.sin(pitch) * Math.sin(roll) * Math.sin(yaw);
        platform_pivots[0][1] = COORDS[0][3] * Math.cos(roll) * Math.sin(yaw) + COORDS[6][3] * v2 + surge;
        platform_pivots[1][1] = COORDS[1][3] * Math.cos(roll) * Math.sin(yaw) + COORDS[7][3] * v2 + surge;
        platform_pivots[2][1] = COORDS[2][3] * Math.cos(roll) * Math.sin(yaw) + COORDS[8][3] * v2 + surge;
        platform_pivots[3][1] = COORDS[3][3] * Math.cos(roll) * Math.sin(yaw) + COORDS[9][3] * v2 + surge;
        platform_pivots[4][1] = COORDS[4][3] * Math.cos(roll) * Math.sin(yaw) + COORDS[10][3] * v2 + surge;
        platform_pivots[5][1] = COORDS[5][3] * Math.cos(roll) * Math.sin(yaw) + COORDS[11][3] * v2 + surge;

        double v3 = Math.sin(pitch) * Math.cos(roll);
        platform_pivots[0][2] = -1 * COORDS[0][3] * Math.sin(roll) + COORDS[6][3] * v3 + Z_HOME + heave;
        platform_pivots[1][2] = -1 * COORDS[1][3] * Math.sin(roll) + COORDS[7][3] * v3 + Z_HOME + heave;
        platform_pivots[2][2] = -1 * COORDS[2][3] * Math.sin(roll) + COORDS[8][3] * v3 + Z_HOME + heave;
        platform_pivots[3][2] = -1 * COORDS[3][3] * Math.sin(roll) + COORDS[9][3] * v3 + Z_HOME + heave;
        platform_pivots[4][2] = -1 * COORDS[4][3] * Math.sin(roll) + COORDS[10][3] * v3 + Z_HOME + heave;
        platform_pivots[5][2] = -1 * COORDS[5][3] * Math.sin(roll) + COORDS[11][3] * v3 + Z_HOME + heave;

//        System.out.println(Arrays.deepToString(platform_pivots).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        double[][] delta_L1 = new double[6][3];

        delta_L1[0][0] = COORDS[0][3] - platform_pivots[0][0];
        delta_L1[1][0] = COORDS[1][3] - platform_pivots[1][0];
        delta_L1[2][0] = COORDS[2][3] - platform_pivots[2][0];
        delta_L1[3][0] = COORDS[3][3] - platform_pivots[3][0];
        delta_L1[4][0] = COORDS[4][3] - platform_pivots[4][0];
        delta_L1[5][0] = COORDS[5][3] - platform_pivots[5][0];

        delta_L1[0][1] = COORDS[6][3] - platform_pivots[0][1];
        delta_L1[1][1] = COORDS[7][3] - platform_pivots[1][1];
        delta_L1[2][1] = COORDS[8][3] - platform_pivots[2][1];
        delta_L1[3][1] = COORDS[9][3] - platform_pivots[3][1];
        delta_L1[4][1] = COORDS[10][3] - platform_pivots[4][1];
        delta_L1[5][1] = COORDS[11][3] - platform_pivots[5][1];

        delta_L1[0][2] = -1 * platform_pivots[0][2];
        delta_L1[1][2] = -1 * platform_pivots[1][2];
        delta_L1[2][2] = -1 * platform_pivots[2][2];
        delta_L1[3][2] = -1 * platform_pivots[3][2];
        delta_L1[4][2] = -1 * platform_pivots[4][2];
        delta_L1[5][2] = -1 * platform_pivots[5][2];

//        System.out.println(Arrays.deepToString(delta_L1).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        double[] delta_L2 = new double[6];

        delta_L2[0] = Math.sqrt(Math.pow(delta_L1[0][0], 2)+Math.pow(delta_L1[0][1], 2) + Math.pow(delta_L1[0][2], 2));
        delta_L2[1] = Math.sqrt(Math.pow(delta_L1[1][0], 2)+Math.pow(delta_L1[1][1], 2) + Math.pow(delta_L1[1][2], 2));
        delta_L2[2] = Math.sqrt(Math.pow(delta_L1[2][0], 2)+Math.pow(delta_L1[2][1], 2) + Math.pow(delta_L1[2][2], 2));
        delta_L2[3] = Math.sqrt(Math.pow(delta_L1[3][0], 2)+Math.pow(delta_L1[3][1], 2) + Math.pow(delta_L1[3][2], 2));
        delta_L2[4] = Math.sqrt(Math.pow(delta_L1[4][0], 2)+Math.pow(delta_L1[4][1], 2) + Math.pow(delta_L1[4][2], 2));
        delta_L2[5] = Math.sqrt(Math.pow(delta_L1[5][0], 2)+Math.pow(delta_L1[5][1], 2) + Math.pow(delta_L1[5][2], 2));

//        System.out.println(Arrays.toString(delta_L2));

        double[][] lmn_alfa = new double[6][5];

        //v4 saves compute time
        double v4 = Math.pow(L2, 2) - Math.pow(L1, 2);
        lmn_alfa[0][0] = Math.pow(delta_L2[0], 2) - v4;
        lmn_alfa[1][0] = Math.pow(delta_L2[1], 2) - v4;
        lmn_alfa[2][0] = Math.pow(delta_L2[2], 2) - v4;
        lmn_alfa[3][0] = Math.pow(delta_L2[3], 2) - v4;
        lmn_alfa[4][0] = Math.pow(delta_L2[4], 2) - v4;
        lmn_alfa[5][0] = Math.pow(delta_L2[5], 2) - v4;

        lmn_alfa[0][1] = 2 * L1 * platform_pivots[0][2];
        lmn_alfa[1][1] = 2 * L1 * platform_pivots[1][2];
        lmn_alfa[2][1] = 2 * L1 * platform_pivots[2][2];
        lmn_alfa[3][1] = 2 * L1 * platform_pivots[3][2];
        lmn_alfa[4][1] = 2 * L1 * platform_pivots[4][2];
        lmn_alfa[5][1] = 2 * L1 * platform_pivots[5][2];

        lmn_alfa[0][2] = 2 * L1 * (Math.cos(THETA_S[0] * Math.PI / 180) * (platform_pivots[0][0] - COORDS[0][3]) + Math.sin(THETA_S[0] * Math.PI / 180) * (platform_pivots[0][1] - COORDS[6][3]));
        lmn_alfa[1][2] = 2 * L1 * (Math.cos(THETA_S[1] * Math.PI / 180) * (platform_pivots[1][0] - COORDS[1][3]) + Math.sin(THETA_S[1] * Math.PI / 180) * (platform_pivots[1][1] - COORDS[7][3]));
        lmn_alfa[2][2] = 2 * L1 * (Math.cos(THETA_S[2] * Math.PI / 180) * (platform_pivots[2][0] - COORDS[2][3]) + Math.sin(THETA_S[2] * Math.PI / 180) * (platform_pivots[2][1] - COORDS[8][3]));
        lmn_alfa[3][2] = 2 * L1 * (Math.cos(THETA_S[3] * Math.PI / 180) * (platform_pivots[3][0] - COORDS[3][3]) + Math.sin(THETA_S[3] * Math.PI / 180) * (platform_pivots[3][1] - COORDS[9][3]));
        lmn_alfa[4][2] = 2 * L1 * (Math.cos(THETA_S[4] * Math.PI / 180) * (platform_pivots[4][0] - COORDS[4][3]) + Math.sin(THETA_S[4] * Math.PI / 180) * (platform_pivots[4][1] - COORDS[10][3]));
        lmn_alfa[5][2] = 2 * L1 * (Math.cos(THETA_S[5] * Math.PI / 180) * (platform_pivots[5][0] - COORDS[5][3]) + Math.sin(THETA_S[5] * Math.PI / 180) * (platform_pivots[5][1] - COORDS[11][3]));

        lmn_alfa[0][3] = Math.asin(lmn_alfa[0][0] / Math.hypot(lmn_alfa[0][1], lmn_alfa[0][2])) - Math.atan(lmn_alfa[0][2] / lmn_alfa[0][1]);
        lmn_alfa[1][3] = Math.asin(lmn_alfa[1][0] / Math.hypot(lmn_alfa[1][1], lmn_alfa[1][2])) - Math.atan(lmn_alfa[1][2] / lmn_alfa[1][1]);
        lmn_alfa[2][3] = Math.asin(lmn_alfa[2][0] / Math.hypot(lmn_alfa[2][1], lmn_alfa[2][2])) - Math.atan(lmn_alfa[2][2] / lmn_alfa[2][1]);
        lmn_alfa[3][3] = Math.asin(lmn_alfa[3][0] / Math.hypot(lmn_alfa[3][1], lmn_alfa[3][2])) - Math.atan(lmn_alfa[3][2] / lmn_alfa[3][1]);
        lmn_alfa[4][3] = Math.asin(lmn_alfa[4][0] / Math.hypot(lmn_alfa[4][1], lmn_alfa[4][2])) - Math.atan(lmn_alfa[4][2] / lmn_alfa[4][1]);
        lmn_alfa[5][3] = Math.asin(lmn_alfa[5][0] / Math.hypot(lmn_alfa[5][1], lmn_alfa[5][2])) - Math.atan(lmn_alfa[5][2] / lmn_alfa[5][1]);

        lmn_alfa[0][4] = Math.toDegrees(lmn_alfa[0][3]);
        lmn_alfa[1][4] = Math.toDegrees(lmn_alfa[1][3]);
        lmn_alfa[2][4] = Math.toDegrees(lmn_alfa[2][3]);
        lmn_alfa[3][4] = Math.toDegrees(lmn_alfa[3][3]);
        lmn_alfa[4][4] = Math.toDegrees(lmn_alfa[4][3]);
        lmn_alfa[5][4] = Math.toDegrees(lmn_alfa[5][3]);

//        System.out.println(Arrays.deepToString(lmn_alfa).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        return(new double[]{lmn_alfa[0][4], lmn_alfa[1][4], lmn_alfa[2][4], lmn_alfa[3][4], lmn_alfa[4][4], lmn_alfa[5][4]});
    }

}
