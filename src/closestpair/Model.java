package closestpair;

import java.util.*;

public class Model {

    // init 2 comparators
    private static final Comparator<Point> COMPARATOR_X = 
            (p1, p2) -> Double.compare(p1.getX(), p2.getY());
    private static final Comparator<Point> COMPARATOR_Y = 
            (p1, p2) -> Double.compare(p1.getY(), p2.getY());

    // brutal methods for 2 - 3 points
    public static Pair brutalForce(List<Point> points) {

        final int numPoints = points.size();
        if (numPoints < 2)
            return null;

        Pair pair = new Pair(points.get(0), points.get(1));

        if (numPoints > 2) {

            for (int i = 0; i < numPoints - 1; i++) {

                final Point point1 = points.get(i);
                for (int j = i + 1; j < numPoints; j++) {

                    final Point point2 = points.get(j);

                    if (new Pair(point1,point2).getDistance() < pair.getDistance()) {

                        pair = new Pair(point1, point2);
                    }
                }
            }
        }
        return pair;
    }

    /**
     * D & C wrapper
     * 1. divide left & right
     * 2. find closest pair left & right
     * 2. merge
     * @param p
     * @return 
     */
    public Pair divideAndConquer(Point[] p) {
        
        int n = p.length;
        List<Point> points = new ArrayList<>();
        
        for(int i = 0; i < n; i ++) {
            points.add(p[i]);
        }
        
        List<Point> pointsSortedByX = new ArrayList<>(points);
        Collections.sort(pointsSortedByX, COMPARATOR_X);
        List<Point> pointsSortedByY = new ArrayList<>(points);
        Collections.sort(pointsSortedByY,COMPARATOR_Y);

        return divideAndConquer(pointsSortedByX, pointsSortedByY);
    }

    private Pair divideAndConquer(List<Point> pointsSortedByX, List<Point> pointsSortedByY) {

        // use brutal force when 2 - 3 points
        final int numPoints = pointsSortedByX.size();
        if (numPoints <= 3)
            return brutalForce(pointsSortedByX);

        final int mid = numPoints / 2;

        List<Point> leftOfCenterSortedX = pointsSortedByX.subList(0, mid);
        List<Point> rightOfCenterSortedX = pointsSortedByX.subList(mid, numPoints);

        final Point midPoint = pointsSortedByX.get(mid);
        List<Point> leftOfCenterSortedY = new ArrayList<>();
        List<Point> rightOfCenterSortedY = new ArrayList<>();

        for (Point p : pointsSortedByY) {
            if (p.getX() < midPoint.getX()) {
                leftOfCenterSortedY.add(p);
            } else {
                rightOfCenterSortedY.add(p);
            }
        }

        Pair closestPair = divideAndConquer(leftOfCenterSortedX, leftOfCenterSortedY);
        final Pair closestPairRight = divideAndConquer(rightOfCenterSortedX, rightOfCenterSortedY);

        if (closestPairRight.getDistance() < closestPair.getDistance())
            closestPair = closestPairRight;
        final double shortestDistance = closestPair.getDistance();

        List<Point> strip = new ArrayList<>();
        for (Point point : pointsSortedByY) {
            if (Math.abs(midPoint.getX() - point.getX()) < shortestDistance)
                strip.add(point);
        }
        return stripClosest(strip, closestPair);
    }

    private Pair stripClosest(List<Point> strip, Pair closestPair) {
        for (int i = 0; i < strip.size() - 1; i++) {

            final Point point1 = strip.get(i);
            for (int j = i + 1; j < strip.size(); j++) {

                final Point point2 = strip.get(j);
                if ((point2.getY() - point1.getY()) >= closestPair.getDistance())
                    break;

                if (new Pair(point1,point2).getDistance() < closestPair.getDistance()) {

                    closestPair = new Pair(point1, point2);
                }
            }
        }
        return closestPair;
    }
}

// http://blog.csdn.net/xiazdong/article/details/8017250

// https://my.oschina.net/bgbfbsdchenzheng/blog/498823