import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {
    private int[] grid;
    private int arraysize; 
    private WeightedQuickUnionUF quickunion;
    
    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        arraysize = n;
        grid = new int[n*n+2];
        for (int i = 0; i < n*n+2; i++) {
            grid[i] = 0; // initialize the grid
        }
        // union the the first virtual element to the first row of matrix,
        // and the last virtual element to the last row of matrix.
        quickunion = new WeightedQuickUnionUF(n*n+2); 
        grid[0] = 1;
        for (int i = 1; i < n+1; i++) quickunion.union(0, i);
        grid[n*n+1] = 1;
        for (int i = n*n-n+1; i < n*n+1; i++) quickunion.union(n*n+1, i);
    }
            
    public    void open(int row, int col)    // open site (row, col) if it is not open already
    {
        int eleindex = (row-1)*arraysize+col;
        if (grid[eleindex] != 1) {
            grid[eleindex] = 1;
            
            int leftindex = eleindex-1;
            
            if (!quickunion.connected(eleindex, leftindex) && grid[leftindex] == 1 && eleindex % arraysize != 1) {
                quickunion.union(eleindex, leftindex); 
            }
            
            int rightindex = eleindex+1; 
            
            if (!quickunion.connected(eleindex, rightindex) && grid[rightindex] == 1 && eleindex % arraysize != 0) {
                quickunion.union(eleindex, rightindex); 
            }
            
            
            int upindex = eleindex-arraysize;
            
            if (upindex > 0 && !quickunion.connected(eleindex, upindex) && grid[upindex] == 1) {
                quickunion.union(eleindex, upindex);
            }
            
            int downindex = eleindex+arraysize; 
            
            if (downindex < arraysize*arraysize+1 && !quickunion.connected(eleindex, downindex) && grid[downindex] == 1) {
                quickunion.union(eleindex, downindex);
            }
            else if (row > arraysize || col > arraysize) throw new IndexOutOfBoundsException();
        }
    }
    public boolean isOpen(int row, int col)  // is site (row, col) open? 
    { 
        if (row > arraysize || col > arraysize) throw new IndexOutOfBoundsException();
        return grid[(row-1)*arraysize+col] == 1; 
    }
    
    public boolean isFull(int row, int col)  // is site (row, col) full?
    { 
        if (row > arraysize || col > arraysize) throw new IndexOutOfBoundsException();
        return quickunion.connected(0, (row-1)*arraysize+col) && grid[(row-1)*arraysize+col] == 1;
    }
    
    public     int numberOfOpenSites()       // number of open sites
    {
        int count = 0;
        for (int i = 1; i < arraysize*arraysize+1; i++) {
            if (grid[i] == 1) count++;
        }
        return count; // compensate for the two virtual sites
    }
    
    public boolean percolates()              // does the system percolate?
    { 
        if (arraysize == 1) return grid[1] == 1;
        
        
        else if (arraysize == 2) {
            int col1sum = grid[1] + grid[3]; // column 1 sum
            int col2sum = grid[2] + grid[4]; // column 2 sum
            return (col1sum == 2 || col2sum == 2);
            
        }
        
        else {
            return quickunion.connected(0, arraysize*arraysize+1); 
        }
    }
    public static void main(String[] args)   // test client (optional)
    {
        int n = StdIn.readInt(); 
        Percolation pc = new Percolation(n);
    
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            pc.open(row, col);
        }
        StdOut.println(pc.numberOfOpenSites());
        StdOut.println(pc.percolates()); 
        
    }
}