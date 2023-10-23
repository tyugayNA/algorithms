import java.awt.geom.Point2D;
import java.util.TreeSet;

public class KdTree {
    private TreeSet<Point2D> points;

    public KdTree() {
        points = new TreeSet<>();
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
