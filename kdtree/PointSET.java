import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class PointSET {
    private SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        if (!points.contains(point)) {
            points.add(point);
        }
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> list = new ArrayList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                list.add(p);
            }
        }
        return list;
    }

    public Point2D nearest(Point2D point) {
        if (isEmpty()) {
            return null;
        }
        Point2D min = point;
        double distance = Double.MAX_VALUE;

        for (Point2D p : points) {
            if (distance > point.distanceTo(p)) {
                distance = point.distanceTo(p);
                min = p;
            }
        }
        return min;
    }

    public static void main(String[] args) {

    }
}
