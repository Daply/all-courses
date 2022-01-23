import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root = null;
    private int size = 0;
    
    public KdTree() {
        // construct an empty set of points 
    }
    
    public boolean isEmpty() {
        // is the set empty? 
        return this.root == null;
    }
    
    public int size() {
        // number of points in the set 
        
        if (this.root == null) {
            return 0;
        }
        
        return this.size;
    }
    
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        if (this.root == null) {
            this.root = new Node(p, true);
            RectHV resultRect = new RectHV(0, 0, 1, 1);
            this.root.rect = resultRect;
            this.size++;
        }
        else if (!contains(p)) {
            Node newNode = new Node(p, true);
            if (insertNode(this.root, newNode)) {
                this.size++;
            }
        }
    }
    
    private boolean insertNode(Node node, Node newNode) {
        boolean inserted = false;
        newNode.color = !node.color;
        // if color RED (by y)
        if (newNode.color) {
            if (newNode.point.y() < node.point.y()) {
                if (node.left != null) {
                    insertNode(node.left, newNode);
                }
                else {
                    node.left = newNode;
                    RectHV resultRect = new RectHV(node.rect.xmin(), node.rect.ymin(),
                            node.rect.xmax(), node.point.y());
                    newNode.rect = resultRect;
                }
                inserted = true;
            }
            else if (newNode.point.y() > node.point.y()) {
                if (node.right != null) {
                    insertNode(node.right, newNode);
                }
                else {
                    node.right = newNode;
                    RectHV resultRect = new RectHV(node.rect.xmin(), node.point.y(),
                            node.rect.xmax(), node.rect.ymax());
                    newNode.rect = resultRect;
                }
                inserted = true;
            }
            // TODO if equal y's
            else if (newNode.point.x() != node.point.x()) {
                // like color BLUE
                if (newNode.point.x() < node.point.x()) {
                    if (node.left != null) {
                        insertNode(node.left, newNode);
                    }
                    else {
                        node.left = newNode;
                        newNode.rect = node.rect;
                    }
                    inserted = true;
                }
                else if (newNode.point.x() > node.point.x()) {
                    if (node.right != null) {
                        insertNode(node.right, newNode);
                    }
                    else {
                        node.right = newNode;
                        newNode.rect = node.rect;
                    }
                    inserted = true;
                }
            }
        }
        // if color BLUE (by x)
        else {
            if (newNode.point.x() < node.point.x()) {
                if (node.left != null) {
                    insertNode(node.left, newNode);
                }
                else {
                    node.left = newNode;
                    RectHV resultRect = new RectHV(node.rect.xmin(), node.rect.ymin(),
                            node.point.x(), node.rect.ymax());
                    newNode.rect = resultRect;
                }
                inserted = true;
            }
            else if (newNode.point.x() > node.point.x()) {
                if (node.right != null) {
                    insertNode(node.right, newNode);
                }
                else {
                    node.right = newNode;
                    RectHV resultRect = new RectHV(node.point.x(), node.rect.ymin(),
                            node.rect.xmax(), node.rect.ymax());
                    newNode.rect = resultRect;
                }
                inserted = true;
            }
            // TODO if equal x's
            else if (newNode.point.y() != node.point.y()) {
                // like color RED
                if (newNode.point.y() < node.point.y()) {
                    if (node.left != null) {
                        insertNode(node.left, newNode);
                    }
                    else {
                        node.left = newNode;
                        newNode.rect = node.rect;
                    }
                    inserted = true;
                }
                else if (newNode.point.y() > node.point.y()) {
                    if (node.right != null) {
                        insertNode(node.right, newNode);
                    }
                    else {
                        node.right = newNode;
                        newNode.rect = node.rect;
                    }
                    inserted = true;
                }
            }
        } 
        return inserted;
    }
    
    public boolean contains(Point2D p) {
        // does the set contain point p? 
        
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        if (this.root == null) {
            return false;
        }
        
        Node node = this.root;
        while (node != null) {
            // if color RED (by y)
            boolean searchNodeColor = !node.color;
            if (searchNodeColor) {
                if (p.y() < node.point.y()) {
                    node = node.left;
                }
                else if (p.y() > node.point.y()) {
                    node = node.right;
                }
                else if (node.point.x() == p.x()) {
                    return true;
                }
                else {
                    if (p.x() < node.point.x()) {
                        node = node.left;
                    }
                    else if (p.x() > node.point.x()) {
                        node = node.right;
                    }
                }
            }
            // if color BLUE (by x)
            else {
                if (p.x() < node.point.x()) {
                    node = node.left;
                }
                else if (p.x() > node.point.x()) {
                    node = node.right;
                }
                else if (node.point.y() == p.y()) {
                    return true;
                }
                else {
                    if (p.y() < node.point.y()) {
                        node = node.left;
                    }
                    else if (p.y() > node.point.y()) {
                        node = node.right;
                    }
                }
            }
        }
        return false;
    }
    
    public void draw() {
        // draw all points to standard draw
        drawSubTree(this.root);
    }
    
    private void drawSubTree(Node node) {
        
        if (node == null) {
            return;
        }
        
        StdDraw.point(node.point.x(), node.point.y());
        if (node.left != null) {
            drawSubTree(node.left);
        }
        if (node.right != null) {
            drawSubTree(node.right);
        }
    }
    
    
    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary) 
        
        if (rect == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        List<Point2D> pointsInRect = new ArrayList<>();
        if (this.root != null) {
            if (rect.intersects(this.root.rect)) {
                pointsInRect = (List<Point2D>) findPoints(this.root, pointsInRect, rect); 
            }
        }
        return pointsInRect;
    }
    
    private Iterable<Point2D> findPoints(Node node, List<Point2D> pointsInRect, RectHV rect) {
        // check if rect contains points
        if (rect.contains(node.point)) {
            pointsInRect.add(node.point);
        }

        RectHV resultRect = node.rect; 
        // StdDraw.filledRectangle(resultRect.xmin(), resultRect.ymin(), resultRect.width()/2, resultRect.height()/2);
        if (rect.intersects(resultRect)) {
            if (node.left != null) {
                findPoints(node.left, pointsInRect, rect);
            }
            if (node.right != null) {
                findPoints(node.right, pointsInRect, rect);
            }
        }
 
        return pointsInRect; 
    }
    
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        if (this.root == null) {
            return null;
        }
        
        double minDistance = 2;
        Point2D neighbor = null;
        neighbor = searchNearestPoint(minDistance, p, neighbor, this.root); 
        return neighbor;
    }
    
    private Point2D searchNearestPoint(double minDistance, Point2D p, Point2D neighbor, Node node) {
        // a nearest neighbor in the set to point p; null if the set is empty
        
        double rectDist = node.rect.distanceSquaredTo(p);
        if (minDistance > rectDist) {
        
            double pointsDist = 0;
            Point2D nodePoint = node.point;
            pointsDist = nodePoint.distanceSquaredTo(p);
            if (pointsDist < minDistance) {
                minDistance = pointsDist;
                neighbor = nodePoint;
            }
        
            Point2D leftNeighbor = null;
            Point2D rightNeighbor = null;
            if (node.left != null) {
                leftNeighbor = searchNearestPoint(minDistance, p, neighbor, node.left);
                minDistance = leftNeighbor.distanceSquaredTo(p);
                neighbor = leftNeighbor;
            }
            if (node.right != null) {
                rightNeighbor = searchNearestPoint(minDistance, p, neighbor, node.right);
                neighbor = rightNeighbor;
                if (leftNeighbor != null) {
                    if (leftNeighbor.distanceSquaredTo(p) < rightNeighbor.distanceSquaredTo(p)) {
                        neighbor = leftNeighbor;
                    }
                }
            }
            
        }
     
        return neighbor;
    }

    private class Node {
        private Point2D point;     // associated data
        private RectHV rect;       // associated rectangle
        private Node left, right;  // left and right subtrees
        private boolean color;     // RED (by y) = true, BLUE (by x) = false

        public Node(Point2D point, boolean color) {
            this.point = point;
            this.color = color;
        }
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional) 
       
        // for unit testing used classes TestKdTree, RangeSearchVisualizer,
        // NearestNeighborVisualizer

    }
}
