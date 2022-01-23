import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * 
 * @author Pleshchankova Daria
 *
 */
public class Board {
    
    private final int[][] blocks;
    private int n = 0;
    
    private int hamming = 0;
    private int manhattan = 0;
    
    private int countRightPlacesBlocks = 0;
    
    private int emptyBI = 0;
    private int emptyBJ = 0;
    
    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        this.n = blocks.length;
        this.blocks = new int [this.n][this.n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (this.blocks[i][j] != 0) {
                    int rightI = (this.blocks[i][j]-1) / this.n;
                    int rightJ = (this.blocks[i][j]-1) % this.n;
                    this.manhattan += Math.abs(i - rightI) + Math.abs(j - rightJ);
                    if (i != rightI || j != rightJ) {
                        this.hamming++;
                    }
                    if (i == rightI && j == rightJ) {
                        this.countRightPlacesBlocks++;
                    }
                }
                else {
                    this.emptyBI = i;
                    this.emptyBJ = j;
                }
            }
        }
    }
    
    public int dimension() {
        return n;
    }
    public int hamming() {
        // number of blocks out of place  
        return hamming;
    }
    public int manhattan() {
        // sum of Manhattan distances between blocks and goal   
        return this.manhattan;
    }
    public boolean isGoal() {
        // is this board the goal board?
        return this.countRightPlacesBlocks == (this.n*this.n-1);
    }
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[][] twinBlocks = new int [this.n][this.n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinBlocks[i][j] = this.blocks[i][j];
            }
        }
        
        int tmp = 0;
        
        if ((this.emptyBI != 0 && this.emptyBJ != 0) ||
                (this.emptyBI != 0 && this.emptyBJ != this.n-1)) {
            tmp = twinBlocks[0][0];
            twinBlocks[0][0] = twinBlocks[0][this.n-1];
            twinBlocks[0][this.n-1] = tmp;
        }
        else {
            tmp = twinBlocks[this.n-1][0];
            twinBlocks[this.n-1][0] = twinBlocks[this.n-1][this.n-1];
            twinBlocks[this.n-1][this.n-1] = tmp;
        }

        
        Board twinBoard = new Board(twinBlocks);
        
        return twinBoard;
    }
    public boolean equals(Object y)  {
        // does this board equal y?
        
        if (y == this) {
            return true;
        }
        
        if (y == null) {
            return false;
        }
        
        if (y.getClass() != this.getClass()) {
            return false;
        }
        
        Board boardComp = (Board) y;
        
        if (boardComp.dimension() != this.n) {
            return false;
        }
        
        for (int i = 0; i < n; i++) {
             for (int j = 0; j < n; j++) {
                  if (this.blocks[i][j] != boardComp.blocks[i][j]) {
                      return false;
                  }
              }
        }
        return true;
    }
    public Iterable<Board> neighbors() {
        // all neighboring boards
        Queue<Board> neighbors = new Queue<Board>();
        int[][] neighborBlocks = new int [this.n][this.n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                neighborBlocks[i][j] = this.blocks[i][j];
            }
        }
        if (this.emptyBI + 1 < this.n) {
            int tmpDownB = neighborBlocks[this.emptyBI+1][this.emptyBJ];
            neighborBlocks[this.emptyBI+1][this.emptyBJ] = 0;
            neighborBlocks[this.emptyBI][this.emptyBJ] = tmpDownB;
            
            Board movedDownB = new Board(neighborBlocks);
            
            neighborBlocks[this.emptyBI+1][this.emptyBJ] = tmpDownB;
            neighborBlocks[this.emptyBI][this.emptyBJ] = 0;
            
            neighbors.enqueue(movedDownB);
        }
        if (this.emptyBI - 1 >= 0) {
            int tmpUpB = neighborBlocks[this.emptyBI-1][this.emptyBJ];
            neighborBlocks[this.emptyBI-1][this.emptyBJ] = 0;
            neighborBlocks[this.emptyBI][this.emptyBJ] = tmpUpB;
            
            Board movedUpB = new Board(neighborBlocks);
            
            neighborBlocks[this.emptyBI-1][this.emptyBJ] = tmpUpB;
            neighborBlocks[this.emptyBI][this.emptyBJ] = 0;
            
            neighbors.enqueue(movedUpB);
        }
        if (this.emptyBJ + 1 < this.n) {
            int tmpRightB = neighborBlocks[this.emptyBI][this.emptyBJ+1];
            neighborBlocks[this.emptyBI][this.emptyBJ+1] = 0;
            neighborBlocks[this.emptyBI][this.emptyBJ] = tmpRightB;
            
            Board movedRightB = new Board(neighborBlocks);
            
            neighborBlocks[this.emptyBI][this.emptyBJ+1] = tmpRightB;
            neighborBlocks[this.emptyBI][this.emptyBJ] = 0;
            
            neighbors.enqueue(movedRightB);
        }
        if (this.emptyBJ - 1 >= 0) {
            int tmpLeftB = neighborBlocks[this.emptyBI][this.emptyBJ-1];
            neighborBlocks[this.emptyBI][this.emptyBJ-1] = 0;
            neighborBlocks[this.emptyBI][this.emptyBJ] = tmpLeftB;
            
            Board movedLeftB = new Board(neighborBlocks);
            
            neighborBlocks[this.emptyBI][this.emptyBJ-1] = tmpLeftB;
            neighborBlocks[this.emptyBI][this.emptyBJ] = 0;
            
            neighbors.enqueue(movedLeftB);
        }
               
        return neighbors;
    }
    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder boardString = new StringBuilder();
        boardString.append(this.n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                 boardString.append(this.blocks[i][j] + " ");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
        int[][] blocks = new int [][] { {1, 2, 4}, {7, 0, 3}, {8, 5, 6} };
        Board b = new Board(blocks);
        StdOut.println(b.manhattan());
        StdOut.println(b.hamming());
        
        for (Board b1 : b.neighbors()) {
            StdOut.println(b1.manhattan());
            StdOut.println(b1.hamming());
            StdOut.println(b1);
        }
        
    }
}