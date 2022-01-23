
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 
 * @author Pleshchankova Daria
 *
 */
public class BruteCollinearPoints {
    
    private final LineSegment[] lineSegments;
    private int numberOfSegments;
    
   /**
    * Constructor 
    * @param points
    */
   public BruteCollinearPoints(Point[] points) {
       // finds all line segments containing 4 points
       if (points == null)
           throw new IllegalArgumentException("array of points is null");
       Point[] pointsUsed = new Point[points.length];
       for (int i = 0; i < points.length; i++) {
           if (points[i] == null)
               throw new IllegalArgumentException("point is null");
           for (int j = i+1; j < points.length; j++) {
               if (points[j] != null && points[i].compareTo(points[j]) == 0)
                   throw new IllegalArgumentException("duplicate points are exist");
           }
       }
       pointsUsed = Arrays.copyOf(points, points.length);
       Arrays.sort(pointsUsed);
       ArrayList<LineSegment> allLineSegments = new ArrayList<>();
       for (int i = 0; i < pointsUsed.length; i++) {
            for (int j = i+1; j < pointsUsed.length; j++) {
                 for (int k = j+1; k < pointsUsed.length; k++) {
                      for (int t = k+1; t < pointsUsed.length; t++) {
                               double slope1 = pointsUsed[i].slopeTo(pointsUsed[j]);
                               double slope2 = pointsUsed[i].slopeTo(pointsUsed[k]);
                               double slope3 = pointsUsed[i].slopeTo(pointsUsed[t]);
                               if (slope1 == slope2 && slope2 == slope3) {
                                   LineSegment line = new LineSegment(pointsUsed[i], pointsUsed[t]);
                                   allLineSegments.add(line);
                               } 
                      }
                  }
              }
          }
       this.lineSegments = new LineSegment[allLineSegments.size()];
       for (int i = 0; i < allLineSegments.size(); i++) {
           this.lineSegments[i] = allLineSegments.get(i);
       }
       this.numberOfSegments = allLineSegments.size();
   }
   
   /**
    * Number of segments
    * @return numberOfSegments
    */
   public int numberOfSegments() {
       // the number of line segments
       return numberOfSegments;
   }
   
   /**
    * Array of segments
    * @return LineSegment[] 
    */
   public LineSegment[] segments() {
       // the line segments
       LineSegment[] copy = lineSegments.clone();
       return copy;
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
       // StdDraw.enableDoubleBuffering();
       // StdDraw.setXscale(0, 32768);
       // StdDraw.setYscale(0, 32768);
       // for (Point p : points) {
       //     p.draw();
       // }
       // StdDraw.show();

       // print and draw the line segments
       BruteCollinearPoints collinear = new BruteCollinearPoints(points);
       for (LineSegment segment : collinear.segments()) {
           if (segment != null) {
               StdOut.println(segment);
               // segment.draw();
           }
       }
       // StdDraw.show();
   }
}