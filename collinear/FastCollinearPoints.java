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
import java.util.Collections;
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
        Point[] copy = points.clone();
        for (Point pivot : points) {
            Arrays.sort(copy, pivot.SLOPE_ORDER);
            LinkedList<Point> collinear = new LinkedList<>();

            int i = 0;
            double prevSlope = 0.0;
            for (Point point : copy) {
                if (pivot.slopeTo(point) != prevSlope || i + 1 == copy.length) {
                    if (pivot.slopeTo(point) == prevSlope) {
                        collinear.add(point);
                    }
                    if (collinear.size() >= 4) {
                        Collections.sort(collinear);
                        if (pivot == collinear.getFirst()) {
                            LineSegment segment = new LineSegment(collinear.getFirst(),
                                                                  collinear.getLast());
                            segments.add(segment);
                        }
                    }
                    collinear.clear();
                    collinear.add(pivot);
                }
                collinear.add(point);
                prevSlope = pivot.slopeTo(point);
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
