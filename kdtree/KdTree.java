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
            size++;
            return;
        }
        if (!contains(point)) {
            root = insertX(root, point);
        }
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
        if (point == null) {
            throw new IllegalArgumentException("");
        }
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
        StdDraw.setPenRadius(0.02);
        StdDraw.point(node.p.x(), node.p.y());
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        drawY(node.lb);
        drawY(node.rt);

    }

    private void drawY(Node node) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(node.p.x(), node.p.y());
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        drawX(node.lb);
        drawX(node.rt);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect is null");
        }
        List<Point2D> list = new ArrayList<>();
        helperRange(rect, root, list);
        return list;
    }

    private void helperRange(RectHV rect, Node node, List<Point2D> list) {
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
        Point2D nearestPoint = root.p;
        return helperNearest(point, root, nearestPoint);

    }

    private Point2D helperNearest(Point2D point, Node node, Point2D nearestPoint) {
        if (node == null) {
            return nearestPoint;
        }
        if (point.distanceSquaredTo(nearestPoint) > point.distanceSquaredTo(node.p)) {
            nearestPoint = node.p;
        }
        if (node.lb != null && node.lb.rect.distanceSquaredTo(point) < nearestPoint.distanceSquaredTo(point)) {
            nearestPoint = helperNearest(point, node.lb, nearestPoint);
        }
        if (node.rt != null && node.rt.rect.distanceSquaredTo(point) < nearestPoint.distanceSquaredTo(point)) {
            nearestPoint = helperNearest(point, node.rt, nearestPoint);
        }
        return nearestPoint;
    }


    public static void main(String[] args) {

        KdTree tree = new KdTree();

        /*
        tree.insert(new Point2D(0.372, 0.497));
        tree.insert(new Point2D(0.564, 0.413));
        tree.insert(new Point2D(0.226, 0.577));
        tree.insert(new Point2D(0.144, 0.179));
        tree.insert(new Point2D(0.083, 0.510));
        tree.insert(new Point2D(0.320, 0.708));
        tree.insert(new Point2D(0.417, 0.362));
        tree.insert(new Point2D(0.862, 0.825));
        tree.insert(new Point2D(0.785, 0.725));
        tree.insert(new Point2D(0.499, 0.208));
         */

        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));

        tree.draw();

        //Point2D point = new Point2D(0.623, 0.086);
        Point2D point = new Point2D(0.696, 0.836);
        Point2D nearestPoint = tree.nearest(point);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.03);
        point.draw();

    }
}
