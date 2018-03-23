/*
 * point
 */
package closestpair;

/**
 *
 * @author Wang Zheng-Yu <zhengyuw@kth.se>
 */
public class Point {

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    private double x;
    private double y;
}
