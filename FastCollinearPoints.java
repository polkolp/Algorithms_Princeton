import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.Stack;

public class FastCollinearPoints {
    private Stack<LineSegment> ls;
    private int count; // count line segments
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        ls = new Stack<LineSegment>(); //create a stack ls to store line segments
        
        // we need a defensive copy of points
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; i++) pointsCopy[i] = points[i];
        
        for (int i = 0; i < pointsCopy.length; i++) {
            
            Arrays.sort(points, pointsCopy[i].slopeOrder()); // sort by slope. Same slope means collinear
            for (int k = 0; k < points.length-2; k++){
                if (pointsCopy[i].slopeTo(points[k+2]) == pointsCopy[i].slopeTo(points[k])) { // is it possible to have a line segment?
                    int j = 2; 
                    if (k == points.length-3) j = 3; // corner case
                    else {
                        while (pointsCopy[i].slopeTo(points[k+j]) == pointsCopy[i].slopeTo(points[k])) j++; // greedy method to search for more collinear points
                    }
                    Point[] collinearPoints = new Point[j+1];
                    collinearPoints[0] = pointsCopy[i];
                    for (int p  = 0; p < j; p++) collinearPoints[p+1] = points[k+p]; //point j collinear points into an array
                    Arrays.sort(collinearPoints);
                    
                    ls.push(new LineSegment(collinearPoints[0], collinearPoints[j])); //push in a new line segment
                    count++;
                    break;
                    
                }
            }
        }
    }
    public int numberOfSegments()        // the number of line segments
    { return count; }
    
    public LineSegment[] segments() {               // the line segments
        LineSegment[] ls2 = new LineSegment[count];
        for (int i = 0; i < count; i++) ls2[i] = ls.pop();
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