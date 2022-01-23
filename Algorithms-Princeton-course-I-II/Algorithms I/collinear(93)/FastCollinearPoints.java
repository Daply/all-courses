
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
public class FastCollinearPoints {
    
    private final ArrayList<LineSegment> lineSegments;
    private int numberOfSegments;
    
   /**
    * Constructor 
    * @param points
    */
   public FastCollinearPoints(Point[] points) {
       // finds all line segments containing 4 points
       if (points == null)
           throw new IllegalArgumentException("array of points is null");
       Point[] pointsUsed = new Point[points.length];
       Point[] pointsSort = new Point[points.length];
       for (int i = 0; i < points.length; i++) {
           if (points[i] == null)
               throw new IllegalArgumentException("point is null");
           for (int j = i+1; j < points.length; j++) {
               if (points[j] != null && points[i].compareTo(points[j]) == 0)
                   throw new IllegalArgumentException("duplicate points are exist");
           }
           pointsUsed[i] = points[i];
           pointsSort[i] = points[i];
       }       
       lineSegments = new ArrayList<LineSegment>();
       ArrayList<Point> startPoint = new ArrayList<Point>();
       ArrayList<Point> endPoint = new ArrayList<Point>();
       ArrayList<Double> slopes = new ArrayList<Double>();
       double currentSlope = Double.NaN;
       Point pMin, pMax;
       pMin = null;
       pMax = null;  
       for (int i = 0; i < pointsUsed.length; i++) {
            Point invPoint = pointsUsed[i];
            Arrays.sort(pointsSort, invPoint.slopeOrder());
            pMin = null;
            pMax = null; 
            currentSlope = Double.NaN;
            for (int j = 0; j < pointsSort.length-2; j++) {    
                 double slope1 = invPoint.slopeTo(pointsSort[j]);
                 double slope2 = invPoint.slopeTo(pointsSort[j+1]);
                 double slope3 = invPoint.slopeTo(pointsSort[j+2]);
                 if (slope1 == slope2 && slope2 == slope3) { 
                     Point p1, p2;
                     p1 = null;
                     p2 = null;                                  
                     
                     if (invPoint.compareTo(pointsSort[j]) < 0 &&
                             invPoint.compareTo(pointsSort[j+1]) < 0 &&
                             invPoint.compareTo(pointsSort[j+2]) < 0) {
                         p1 = invPoint;
                     }
                     if (pointsSort[j].compareTo(invPoint) < 0 &&
                             pointsSort[j].compareTo(pointsSort[j+1]) < 0 &&
                             pointsSort[j].compareTo(pointsSort[j+2]) < 0) {
                         p1 = pointsSort[j];
                     }
                     if (pointsSort[j+1].compareTo(invPoint) < 0 &&
                             pointsSort[j+1].compareTo(pointsSort[j]) < 0 &&
                             pointsSort[j+1].compareTo(pointsSort[j+2]) < 0) {
                         p1 = pointsSort[j+1];
                     }
                     if (pointsSort[j+2].compareTo(invPoint) < 0 &&
                             pointsSort[j+2].compareTo(pointsSort[j]) < 0 &&
                             pointsSort[j+2].compareTo(pointsSort[j+1]) < 0) {
                         p1 = pointsSort[j+2];
                     }      

                     
                     if (invPoint.compareTo(pointsSort[j]) > 0 &&
                             invPoint.compareTo(pointsSort[j+1]) > 0 &&
                             invPoint.compareTo(pointsSort[j+2]) > 0) {
                         p2 = invPoint;
                     }
                     if (pointsSort[j].compareTo(invPoint) > 0 &&
                             pointsSort[j].compareTo(pointsSort[j+1]) > 0 &&
                             pointsSort[j].compareTo(pointsSort[j+2]) > 0) {
                         p2 = pointsSort[j];
                     }
                     if (pointsSort[j+1].compareTo(invPoint) > 0 &&
                             pointsSort[j+1].compareTo(pointsSort[j]) > 0 &&
                             pointsSort[j+1].compareTo(pointsSort[j+2]) > 0) {
                         p2 = pointsSort[j+1];
                     }
                     if (pointsSort[j+2].compareTo(invPoint) > 0 &&
                             pointsSort[j+2].compareTo(pointsSort[j]) > 0 &&
                             pointsSort[j+2].compareTo(pointsSort[j+1]) > 0) {
                         p2 = pointsSort[j+2];
                     }       
                     
                     // if slope is equal to current found slope
                     if (p1 != null && p2 != null && currentSlope == slope1) {
                         if (pMin != null && pMax != null) {
                             if (p1.compareTo(pMin) < 0) { 
                                 pMin = p1;
                             }
                             if (p2.compareTo(pMax) > 0) {
                                 pMax = p2;
                             }   
                         }
                     }
                     else if (currentSlope != slope1) {
                         
                         // add previous found points as segment
                         if (pMin != null && pMax != null) {
                             startPoint.add(pMin);
                             endPoint.add(pMax);
                             slopes.add(currentSlope);
                         }
                         
                         currentSlope = slope1;
                         pMin = p1;
                         pMax = p2;
                     }
                 }   
            }
             // add all found segments
            // add previous found points as segment
            if (pMin != null && pMax != null) {
                startPoint.add(pMin);
                endPoint.add(pMax);
                slopes.add(currentSlope);
            }
       }   
       
       
       for (int i = 0; i < slopes.size(); i++) {
           for (int j = 0; j < slopes.size(); j++) {
               if (i != j) {
                   if (slopes.get(i).equals(slopes.get(j))) {
                       if (startPoint.get(i).compareTo(startPoint.get(j)) == 0) {
                           startPoint.remove(j);
                           endPoint.remove(j);
                           slopes.remove(j);
                           j--;
                       }
                   }
               }
           }
           LineSegment line = new LineSegment(startPoint.get(i), endPoint.get(i));
           lineSegments.add(line);
       }
	   this.numberOfSegments = lineSegments.size();
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
       LineSegment[] copy = lineSegments.toArray(new LineSegment[lineSegments.size()]);
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
       //    p.draw();
       // }
       // StdDraw.show();

       // print and draw the line segments
       FastCollinearPoints collinear = new FastCollinearPoints(points);
       for (LineSegment segment : collinear.segments()) {
           if (segment != null) {
               StdOut.println(segment);
               // segment.draw();
           }
       }
       // StdDraw.show();
   }
}
