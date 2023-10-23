import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;

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

    public static void main(String[] args) {

    }
}
