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
        root = insertX(root, point);
    }

    private Node insertX(Node node, Point2D point) {
        if (node == null) {
            size++;
            return new Node(point);
        }
        if (point.x() < node.p.x()) {
            node.lb = insertY(node.lb, point);
        } else if (point.x() > node.p.x()) {
            node.rt = insertY(node.rt, point);
        }
        return node;
    }

    private Node insertY(Node node, Point2D point) {
        if (node == null) {
            size++;
            return new Node(point);
        }
        if (point.y() < node.p.y()) {
            node.lb = insertX(node.lb, point);
        } else if (point.y() > node.p.y()) {
            node.rt = insertX(node.rt, point);
        }
        return node;
    }

    public boolean contains(Point2D point) {
        return containX(root, point);
    }

    private boolean containX(Node node, Point2D point) {
        if (node == null) {
            return false;
        }
        if (point.x() < node.p.x()) {
            return containY(node.lb, point);
        } else if (point.x() > node.p.x()) {
            return containY(node.rt, point);
        } else {
            return containY(node, point);
        }
    }

    private boolean containY(Node node, Point2D point) {
        if (node == null) {
            return false;
        }
        if (point.y() < node.p.y()) {
            return containX(node.lb, point);
        } else if (point.y() > node.p.y()) {
            return containX(node.rt, point);
        } else {
            return containX(node, point);
        }
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
