package helper;

public class Plane {
    //
    //represents translation / rotation from master plane
    private final Point fromBase;

    public Plane() {
        fromBase = new Point(0.0,0.0,0.0,0.0,0.0,0.0);
    }

    public Plane(Point thisFromBase) {
        fromBase = thisFromBase;
    }

    public double getNormalDistance(Point toPoint) {
        toPoint.rotateAboutZ(Math.toDegrees(fromBase.getAngleTo(270)));
        fromBase.rotateToZ(270);
        System.out.println(toPoint.getPoint());
        return(Math.abs(fromBase.getPoint().get(1) - toPoint.getPoint().get(1)));
    }
}