public class Main {
    public static void main(String[] args) {
        Point origin = new Point(0.0,0.0,0.0,0.0,0.0,0.0);
        Point one = new Point(0.0,1.0,2.0);
        System.out.println(one.distanceTo(origin));
    }
}
