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

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int n; //
    private int trials; //
    private double[] threshold; //

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n_, int trials_) {

        if (n_ > 0 && trials_ > 0) {
            n = n_;
            trials = trials_;
        }
        else
            throw new RuntimeException();
        threshold = new double[trials];
        for (int i = 0; i < trials; i++)
            threshold[i] = 0;
        double rand_row, rand_col;

        for (int times = 0; times < trials; times++) {
            Percolation p = new Percolation(n);
            while (true) {
                if (p.percolates()) {
                    threshold[times] = (double) p.numberOfOpenSites() / (n * n);
                    break;
                }
                while (true) {
                    rand_row = Math.ceil(StdRandom.uniform() * n);
                    rand_col = Math.ceil(StdRandom.uniform() * n);
                    if (!p.isOpen((int) rand_row, (int) rand_col))
                        break;
                }
                p.open((int) rand_row, (int) rand_col);

            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        double mean;
        for (int i = 0; i < trials; i++)
            sum += threshold[i];
        mean = sum / trials;
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = mean();
        double standard;
        double val = 0;
        for (int i = 0; i < trials; i++)
            val += (threshold[i] - mean) * (threshold[i] - mean);
        standard = Math.pow(val / (trials - 1), 0.5);
        return standard;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - 1.96 * stddev() / Math.pow(trials, 0.5));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + 1.96 * stddev() / Math.pow(trials, 0.5));
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats s = new PercolationStats(Integer.parseInt(args[0]),
                                                  Integer.parseInt(args[1]));

        System.out.print("mean                  =");
        System.out.println(s.mean());
        System.out.print("sttdev                =");
        System.out.println(s.stddev());
        System.out.print("95% confidence interval= [");
        System.out.print(s.confidenceLo());
        System.out.print(s.confidenceHi());
        System.out.print("]");

    }


}
