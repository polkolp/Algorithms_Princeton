import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Stack;

public class PointSET { // insert and contains logN, nearest and range N.Implement using a redblack BST 
    private RedBlackBST<Point2D, Integer> rb;
    public         PointSET()                               // construct an empty set of points 
    { rb = new RedBlackBST<Point2D, Integer>(); }
    public           boolean isEmpty()                      // is the set empty?
    { return rb.isEmpty(); }
    public               int size()                         // number of points in the set 
    { return rb.size(); }
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        Point2D pointCopy = p;
        if (!contains(pointCopy)) rb.put(pointCopy,1); // let's see how to use continue
        
    }
    public           boolean contains(Point2D p)            // does the set contain point p? 
    {
        return rb.contains(p);
    }
    public              void draw()                         // draw all points to standard draw 
    {
        for (Point2D p: rb.keys()) {
            p.draw();
        }
        //  StdDraw.show(); // might need this
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        Stack<Point2D> s = new Stack<Point2D>(); // if doesnt work use rank
        RectHV rect2 = rect;
        for (Point2D p: rb.keys()) {
            if (rect2.contains(p)) s.push(p);       
        }
        return s;
    }
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        double dist = 99999;
        Point2D nearest = null;
        for (Point2D pIter:rb.keys()) {
            if (p.distanceTo(pIter) < dist) {
                dist = p.distanceTo(pIter);
                nearest = pIter;
            }
        }
        return nearest;
    }
    
    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        int iter = 100;
        PointSET ps = new PointSET();
        StdOut.println(ps.isEmpty());
        for (int i = 1; i < iter; i++) {
            double x = StdRandom.uniform();
            double y = StdRandom.uniform();
            Point2D p = new Point2D(x, y);
            ps.insert(p);
        }
        StdOut.println(ps.size());
        ps.draw();
        RectHV rc = new RectHV(0.1,0.1,0.5,0.5);
        rc.draw();
        for (Point2D p: ps.range(rc)) {
            StdDraw.circle(p.x(), p.y(), 0.01);
        }

    
    
    }
}