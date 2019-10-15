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

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.TrieSET;

public class BoggleSolver_ {
    private TrieSET dict;
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
        dict = new TrieSET();
        for (int i = 0; i < dictionary.length; i++) {
            dict.add(dictionary[i]);
        }
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

    private void DFS(int row, int col, String prefix_before, String prefix_position,
                     int real_len, Queue<String> PK_before) {
        //StdOut.println(real_len);
        //StdOut.println(prefix_before);
        char current_letter = letters[row][col];
        String position = String.format(",%d,", row * n + col);
        //StdOut.println("   " + prefix_position);
        //StdOut.println("        " + position);
        if (prefix_position.contains(position)) {
            /*
            if (!PossibleKeys.isEmpty()) {
                for (String key : PossibleKeys) {
                    if (prefix_before.contains(key)) {
                        if (scoreOf(key) != 0) {
                            results.add(key);
                        }
                    }
                }
            }
            */
            //StdOut.println("                       lll");
            return;
        }
        else {
            prefix_position += String.format("%d,", row * n + col);
        }
        String prefix = prefix_before;
        if (current_letter != 'Q') {
            prefix += current_letter;
        }
        else {
            prefix += "QU";
        }
        real_len++;
        if (real_len > m * n) {
            //StdOut.println("                       " + real_len);
            return;
        }
        //StdOut.println("                  " + prefix);
        /*
        if (prefix.length() == m * n) {
            search_strings.push(prefix);
            return;
        }
        */
        TrieSET DICT = new TrieSET();
        for (String key : PK_before) {
            DICT.add(key);
        }
        //Queue<String> PK = (Queue<String>) DICT.keysWithPrefix(prefix);
        Queue<String> PK = PK_before;
        /*
        Queue<String> PK = new Queue<String>();
        for (String key : PK_before) {
            if (current_letter != 'Q') {
                if (key.length() >= prefix.length()
                        && key.charAt(prefix.length() - 1) == current_letter) {
                    if (key.length() == prefix.length() && prefix.length() >= 3) {
                        results.add(key);
                    }
                    else {
                        PK.enqueue(key);
                    }
                }
            }
            else {
                if (key.length() >= prefix.length()
                        && key.charAt(prefix.length() - 1) == 'U'
                        && key.charAt(prefix.length() - 2) == 'Q') {
                    if (key.length() == prefix.length() && prefix.length() >= 3) {
                        results.add(key);
                    }
                    else {
                        PK.enqueue(key);
                    }
                }
            }
        }
        */
        //StdOut.println("                        " + PK.size());
        /*
        for (String a : PK) {
            StdOut.println(a);
        }
        */
        if (PK.isEmpty()) {
            /*
            if (!PossibleKeys.isEmpty()) {
                for (String key : PossibleKeys) {
                    if (prefix_before.contains(key)) {
                        if (scoreOf(key) != 0) {
                            results.add(key);
                        }
                    }
                }
            }
            */
            return;
        }
        /*
        for (String s : PK) {
            PossibleKeys.add(s);
        }
        */
        /*
        TrieSET new_DICT = new TrieSET();
        for (String key : PK) {
            new_DICT.add(key);
        }
        */
        if (prefix.length() >= 3) {
            if (DICT.contains(prefix)) {
                results.add(prefix);
                if (PK.size() == 1) {
                    return;
                }
            }
        }
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                //StdOut.println(i + "," + j);
                if (IsIllegal(i, j) && (!(i == row && j == col))) {
                    //StdOut.println(111);
                    if (letters[i][j] != 'Q') {
                        Queue<String> PK_new = (Queue<String>) DICT
                                .keysWithPrefix(prefix + letters[i][j]);
                        if (!PK_new.isEmpty()) {
                            DFS(i, j, prefix, prefix_position, real_len, PK_new);
                        }
                    }
                    else {
                        Queue<String> PK_new = (Queue<String>) DICT.keysWithPrefix(prefix + "QU");
                        if (!PK_new.isEmpty()) {
                            DFS(i, j, prefix, prefix_position, real_len, PK_new);
                        }
                    }
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
                        Queue<String> Q = (Queue<String>) dict.keysWithPrefix("" + letters[i][j]);

                        if (IsIllegal(k, l) && (!(k == i && l == j)) && (!Q.isEmpty())) {
                            if (letters[i][j] != 'Q') {
                                DFS(k, l, "" + letters[i][j], String.format(",%d,", i * n + j),
                                    1, Q);
                            }
                            else {
                                DFS(k, l, "QU", String.format(",%d,", i * n + j),
                                    1, Q);
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

}
