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

import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private Point[] Points;
    private int num;
    private LineSegment[] LS;

    /*
    private int flag;
    private void Add(Point a, Point b) {
        if (flag < LS.length) {
            LS[flag++] = new LineSegment(a, b);
        }
        else {
            LS.length= LS.length*2;
        }
    }
*/
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j].compareTo(points[i]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }
        Points = points;
        num = 0;
        //flag = 0;
    }


    // the number of line segments
    public int numberOfSegments() {
        return num;
    }

    // the line segments
    public LineSegment[] segments() {
        LS = new LineSegment[Points.length * (Points.length - 1) / 2];
        for (int i = 0; i < Points.length; i++) {
            for (int j = i + 1; j < Points.length; j++) {
                for (int k = j + 1; k < Points.length; k++) {
                    for (int l = k + 1; l < Points.length; l++) {
                        boolean flag_LS = true;
                        //if (Points[i].slopeTo(Points[j]) == Points[k].slopeTo(Points[l])) {
                        int SegmentArray[] = { i, j, k, l };
                        //To check through a full arrangement, use 24 different combinations of val[0], val[1], m ,n as indexs
                        for (int m = 0; m < 4; m++) {
                            for (int n = m + 1; n < 4; n++) {
                                int[] val = new int[2];
                                int fg = 0;
                                for (int p = 0; p < 4; p++) {
                                    if (p != m && p != n) {
                                        val[fg++] = p;
                                    }
                                }
                                if (Points[SegmentArray[m]].slopeTo(Points[SegmentArray[n]])
                                        != Points[SegmentArray[val[0]]]
                                        .slopeTo(Points[SegmentArray[val[1]]])) {
                                    flag_LS = false;
                                    break;
                                }
                                if (Points[SegmentArray[m]].slopeTo(Points[SegmentArray[n]])
                                        != Points[SegmentArray[val[1]]]
                                        .slopeTo(Points[SegmentArray[val[0]]])) {
                                    flag_LS = false;
                                    break;
                                }
                            }
                        }
                        /*
                        for (int m = 3; m > 0; m--) {
                            if (!flag_m) {
                                break;
                            }
                            for (int n = 0; n < m; n++) {

                                if (Points[SegmentArray[n]]
                                        .compareTo(Points[SegmentArray[n + 1]]) < 0) {
                                    swap = SegmentArray[n];
                                    SegmentArray[n] = SegmentArray[n + 1];
                                    SegmentArray[n + 1] = swap;
                                    if (Points[SegmentArray[0]].slopeTo(Points[SegmentArray[1]])
                                            != Points[SegmentArray[2]]
                                            .slopeTo(Points[SegmentArray[3]])) {
                                        flag_LS = false;
                                        flag_m = false;
                                        break;
                                    }
                                }
                            }
                        }
                        */
                        if (flag_LS) {
                            int swap;
                            for (int m = 3; m > 0; m--) {
                                for (int n = 0; n < m; n++) {
                                    if (Points[SegmentArray[n]]
                                            .compareTo(Points[SegmentArray[n + 1]]) < 0) {
                                        swap = SegmentArray[n];
                                        SegmentArray[n] = SegmentArray[n + 1];
                                        SegmentArray[n + 1] = swap;
                                    }
                                }
                            }
                            LS[num++] = new LineSegment(Points[SegmentArray[0]],
                                                        Points[SegmentArray[3]]);
                        }
                        //}
                    }
                }
            }
        }
        LineSegment[] LS_ = new LineSegment[num];
        for (int i = 0; i < num; i++) {
            LS_[i] = LS[i];
        }
        return LS_;
    }

    public static void main(String[] args) {
/*
        In in = new In(args[0]);
        // read the n points from a file
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
*/
    }
}
