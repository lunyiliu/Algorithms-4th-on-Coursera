import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

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
public class KdTree {
    private int NodeNum;
    private Node root;

    private class Node {
        public Point2D point;
        public RectHV rect;
        public Node lb;
        public Node rt;

        public Node(Point2D point_, RectHV rect_, Node lb_, Node rt_) {
            point = point_;
            rect = rect_;
            lb = lb_;
            rt = rt_;
            // Vertical=true;
        }
    }

    // construct an empty set of points
    public KdTree() {
        NodeNum = 0;
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return NodeNum == 0;
    }

    // number of points in the set
    public int size() {
        return NodeNum;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            root = new Node(p, new RectHV(0, 0, 1, 1), null, null);
            NodeNum++;
        }
        else {
            root = insert(p, root, true, new RectHV(0, 0, 1, 1));
        }
    }

    private Node insert(Point2D p, Node x, boolean IsVertical, RectHV rect) {
        if (x == null) {
            NodeNum++;
            return new Node(p, rect, null, null);
        }
        else {
            if (IsVertical) {
                if (p.x() < x.point.x()) {
                    x.lb = insert(p, x.lb, false,
                                  new RectHV(rect.xmin(), rect.ymin(), x.point.x(), rect.ymax()));
                    //this new rect pra is the rect of its children,but rect is its father's rect
                }
                else {
                    if (!p.equals(x.point)) {
                        x.rt = insert(p, x.rt, false,
                                      new RectHV(x.point.x(), rect.ymin(), rect.xmax(),
                                                 rect.ymax()));
                    }
                    else {
                        return x;
                    }
                }
            }
            else {
                if (p.y() < x.point.y()) {
                    x.lb = insert(p, x.lb, true,
                                  new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.point.y()));
                }
                else {
                    if (!p.equals(x.point)) {
                        x.rt = insert(p, x.rt, true,
                                      new RectHV(rect.xmin(), x.point.y(), rect.xmax(),
                                                 rect.ymax()));
                    }
                    else {
                        return x;
                    }
                }
            }
            return x;//this x is the left (or right) subtree of node in last loop, which means changes of x here effect every node in the process.
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return false;
        }
        else {
            return contains(p, root, true);
        }
    }

    private boolean contains(Point2D p, Node x, boolean IsVertical) {
        if (x == null) {
            return false;
        }
        else {
            if (p.equals(x.point)) return true;
            if (IsVertical) {
                if (p.x() < x.point.x()) {
                    return contains(p, x.lb, false);
                }
                else {
                    if (p.x() >= x.point.x()) {
                        return contains(p, x.rt, false);
                    }
                }
            }
            else {
                if (p.y() < x.point.y()) {
                    return contains(p, x.lb, true);
                }
                else {
                    if (p.y() >= x.point.y()) {
                        return contains(p, x.rt, true);
                    }
                }
            }
        }
        return true;
    }

    // draw all points to standard draw
    public void draw() {
        if (root != null) {
            draw(root, true);
        }
    }

    private void draw(Node x, boolean IsVertical) {
        StdDraw.setPenRadius(0.01);
        if (IsVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.point.x(), x.rect.ymin(), x.point.x(), x.rect.ymax());

        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.point.y(), x.rect.xmax(), x.point.y());

        }
        if (x.lb != null) {
            draw(x.lb, !IsVertical);
        }
        if (x.rt != null) {
            draw(x.rt, !IsVertical);
        }
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLACK);
        x.point.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> points = new Stack<Point2D>();
        if (root == null) {
            return points;
        }
        else {
            range(rect, points, root);
        }
        return points;
    }

    private void range(RectHV rect, Stack<Point2D> contains, Node x) {
        if (rect.contains(x.point)) {
            contains.push(x.point);
        }
        if (x.lb != null) {
            if (rect.intersects(x.lb.rect)) {
                range(rect, contains, x.lb);
            }
        }
        if (x.rt != null) {
            if (rect.intersects(x.rt.rect)) {
                range(rect, contains, x.rt);
            }
        }

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }
        else {
            Point2D champion = root.point;
            return nearest(p, root, champion);
        }
    }

    private Point2D nearest(Point2D p, Node x, Point2D champion) {
        if (p.distanceTo(x.point) < p.distanceTo(champion)) {
            champion = x.point;
        }
        if (x.lb != null && x.lb.rect.contains(p)) {
            champion = nearest(p, x.lb, champion);
            if (x.rt != null && x.rt.rect.distanceTo(p) < p.distanceTo(champion)) {
                champion = nearest(p, x.rt, champion);
            }
        }
        else {
            if (x.rt != null) {
                champion = nearest(p, x.rt, champion);
            }
            if (x.lb != null && x.lb.rect.distanceTo(p) < p.distanceTo(champion)) {
                champion = nearest(p, x.lb, champion);
            }
        }
        return champion;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        /*
        KdTree points = new KdTree();
        String filename = args[0];
        In in = new In(filename);
        //PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            points.insert(p);
            //brute.insert(p);
        }
        points.draw();
        StdDraw.setPenColor(StdDraw.RED);
        points.nearest(new Point2D(0.1, 0.1)).draw();

        //StdOut.println(points.contains(new Point2D(0.5, 0.2)));
        */
    }
}
