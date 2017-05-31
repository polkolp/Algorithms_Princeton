import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private int[] grid;
    private int arraysize; 
    private WeightedQuickUnionUF quickunion;
    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        arraysize = n;
        grid = new int[n*n+2];
        for (int i = 0; i<n*n+2; i++){
            grid[i] = 0;// initialize the grid
        }
        // union the the first virtual element to the first row of matrix,
        // and the last virtual element to the last row of matrix.
        quickunion = new WeightedQuickUnionUF(n*n+2); 
        grid[0] = 1;
        for (int i = 1; i<n+1; i++) quickunion.union(0,i);
        grid[n*n+1] = 1;
        for (int i = n*n-n+1; i<n*n+1; i++) quickunion.union(n*n+1,i);
    }

            
    public    void open(int row, int col)    // open site (row, col) if it is not open already
    {
        int eleindex = (row-1)*arraysize+col;
        grid[eleindex] = 1;
       
        int leftindex = eleindex-1;
 
        if (!quickunion.connected(eleindex,leftindex) && grid[leftindex] ==1 && eleindex%arraysize != 1) {
            quickunion.union(eleindex,leftindex);
            StdOut.print('y');}
        else
            StdOut.print('n');
       
        int rightindex = eleindex+1; 

        if (!quickunion.connected(eleindex,rightindex) && grid[rightindex] ==1 && eleindex%arraysize != 0) {
            quickunion.union(eleindex,rightindex);
            StdOut.print('y');}
        else
            StdOut.print('n');
       
        int upindex = eleindex-arraysize;

        if (upindex > 0 && !quickunion.connected(eleindex,upindex) && grid[upindex] ==1) {
            quickunion.union(eleindex,upindex);
            StdOut.print('y');}        
        else
            StdOut.print('n');
        
        int downindex = eleindex+arraysize; 

        if (downindex < arraysize*arraysize+1 && !quickunion.connected(eleindex,downindex) && grid[downindex] ==1 && eleindex/arraysize != arraysize) {
            quickunion.union(eleindex,downindex);
            StdOut.print('y');}        
        else
            StdOut.print('n');
        StdOut.print('\n');
        StdOut.print(quickunion.connected(3,4));
        StdOut.print(quickunion.connected(1,4));
        StdOut.print(quickunion.connected(5,4));
        StdOut.print(quickunion.connected(7,4));
        StdOut.print('\n');
    }
    public boolean isOpen(int row, int col)  // is site (row, col) open? 
    {return grid[(row-1)*arraysize+col] ==1;}
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {return quickunion.connected(0, (row-1)*arraysize+col+1);}
    public     int numberOfOpenSites()       // number of open sites
    {
        int count = 0;
        for (int i = 0; i<arraysize*arraysize+2; i++){
            if (grid[i] ==1) count++;
        }
        return count-2; //compensate for the two virtual sites
    }
    public boolean percolates()              // does the system percolate?
    {return quickunion.connected(0,arraysize*arraysize+1);}

    public static void main(String[] args)   // test client (optional)
    {
        Percolation pc = new Percolation(3);
        pc.open(2,1);
        pc.open(1,2);
        pc.open(1,3);
        pc.open(2,3);
        pc.open(3,3);
        StdOut.print(pc.isFull(2,1));       
        StdOut.print('\n');
        StdOut.print(pc.isOpen(2,2));
        StdOut.print('\n');
        StdOut.print(pc.isOpen(2,3));
        StdOut.print('\n');
        StdOut.print(pc.numberOfOpenSites());
        StdOut.print('\n');
        StdOut.print(pc.percolates());
    }
}