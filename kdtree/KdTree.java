import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private int size;
    private Node root;

    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the rectangle
        private Node lb; // left bottom
        private Node rt; // right top

        public Node(Point2D point) {
            this.p = point;
        }
    }

    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        root = insert(root, point);
    }

    private Node insert(Node x, Point2D point) {
        // if added new Node then
        if (root == null) {
            root = new Node(point);
            size++;
            return x;
        }
        return x;
    }

    public boolean contains(Point2D p) {

        return false;
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> list = new ArrayList<>();
        return list;
    }

    public Point2D nearest(Point2D point) {
        if (isEmpty()) {
            return null;
        }
        Point2D min = point;
        return min;
    }


    public static void main(String[] args) {

    }
}
