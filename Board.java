import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;
import java.util.Arrays;

public class Board{
    private int step;
    private int[][] blocks;
    
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    { 
        step = 0;
        this.blocks = blocks;
    } // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    { return blocks.length; }
    public int hamming()                   // number of blocks out of place
    {
        int count = 0; // count out of place elements
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != i*dimension()+j+1 && blocks[i][j] != 0) count++;
            }
        }
        
        return count+step;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int count = 0; // count out of place elements
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != i*dimension()+j+1 && blocks[i][j] != 0) count += Math.abs(i - (blocks[i][j]-1)/dimension()) + Math.abs(j - (blocks[i][j]-1)%dimension()); // Manhatten distance
            }
        }
        return count+step;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != i*dimension()+j+1) return false;
            }
        }
        return true;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    { 
// defensive copy of blocks
        int[][] blocksCopy= new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++)  blocksCopy[i][j] = blocks[i][j];
        }
        // prepare for swap
        int i = StdRandom.uniform(dimension());
        int j = StdRandom.uniform(dimension());
        while (blocks[i][j] == 0) {
            i = StdRandom.uniform(dimension());
            j = StdRandom.uniform(dimension());
        }
        int iSwap = StdRandom.uniform(dimension());
        int jSwap = StdRandom.uniform(dimension());
        while (i == iSwap && j == jSwap || blocks[iSwap][jSwap] == 0) { // prevent same element
            iSwap = StdRandom.uniform(dimension());
            jSwap = StdRandom.uniform(dimension());
        }
        swap(blocksCopy, i, j, iSwap, jSwap);
        Board twinBoard = new Board(blocksCopy); 
        return twinBoard;
    }
    private void swap(int[][] block, int i, int j, int iSwap, int jSwap) // swap elemetns in blocks[i][j] and blocks[iSwap][jSwap]
    {
        int temp = 0;
        temp = block[i][j];
        block[i][j] = block[iSwap][jSwap];
        block[iSwap][jSwap] = temp;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y; 
        return Arrays.equals(this.blocks, that.blocks);
        /*        for (int i = 0; i < dimension(); i++) {
         for (int j = 0; j < dimension(); j++) {
         if (blocks[i][j] != i*dimension()+j+1) return false;
         }
         }
         return true; */
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> nextBoard = new Stack<Board>();
        int iZero = 0;
        int jZero = 0;
        // find zero
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0 ) {
                    iZero = i;
                    jZero = j;
                }
            }
        }
        // see possible next move
        Stack<Integer> idx = neighborIndex(iZero, jZero);
        while (!idx.isEmpty()) {
            // defensive copy of blocks
            int[][] blocksCopy= new int[dimension()][dimension()];
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++)  blocksCopy[i][j] = blocks[i][j];
            }
            
            int iNext = idx.pop();
            int jNext = idx.pop();
            
            // new Board generation
            swap(blocksCopy, iZero, jZero, iNext, jNext);
            nextBoard.push(new Board(blocksCopy)); // pushs in a reference type. Need a defensive copy
        }
        step++;
        return nextBoard;
    }
    private Stack<Integer> neighborIndex(int iZero, int jZero) // find possible next moves
    {
        Stack<Integer> s = new Stack<Integer>();        
        //corner case
        if (iZero != 0) {
            s.push(iZero-1);
            s.push(jZero);
        }
        if (iZero != dimension()-1) {
            s.push(iZero+1);
            s.push(jZero);
        }
        if (jZero != 0) {
            s.push(iZero);
            s.push(jZero-1);
        }
        if (jZero != dimension()-1) {
            s.push(iZero);
            s.push(jZero+1);
        }
        return s;
    }
    
    public String toString()               // string representation of this board (in the output format specified below)
    { 
        String s = Integer.toString(dimension()) + "\n";
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s += Integer.toString(blocks[i][j])+"\t";
            }
            s += "\n";
        }
        return s;
    }
    
    public static void main(String[] args) // unit tests (not graded)
    {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(initial.dimension());
        StdOut.println("Hamming " + initial.hamming());
        StdOut.println("Manhattan " + initial.manhattan());
        StdOut.println(initial.isGoal());
        StdOut.println(initial.twin());
        
        StdOut.println(initial.equals(initial));
        for (Board b: initial.neighbors()) StdOut.println(b);
        
    }
}