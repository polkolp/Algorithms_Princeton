public class Board {
    private int step;
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    { step = 0; } // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    { return blocks.length; }
    public int hamming()                   // number of blocks out of place
    {
        int count = 0; // count out of place elements
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); i++) {
                if (blocks[i][j] != i*dimension()+j+1) count++;
            }
        }
        return count+step;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
        public boolean isGoal()                // is this board the goal board?
        public Board twin()                    // a board that is obtained by exchanging any pair of blocks
        public boolean equals(Object y)        // does this board equal y?
        public Iterable<Board> neighbors()     // all neighboring boards
        public String toString()               // string representation of this board (in the output format specified below)
        
        public static void main(String[] args) // unit tests (not graded)
    {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
    }
}