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

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private class Node implements Comparable<Node> {
        public Node next;
        public Board board;
        public int Manhattan;
        public int NodeMoves;

        public Node(Board board_, Node next_, int NodeMoves_) {
            board = board_;
            next = next_;
            NodeMoves = NodeMoves_;
            Manhattan = board.manhattan() + NodeMoves;
        }

        @Override
        public int compareTo(Node that) {
            if (this.Manhattan > that.Manhattan) {
                return 1;
            }
            if (this.Manhattan < that.Manhattan) {
                return -1;
            }
            return 0;
        }
    }

    private Stack<Board> solution;
    private int moves;
    private Node InitialNode;
    private boolean solverable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial != null) {
            InitialNode = new Node(initial, null, 0);
            solution = new Stack<Board>();
        }
        else {
            throw new IllegalArgumentException();
        }
        if (InitialNode.board.isGoal()) {
            moves = 0;
            solution.push(InitialNode.board);
            solverable = true;
            return;
        }
        Node current = InitialNode;
        Node current_twin = new Node(InitialNode.board.twin(), null, 0);
        MinPQ<Node> open = new MinPQ<Node>();
        MinPQ<Node> open_twin = new MinPQ<Node>();
        while (true) {
            Iterable<Board> neighbors = current.board.neighbors();
            Iterable<Board> neighbors_twin = current_twin.board.neighbors();
            for (Board neighbor : neighbors) {
                //StdOut.print(neighbor.toString());

                boolean flag_equal = false;
                if ((!open.isEmpty()) && (current.next != null)) {
                    if (current.next.board.equals(neighbor)) {
                        flag_equal = true;
                    }
                    if (!flag_equal) {
                        open.insert(new Node(neighbor, current, current.NodeMoves + 1));
                    }
                }
                else {
                    open.insert(new Node(neighbor, current, current.NodeMoves + 1));
                }
            }
            for (Board neighbor : neighbors_twin) {
                //StdOut.print(neighbor.toString());
                boolean flag_equal_twin = false;
                if ((!open_twin.isEmpty()) && (current_twin.next != null)) {
                    if (current_twin.next.board.equals(neighbor)) {
                        flag_equal_twin = true;
                    }

                    if (!flag_equal_twin) {
                        open_twin.insert(new Node(neighbor, current_twin,
                                                  current_twin.NodeMoves + 1));
                    }
                }
                else {
                    open_twin.insert(new Node(neighbor, current_twin, current_twin.NodeMoves + 1));
                }
            }
            Node OperationNode = open.min();
            open.delMin();
            Node OperationNode_twin = open_twin.min();
            open_twin.delMin();
            //StdOut.print(OperationNode.board.toString());
            if (OperationNode.board.isGoal()) {
                while (OperationNode.next != null) {
                    solution.push(OperationNode.board);
                    OperationNode = OperationNode.next;
                }
                solution.push(OperationNode.board);
                moves = solution.size() - 1;
                solverable = true;
                break;
            }
            else {
                current = OperationNode;
            }
            if (OperationNode_twin.board.isGoal()) {
                solution = null;
                moves = -1;
                solverable = false;
                break;
            }
            else {
                current_twin = OperationNode_twin;
            }
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solverable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
/*
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);


        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);

        }
*/
    }
}

