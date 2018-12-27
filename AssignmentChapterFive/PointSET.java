/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> PointBST;

    // construct an empty set of points
    public PointSET() {
        PointBST = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return PointBST.isEmpty();
    }

    // number of points in the set
    public int size() {
        return PointBST.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p != null) {
            PointBST.add(p);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p != null) {
            return PointBST.contains(p);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // draw all points to standard draw
    public void draw() {

        for (Point2D point : PointBST) {
            point.draw();
            // StdDraw.text(point.x(), point.y(), String.format("%f,%f", point.x(), point.y()));
            StdDraw.show();
        }

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> Points = new Stack<Point2D>();
        for (Point2D point : PointBST) {
            if (rect.contains(point)) {
                Points.push(point);
            }
        }
        return Points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Point2D champion = null;
        for (Point2D point : PointBST) {
            if (champion == null) {
                champion = point;
            }
            else {
                if (p.distanceTo(point) < p.distanceTo(champion)) {
                    champion = point;
                }
            }
        }
        return champion;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {


    }
}
