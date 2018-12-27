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

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int i = 0;
        String str;
        RandomizedQueue<String> MyRQ = new RandomizedQueue<String>();
        while (i < k) {
            str = StdIn.readString();
            if (StdRandom.uniform(2) == 1) {
                MyRQ.enqueue(str);
                i++;
            }
            if (StdIn.isEmpty()) break;
        }
        for (String s : MyRQ) {
            StdOut.println(s);
        }
    }
}
