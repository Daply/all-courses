
import java.util.List;
import java.util.ArrayList;

public class CircularSuffixArray {

    private final List<CircularSuffix> suffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        validateString(s);

        this.suffixes = new ArrayList<>();
        createSuffixesArray(s);
    }

    private void createSuffixesArray(String inputString) {
        if (!inputString.isEmpty()) {
            for (int i = 0; i < inputString.length(); i++) {
                this.suffixes.add(new CircularSuffix(inputString, i));
            }
            this.suffixes.sort(CircularSuffix::compareTo);
        }
    }

    // length of s
    public int length() {
        return this.suffixes.size();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        validateIndex(i);

        return this.suffixes.get(i).suffixIndex;
    }

    private void validateString(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= length()) {
            throw new IllegalArgumentException();
        }
    }

    private class CircularSuffix implements Comparable<CircularSuffix> {

        private final String inputString;
        private final int suffixIndex;

        public CircularSuffix(String inputString, int suffixIndex) {
            this.inputString = inputString;
            this.suffixIndex = suffixIndex;
        }

        private int compareInputStrings(String inputString1, int suffixIndex1,
                                        String inputString2, int suffixIndex2) {
            int inputStringLength = inputString1.length();
            char [] charactersInputString1 = inputString1.toCharArray();
            char [] charactersInputString2 = inputString2.toCharArray();
            for (int i = 0; i < inputStringLength; i++) {
                int index1 = i + suffixIndex1;
                int index2 = i + suffixIndex2;
                if (index1 >= inputStringLength)
                    index1 = index1 - inputStringLength;
                if (index2 >= inputStringLength)
                    index2 = index2 - inputStringLength;
                char char1 = charactersInputString1[index1];
                char char2 = charactersInputString2[index2];
                if (char1 != char2) {
                    return char1 - char2;
                }
            }
            return 0;
        }

        @Override
        public int compareTo(CircularSuffix circularSuffix) {
            return compareInputStrings(this.inputString, this.suffixIndex,
                    circularSuffix.inputString, circularSuffix.suffixIndex);
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        // test
    }

}