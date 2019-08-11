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

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class SAP {
    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        digraph = new Digraph(G);
    }

    private int[][] MarkVertices(int v, int w) {
        int[][] IsMarkedByVW = new int[digraph.V()][2];
        for (int i = 0; i < digraph.V(); i++) {
            for (int j = 0; j < 2; j++) {
                IsMarkedByVW[i][j] = -1;
            }
        }
        IsMarkedByVW[v][0] = 0;
        IsMarkedByVW[w][1] = 0;
        Queue<Integer> BFS_V = new Queue<Integer>();
        Queue<Integer> BFS_W = new Queue<Integer>();
        BFS_V.enqueue(v);
        BFS_W.enqueue(w);
        while (!BFS_V.isEmpty()) {
            int current = BFS_V.dequeue();
            for (int adj : digraph.adj(current)) {
                if (IsMarkedByVW[adj][0] == -1) {
                    BFS_V.enqueue(adj);
                    IsMarkedByVW[adj][0] = IsMarkedByVW[current][0] + 1;
                }
            }
        }
        while (!BFS_W.isEmpty()) {
            int current = BFS_W.dequeue();
            for (int adj : digraph.adj(current)) {
                if (IsMarkedByVW[adj][1] == -1) {
                    BFS_W.enqueue(adj);
                    IsMarkedByVW[adj][1] = IsMarkedByVW[current][1] + 1;
                }
            }
        }
        return IsMarkedByVW;
    }

    private int[] MarkVertices(Iterable<Integer> v, Iterable<Integer> w) {
        int Shortest = -1, ancestor = -1;
        int lengthV = 0, lengthW = 0;
        for (int _v : v) {
            lengthV++;
        }
        for (int _w : w) {
            lengthW++;
        }
        int[] Vs = new int[lengthV];
        int[] Ws = new int[lengthW];
        int indexV = 0;
        int indexW = 0;
        for (int _v : v) {
            Vs[indexV++] = _v;
        }
        for (int _w : w) {
            Ws[indexW++] = _w;
        }
        int[][] IsMarkedByVW = new int[digraph.V()][lengthV + lengthW];
        for (int i = 0; i < digraph.V(); i++) {
            for (int j = 0; j < lengthV + lengthW; j++) {
                IsMarkedByVW[i][j] = -1;
            }
        }
        Queue<int[]> BFS = new Queue<int[]>();
        for (int i = 0; i < lengthV; i++) {
            IsMarkedByVW[Vs[i]][i] = 0;
            BFS.enqueue(new int[] { Vs[i], i });
        }
        for (int i = 0; i < lengthW; i++) {
            IsMarkedByVW[Ws[i]][i + lengthV] = 0;
            BFS.enqueue(new int[] { Ws[i], i + lengthV });
        }


        while (!BFS.isEmpty()) {
            int[] CurrentData = BFS.dequeue();
            int CurrentNode = CurrentData[0];
            int CurrentSignal = CurrentData[1];
            for (int adj : digraph.adj(CurrentNode)) {
                if (IsMarkedByVW[adj][CurrentSignal] == -1) {
                    BFS.enqueue(new int[] { adj, CurrentSignal });
                    IsMarkedByVW[adj][CurrentSignal] = IsMarkedByVW[CurrentNode][CurrentSignal] + 1;
                }
            }
            for (int i = 0; i < lengthV; i++) {
                if (IsMarkedByVW[CurrentNode][i] == -1) {
                    continue;
                }
                for (int j = lengthV; j < lengthW + lengthV; j++) {

                    if (IsMarkedByVW[CurrentNode][i] != -1 && IsMarkedByVW[CurrentNode][j] != -1) {
                        if (Shortest == -1) {
                            Shortest = IsMarkedByVW[CurrentNode][i] + IsMarkedByVW[CurrentNode][j];
                            ancestor = CurrentNode;
                        }
                        else {
                            if (IsMarkedByVW[CurrentNode][i] + IsMarkedByVW[CurrentNode][j]
                                    < Shortest) {
                                Shortest = IsMarkedByVW[CurrentNode][i]
                                        + IsMarkedByVW[CurrentNode][j];
                                ancestor = CurrentNode;
                            }
                        }
                    }
                }
            }
        }
        /*
        for (int i = 0; i < IsMarkedByVW.length; i++) {
            for (int j = 0; j < IsMarkedByVW[0].length; j++) {
                StdOut.print(IsMarkedByVW[i][j]);
                StdOut.print(" ");
            }
            StdOut.print(i);
            StdOut.println();
        }
*/
        return new int[] { Shortest, ancestor };
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if ((v < 0) || (v >= digraph.V()) || (w < 0) || (w >= digraph.V())) {
            throw new IllegalArgumentException();
        }
        int[][] IsMarkedByVW = MarkVertices(v, w);
        int[] length = new int[digraph.V()];
        int Shortest = -1;
        for (int i = 0; i < IsMarkedByVW.length; i++) {
            if (IsMarkedByVW[i][0] != -1 && IsMarkedByVW[i][1] != -1) {
                length[i] = IsMarkedByVW[i][0] + IsMarkedByVW[i][1];
                if (Shortest == -1) {
                    Shortest = length[i];
                }
                else {
                    if (length[i] < Shortest) {
                        Shortest = length[i];
                    }
                }
            }
            else {
                length[i] = -1;
            }
        }
        return Shortest;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if ((v < 0) || (v >= digraph.V()) || (w < 0) || (w >= digraph.V())) {
            throw new IllegalArgumentException();
        }
        int[][] IsMarkedByVW = MarkVertices(v, w);
        int[] length = new int[digraph.V()];
        int Ancestor = -1;
        int Shortest = -1;
        for (int i = 0; i < IsMarkedByVW.length; i++) {
            if (IsMarkedByVW[i][0] != -1 && IsMarkedByVW[i][1] != -1) {
                length[i] = IsMarkedByVW[i][0] + IsMarkedByVW[i][1];
                if (Shortest == -1) {
                    Shortest = length[i];
                    Ancestor = i;
                }
                else {
                    if (length[i] < Shortest) {
                        Shortest = length[i];
                        Ancestor = i;
                    }
                }
            }
            else {
                length[i] = -1;
            }
        }
        return Ancestor;
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        for (Integer _v : v) {
            if ((_v < 0) || (_v >= digraph.V()) || _v == null) {
                throw new IllegalArgumentException();
            }
        }
        for (Integer _w : w) {
            if ((_w < 0) || (_w >= digraph.V()) || _w == null) {
                throw new IllegalArgumentException();
            }
        }
        return MarkVertices(v, w)[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        for (int _v : v) {
            if ((_v < 0) || (_v >= digraph.V())) {
                throw new IllegalArgumentException();
            }
        }
        for (int _w : w) {
            if ((_w < 0) || (_w >= digraph.V())) {
                throw new IllegalArgumentException();
            }
        }
        return MarkVertices(v, w)[1];
    }

    // do unit testing of this class
    public static void main(String[] args) {
        /*
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Stack<Integer> Vs = new Stack<Integer>();
        Vs.push(7);
        Vs.push(8);
        Stack<Integer> Ws = new Stack<Integer>();

        Ws.push(3);
        Ws.push(2);
        int length = sap.length(Vs, Ws);
        int ancestor = sap.ancestor(Vs, Ws);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
*/
    }
}

