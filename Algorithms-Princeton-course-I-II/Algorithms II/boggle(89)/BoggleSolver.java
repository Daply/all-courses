
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;
import java.util.Objects;

public class BoggleSolver {

    private final char Q = 'Q';
    private final char U = 'U';

    private final int [] SCORES = {0, 0, 0, 1, 1, 2, 3, 5, 11};
    private final int MAX_LENGTH = 8;

    private final Set<String> wordsDictionary;
    private final Map<Character, TrieNode> tries;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.wordsDictionary = new HashSet<>();
        this.tries = new HashMap<>();
        addWordsToDictionary(dictionary);
        addWordsToTries(dictionary);
    }

    private void addWordsToDictionary(String[] dictionary) {
        this.wordsDictionary.addAll(Arrays.asList(dictionary));
    }

    private void addWordsToTries(String[] dictionary) {
        for (String word: dictionary) {
            putTrie(word);
        }
    }

    private void putTrie(String word) {
        char firstLetter = word.charAt(0);
        TrieNode letterTrie;
        if (this.tries.containsKey(firstLetter)) {
            letterTrie = this.tries.get(firstLetter);
        }
        else {
            letterTrie = new TrieNode();
            letterTrie.currentCharacter = firstLetter;
            letterTrie.nextCharacters = new ArrayList<>();
            this.tries.put(firstLetter, letterTrie);
        }
        TrieNode currentTrie = letterTrie;
        for (int i = 1; i < word.length(); i++) {
            char currentCharacter = word.charAt(i);
            TrieNode nextTrie = null;
            if (currentTrie.nextCharacters != null) {
                for (TrieNode node : currentTrie.nextCharacters) {
                    if (node.currentCharacter == currentCharacter) {
                        nextTrie = node;
                        break;
                    }
                }
            }
            if (nextTrie == null) {
                nextTrie = new TrieNode();
                nextTrie.currentCharacter = currentCharacter;
                if (currentTrie.nextCharacters == null)
                    currentTrie.nextCharacters = new ArrayList<>();
                currentTrie.nextCharacters.add(nextTrie);
            }
            currentTrie = nextTrie;
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> result = new HashSet<>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                result.addAll(getAllValidWordsStartingFromLetter(board, i, j));
            }
        }
        return result;
    }

    private Set<String> getAllValidWordsStartingFromLetter(BoggleBoard board, int i, int j) {
        Set<String> result = new HashSet<>();
        Stack<WordNode> stack = new Stack<>();
        Set<WordNode> previous = new HashSet<>();
        WordNode node = new WordNode();
        node.resultWord = board.getLetter(i, j) + "";
        node.i = i;
        node.j = j;
        node.previous = previous;
        stack.push(node);
        boolean addNext;
        while (!stack.isEmpty()) {
            WordNode currentNode = stack.pop();
            addNext = false;

            if (currentNode.resultWord.charAt(currentNode.resultWord.length() - 1) == Q) {
                currentNode.resultWord = currentNode.resultWord + U;
            }

            if (this.wordsDictionary.contains(currentNode.resultWord)) {
                if (currentNode.resultWord.length() > 2)
                    result.add(currentNode.resultWord);
                addNext = true;
            }
            if (!addNext) {
                if (currentNode.resultWord.length() > 2)
                    addNext = searchByPrefix(currentNode.resultWord);
                else
                    addNext = true;
            }
            if (addNext) {
                if (currentNode.i > 0 && currentNode.j > 0) {
                    WordNode leftUpNode = createNewNode(board.getLetter(currentNode.i - 1, currentNode.j - 1),
                            currentNode, currentNode.i - 1, currentNode.j - 1);
                    if (!currentNode.previous.contains(leftUpNode))
                        stack.push(leftUpNode);
                }
                if (currentNode.j > 0) {
                    WordNode leftNode = createNewNode(board.getLetter(currentNode.i, currentNode.j - 1),
                            currentNode, currentNode.i, currentNode.j - 1);
                    if (!currentNode.previous.contains(leftNode))
                        stack.push(leftNode);
                }
                if (currentNode.i < board.rows() - 1 && currentNode.j > 0) {
                    WordNode leftDownNode = createNewNode(board.getLetter(currentNode.i + 1, currentNode.j - 1),
                            currentNode, currentNode.i + 1, currentNode.j - 1);
                    if (!currentNode.previous.contains(leftDownNode))
                        stack.push(leftDownNode);
                }
                if (currentNode.i > 0) {
                    WordNode upNode = createNewNode(board.getLetter(currentNode.i - 1, currentNode.j),
                            currentNode, currentNode.i - 1, currentNode.j);
                    if (!currentNode.previous.contains(upNode))
                        stack.push(upNode);
                }
                if (currentNode.i < board.rows() - 1) {
                    WordNode downNode = createNewNode(board.getLetter(currentNode.i + 1, currentNode.j),
                            currentNode, currentNode.i + 1, currentNode.j);
                    if (!currentNode.previous.contains(downNode))
                        stack.push(downNode);
                }
                if (currentNode.i > 0 && currentNode.j < board.cols() - 1) {
                    WordNode rightUpNode = createNewNode(board.getLetter(currentNode.i - 1, currentNode.j + 1),
                            currentNode, currentNode.i - 1, currentNode.j + 1);
                    if (!currentNode.previous.contains(rightUpNode))
                        stack.push(rightUpNode);
                }
                if (currentNode.j < board.cols() - 1) {
                    WordNode rightNode = createNewNode(board.getLetter(currentNode.i, currentNode.j + 1),
                            currentNode, currentNode.i, currentNode.j + 1);
                    if (!currentNode.previous.contains(rightNode))
                        stack.push(rightNode);
                }
                if (currentNode.i < board.rows() - 1 && currentNode.j < board.cols() - 1) {
                    WordNode rightDownNode = createNewNode(board.getLetter(currentNode.i + 1, currentNode.j + 1),
                            currentNode, currentNode.i + 1, currentNode.j + 1);
                    if (!currentNode.previous.contains(rightDownNode))
                        stack.push(rightDownNode);
                }
            }
        }
        return result;
    }

    private WordNode createNewNode(char letter, WordNode previousNode, int i, int j) {
        WordNode newNode = new WordNode();
        newNode.resultWord = previousNode.resultWord + letter;
        newNode.i = i;
        newNode.j = j;
        newNode.previous = new HashSet<>();
        newNode.previous.addAll(previousNode.previous);
        newNode.previous.add(previousNode);
        return newNode;
    }

    private boolean searchByPrefix(String wordPrefix) {
        char firstLetter = wordPrefix.charAt(0);
        TrieNode node = this.tries.get(firstLetter);
        int charIndex = 1;
        boolean hasNext = false;
        if (node != null) {
            hasNext = true;
        }
        while (hasNext) {
            hasNext = false;
            if (node.nextCharacters != null && charIndex < wordPrefix.length()) {
                for (TrieNode nextNode : node.nextCharacters) {
                    if (nextNode.currentCharacter == wordPrefix.charAt(charIndex)) {
                        charIndex++;
                        node = nextNode;
                        hasNext = true;
                        break;
                    }
                }
            }
        }
        return charIndex == wordPrefix.length();
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null || word.length() == 0 || !this.wordsDictionary.contains(word)) {
            return 0;
        }
        int length = word.length();
        if (length <= MAX_LENGTH)
            return SCORES[length];
        return SCORES[MAX_LENGTH];
    }

    private class TrieNode {
        char currentCharacter;
        List<TrieNode> nextCharacters;
    }

    private class WordNode {
        String resultWord;
        int i;
        int j;
        Set<WordNode> previous = new HashSet<>();

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            WordNode indexPair = (WordNode) object;
            return i == indexPair.i &&
                    j == indexPair.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }

    }

}

