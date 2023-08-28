/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("null pointer");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("null value");
            }
        }
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("repeated points");
            }
        }

        segments = new ArrayList<>();
        for (Point pivot : points) {
            Point[] copy = points.clone();
            Arrays.sort(copy, pivot.SLOPE_ORDER);
            LinkedList<Point> collinear = new LinkedList<>();

            int i = 0;
            double slope = 0.0;
            for (Point point : copy) {
                if (pivot.slopeTo(point) != slope || i + 1 == copy.length) {
                    if (collinear.size() >= 4) {
                        // if (pivot.compareTo(point) > 0) {
                        LineSegment segment = new LineSegment(pivot, point);
                        segments.add(segment);
                        //}
                    }
                    collinear.clear();
                    collinear.add(pivot);
                }
                collinear.add(point);
                slope = pivot.slopeTo(point);
                i++;
            }
        }

    }

    public int numberOfSegments() {        // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {               // the line segments
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
