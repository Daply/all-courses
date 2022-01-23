import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {

    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {

        if (G == null) {
            throw new IllegalArgumentException();
        }

        this.digraph = new Digraph(G.V());
        copyDigraph(G);
    }

    private void copyDigraph(Digraph G) {
        for (int vertex = 0; vertex < G.V(); vertex++) {
            for (int connected: G.adj(vertex)) {
                this.digraph.addEdge(vertex, connected);
            }
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validate(v, w);

        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(this.digraph, w);

        return findLength(bfsv, bfsw);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validate(v, w);

        if (v == w) {
            return v;
        }

        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(this.digraph, w);

        return findAncestor(bfsv, bfsw);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v, w);

        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(this.digraph, w);

        return findLength(bfsv, bfsw);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v, w);

        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(this.digraph, w);

        return findAncestor(bfsv, bfsw);
    }

    private int findLength(BreadthFirstDirectedPaths bfsv, BreadthFirstDirectedPaths bfsw) {
        int length = -1;
        int ancestor = findAncestor(bfsv, bfsw);
        if (ancestor != -1) {
            length = bfsv.distTo(ancestor) + bfsw.distTo(ancestor);
        }

        return length;
    }

    private int findAncestor(BreadthFirstDirectedPaths bfsv, BreadthFirstDirectedPaths bfsw) {
        int length = -1;
        int ancestor = -1;

        for (int vertex = 0; vertex < this.digraph.V(); vertex++) {

            if (bfsv.hasPathTo(vertex) && bfsw.hasPathTo(vertex)) {
                int foundLength = bfsv.distTo(vertex) + bfsw.distTo(vertex);
                if (length == -1 || foundLength < length) {
                    length = foundLength;
                    ancestor = vertex;
                }
            }
        }
        return ancestor;
    }

    private void validateVertex(int v) {
        if (v < 0 || v > this.digraph.V() - 1) {
            throw new IllegalArgumentException();
        }
    }

    private void validate(int v, int w) {
        validateVertex(v);
        validateVertex(w);
    }

    private void validate(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }

        for (Integer value: v) {
            if (value == null)
                throw new IllegalArgumentException();
            validateVertex(value);
        }

        for (Integer value: w) {
            if (value == null)
                throw new IllegalArgumentException();
            validateVertex(value);
        }

    }

    public static void main(String[] args) {
        // testing
    }

}

