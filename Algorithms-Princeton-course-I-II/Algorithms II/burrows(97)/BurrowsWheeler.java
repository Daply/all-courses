import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    private static final int ALPHABET_SIZE = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String inputString = BinaryStdIn.readString();
        BinaryStdIn.close();

        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(inputString);

        int first = 0;
        char[] output = new char[inputString.length()];
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            int index = circularSuffixArray.index(i) - 1;
            if (index >= 0)
                output[i] = inputString.charAt(index);
            else {
                first = i;
                output[i] = inputString.charAt(inputString.length() - 1);
            }
        }

        BinaryStdOut.write(first);
        BinaryStdOut.write(new String(output));
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String inputString = BinaryStdIn.readString();
        BinaryStdIn.close();

        int[] next = new int[inputString.length()];

        int[] count = new int[ALPHABET_SIZE + 1];
        for (int i = 0; i < inputString.length(); i++)
            count[inputString.charAt(i) + 1]++;

        for (int r = 0; r < ALPHABET_SIZE; r++)
            count[r + 1] += count[r];

        for (int i = 0; i < inputString.length(); i++)
            next[count[inputString.charAt(i)]++] = i;

        for (int i = next[first], c = 0; c < inputString.length(); i = next[i], c++) {
            BinaryStdOut.write(inputString.charAt(i));
        }

        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }
        else if (args[0].equals("+")) {
            inverseTransform();
        }
    }

}
