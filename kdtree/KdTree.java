import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

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
        if (root == null) {
            root = new Node(point);
            root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            return;
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
            node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else if (point.x() >= node.p.x()) {
            node.rt = insertY(node.rt, point);
            node.rt.rect = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
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
            node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
        } else if (point.y() >= node.p.y()) {
            node.rt = insertX(node.rt, point);
            node.rt.rect = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
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
        if (node.p.equals(point)) {
            return true;
        }
        if (point.x() < node.p.x()) {
            return containY(node.lb, point);
        } else {
            return containY(node.rt, point);
        }
    }

    private boolean containY(Node node, Point2D point) {
        if (node == null) {
            return false;
        }
        if (node.p.equals(point)) {
            return true;
        }
        if (point.y() < node.p.y()) {
            return containX(node.lb, point);
        } else {
            return containX(node.rt, point);
        }
    }

    public void draw() {
        drawX(root);
    }

    private void drawX(Node node) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        drawY(node.lb);
        drawY(node.rt);

    }

    private void drawY(Node node) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        drawX(node.lb);
        drawX(node.rt);
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> list = new ArrayList<>();

        helperRange(rect, root, list);

        return list;
    }

    private void helperRange(RectHV rect, Node node, List<Point2D> list) {
        if (rect == null) {
            throw new IllegalArgumentException("rect is null");
        }
        if (node == null) {
            return;
        }
        if (!node.rect.intersects(rect)) {
            return;
        } else {
            if (rect.contains(node.p)) {
                list.add(node.p);
            }
            helperRange(rect, node.lb, list);
            helperRange(rect, node.rt, list);
        }
    }

    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException("point is null");
        }
        if (isEmpty()) {
            return null;
        }
        double distance = Double.MAX_VALUE;
        return helperNearest(point, root, distance);
    }

    private Point2D helperNearest(Point2D point, Node node, double distance) {
        
        return null;
    }


    public static void main(String[] args) {
        KdTree tree = new KdTree();
        Point2D p1 = new Point2D(0.4, 0.6);
        Point2D p2 = new Point2D(0.2, 0.4);
        Point2D p3 = new Point2D(0.7, 0.8);
        Point2D p4 = new Point2D(0.7, 0.5);
        Point2D p5 = new Point2D(0.3, 0.8);
        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);
        StdOut.println(tree.contains(p1));
        StdOut.println(tree.contains(p2));
        StdOut.println(tree.contains(p3));
        StdOut.println(tree.contains(p4));
        StdOut.println(tree.contains(p5));
        tree.draw();
    }
}
