import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private int expNum;
   private double[] openedSiteNums;
   private Percolation percolation;
       
   /**
    * Perform trials independent experiments on an n-by-n grid
    */
   public PercolationStats(int n, int trials) {
       if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Given n <= 0 || trials <= 0");
        }
       expNum = trials;
       openedSiteNums = new double[trials];
       for (int i = 0; i < trials; i++) {
           percolation = new Percolation(n);
           while (!percolation.percolates()) {
               int rowSeed = StdRandom.uniform(1, n + 1);
               int colSeed = StdRandom.uniform(1, n + 1);
               percolation.open(rowSeed, colSeed);
           }
           openedSiteNums[i] = (double) percolation.numberOfOpenSites()/(n*n);
       }
   }
   
   /**
    * Sample mean of percolation threshold
    */
   public double mean() {
       return StdStats.mean(openedSiteNums);
   }
   
   /**
    * Sample standard deviation of percolation threshold
    */
   public double stddev() {
       return StdStats.stddev(openedSiteNums);
   }
   
   /**
    * Low  endpoint of 95% confidence interval
    */
   public double confidenceLo() {
       return mean() - ((1.96 * stddev()) / Math.sqrt(expNum));
   }
   
   /**
    * High  endpoint of 95% confidence interval
    */
   public double confidenceHi() {
       return mean() + ((1.96 * stddev()) / Math.sqrt(expNum));
   }
   
   public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        String confidenceInterval = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + confidenceInterval);
    }
}