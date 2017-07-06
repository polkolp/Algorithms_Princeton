import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

public class KdTree {
    private Node root;
    private class Node 
    {
        private final Point2D p;
        private final RectHV r;
        private int size;
        private Node left, right;
        public Node(Point2D pp, RectHV rr, int N) {
            this.p = pp;
            this.r = rr; // denote the rect represented by the node
            this.size = N;
        }
    }
    public         KdTree() { }                               // construct an empty set of points 
    public           boolean isEmpty()                      // is the set empty? 
    { return size(root) == 0; }
    public               int size()                         // number of points in the set 
    { return size(root); }
    private int size(Node n) {
        if (n == null) return 0;
        return n.size;
    }
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    { 
        RectHV rect = new RectHV(0,0,1,1); // the initial rect
        root = insert(root, p, rect, 0); 
    }
    private Node insert(Node node, Point2D point, RectHV rect, int depth)
    {         
        if (node == null) return new Node(point, rect, 1);
        
        if (depth%2 == 0) {
            if (point.x() < node.p.x()) {
                rect = new RectHV(rect.xmin(),rect.ymin(),node.p.x(),rect.ymax()); 
                node.left = insert(node.left, point, rect, depth+1); // notice cannot use depth++ here. A ton of trouble.
            }
            else if (point.x() > node.p.x()) {
                rect = new RectHV(node.p.x(),rect.ymin(),rect.xmax(),rect.ymax()); // update rect
                node.right = insert(node.right, point, rect, depth+1);
            }
        }
        else {
            
            if (point.y() < node.p.y()) {
                rect = new RectHV(rect.xmin(),rect.ymin(),rect.xmax(),node.p.y()); // update rect
                node.left = insert(node.left, point, rect, depth+1);
            }
            else if (point.x() > node.p.x()) {
                rect = new RectHV(rect.xmin(),node.p.y(),rect.xmax(),rect.ymax()); // update rect
                node.right = insert(node.right, point, rect, depth+1);
            }
        }
        node.size = size(node.left)+size(node.right)+1;
        return node;
    }
    public           boolean contains(Point2D p)            // does the set contain point p? 
    {
        return contains(root, p, 0);
    }
    
    private boolean contains(Node n, Point2D p, int depth) {
        if (n == null) return false;
        
        if (depth%2 == 0) {
            depth++;
            if (p.x() < n.p.x()) return contains(n.left, p, depth);
            else if (p.x() > n.p.x()) return contains(n.right, p, depth);
            else return true;
        }
        else {
            depth++;
            if (p.y() < n.p.y()) return contains(n.left, p, depth);
            else if (p.y() > n.p.y()) return contains(n.right, p, depth);
            else return true;
        }
    }
    public              void draw()                         // draw all points to standard draw
    { 
// list all keys
        StdDraw.clear(); // add this line
        draw(root, 0);  
    }
    private void draw(Node n, int depth) {
        if (n != null) { // This is important to prevent null pointer error
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            n.p.draw();
            if (depth%2 == 0) {
                StdDraw.setPenRadius(0.005);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.p.x(), n.r.ymin(), n.p.x(), n.r.ymax());
            }
            else {
                StdDraw.setPenRadius(0.005);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.r.xmin(), n.p.y(), n.r.xmax(), n.p.y());
            }
            
            draw(n.left, depth+1);
            draw(n.right, depth+1);
        }
    }
    
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        Stack<Point2D> s = new Stack<Point2D>();
        return range(root, rect, s);
    }
    private Stack<Point2D> range(Node n, RectHV rect, Stack<Point2D> s) { // return has to be void due to recursive structure
        if (n == null) return null;
        if (n.r.intersects(rect) && rect.contains(n.p)) {
            s.push(n.p);
            range(n.left, rect, s);
            range(n.right, rect, s);
        }
        return s;
    }
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    { 
        if (isEmpty()) return null;
        Point2D min = null; 
        return nearest(root, p, min);
    }
    private Point2D nearest(Node n, Point2D p, Point2D min)
    {
        if (n == null) return null;
        double newDistance = n.r.distanceTo(p);
        if (min == null || newDistance < p.distanceTo(min)) {
            min = n.p;
            // pruning rule to go left
            if (n.left != null && n.left.r.contains(p)) {
                min = nearest(n.left, p, min);
                min = nearest(n.right, p, min);
            }
            else {
                min = nearest(n.right, p, min);
                min = nearest(n.left, p, min);
            }
            
        }
        return min;
    }
    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        
        
        double x = 0.5;
        double y = 0.5;
        StdOut.printf("%8.6f %8.6f\n", x, y);
        Point2D p = new Point2D(x, y);
        if (rect.contains(p)) {
            StdOut.printf("%8.6f %8.6f\n", x, y);
            kdtree.insert(p);
            StdDraw.clear();
            kdtree.draw();
            StdDraw.show();
        }
        StdDraw.pause(50);
        
        x = 0.3;
        y = 0.3;
        StdOut.printf("%8.6f %8.6f\n", x, y);
        p = new Point2D(x, y);
        if (rect.contains(p)) {
            StdOut.printf("%8.6f %8.6f\n", x, y);
            kdtree.insert(p);
            StdDraw.clear();
            kdtree.draw();
            StdDraw.show();
        }
        StdDraw.pause(50);
        
        x = 0.7;
        y = 0.7;
        StdOut.printf("%8.6f %8.6f\n", x, y);
        p = new Point2D(x, y);
        if (rect.contains(p)) {
            StdOut.printf("%8.6f %8.6f\n", x, y);
            kdtree.insert(p);
            StdDraw.clear();
            kdtree.draw();
            StdDraw.show();
        }
        StdDraw.pause(50);
        
    }
}