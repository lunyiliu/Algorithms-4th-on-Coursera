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

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class BoggleSolver {

    private class Trie26way {
        private Node root;
        private Node CheckPoint;
        private int CP_index;
        public Stack<String> matched_words;

        private class Node {
            private Object value;
            private Node[] next = new Node[26];
        }

        public Trie26way(String[] dictionary) {
            root = new Node();
            for (int i = 0; i < dictionary.length; i++) {
                put(dictionary[i]);
            }
        }

        public void put(String key) {
            put(root, key, 0);
        }

        private Node put(Node x, String key, int d) {
            if (x == null) {
                x = new Node();
            }
            if (d == key.length()) {
                x.value = key;
                return x;
            }
            char letter = key.charAt(d++);
            //StdOut.println((int) 'A');
            x.next[letter - 65] = put(x.next[letter - 65], key, d);
            return x;
        }

        private boolean IsPrefixMatched(String prefix) {
            if (CheckPoint == null) {
                CheckPoint = root;
                CP_index = 0;
            }
            matched_words = new Stack<String>();
            prefix = prefix.substring(CP_index);
            //StdOut.println(CheckPoint.next[prefix.charAt(0) - 65]);

            int i = prefix.charAt(0) - 65;
            StdOut.println(i);
            boolean a = search(CheckPoint.next[i], prefix, 1);
            return a;
        }

        private boolean search(Node x, String prefix, int d) {

            if (x == null) {
                return false;
            }

            if (x.value != null) {
                String s = (String) x.value;
                if (s.length() >= 3) {
                    matched_words.push(s);
                }

            }

            if (d == prefix.length()) {
                CheckPoint = x;
                CP_index = d;
                //StdOut.println(prefix);
                return true;
            }

            char letter = prefix.charAt(d);
            return search(x.next[letter - 65], prefix, d + 1);
        }
    }

    private Trie26way dict;
    //private Stack<String> search_strings;
    private char[][] letters;
    private int m;
    private int n;
    private SET<String> results;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException();
        }
        dict = new Trie26way(dictionary);
        //search_strings = new Stack<String>();
    }

    private boolean IsIllegal(int row, int col) {
        if (row < 0 || row >= m) {
            return false;
        }
        if (col < 0 || col >= n) {
            return false;
        }
        return true;
    }

    private void DFS(int row, int col, String prefix, String prefix_position,
                     int real_len) {
        Trie26way.Node check_point = dict.CheckPoint;
        int cpIndex = dict.CP_index;
        char current_letter = letters[row][col];
        String position = String.format(",%d,", row * n + col);
        if (prefix_position.contains(position)) {
            return;
        }
        else {
            prefix_position += String.format("%d,", row * n + col);
        }
        if (current_letter != 'Q') {
            prefix += current_letter;
        }
        else {
            prefix += "QU";
        }
        real_len++;
        if (real_len > m * n) {
            return;
        }
        if (!dict.IsPrefixMatched(prefix)) {
            return;
        }
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (IsIllegal(i, j) && (!(i == row && j == col))) {
                    DFS(i, j, prefix, prefix_position, real_len);
                    dict.CheckPoint = check_point;
                    dict.CP_index = cpIndex;
                }
            }
        }
    }


    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }
        results = new SET<String>();
        m = board.rows();
        n = board.cols();
        letters = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                letters[i][j] = board.getLetter(i, j);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        //StdOut.println(i + "," + j + "," + k + "," + l);
                        boolean IsPrefixMatched = dict.IsPrefixMatched("" + letters[i][j]);
                        if (IsIllegal(k, l) && (!(k == i && l == j)) && (IsPrefixMatched)) {
                            if (letters[i][j] != 'Q') {
                                DFS(k, l, "" + letters[i][j], String.format(",%d,", i * n + j),
                                    1);
                            }
                            else {
                                DFS(k, l, "QU", String.format(",%d,", i * n + j),
                                    1);
                            }
                        }
                    }
                }

            }
        }

/*
        int i = 2, j = 1, k = 2, l = 0;
        if (letters[i][j] != 'Q') {
            DFS(k, l, "" + letters[i][j], String.format(",%d,", i * n + j),
                new SET<String>(), 1);
        }
        else {
            DFS(k, l, "QU", String.format(",%d,", i * n + j),
                new SET<String>(), 1);
        }
*/
        return results;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        if (word.length() < 3) {
            return 0;
        }
        if (word.length() == 3 || word.length() == 4) {
            return 1;
        }
        if (word.length() == 5) {
            return 2;
        }
        if (word.length() == 6) {
            return 3;
        }
        if (word.length() == 7) {
            return 5;
        }
        return 11;

    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver BS = new BoggleSolver(dictionary);
        BoggleSolver.Trie26way Trie = BS.new Trie26way(dictionary);
        StdOut.println(Trie.IsPrefixMatched("A"));
        for (String s : Trie.matched_words) {
            StdOut.println(s);
        }
        StdOut.println(Trie.IsPrefixMatched("REAL"));
        for (String s : Trie.matched_words) {
            StdOut.println(s);
        }
    }
}
