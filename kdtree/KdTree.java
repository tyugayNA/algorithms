import edu.princeton.cs.algs4.RectHV;

import java.awt.geom.Point2D;

public class KdTree {

    public KdTree() {

    }

    public boolean isEmpty() {
        return false;
    }

    public int size() {
        return 0;
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
    }

    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the rectangle
        private Node lb; // left bottom
        private Node rt; // right top
    }

    public static void main(String[] args) {

    }
}
