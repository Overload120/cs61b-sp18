package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF field;
    private boolean[][] arrIsOpen;
    private int numOfOpenSites;
    int numOfBottomFills;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        numOfOpenSites = 0;
        numOfBottomFills = 0;
        field = new WeightedQuickUnionUF(N * N + 2);
        arrIsOpen = new boolean[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                arrIsOpen[i][j] = false;
            }
        }
    }                // create N-by-N grid, with all sites initially blocked

    public void open(int row, int col) {
        if (row >= arrIsOpen.length || col >= arrIsOpen[0].length || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        }
        arrIsOpen[row][col] = true;
        if (row == 0) {
            field.union(row * arrIsOpen.length + col, arrIsOpen.length * arrIsOpen.length);
        }
        if (row == arrIsOpen.length - 1 && numOfBottomFills == 0) {
            field.union(row * arrIsOpen.length + col, arrIsOpen.length * arrIsOpen.length + 1);
            numOfBottomFills += 1;
        }
        numOfOpenSites += 1;
        int currentRow;
        int currentCol;
        boolean rowIsOutside;
        boolean colIsOutside;
        for (int i = 1; i <= 2; ++i) {
            currentRow = row + (int) Math.pow(-1, i);
            rowIsOutside = currentRow < 0 || currentRow > arrIsOpen.length - 1;
            if (rowIsOutside) {
                continue;
            }
            if (arrIsOpen[currentRow][col]) {
                field.union(row * arrIsOpen.length + col, currentRow * arrIsOpen.length + col);
            }
        }
        for (int i = 1; i <= 2; ++i) {
            currentCol = col + (int) Math.pow(-1, i);
            colIsOutside = currentCol < 0 || currentCol > arrIsOpen[0].length - 1;
            if (colIsOutside) {
                continue;
            }
            if (arrIsOpen[row][currentCol]) {
                field.union(row * arrIsOpen.length + col, row * arrIsOpen.length + currentCol);
            }
        }

    }

    // open the site (row, col) if it is not open already
    public boolean isOpen(int row, int col) {
        if (row >= arrIsOpen.length || col >= arrIsOpen[0].length || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return arrIsOpen[row][col];
    }  // is the site (row, col) open?

    public boolean isFull(int row, int col) {
        if (row >= arrIsOpen.length || col >= arrIsOpen[0].length || row < 0 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return field.connected(row * arrIsOpen.length + col, arrIsOpen.length * arrIsOpen.length);
    }  // is the site (row, col) full?

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }           // number of open sites

    public boolean percolates() {
        return field.connected(arrIsOpen.length * arrIsOpen.length + 1, arrIsOpen.length * arrIsOpen.length);
    }// does the system percolate?

    public static void main(String[] args) {

    }   // use for unit testing (not required)
}
