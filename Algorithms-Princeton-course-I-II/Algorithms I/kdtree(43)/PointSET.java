import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
 import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    
    private Set<Point2D> pointSet = null;
    
    public PointSET() {
        // construct an empty set of points 
        this.pointSet = new TreeSet<Point2D>();
    }
    
    public boolean isEmpty() {
        // is the set empty? 
        return this.pointSet.isEmpty();
    }
    
    public int size() {
        // number of points in the set 
        return this.pointSet.size();
    }
    
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        this.pointSet.add(p);
    }
    
    public boolean contains(Point2D p) {
        // does the set contain point p? 
        
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        return this.pointSet.contains(p);
    }
    
    public void draw() {
        // draw all points to standard draw 
         for (Point2D p: this.pointSet) {
             StdDraw.point(p.x(), p.y());
         }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary) 
        
        if (rect == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        List<Point2D> pointsInRect = new ArrayList<>();
        for (Point2D p: this.pointSet) {
            if (rect.contains(p)) {
                pointsInRect.add(p);
            }
        }
        return pointsInRect;
    }
    
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        double minDistance = 2;
        double dist = 0;
        Point2D neighbor = null;
        for (Point2D p1: this.pointSet) {
            dist = p.distanceSquaredTo(p1);
            if (dist < minDistance) {
                neighbor = p1;
                minDistance = dist;
            }
        }
        return neighbor;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional) 
        
        // for unit testing used classes TestKdTree, RangeSearchVisualizer,
        // NearestNeighborVisualizer
        
    }
}
