import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Stack;

public class PointSET { // insert and contains logN, nearest and range N.Implement using a redblack BST 
    private Stack pointStack;
    public         PointSET()                               // construct an empty set of points 
    { pointStack = new Stack(); }
    public           boolean isEmpty()                      // is the set empty?
    { return pointStack.isEmpty(); }
    public               int size()                         // number of points in the set 
    { 
        int count = 0;
        Stack psCopy = pointStack; 
        while (!psCopy.isEmpty()) {
            psCopy.pop();
            size++;
        }
        return size;
    }
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
    if (!contains(p)) continue; // let's see how to use continue
    
    }
        public           boolean contains(Point2D p)            // does the set contain point p? 
        public              void draw()                         // draw all points to standard draw 
        public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
        public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
        
        public static void main(String[] args)                  // unit testing of the methods (optional) 
}