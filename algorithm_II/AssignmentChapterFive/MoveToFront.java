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

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] ascii = new int[256];
        for (int i = 0; i < 256; i++) {
            ascii[i] = i;
        }
        // StdOut.println("encode:");
        while (!BinaryStdIn.isEmpty()) {
            int current = (int) BinaryStdIn.readChar();
            // StdOut.println(current);
            // StdOut.println(ascii[current]);
            BinaryStdOut.write((char) ascii[current]);
            for (int i = 0; i < 256; i++) {
                if (ascii[i] < ascii[current]) {
                    ascii[i]++;
                }
            }
            ascii[current] = 0;
        }
        BinaryStdOut.close();
    }


    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] ascii = new int[256];
        for (int i = 0; i < 256; i++) {
            ascii[i] = i;
        }
        //StdOut.println("decode:");
        while (!BinaryStdIn.isEmpty()) {
            int current = (int) BinaryStdIn.readChar();
            //StdOut.print(current);
            /*(
            for (int i = 0; i < 90; i++) {
                StdOut.print(ascii[i]);
                StdOut.print(" ");
            }
            StdOut.println("");
            StdOut.println(ascii[current]);
            */
            BinaryStdOut.write((char) ascii[current]);
            int temp = ascii[current];
            for (int i = current; i >= 0; i--) {
                if (i == 0) {
                    ascii[0] = temp;
                }
                else {
                    ascii[i] = ascii[i - 1];
                }

            }

            //ascii[current] = 0;
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        if (args[0].equals("+")) {
            decode();
        }
    }

}
