import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.Stack;

public class FastCollinearPoints {
    private Stack<LineSegment> ls;
    private int count; // count line segments
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        Stack<Point> collinearStart = new Stack<Point>();
        Stack<Point> collinearEnd = new Stack<Point>();
        Stack<Point> collinearStartCp = new Stack<Point>();
        Stack<Point> collinearEndCp = new Stack<Point>();
        int label = 0;
        
        
        
        // exceptions
        if (points == null) throw new NullPointerException();
        Arrays.sort(points);
        if (points[points.length-1] == null) throw new NullPointerException();
        for (int i = 0; i < points.length-1; i++) {
            if (points[i] == null) throw new NullPointerException();
            else if (points[i+1].compareTo(points[i]) == 0) throw new IllegalArgumentException();
        }
        
        ls = new Stack<LineSegment>();
        
        // we need a defensive copy of points
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) pointsCopy[i] = points[i];
        
        for (int i = 0; i < pointsCopy.length; i++) {
            
            Arrays.sort(points, pointsCopy[i].slopeOrder()); // sort by slope. Same slope means collinear
            for (int k = 0; k < points.length-2; k++) {
                if (pointsCopy[i].slopeTo(points[k+2]) == pointsCopy[i].slopeTo(points[k])) { // is it possible to have a line segment?
                    int j = 2; 
                    if (k == points.length-3) j = 3; // corner case
                    else {
                        while (pointsCopy[i].slopeTo(points[k+j]) == pointsCopy[i].slopeTo(points[k])) 
                            j++; // greedy method to search for more collinear points
                    }
                    Point[] collinearPoints = new Point[j+1];
                    collinearPoints[0] = pointsCopy[i];
                    for (int p  = 0; p < j; p++) collinearPoints[p+1] = points[k+p]; //point j collinear points into an array
                    Arrays.sort(collinearPoints);
                    
                    // check duplicates
                    
                    if (collinearStart.isEmpty()) { // if first group push in and do no check
                        collinearStart.push(collinearPoints[0]);
                        collinearEnd.push(collinearPoints[j]);
                    }
                    else {
                        while (!collinearStart.isEmpty()) {
                            Point a = collinearStart.pop(); // check every point and push into another temporary stack
                            Point b = collinearEnd.pop();
                            if (collinearPoints[0].compareTo(a) == 0 // if duplicate, push into temp stack and break
                                    && collinearPoints[j].compareTo(b) == 0) {
                                collinearStartCp.push(a);
                                collinearEndCp.push(b);
                                label = 1;
                                break;
                            }
                            collinearStartCp.push(a);
                            collinearEndCp.push(b);
                            collinearStartCp.push(collinearPoints[0]); // otherwise just retrieve the poped values
                            collinearEndCp.push(collinearPoints[j]);
                        }
                    }
                    while (!collinearStartCp.isEmpty()) {
                        collinearStart.push(collinearStartCp.pop());
                        collinearEnd.push(collinearEndCp.pop());
                    }
                    if (label == 0) {
                        ls.push(new LineSegment(collinearPoints[0], collinearPoints[j])); // push in a new line segment
                        count++; 
                    }
                    label = 0;
                    break;
                }
            }
        }
    }
    public int numberOfSegments()        // the number of line segments
    { return count; }
    
    public LineSegment[] segments() {               // the line segments
        LineSegment[] ls2 = new LineSegment[count];
        
        Stack<LineSegment> lsCopy = new Stack<LineSegment>();
        for (int i = 0; i < count; i++) {
            LineSegment ll = ls.pop();
            ls2[i] = ll;
            lsCopy.push(ll);
        }
        for (int i = 0; i < count; i++) ls.push(lsCopy.pop());
        return ls2;
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