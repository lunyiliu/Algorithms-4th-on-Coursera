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
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private class MyLineSegment {
        public Point p;   // one endpoint of this line segment
        public Point q;   // the other endpoint of this line segment

        /**
         * Initializes a new line segment.
         *
         * @param p one endpoint
         * @param q the other endpoint
         * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt> is <tt>null</tt>
         */
        public MyLineSegment(Point p, Point q) {
            if (p == null || q == null) {
                throw new NullPointerException("argument is null");
            }
            this.p = p;
            this.q = q;
        }
    }

    private Point[] Points;
    private int num;
    private LineSegment[] LS;
    private MyLineSegment[] MyLS;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
    }

    // the number of line segments
    public int numberOfSegments() {
        return num;
    }

    // the line segments
    public LineSegment[] segments() {
        LS = new LineSegment[Points.length * (Points.length - 1) / 2];
        MyLS = new MyLineSegment[Points.length * (Points.length - 1) / 2];
        //for (int i = 0; i < Points.length; i++) {
        for (int i = 0; i < Points.length; i++) {
            boolean Repeated = false;
            int StartIndex = 0;
            int EndIndex = 0;
            int AdjacentFlag = 0;
            Point[] Points_ = new Point[Points.length];
            //if (Points[i] != null) {
            for (int g = 0; g < Points.length; g++) {
                //Points_ = Points;//java是引用赋值。。。
                Points_[g] = Points[g];
            }
            Arrays.sort(Points_, Points_[i].slopeOrder());
/*
            for (int g = 0; g < Points_.length; g++) {
                StdOut.println(Points_[g]);
            }
            StdOut.println(" ");
            */
/*
            StdOut.println(Points_[0].slopeTo(Points_[4]));
            StdOut.println(Points_[0].slopeTo(Points_[5]));
            */
            for (int j = 0; j < Points_.length - 1; j++) {
/*
                StdOut.println(StartIndex);
                StdOut.println(EndIndex);
                StdOut.println(AdjacentFlag);
                StdOut.println(" ");
                */

                if (Points_[0].slopeTo(Points_[j]) == Points_[0].slopeTo(Points_[j + 1])) {
                    AdjacentFlag++;

                    if (AdjacentFlag == 1) {
                        StartIndex = j;
                    }
                }

                else {
                    if (AdjacentFlag >= 2) {
                        EndIndex = j;
                        break;

                    }
                    else {
                        AdjacentFlag = 0;
                        StartIndex = 0;
                    }
                }
/*
                StdOut.println(StartIndex);
                StdOut.println(EndIndex);
                StdOut.println(AdjacentFlag);
                StdOut.println(" ");
                */
                if (AdjacentFlag >= 2) {
                    EndIndex = j + 1;
                }
            }
            // }
            if (AdjacentFlag < 2) {
                continue;
            }
/*
            StdOut.println(StartIndex);
            StdOut.println(EndIndex);
            StdOut.println(AdjacentFlag);
            */
            //StdOut.println(AdjacentFlag);
            int SegmentArray[] = new int[AdjacentFlag + 2];
            int SegNum = 0;
            for (int l = StartIndex; l <= EndIndex; l++) {
                SegmentArray[SegNum++] = l;
            }

            SegmentArray[SegNum] = 0;

            //StdOut.println(" ");
            int swap;
            for (int m = SegmentArray.length - 1; m > 0; m--) {
                for (int n = 0; n < m; n++) {
                    if (Points_[SegmentArray[n]]
                            .compareTo(Points_[SegmentArray[n + 1]]) < 0) {
                        swap = SegmentArray[n];
                        SegmentArray[n] = SegmentArray[n + 1];
                        SegmentArray[n + 1] = swap;
                    }
                }
            }
            /*
            for (int g = 0; g < SegmentArray.length; g++) {
                StdOut.println(SegmentArray[g]);
            }
            */
            //StdOut.println(num);
            //if (num != 0) {
            for (int c = 0; c < num; c++) {
                if ((MyLS[c].p.compareTo(Points_[SegmentArray[0]]) == 0
                        && MyLS[c].q.compareTo(Points_[SegmentArray[SegNum]]) == 0) || (
                        MyLS[c].p.compareTo(Points_[SegmentArray[SegNum]]) == 0
                                && MyLS[c].q.compareTo(Points_[SegmentArray[0]]) == 0))
                    Repeated = true;
            }
            //  }
            //StdOut.println(Repeated);
            if (!Repeated) {
                MyLS[num] = new MyLineSegment(Points_[SegmentArray[0]],
                                              Points_[SegmentArray[SegNum]]);
                LS[num++] = new LineSegment(Points_[SegmentArray[0]],
                                            Points_[SegmentArray[SegNum]]);
            }
            /*
            for (int k = 0; k < SegNum; k++) {
                Points[SegmentArray[k]] = null;
            }
            */
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

        // Arrays.sort(points, points[1].slopeOrder());
        // for (int i = 0; i < n; i++) {
        //  StdOut.println(points[i]);
        //  }


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
*/
    }
}
