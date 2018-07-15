import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {       
   private boolean[][] opened;
   private int top = 0;
   private int bottom;
   private int size;
   private WeightedQuickUnionUF uf;
   private int openedSiteNum = 0;
   
   /**
    * Creates n-by-n grid, with all sites blocked.
    */
   public Percolation(int n) {
       size = n;
       bottom = n * n + 1;
       uf = new WeightedQuickUnionUF(n * n + 2);
       opened = new boolean[n][n];
   }

   /**
    * Opens site (row row, column col) if it is not open already.
    */
   public void open(int row, int col) {
       if (! opened[row - 1][col - 1]) {
           opened[row - 1][col - 1] = true;
           openedSiteNum += 1;
       }
       if (row == 1) {
           uf.union(getUFIndex(row, col), top);
       }
       if (row == size) {
           uf.union(getUFIndex(row, col), bottom);
       }

       if (col > 1 && isOpen(row, col - 1)) {
           uf.union(getUFIndex(row, col), getUFIndex(row, col - 1));
       }
       if (col < size && isOpen(row, col + 1)) {
           uf.union(getUFIndex(row, col), getUFIndex(row, col + 1));
       }
       if (row > 1 && isOpen(row - 1, col)) {
           uf.union(getUFIndex(row, col), getUFIndex(row - 1, col));
       }
       if (row < size && isOpen(row + 1, col)) {
           uf.union(getUFIndex(row, col), getUFIndex(row + 1, col));
       }
   }

   /**
    * Is site (row row, column col) open?
    */
   public boolean isOpen(int row, int col) {
       return opened[row - 1][col - 1];
   }

   /**
    * Is site (row row, column col) full?
    */
   public boolean isFull(int row, int col) {
       if (1 <= row && row <= size && 1 <= col && col <= size) {
           return uf.connected(top, getUFIndex(row , col));
       } else {
           throw new IllegalArgumentException();
       }
   }
   
   /**
    * Number of open sites
    */
   public int numberOfOpenSites() {
       return openedSiteNum;
   }      

   /**
    * Does the system percolate?
    */
   public boolean percolates() {
       return uf.connected(top, bottom);
   }

   private int getUFIndex(int row, int col) {
       return size * (row - 1) + col;
   }
}