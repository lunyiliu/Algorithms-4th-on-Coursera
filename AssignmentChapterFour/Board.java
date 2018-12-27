import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;
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

public class Board {
    private int[][] Blocks;//data structure to put in numbers in a board
    private int n;
    private int[][] goal;
    private int Blank_i, Blank_j;
    private Board twin_;

    // construct a board from an n-by-n array of blocks
    /*
    public Board(int[][] blocks, int move) {
        n = blocks.length;
        Blocks = new int[n][n];
        goal = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Blocks[i][j] = blocks[i][j];
                if (i == n - 1 && j == n - 1) {
                    goal[i][j] = 0;
                }
                else {
                    goal[i][j] = j + 1 + i * n;
                }
                if (Blocks[i][j] == 0) {
                    Blank_i = i;
                    Blank_j = j;
                }

            }
        }
        moves = move;
    }
*/
    public Board(int[][] blocks) {
        n = blocks.length;
        Blocks = new int[n][n];
        goal = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Blocks[i][j] = blocks[i][j];
                if (i == n - 1 && j == n - 1) {
                    goal[i][j] = 0;
                }
                else {
                    goal[i][j] = j + 1 + i * n;
                }
                if (Blocks[i][j] == 0) {
                    Blank_i = i;
                    Blank_j = j;
                }

            }
        }
        twin_ = null;
    }

    // (where blocks[i][j] = block in row i, column j)
    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) {
                    break;
                }
                else {
                    if (Blocks[i][j] != goal[i][j])
                        hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int i_goal = 0;
        int j_goal = 0;
        int[][] manhattan = new int[n][n];
        int Manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (Blocks[i][j] == 0) {
                    manhattan[i][j] = 0;
                }
                else {
                    if (Blocks[i][j] % n != 0) {
                        j_goal = Blocks[i][j] % n - 1;
                    }
                    else {
                        j_goal = n - 1;
                    }
                    i_goal = (Blocks[i][j] - 1) / n;
                    manhattan[i][j] = Math.abs(i - i_goal) + Math.abs(j - j_goal);
                }

                Manhattan += manhattan[i][j];
            }
        }
        /*
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                StdOut.print(manhattan[i][j]);
                StdOut.print(" ");
            }
        }
        */

        return Manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (Blocks[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        if (twin_ == null) {
            int Blocks_copy[][] = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Blocks_copy[i][j] = Blocks[i][j];
                }
            }
            int i_random1 = 0, i_random2 = 0, j_random1 = 0, j_random2 = 0;
            while ((!((Math.abs(i_random1 - i_random2) == 1 && (j_random1 == j_random2))
                    || (Math.abs(j_random1 - j_random2) == 1) && (i_random1 == i_random2)))
                    || ((Blocks_copy[i_random1][j_random1] == 0) || (
                    Blocks_copy[i_random2][j_random2]
                            == 0))) {
                i_random1 = StdRandom.uniform(n);
                j_random1 = StdRandom.uniform(n);
                i_random2 = StdRandom.uniform(n);
                j_random2 = StdRandom.uniform(n);

            }
        /*
       int i_random2, j_random2;
        int i_random1 = StdRandom.uniform(n);
        int j_random1 = StdRandom.uniform(n);
        if (i_random1 == 0) {
            if (j_random1 == 0) {

            }
        }
        */
            int swap = Blocks_copy[i_random1][j_random1];
            Blocks_copy[i_random1][j_random1] = Blocks_copy[i_random2][j_random2];
            Blocks_copy[i_random2][j_random2] = swap;
            Board twin = new Board(Blocks_copy);
            twin_ = twin;
            return twin;
        }
        else {
            return twin_;
        }
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.n != ((Board) y).n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.Blocks[i][j] != this.Blocks[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int Blocks_copy[][] = new int[n][n];
        Stack<Board> neighbors = new Stack<Board>();
        if (Blank_i != 0) {
            int swap;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Blocks_copy[i][j] = Blocks[i][j];
                }
            }
            swap = Blocks_copy[Blank_i - 1][Blank_j];
            Blocks_copy[Blank_i - 1][Blank_j] = Blocks_copy[Blank_i][Blank_j];
            Blocks_copy[Blank_i][Blank_j] = swap;
            Board new_board = new Board(Blocks_copy);
            neighbors.push(new_board);

        }
        if (Blank_i != n - 1) {
            int swap;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Blocks_copy[i][j] = Blocks[i][j];
                }
            }
            swap = Blocks_copy[Blank_i + 1][Blank_j];
            Blocks_copy[Blank_i + 1][Blank_j] = Blocks_copy[Blank_i][Blank_j];
            Blocks_copy[Blank_i][Blank_j] = swap;
            Board new_board = new Board(Blocks_copy);
            neighbors.push(new_board);
        }
        if (Blank_j != 0) {
            int swap;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Blocks_copy[i][j] = Blocks[i][j];
                }
            }
            swap = Blocks_copy[Blank_i][Blank_j - 1];
            Blocks_copy[Blank_i][Blank_j - 1] = Blocks_copy[Blank_i][Blank_j];
            Blocks_copy[Blank_i][Blank_j] = swap;
            Board new_board = new Board(Blocks_copy);
            neighbors.push(new_board);
        }
        if (Blank_j != n - 1) {
            int swap;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Blocks_copy[i][j] = Blocks[i][j];
                }
            }
            swap = Blocks_copy[Blank_i][Blank_j + 1];
            Blocks_copy[Blank_i][Blank_j + 1] = Blocks_copy[Blank_i][Blank_j];
            Blocks_copy[Blank_i][Blank_j] = swap;
            Board new_board = new Board(Blocks_copy);
            neighbors.push(new_board);
        }
        return neighbors;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", Blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        /*
        int n = 3;
        int[][] test = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board board = new Board(test);
        Iterable<Board> neighbors = board.neighbors();
        StdOut.print(board.equals(new Board(board.goal)));
        StdOut.print(board.twin().toString());
        StdOut.print(board.toString());
        StdOut.println(board.manhattan());
        for (Board neighbor : neighbors) {
            StdOut.print(neighbor.toString());
        }
*/
    } // unit tests (not graded)
}
