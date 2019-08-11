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
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;
import java.util.Map;

public class WordNet {
    private Map<String, Stack<Integer>> SynsetToID;
    private Stack<int[]> DigraphStack;
    private Digraph digraph;
    private Map<String, Stack<String>> NounToSynset;
    private String[] IDToSynset;

    private boolean DFS(int Node, int[] states, Digraph digraph, boolean IsAcyclic) {
        states[Node] = 1;
        for (int AdjNode : digraph.adj(Node)) {
            if (states[AdjNode] == 2) {
                continue;
            }
            if (states[AdjNode] == 1) {
                return false; // when the new node has not been returned yet, it is not acyclic
            }
            if (states[AdjNode] == 0) {
                IsAcyclic = DFS(AdjNode, states, digraph, IsAcyclic);
            }

        }
        states[Node] = 2;
        return IsAcyclic;
    }

    private boolean IsAcyclic(Digraph digraph) {
        int[] states = new int[digraph.V()];
        for (int i = 0; i < states.length; i++) {
            states[i] = 0;
        }
        return DFS(0, states, digraph, true);
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        int V = 0;
        SynsetToID = new HashMap<String, Stack<Integer>>();
        DigraphStack = new Stack<int[]>();
        NounToSynset = new HashMap<String, Stack<String>>();
        In Synsets = new In(synsets);
        In Hyper = new In(hypernyms);
        while (Hyper.hasNextLine()) {
            String line = Hyper.readLine();
            String[] components_string = line.split(",");
            int[] components = new int[components_string.length];
            for (int i = 0; i < components.length; i++) {
                components[i] = Integer.parseInt(components_string[i]);
                if (components[i] > V) {
                    V = components[i];
                }
            }
            DigraphStack.push(components);
        }
        digraph = new Digraph(V + 1);
        for (int[] components : DigraphStack) {
            if (components.length > 1) {
                for (int i = 1; i < components.length; i++) {
                    digraph.addEdge(components[0], components[i]);
                }
            }
        }
        if (!IsAcyclic(digraph)) {
            throw new IllegalArgumentException();
        }
        IDToSynset = new String[V + 1];
        while (Synsets.hasNextLine()) {
            String line = Synsets.readLine();
            String[] components = line.split(",");
            int ID = Integer.parseInt(components[0]);
            String Synset = components[1];
            IDToSynset[ID] = Synset;
            if (!SynsetToID.containsKey(Synset)) {
                Stack<Integer> IDs = new Stack<Integer>();
                IDs.push(ID);
                SynsetToID.put(Synset, IDs);
            }
            else {
                SynsetToID.get(Synset).push(ID);
            }
            for (String noun : Synset.split(" ")) {
                if (NounToSynset.containsKey(noun)) {
                    NounToSynset.get(noun).push(Synset);
                }
                else {
                    Stack<String> AppearedSynset = new Stack<String>();
                    AppearedSynset.push(Synset);
                    NounToSynset.put(noun, AppearedSynset);
                }
            }
        }
    }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return NounToSynset.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return NounToSynset.containsKey(word);
    }


    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if ((!isNoun(nounA)) || (!isNoun(nounB))) {
            throw new IllegalArgumentException();
        }
        SAP sap = new SAP(digraph);
        Stack<Integer> Vs = new Stack<Integer>();
        Stack<Integer> Ws = new Stack<Integer>();
        //StdOut.println(NounToSynset.get(nounA));
        for (String Synset : NounToSynset.get(nounA)) {
            for (int ID : SynsetToID.get(Synset)) {
                Vs.push(ID);
            }
        }
        for (String Synset : NounToSynset.get(nounB)) {
            for (int ID : SynsetToID.get(Synset)) {
                Ws.push(ID);
            }
        }
        return sap.length(Vs, Ws);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if ((!isNoun(nounA)) || (!isNoun(nounB))) {
            throw new IllegalArgumentException();
        }
        SAP sap = new SAP(digraph);
        Stack<Integer> Vs = new Stack<Integer>();
        Stack<Integer> Ws = new Stack<Integer>();

        for (String Synset : NounToSynset.get(nounA)) {
            for (int ID : SynsetToID.get(Synset)) {
                Vs.push(ID);
            }
        }
        for (String Synset : NounToSynset.get(nounB)) {
            for (int ID : SynsetToID.get(Synset)) {
                Ws.push(ID);
            }
        }
        return IDToSynset[sap.ancestor(Vs, Ws)];
    }

    public static void main(String[] args) {
/*
        WordNet wordnet = new WordNet(args[0], args[1]);

        int length = wordnet.distance("binding", "week_from_Monday");
        String ancestor = wordnet.sap("binding", "week_from_Monday");
        StdOut.printf("length = %d, ancestor = %s\n", length, ancestor);
*/
/*
        for (String Synset : wordnet.NounToSynset.get("histology")) {
            StdOut.println(Synset);
        }
*/

        //StdOut.println(wordnet.SynsetToID.get("American_water_spaniel"));

        /*
        for (int component : wordnet.digraph.adj(wordnet.SynsetToID.get("binding"))) {
            StdOut.println(wordnet.IDToSynset[component]);
        }
        */

    }

}


