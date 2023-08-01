/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites;
    private int virtualTop;
    private int virtualBottom;
    private boolean[][] grid;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF backwashUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        openSites = 0;
        grid = new boolean[n][n];
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        backwashUF = new WeightedQuickUnionUF(n * n + 1);
        virtualTop = n * n;
        virtualBottom = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBounds(row, col);
        int cell;
        int x = row - 1, y = col - 1;

        if (isOpen(row, col)) {
            return;
        }
        grid[x][y] = true;
        openSites++;
        cell = x * grid.length + y;

        if (row == 1) {
            weightedQuickUnionUF.union(cell, virtualTop);
            backwashUF.union(cell, virtualTop);
        }
        if (row == grid.length) {
            weightedQuickUnionUF.union(cell, virtualBottom);
        }
        if (isOpen(row - 1, col)) {
            int cellAbove = (x - 1) * grid.length + y;
            weightedQuickUnionUF.union(cell, cellAbove);
            backwashUF.union(cell, cellAbove);
        }
        if (isOpen(row + 1, col)) {
            int cellBelow = (x + 1) * grid.length + y;
            weightedQuickUnionUF.union(cell, cellBelow);
            backwashUF.union(cell, cellBelow);
        }
        if (isOpen(row, col - 1)) {
            int cellLeft = x * grid.length + y - 1;
            weightedQuickUnionUF.union(cell, cellLeft);
            backwashUF.union(cell, cellLeft);
        }
        if (isOpen(row, col + 1)) {
            int cellRight = x * grid.length + y - 1;
            weightedQuickUnionUF.union(cell, cellRight);
            backwashUF.union(cell, cellRight);
        }

        // check cell above
        if (x - 1 > 0) {
            if (grid[x - 1][y]) {
                int cellUp = (x - 1) * grid.length + y;
                weightedQuickUnionUF.union(cell, cellUp);
                backwashUF.union(cell, cellUp);
            }
        } else {
            weightedQuickUnionUF.union(cell, virtualTop);
            backwashUF.union(cell, virtualTop);
        }

        // check cell below
        if (x + 1 < grid.length) {
            if (grid[x + 1][y]) {
                int cellDown = (x + 1) * grid.length + y;
                weightedQuickUnionUF.union(cell, cellDown);
                backwashUF.union(cell, cellDown);
            }
        } else {
            weightedQuickUnionUF.union(cell, virtualBottom);
            if (backwashUF.find(virtualTop) == backwashUF.find(cell)) {
                backwashUF.union(cell, virtualBottom);
            }
        }

        // check cell left
        if (y - 1 > 0) {
            if (grid[x][y - 1]) {
                int cellLeft = x * grid.length + (y - 1);
                weightedQuickUnionUF.union(cell, cellLeft);
                backwashUF.union(cell, cellLeft);
            }
        }

        // check cell right
        if (y + 1 > grid.length) {
            if (grid[x][y + 1]) {
                int cellRight = x * grid.length + (y + 1);
                weightedQuickUnionUF.union(cell, cellRight);
                backwashUF.union(cell, cellRight);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        int x = row - 1, y = col - 1;
        int cell = x * grid.length + y;
        return backwashUF.find(cell) == backwashUF.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(virtualTop) == weightedQuickUnionUF.find(virtualBottom);
    }

    private void checkBounds(int row, int col) {
        if (row < 1 || row > grid.length) {
            throw new IllegalArgumentException("row is out off bounds");
        }
        if (col < 1 || col > grid.length) {
            throw new IllegalArgumentException("col is out off bounds");
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation test = new Percolation(2);
        test.open(1, 1);
        test.open(2, 1);
        StdOut.println(test.percolates());
    }
}
