
public class Outcast {

    private WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        int maximumDistance = 0;
        String maxNoun = "";
        int currentDistance;
        for (int i = 0; i < nouns.length; i++) {
            currentDistance = 0;
            for (int j = 0; j < nouns.length; j++) {
                 if (i != j) {
                     currentDistance += this.wordnet.distance(nouns[i], nouns[j]);
                 }
            }
            if (currentDistance > maximumDistance) {
                maximumDistance = currentDistance;
                maxNoun = nouns[i];
            }
        }
        return maxNoun;
    }

    public static void main(String[] args) {
        // testing
    }

}