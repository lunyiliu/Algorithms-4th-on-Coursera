/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] Array_statu; // represent the status of the array
    // private int[] Array_size;
    private int n;
    private WeightedQuickUnionUF UF; // the WeightedQuickUnionUF object

    // constructor
    public Percolation(int n_) {
        if (n_ <= 0) throw new IllegalArgumentException();
        // Array_root = new int[n_ * n_ + 2];
        Array_statu = new int[n_ * n_ + 2];
        // Array_size = new int[n_ * n_ + 2];
        for (int i = 0; i < n_ * n_ + 2; i++) {
            // if (i < n_)
            // Array_root[i] = n_ * n_;

            // else if (i > n_ * n_ - n_ - 1 && i < n_ * n_)
            // Array_root[i] = n_ * n_ + 1;
            // else
            // Array_root[i] = i;
            Array_statu[i] = 0;
            // Array_size[i] = 1;
        }

        // Array_statu[n * n] = 2;
        // Array_statu[n * n + 1] = 2;

        n = n_;
        UF = new WeightedQuickUnionUF(n * n + 2);
        /*
        for (int i = 0; i < n; i++) UF.union(n * n, i);
        for (int i = n * n - n; i < n * n; i++) UF.union(n * n + 1, i);
        */
    }

    // open site (row, col) if it is not open already
    public void open(int rand_row, int rand_col) {
        /*
        if ((get_address(rand_row, rand_col) > n * n - n - 1
                && get_address(rand_row, rand_col) < n * n)) {
            for (int i = n * n - n; i < n * n; i++)
                Array_statu[i] = 2;
        }
        */
        if (rand_row <= 0 || rand_col <= 0 || rand_row > n || rand_col > n)
            throw new IllegalArgumentException();

        if (rand_row == 1) UF.union(n * n, get_address(rand_row, rand_col));
        if (rand_row == n) UF.union(n * n + 1, get_address(rand_row, rand_col));
        Array_statu[get_address(rand_row, rand_col)] = 1;

        if (rand_row != 1 && (Array_statu[get_address(rand_row - 1, rand_col)] != 0)) {

            UF.union(get_address(rand_row, rand_col), get_address(rand_row - 1, rand_col));
            /*
            Array_statu[UF.find(get_address(rand_row, rand_col))] = 2;
            Array_statu[UF.find(get_address(rand_row - 1, rand_col))] = 2;
            */
        }
        if (rand_row != n && (Array_statu[get_address(rand_row + 1, rand_col)] != 0)) {
            UF.union(get_address(rand_row, rand_col), get_address(rand_row + 1, rand_col));
        }
        if (rand_col != 1 && (Array_statu[get_address(rand_row, rand_col - 1)] != 0)) {
            UF.union(get_address(rand_row, rand_col), get_address(rand_row, rand_col - 1));

        }
        if (rand_col != n && (Array_statu[get_address(rand_row, rand_col + 1)] != 0)) {
            UF.union(get_address(rand_row, rand_col), get_address(rand_row, rand_col + 1));
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n)
            throw new IllegalArgumentException();
        else
            return Array_statu[get_address(row, col)] != 0;


    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n)
            throw new IllegalArgumentException();
        else
            return UF.connected(get_address(row, col), n * n);
    }

    // does the system percolate?
    public boolean percolates() {
        return UF.connected(n * n, n * n + 1);
    }

    // number of open sites
    public int numberOfOpenSites() {
        int num = 0;
        for (int i = 0; i < n * n; i++) {
            if (Array_statu[i] != 0)
                num++;
        }
        return num;
    }

    // get the  address
    private int get_address(int row, int col) {
        int address;
        if (row == 0 && col == 0) {
            address = n * n;
        }
        else if (row == n && col == n + 1) {
            address = n * n + 1;
        }
        else {
            address = (row - 1) * n + col - 1;
        }
        try {
            if (address >= 0 || address <= n * n + 1)
                return address;
            else
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e) {
            return -1;
        }
    }

}

