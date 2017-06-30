import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Stack;

public class Solver {
    private int step;
    private Node latestMain;
    private class Node implements Comparable<Node> {
        private Node prev;
        private Board board;
        private int step;
        private int priority;
        public int compareTo(Node that) { // notice public method in private class
            if (this == that) return 0;
            else if (this.priority < that.priority) return -1;
            else if (this.priority > that.priority) return 1;
            else return 0;
        }
    }
    
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
//        if (initial == null) throw new NullPointerException();
        MinPQ<Node> pqMain = new MinPQ<Node>();
        MinPQ<Node> pqTwin = new MinPQ<Node>();
        step = 0;
        
        // A* algorithm. Step 1 push the initial node to minPQ
        Node first = new Node(); // define initial node
        first.board = initial;
        first.step = step;
        first.priority = first.board.manhattan()+first.step; // define priority
        
        
        Node firstTwin = new Node(); // the twin of first node for another tree
        firstTwin.board = initial.twin();
        firstTwin.step = step;
        firstTwin.priority = firstTwin.board.manhattan()+firstTwin.step; // define priority
        
        pqMain.insert(first);
        pqTwin.insert(firstTwin);
        
        // step 2. delete one with min priority
        latestMain = first;
        Node latestTwin = firstTwin;
        
        while (!latestMain.board.isGoal() && !latestTwin.board.isGoal()) {
            latestMain = pqMain.delMin(); 
// notice the series of latestMain may requires turn back and is not a chain of final solution. Need to traceback from final solution.
            latestTwin = pqTwin.delMin();
            
            
            if (!latestMain.board.isGoal() && !latestTwin.board.isGoal()) {
                // step 3. add neighbors to minPQ
                
                Iterable<Board> neighborMain = latestMain.board.neighbors();
                
                for (Board b: neighborMain) {
                    Node boardMain = new Node();
                    boardMain.prev = latestMain;
                    boardMain.step = boardMain.prev.step+1;
                    boardMain.board = b;
                    boardMain.priority = boardMain.board.manhattan()+boardMain.step; // see if need this
                    // critical optimization
                    if (latestMain.prev == null) pqMain.insert(boardMain);
                    else if (!boardMain.board.equals(latestMain.prev.board)) pqMain.insert(boardMain);
                }
                
                Iterable<Board> neighborTwin = latestTwin.board.neighbors();
                
                for (Board b: neighborTwin) {
                    Node boardTwin = new Node();
                    boardTwin.prev = latestTwin;
                    boardTwin.step = boardTwin.prev.step+1;
                    boardTwin.board = b;
                    boardTwin.priority = boardTwin.board.manhattan()+boardTwin.step; // see if need this
                    // critical optimization
                    if (latestTwin.prev == null) pqTwin.insert(boardTwin);
                    else if (!boardTwin.board.equals(latestTwin.prev.board)) pqTwin.insert(boardTwin);
                }
            }
        }
    }
    
    
    public boolean isSolvable()            // is the initial board solvable?
    {
        return latestMain.board.isGoal();
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (isSolvable()) return latestMain.step;
        else return -1;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    { 
        Stack<Board> tempsteps = new Stack<Board>();
        Stack<Board> finalsteps = new Stack<Board>();
        Node latestMainCopy = latestMain;
        while (latestMainCopy != null) { // trace back using latest.prev
            tempsteps.push(latestMainCopy.board);
            latestMainCopy = latestMainCopy.prev;
        }
        while (!tempsteps.isEmpty()) finalsteps.push(tempsteps.pop());
        return finalsteps; 
    } // use a stack
    
    
    public static void main(String[] args) { // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
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