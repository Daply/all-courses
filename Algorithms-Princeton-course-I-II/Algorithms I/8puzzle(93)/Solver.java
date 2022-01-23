import java.util.ArrayList;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * 
 * @author Pleshchankova Daria
 *
 */
public class Solver {
    private SearchNode initNode = null;
    private SearchNode twinNode = null;
    
    /**
     * Constructor
     * Solving a board
     * @param initial - start board
     */
    public Solver(Board initial)  {
        // find a solution to the initial board (using the A* algorithm)
        
        if (initial == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        int moves = 0;
        MinPQ<SearchNode> minQ = new MinPQ<SearchNode> ();
        this.initNode = new SearchNode(initial, moves);
        this.twinNode = new SearchNode(initial.twin(), moves);
        minQ.insert(initNode);
        minQ.insert(twinNode);
        // get first node
        this.initNode = minQ.delMin();
        while (!this.initNode.currentBoard.isGoal()) {
            moves = initNode.searchMoves + 1;
            for (Board neighbor: this.initNode.currentBoard.neighbors()) {
                if (this.initNode.previousNode == null || 
                        !this.initNode.previousNode.currentBoard.equals(neighbor)) {
                    SearchNode neighborNode = new SearchNode(neighbor, moves);
                    neighborNode.previousNode = this.initNode;
                    minQ.insert(neighborNode);
//                    StdOut.println(neighborNode.currentBoard);
                }
            }
            this.initNode = minQ.delMin();
        }
    }
    
    /**
     * Check if board is solvable
     * @return
     */
    public boolean isSolvable() {
        // is the initial board solvable?
        if (this.initNode != null) {
            SearchNode node = this.initNode;
            while (node.previousNode != null) {
                node = node.previousNode;
            }
            // if solved one not twin, so exactly initial was solvable
            if (node.currentBoard.equals(twinNode.currentBoard)) 
                return false;
            else         
                return true;
        }
        return false;
    }
    
    /**
     * Return number of moves, if board is solvable
     * @return
     */
    public int moves() {
        if (isSolvable())
            return this.initNode.searchMoves;
        return -1;
    }
    
    /**
     * Return solution, if board is solvable
     * @return stack of boards solution
     */
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        ArrayList<Board> solution = null;
        if (this.initNode != null) {
            Stack<Board> saveSolution = new Stack<Board> ();
            if (isSolvable()) {
                SearchNode minNode = this.initNode;
                saveSolution.push(minNode.currentBoard);
                while (minNode.previousNode != null) {
                    minNode = minNode.previousNode;
                    saveSolution.push(minNode.currentBoard);
                }
                solution = new ArrayList<Board>();
                while (!saveSolution.isEmpty()) {
                    solution.add(saveSolution.pop());
                }
            }
        }
        return solution;
    }
    
    /**
     * Inner class for connecting previous board and current
     *
     */
    private static class SearchNode implements Comparable<SearchNode> {

        private Board currentBoard = null;
        private SearchNode previousNode = null;
        private int searchMoves = 0;
        
        public SearchNode(Board board, int moves) {
            this.currentBoard = board;
            this.searchMoves = moves;
        }
        
        @Override
        public int compareTo(SearchNode node) {
            // TODO Auto-generated method stub
            if (this.currentBoard.manhattan()+this.searchMoves > 
                    node.currentBoard.manhattan()+node.searchMoves) {
                return 1;
            }
            if (this.currentBoard.manhattan()+this.searchMoves < 
                    node.currentBoard.manhattan()+node.searchMoves) {
                return -1;
            }
            return 0;
        }
        
    }
    
    public static void main(String[] args) {
        // solve a slider puzzle (given below)
         int[][] blocks = new int [][] { {5, 1, 8}, {2, 7, 3}, {4, 0, 6} } ;
        // int[][] blocks = new int [][] { {0, 1}, {3, 2}} ;
        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] blocks = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
