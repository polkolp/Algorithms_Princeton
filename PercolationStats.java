import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private Percolation pc;
    private double[] fracopen; //fraction of open sites

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        fracopen = new double[trials];
        
        for (int i = 0; i < trials; i++) {      
            pc = new Percolation(n);
            while (!pc.percolates()) {
                int randrow = StdRandom.uniform(n);
                int randcol = StdRandom.uniform(n); 
                pc.open(randrow+1, randcol+1);
            }
            fracopen[i] = ((double) pc.numberOfOpenSites())/(n*n);
        }
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(fracopen);
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(fracopen);
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        int t = fracopen.length;
        return StdStats.mean(fracopen)-1.96*StdStats.stddev(fracopen)/Math.sqrt(t);
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        int t = fracopen.length;
        return StdStats.mean(fracopen)+1.96*StdStats.stddev(fracopen)/Math.sqrt(t);
    }
    
    public static void main(String[] args)        // test client (described below)
    {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        
        PercolationStats pcs = new PercolationStats(n, trials);
        StdOut.println("mean" + "\t = " + pcs.mean());
        StdOut.println("stddev" + "\t = " + pcs.stddev());
        StdOut.println("95% confidence interval" + "\t = [" + pcs.confidenceLo() + pcs.confidenceHi() + "]");
    }
}