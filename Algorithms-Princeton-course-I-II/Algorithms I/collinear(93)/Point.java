
import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

   private final int x;
   private final int y;
    
   public Point(int x, int y) {
       this.x = x;
       this.y = y;
   }

   public void draw() {
       StdDraw.point(x, y);
   }
   public void drawTo(Point that) {
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.line(this.x, this.y, that.x, that.y);
   }
   public String toString() {
       return "(" + this.x + ", " + this.y + ")";
   }
   
   public int compareTo(Point that) {
       if (this.y != that.y) {
           if (this.y > that.y)
               return 1;
           else
               return -1;
       }
       else {
           if (this.x > that.x)
               return 1;
           if (this.x < that.x)
               return -1;
           if (this.x == that.x) 
               return 0;
       }
       return 0;
   }
   public double slopeTo(Point that) {
       double x0 = this.x;
       double y0 = this.y;
       double x1 = that.x;
       double y1 = that.y;
       
       double posInf = Double.POSITIVE_INFINITY;
       double negInf = Double.NEGATIVE_INFINITY;
       double posZero = +0.0;
       
       if (y0 == y1 && x0 == x1) {
           return negInf;
       }
       
       if (y0 == y1) {
           return posZero;
       }
       
       if (x0 == x1) {
           return posInf;
       }
       
       return (y1 - y0)/(x1 - x0);
   }
   public Comparator<Point> slopeOrder() {
    return new SlopeComparator(); 
   }
   
   private class SlopeComparator implements Comparator<Point> 
   { 
       public int compare(Point a, Point b) 
       { 
           double slope1 = slopeTo(a);
           double slope2 = slopeTo(b);
           
           if (slope1 < slope2) {
               return -1; 
           }
           else if (slope1 > slope2) {
               return 1; 
           }
           else {
               return 0; 
           }
       } 
   } 
   
   public static void main(String[] args) {
       Point p = new Point(8, 9);
       Point q = new Point(8, 1);
       Point r = new Point(8, 3);
       StdOut.println(p.slopeOrder().compare(q, q));
       StdOut.println(p.slopeOrder().compare(q, r));
   }
}
