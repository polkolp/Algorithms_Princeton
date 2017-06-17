import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Stack;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    private int count;
    private Stack<LineSegment> ls;
    
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) throw new NullPointerException();
        Arrays.sort(points);
        if (points[points.length-1] == null) throw new NullPointerException();
        for (int i = 0; i < points.length-1; i++) {
            if (points[i] == null) throw new NullPointerException();
            else if (points[i+1].compareTo(points[i]) == 0) throw new IllegalArgumentException();
        }
        
        ls = new Stack<LineSegment>(); //create a stack ls to store line segments
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int l = k+1; l < points.length; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) 
                                && points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                            Point[] pl = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(pl);
                            count++;
                            ls.push(new LineSegment(pl[0], pl[3])); //push in a new line segment
                        }
                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    
}
