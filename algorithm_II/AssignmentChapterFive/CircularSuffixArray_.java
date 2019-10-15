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

public class CircularSuffixArray_ {
    private String[] CSA;
    private int N;
    private static final int R = 256;   // extended ASCII alphabet size
    private static final int CUTOFF = 5;
    private int[] index;
    private int[] a_index;

    // circular suffix array of s
    public CircularSuffixArray_(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        N = s.length();
        CSA = new String[N];
        index = new int[N];
        a_index = new int[N];
        for (int i = 0; i < N; i++) {
            CSA[i] = s.substring(i) + s.substring(0, i);
            index[i] = i;
            a_index[i] = i;
        }
        sort(CSA);
        /*
        for (int i = 0; i < s.length(); i++) {
            BinaryStdOut.write(s.charAt(i));
        }
        */
    }

    // length of s
    public int length() {
        return N;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i >= 0 && i < N) {
            return a_index[i];
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = args[0];
        CircularSuffixArray_ CSA = new CircularSuffixArray_(s);
        for (int i = 0; i < s.length(); i++) {
            //StdOut.println(CSA.index(i));
        }
    }

    private void sort(String[] a) {
        int n = a.length;
        String[] aux = new String[n];
        sort(a, 0, n - 1, 0, aux, a_index);
    }

    // return dth character of s, -1 if d = length of string
    private int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private void sort(String[] a, int lo, int hi, int d, String[] aux, int[] a_index) {
       /*
        StdOut.print(lo);
        StdOut.print(",");
        StdOut.print(hi);
        StdOut.println();
        */
        // cutoff to insertion sort for small subarrays
        if (hi - lo <= 0) {
            //insertion(a, lo, hi, d);
            return;
        }

        // compute frequency counts
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            count[c + 2]++;
        }
        // transform counts to indicies
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];

        // distribute
/*
        StdOut.print(lo);
        StdOut.print(":");
        StdOut.print(hi);
        StdOut.println();
*/
        for (int i = lo; i <= hi; i++) {

            int c = charAt(a[i], d);
            int temp = count[c + 1]++;
            index[temp] = a_index[i];
            aux[temp] = a[i];
        }
        // copy back
        /*
        for (int i = 0; i < index.length; i++) {
            StdOut.print("              " + a_index[i]);
            StdOut.println();

        }
        */
        for (int i = lo; i <= hi; i++) {
            a_index[i] = index[i - lo];
            a[i] = aux[i - lo];
        }


        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux, a_index);

    }


    // insertion sort a[lo..hi], starting at dth character
    private void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                exch(a, j, j - 1);
    }

    // exchange a[i] and a[j]
    private void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        int temp_i = index[i];
        index[i] = index[j];
        index[j] = temp_i;
    }

    // is v less than w, starting at character d
    private boolean less(String v, String w, int d) {
        // assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }
}
