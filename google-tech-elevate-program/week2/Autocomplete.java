package week2;

import java.util.*;

public class Autocomplete {

    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);

        final int n = sc.nextInt();
        sc.nextLine();
        PrefixTree tree = new PrefixTree();
        for (int i = 0; i < n; ++i) {
            tree.addWord(sc.nextLine().trim());
        }

        final int q = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < q; ++i) {
            List<String> result = new ArrayList<>(tree.getWordsByPrefix(sc.nextLine().trim()));
            result.sort(String::compareTo);
            if (result.isEmpty()) {
                System.out.println("<no matches>");
            }
            else {
                StringBuilder sb = new StringBuilder();
                for (int index = 0; index < 10 && index < result.size(); index++) {
                    sb.append(result.get(index)).append(" ");
                }
                System.out.println(sb.toString().trim());
            }
        }

    }

    static class PrefixTree {
        private Set<Node> nodes;

        public PrefixTree() {
            this.nodes = new TreeSet<>();
        }

        public Node getNode(char character, Set<Node> nodes) {
            for (Node node: nodes) {
                if (node.character == character) {
                    return node;
                }
            }
            return null;
        }

        public void addWord(String word) {
            Set<Node> rootNodes = this.nodes;
            Node prevNode = null;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                Node nextNode = getNode(c, rootNodes);
                if (nextNode == null) {
                    nextNode = new Node(c);
                    nextNode.previousNode = prevNode;
                }
                if (i == word.length() - 1) {
                    nextNode.finalWord = word;
                }
                rootNodes.add(nextNode);
                rootNodes = nextNode.nextNodes;
                prevNode = nextNode;
            }
        }

        public Set<String> getWordsByPrefix(String prefix) {
            return new TreeSet<>(findWordsByPrefix(prefix, this.nodes));
        }

        public Set<String> findWordsByPrefix(String prefix, Set<Node> nodes) {
            Set<String> words = new TreeSet<>();
            int charIndex = 0;
            Queue<NodeProgress> queue = new LinkedList<>();
            for (Node node: nodes) {
                NodeProgress nodeProgress = new NodeProgress();
                nodeProgress.currentPrefixIndex = charIndex;
                nodeProgress.node = node;
                if (node.character == prefix.charAt(charIndex))
                    nodeProgress.numberOfTypos = 0;
                else
                    nodeProgress.numberOfTypos = 1;
                queue.add(nodeProgress);
            }

            NodeProgress next = null;
            while (!queue.isEmpty()) {
                next = queue.poll();

                if (next.currentPrefixIndex == prefix.length() - 1) {
                    if (!next.node.finalWord.isEmpty()) {
                        words.add(next.node.finalWord);
                    }
                    words.addAll(getWordsByFromSubtrie(next.node));
                }
                else {
                    for (Node node : next.node.nextNodes) {
                        NodeProgress nodeProgress = new NodeProgress();
                        nodeProgress.currentPrefixIndex = next.currentPrefixIndex + 1;
                        nodeProgress.node = node;
                        if (node.character == prefix.charAt(next.currentPrefixIndex + 1))
                            nodeProgress.numberOfTypos = next.numberOfTypos;
                        else
                            nodeProgress.numberOfTypos = next.numberOfTypos + 1;

                        if (nodeProgress.numberOfTypos <= 1) {
                            queue.add(nodeProgress);
                        }
                    }
                }

            }

            return words;
        }

        public Set<String> getWordsByFromSubtrie(Node node) {
            Set<String> words = new TreeSet<>();
            Queue<Node> queue = new LinkedList<>();
            for (Node next: node.nextNodes) {
                if (!next.finalWord.isEmpty()) {
                    words.add(next.finalWord);
                }
                queue.add(next);
            }
            while (!queue.isEmpty()) {
                node = queue.poll();
                for (Node next: node.nextNodes) {
                    if (!next.finalWord.isEmpty()) {
                        words.add(next.finalWord);
                    }
                    queue.add(next);
                }
            }
            return words;
        }

    }

    static class Node implements Comparable<Node> {
        private char character;
        private String finalWord;
        private Node previousNode;
        private Set<Node> nextNodes;

        public Node(char character) {
            this.character = character;
            this.finalWord = "";
            this.nextNodes = new TreeSet<>();
        }

        @Override
        public int compareTo(Node o) {
            return this.character - o.character;
        }
    }

    static class NodeProgress {
        Node node;
        int currentPrefixIndex;
        int numberOfTypos;
    }

}
