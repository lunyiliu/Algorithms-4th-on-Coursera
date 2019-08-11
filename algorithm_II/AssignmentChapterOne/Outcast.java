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

public class Outcast {
    private WordNet WN;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new IllegalArgumentException();
        }
        WN = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = " ";
        int farthest = -1;
        for (int i = 0; i < nouns.length; i++) {
            int relateness = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) {
                    continue;
                }
                relateness += WN.distance(nouns[i], nouns[j]);
            }
            if (farthest == -1) {
                outcast = nouns[i];
                farthest = relateness;
            }
            else {
                if (relateness > farthest) {
                    outcast = nouns[i];
                    farthest = relateness;
                }
            }
        }
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {
        /*
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
        */
    }
}
