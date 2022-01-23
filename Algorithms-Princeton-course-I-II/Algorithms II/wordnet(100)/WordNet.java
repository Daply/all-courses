import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class WordNet {

    private final Map<Integer, Synset> synsets;
    private final Map<String, Set<Integer>> synsetsIds;
    private final Digraph digraph;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        validate(synsets, hypernyms);

        this.synsets = new HashMap<>();
        this.synsetsIds = new HashMap<>();
        readFromFile(synsets, true, false);
        this.digraph = new Digraph(this.synsets.size());
        readFromFile(hypernyms, false, true);
        this.sap = new SAP(this.digraph);
        checkIfHasCycle();
        checkIfRooted();
    }

    private void readFromFile(String fileName, boolean readSynsets, boolean readHypernyms) {
        In in = new In(fileName);
        String currentFileLine;
        String [] currentRecord;

        while (in.hasNextLine()) {
            currentFileLine = in.readLine();
            currentRecord = currentFileLine.split(",");
            if (readSynsets) {
                readSynsetFromFile(currentRecord);
            }
            else if (readHypernyms) {
                readHypernymFromFile(currentRecord);
            }
        }
    }

    private void readSynsetFromFile(String [] currentRecord) {
        Synset synset = new Synset();
        String [] set = currentRecord[1].split("\\s");
        synset.addNouns(set);
        this.synsets.put(Integer.parseInt(currentRecord[0]), synset);
        Set<Integer> ids = null;
        for (String setSynset: set) {
            ids = this.synsetsIds.get(setSynset);
            if (ids != null) {
                ids = this.synsetsIds.get(setSynset);
                ids.add(Integer.parseInt(currentRecord[0]));
                this.synsetsIds.replace(setSynset, ids);
            }
            else {
                ids = new HashSet<>();
                ids.add(Integer.parseInt(currentRecord[0]));
                this.synsetsIds.put(setSynset, ids);
            }
        }
    }

    private void readHypernymFromFile(String [] currentRecord) {
        int hypernym = Integer.parseInt(currentRecord[0]);
        for (int i = 1; i < currentRecord.length; i++) {
            int hypernymConnected = Integer.parseInt(currentRecord[i]);
            this.digraph.addEdge(hypernym, hypernymConnected);
        }
    }

    private void checkIfHasCycle() {
        DirectedCycle cycle = new DirectedCycle(this.digraph);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
    }

    private void checkIfRooted() {
        int countRoots = 0;
        for (int i = 0; i < this.digraph.V(); i++) {
            if (!this.digraph.adj(i).iterator().hasNext())
                countRoots++;
        }

        if (countRoots != 1) {
            throw new IllegalArgumentException();
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return getNounsList();
    }

    private List<String> getNounsList() {
        return this.synsets
                .values()
                .parallelStream()
                .map(Synset::getNouns)
                .flatMap(Set::parallelStream)
                .distinct()
                .collect(Collectors.toList());
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        validate(word);

        return this.synsetsIds.containsKey(word);
    }

    private List<Integer> getSynsetIdsByNoun(String word) {
        validate(word);

        return new ArrayList<>(this.synsetsIds.get(word));
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validate(nounA, nounB);
        validateNouns(nounA, nounB);

        List<Integer> nounAIds = getSynsetIdsByNoun(nounA);
        List<Integer> nounBIds = getSynsetIdsByNoun(nounB);

        return this.sap.length(nounAIds, nounBIds);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        validate(nounA, nounB);
        validateNouns(nounA, nounB);

        List<Integer> nounAIds = getSynsetIdsByNoun(nounA);
        List<Integer> nounBIds = getSynsetIdsByNoun(nounB);

        int synset = this.sap.ancestor(nounAIds, nounBIds);
        return this.synsets.get(synset).getNounsAsString();
    }

    private static class Synset {
        private final Set<String> nounsSet;

        public Synset() {
            this.nounsSet = new HashSet<>();
        }

        public void addNouns(String [] nouns) {
            this.nounsSet.addAll(Arrays.asList(nouns));
        }

        public Set<String> getNouns() {
            return this.nounsSet;
        }

        public String getNounsAsString() {
            StringBuilder sb = new StringBuilder();
            for (String noun: this.nounsSet) {
                sb.append(noun).append(" ");
            }
            return sb.toString().trim();
        }

        @Override
        public String toString() {
            return "Synset{" +
                    "nouns=" + this.nounsSet +
                    '}';
        }

    }

    private void validate(String s) {
        if (s == null)
            throw new IllegalArgumentException();
    }

    private void validate(String s1, String s2) {
        validate(s1);
        validate(s2);
    }

    private void validateNoun(String s) {
        if (!isNoun(s))
            throw new IllegalArgumentException();
    }

    private void validateNouns(String s1, String s2) {
        validateNoun(s1);
        validateNoun(s2);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        // testing
    }

}
