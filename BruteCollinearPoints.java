import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;


public class BruteCollinearPoints {
    private int count;
    private LineSegment[] ls;
    
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) throw new NullPointerException();
        Arrays.sort(points);
        if (points[points.length-1] == null) throw new NullPointerException();
        for (int i = 0; i < points.length-1; i++) {
            if (points[i] == null) throw new NullPointerException();
            else if (points[i+1].compareTo(points[i]) == 0) throw new IllegalArgumentException();
        }
        
        ls = new LineSegment[points.length];
        
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int l = k+1; l < points.length; l++) {
                        if (Math.abs(points[i].slopeTo(points[j])) == Math.abs(points[i].slopeTo(points[k])) 
                                && Math.abs(points[i].slopeTo(points[j])) == Math.abs(points[i].slopeTo(points[l]))) {
                            Point[] pl = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(pl);
                            ls[count++] = new LineSegment(pl[0], pl[3]);
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
        int i = 0;
        int j = 0;
        while (ls[i] != null && i < ls.length) {
            ls2[j] = ls[i++];
            if (j != count-1) j++;
        }
        return ls2;
    }
    
    public static void main(String[] args) {
        
        Point[] p = new Point[5];
        p[0] = new Point(0, 0);
        p[1] = new Point(2, 2);
        p[2] = new Point(1, 1);
        p[3] = new Point(3, 3);
        p[4] = new Point(3, 1);
        
        BruteCollinearPoints bc = new BruteCollinearPoints(p);
        StdOut.println(bc.numberOfSegments());
        for (int i = 0; i < bc.segments().length; i++) StdOut.println(bc.segments()[i]);

    }
}