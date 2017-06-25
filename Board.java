import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;
import java.util.Arrays;

public final class Board {
    private int[][] newblocks;
    
    public Board(int[][] blocks)           // construct a board from an n-by-n array of newblocks
    { 
        newblocks = new int[blocks.length][blocks.length];
        //              this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) 
            for (int j = 0; j < blocks.length; j++) 
            newblocks[i][j] = blocks[i][j];
    }
    
    public int dimension()                 // board dimension n
    { return newblocks.length; }
    public int hamming()                   // number of newblocks out of place
    {
        int count = 0; // count out of place elements
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (newblocks[i][j] != i*dimension()+j+1 && newblocks[i][j] != 0) count++;
            }
        }
        
        return count;
    }
    public int manhattan()                 // sum of Manhattan distances between newblocks and goal
    {
        int count = 0; // count out of place elements
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (newblocks[i][j] != i*dimension()+j+1 && newblocks[i][j] != 0) 
                    count += Math.abs(i - (newblocks[i][j]-1)/dimension()) + Math.abs(j - (newblocks[i][j]-1) % dimension());
            }
        }
        return count;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) { // notice corner case
                if (i == dimension()-1 && j == dimension()-1) return newblocks[i][j] == 0;
                else if (newblocks[i][j] != i*dimension()+j+1) return false;
            }
        }
        return true;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of newblocks
    { 
// defensive copy of newblocks
        int[][] newblocksCopy = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++)  newblocksCopy[i][j] = newblocks[i][j];
        }
        // prepare for swap
        int i = StdRandom.uniform(dimension());
        int j = StdRandom.uniform(dimension());
        while (newblocks[i][j] == 0) {
            i = StdRandom.uniform(dimension());
            j = StdRandom.uniform(dimension());
        }
        int iSwap = StdRandom.uniform(dimension());
        int jSwap = StdRandom.uniform(dimension());
        while (i == iSwap && j == jSwap || newblocks[iSwap][jSwap] == 0) { // prevent same element
            iSwap = StdRandom.uniform(dimension());
            jSwap = StdRandom.uniform(dimension());
        }
        swap(newblocksCopy, i, j, iSwap, jSwap);
        Board twinBoard = new Board(newblocksCopy); 
        return twinBoard;
    }
    private void swap(int[][] block, int i, int j, int iSwap, int jSwap) // swap elemetns in newblocks[i][j] and newblocks[iSwap][jSwap]
    {
        int temp = 0;
        temp = block[i][j];
        block[i][j] = block[iSwap][jSwap];
        block[iSwap][jSwap] = temp;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == null) return false;
        if (this == y) return true;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y; 
        return Arrays.equals(this.newblocks, that.newblocks);
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> nextBoard = new Stack<Board>();
        int iZero = 0;
        int jZero = 0;
        // find zero
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (newblocks[i][j] == 0) {
                    iZero = i;
                    jZero = j;
                }
            }
        }
        // see possible next move
        Stack<Integer> idx = neighborIndex(iZero, jZero);
        while (!idx.isEmpty()) {
            // defensive copy of newblocks
            int[][] newblocksCopy = new int[dimension()][dimension()];
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++)  newblocksCopy[i][j] = newblocks[i][j];
            }
            
            int jNext = idx.pop();
            int iNext = idx.pop(); // notice order reversed
            
            // new Board generation
            swap(newblocksCopy, iZero, jZero, iNext, jNext);
            nextBoard.push(new Board(newblocksCopy)); // pushs in a reference type. Need a defensive copy
        }
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
        StringBuilder s = new StringBuilder();
        s.append(dimension());
        s.append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(newblocks[i][j]);
                s.append("\t");
            }
            s.append("\n");
        }
        return s.toString();
    } 
    
    public static void main(String[] args) // unit tests (not graded)
    {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] newblocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            newblocks[i][j] = in.readInt();
        Board initial = new Board(newblocks);
        Board initial2 = new Board(newblocks);
        StdOut.println(initial);
        StdOut.println(initial.equals(initial2));
        /*        
         StdOut.println(initial.dimension());
         StdOut.println("Hamming " + initial.hamming());
         StdOut.println("Manhattan " + initial.manhattan());
         StdOut.println(initial.isGoal());
         StdOut.println(initial.twin());
         
         StdOut.println(initial.equals(initial)); */
//        for (Board b: initial.neighbors()) StdOut.println(b);
        
    }
}