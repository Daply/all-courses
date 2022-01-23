import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static final int ALPHABET_SIZE = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char [] alphabet = new char[ALPHABET_SIZE];
        int [] inverseAlphabet = new int[ALPHABET_SIZE];
        for (char c = 0; c < ALPHABET_SIZE; c++) {
            alphabet[c] = c;
            inverseAlphabet[c] = c;
        }

        String inputString = BinaryStdIn.readString();
        BinaryStdIn.close();

        for (int i = 0; i < inputString.length(); i++) {
            char character = inputString.charAt(i);
            int index = inverseAlphabet[character];

            BinaryStdOut.write((char) index);

            if (alphabet[0] != character) {
                while (index > 0) {
                    alphabet[index] = alphabet[index - 1];
                    inverseAlphabet[alphabet[index]] = index;
                    index--;
                }
                alphabet[0] = character;
                inverseAlphabet[character] = 0;
            }
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char [] alphabet = new char[ALPHABET_SIZE];
        for (char c = 0; c < ALPHABET_SIZE; c++) {
            alphabet[c] = c;
        }

        String inputString = BinaryStdIn.readString();
        BinaryStdIn.close();

        for (int i = 0; i < inputString.length(); i++) {
            char character = inputString.charAt(i);
            int index = character;

            BinaryStdOut.write(alphabet[character]);

            char firstCharacter = alphabet[character];
            while (index > 0) {
                alphabet[index] = alphabet[index - 1];
                index--;
            }
            alphabet[0] = firstCharacter;
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        else if (args[0].equals("+")) {
            decode();
        }
    }

}