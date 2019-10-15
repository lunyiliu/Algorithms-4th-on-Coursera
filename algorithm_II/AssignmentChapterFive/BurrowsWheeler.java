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

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;


public class BurrowsWheeler {
    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        // long start1 = System.currentTimeMillis();
        StringBuilder s = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            s.append(BinaryStdIn.readChar());
        }
        // StdOut.println(s);
        CircularSuffixArray CSA = new CircularSuffixArray(s.toString());
        int first = -1;
        for (int i = 0; i < s.length(); i++) {
            if (CSA.index(i) == 0) {
                first = i;
            }
        }
        //StdOut.println(first);
        BinaryStdOut.write(first);
        for (int i = 0; i < s.length(); i++) {
            if (i != first) {
                BinaryStdOut.write(s.charAt(CSA.index(i) - 1));
            }
            else {
                BinaryStdOut.write(s.charAt(s.length() - 1));
            }

        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        //long start1 = System.currentTimeMillis();
        int first = BinaryStdIn.readInt();
        StringBuilder t = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            t.append(BinaryStdIn.readChar());
        }
        //long start2 = System.currentTimeMillis();
        String t_str = t.toString();
        char[] t_char = t_str.toCharArray();

        Arrays.sort(t_char);

        String s = String.valueOf(t_char);
        // long end2 = System.currentTimeMillis();
        //System.out.println(end2 - start2);
        /*
        for (int i = 0; i < s.length(); i++) {
            StdOut.println(s.charAt(i));
        }
        */
        int[] next = new int[t_str.length()];
        //constructing the count array of s
        int[] count = new int[257];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) + 1]++;
        }
        //constructing the cumsum
        for (int i = 0; i < count.length - 1; i++) {
            count[i + 1] += count[i];
        }
        //get next[]
        for (int i = 0; i < t_str.length(); i++) {
            next[count[t_str.charAt(i)]++] = i;
        }
/*
        for (int i = 0; i < next.length; i++) {
            StdOut.println(next[i]);
        }
*/
        BinaryStdOut.write(s.charAt(first));
        int current = first;
        for (int i = 1; i < next.length; i++) {
            current = next[current];
            BinaryStdOut.write(s.charAt(current));
        }

        //long end1 = System.currentTimeMillis();
        // BinaryStdOut.write('\n');
        //BinaryStdOut.write(String.format("%d", end1 - start2));
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }
        if (args[0].equals("+")) {
            inverseTransform();


        }
    }

}
