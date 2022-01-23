
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	// -1 - is closed
	// 0 - is open
	// 1 - full
	private int [][] grid;
	// number of open sites
	private int numOfOpenSites = 0;
	// flag if system percolates
	private boolean isPercolate = false;
	// union-find
	private WeightedQuickUnionUF uf = null;
	
	public Percolation(int n) {
		if (n <= 0) {
            throw new IllegalArgumentException("n: "+ n + " is less or equal than 0 ");  
        }
		grid = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = -1;
			}
		}
		// extra two for top node and bottom
		uf = new WeightedQuickUnionUF(n*n + 2);
		
		// connect first line of grid with top node
		for (int i = 1; i <= n; i++)
		    uf.union(0, i);
		// connect last line of grid with bottom node
	    for (int i = 0; i < n; i++)
	        uf.union(n*n - i, n*n + 1);
	}
	private void init(int i, int j) {
        int n = grid.length;
		int num = 0;
		int upNeighbor = 0;
		int leftNeighbor = 0;
		int rightNeighbor = 0;
		int bottomNeighbor = 0;
			
		boolean full = false;
		
		if (i == 0)
		    full = true;
		
		// count number of node in array
		num = i*n + j + 1;
		
        // look through neighbours
		upNeighbor = num - n;
		leftNeighbor = num - 1;
		rightNeighbor = num + 1;
		bottomNeighbor = num + n;
				
		if (upNeighbor >= 0 && upNeighbor <= n*n+1 &&
				i - 1 >= 0 && (grid[i-1][j] == 0 || grid[i-1][j] == 1)) {
			if (!uf.connected(num, upNeighbor))
				uf.union(num, upNeighbor);
			if (grid[i-1][j] == 1)
			    full = true;
		}
		if (leftNeighbor >= 0 && leftNeighbor <= n*n+1 &&
				j - 1 >= 0 && (grid[i][j-1] == 0 || grid[i][j-1] == 1)) {
			if (!uf.connected(num, leftNeighbor))
				uf.union(num, leftNeighbor);
	        if (grid[i][j-1] == 1)
	            full = true;
		}
		if (rightNeighbor >= 0 && rightNeighbor <= n*n+1 &&
				j + 1 < n && (grid[i][j+1] == 0 || grid[i][j+1] == 1)) {
			if (!uf.connected(num, rightNeighbor))
				uf.union(num, rightNeighbor);
	        if (grid[i][j+1] == 1)
	            full = true;
		}
		if (bottomNeighbor >= 0 && bottomNeighbor <= n*n+1 &&
				i + 1 < n && (grid[i+1][j] == 0 || grid[i+1][j] == 1)) {
			if (!uf.connected(num, bottomNeighbor))
				uf.union(num, bottomNeighbor);
            if (grid[i+1][j] == 1)
                full = true;
		}			
        if (full) {
            grid[i][j] = 1;
            fullNeighbours(i, j);
        }
		
        checkIfPercolates();
	}
	
	private void fullNeighbours(int i, int j) {
	    int n = grid.length;
        if (i - 1 >= 0 && grid[i-1][j] == 0) {
            grid[i-1][j] = 1;
            fullNeighbours(i-1, j);
        }
        if (j - 1 >= 0 && grid[i][j-1] == 0) {
            grid[i][j-1] = 1;
            fullNeighbours(i, j-1);
        }
        if (j + 1 < n && grid[i][j+1] == 0) {
            grid[i][j+1] = 1;
            fullNeighbours(i, j+1);
        }
        if (i + 1 < n && grid[i+1][j] == 0) {
            grid[i+1][j] = 1;
            fullNeighbours(i+1, j);
        }
	}
	
	private void checkIfPercolates() {
	   int n = grid.length;
       if (uf.connected(0, n*n+1)) {
           isPercolate = true;
       }   
	}
	
	public void open(int row, int col) {
	    row--;
	    col--;
	    validation(row, col);
		if (grid[row][col] == -1) {
			grid[row][col] = 0;
			numOfOpenSites++;
			init(row, col);
		}
	}
	public boolean isOpen(int row, int col) {
	    row--;
        col--;
        validation(row, col);
		if (grid[row][col] == 0 || grid[row][col] == 1) {
			return true;
		}
		return false;
	}
	public boolean isFull(int row, int col) {
	    row--;
        col--;
        validation(row, col);
        if (grid[row][col] == 1) {
            return true;
        }
		return false;
	}
	private void validation(int row, int col) {
	    if (row < 0 || row >= grid.length) {
            throw new IllegalArgumentException(" row: " + row + " is not in interval [0, " +
                                               grid.length + "]");  
        }
        if (col < 0 || col >= grid[row].length) {
            throw new IllegalArgumentException("col: " + col + " is not in interval [0, " +
                                                grid[row].length + "]");  
        }
	}
	public int numberOfOpenSites() {
		return numOfOpenSites;
	}
	public boolean percolates() {
		return isPercolate;
	}
	
	public static void main(String[] args) {
	    // test client (optional)
	    
	    
	}
}
