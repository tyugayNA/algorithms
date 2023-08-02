/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites;
    private final int virtualTop;
    private final int virtualBottom;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF backwashUF;

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
            weightedQuickUnionUF.union(virtualTop, cell);
            backwashUF.union(virtualTop, cell);
        }
        if (row == grid.length) {
            weightedQuickUnionUF.union(virtualBottom, cell);
        }
        if (isOnBounds(row - 1, col) && isOpen(row - 1, col)) {
            int cellAbove = (x - 1) * grid.length + y;
            weightedQuickUnionUF.union(cell, cellAbove);
            backwashUF.union(cell, cellAbove);
        }
        if (isOnBounds(row + 1, col) && isOpen(row + 1, col)) {
            int cellBelow = (x + 1) * grid.length + y;
            weightedQuickUnionUF.union(cell, cellBelow);
            backwashUF.union(cell, cellBelow);
        }
        if (isOnBounds(row, col - 1) && isOpen(row, col - 1)) {
            int cellLeft = x * grid.length + y - 1;
            weightedQuickUnionUF.union(cell, cellLeft);
            backwashUF.union(cell, cellLeft);
        }
        if (isOnBounds(row, col + 1) && isOpen(row, col + 1)) {
            int cellRight = x * grid.length + y + 1;
            weightedQuickUnionUF.union(cell, cellRight);
            backwashUF.union(cell, cellRight);
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
        int cell = (row - 1) * grid.length + col - 1;
        return backwashUF.find(virtualTop) == backwashUF.find(cell);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(virtualTop) == weightedQuickUnionUF.find(virtualBottom);
    }

    private boolean isOnBounds(int row, int col) {
        return row > 0 && row <= grid.length && col > 0 && col <= grid.length;
    }

    private void checkBounds(int row, int col) {
        if (row < 1 || row > grid.length) {
            throw new IllegalArgumentException("row is out off bounds");
        }
        if (col < 1 || col > grid.length) {
            throw new IllegalArgumentException("col is out off bounds");
        }
    }
}
