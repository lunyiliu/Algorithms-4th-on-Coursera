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

import edu.princeton.cs.algs4.StdOut;

public class test {
    private class Node {
        private int value;
        private Node[] next = new Node[26];

        private Node(int v) {
            value = v;
        }
    }

    private void test_1() {
        Node a = new Node(2);
        //a[0] = 'c';
        //StdOut.println(a[0]);
    }

    private void test_() {
        Node a = new Node(1);
        StdOut.println(a.value);
        test_1();
        StdOut.println(a.value);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        /*
        test t = new test();
        t.test_();
        */

        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        //StdOut.println(board.toString());
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }

/*
        TrieSET dict = new TrieSET();
        for (int i = 0; i < dictionary.length; i++) {
            dict.add(dictionary[i]);
        }
        */
        /*
        for (String a : dict.keysWithPrefix("DOOOOOOOO")) {
            StdOut.println(a);
        }
        */
        /*
        Queue<String> a = (Queue<String>) dict.keysWithPrefix("DO");
        StdOut.println(a.isEmpty());
        */
        StdOut.println("Score = " + score);
    }
}
