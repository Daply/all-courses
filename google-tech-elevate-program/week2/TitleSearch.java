package week2;

import java.util.*;
import java.util.stream.Collectors;

public class TitleSearch {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);

        final int n = sc.nextInt();
        sc.nextLine();
        WebPagesTrie trie = new WebPagesTrie();
        for (int i = 0; i < n; ++i) {
            String title = sc.nextLine();
            trie.addTitle(title);
        }

        final int q = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < q; ++i) {
            String query = sc.nextLine();
            List<String> titles = trie.getTitlesByQuery(query);
            titles.sort(TitleSearch::compare);
            System.out.println(Math.min(titles.size(), 10));
            for (int titleIndex = 0; titleIndex < 10 && titleIndex < titles.size(); titleIndex++) {
                System.out.println(titles.get(titleIndex));
            }
        }
    }

    public static int compare(String title1, String title2) {
        String[] title1Words = title1.split("\\s");
        String[] title2Words = title2.split("\\s");
        if (title1Words.length == title2Words.length) {
            return title1.compareTo(title2);
        }
        else {
            return title1Words.length - title2Words.length;
        }
    }

    static class WebPagesTrie {
        List<TitleNode> rootNodes;

        public WebPagesTrie() {
            this.rootNodes = new ArrayList<>();
        }

        public TitleNode find(List<TitleNode> nodes, String word) {
            for (TitleNode node: nodes) {
                if (node.word.equals(word))
                    return node;
            }
            return null;
        }

        public void addTitle(String title) {
            String[] titleWords = title.split("\\s");
            List<String> words = Arrays.asList(titleWords);
            words.sort(String::compareTo);
            TitleNode current = null, previous = null;

            List<TitleNode> nextNodes = this.rootNodes;
            for (String word: words) {
                TitleNode found = find(nextNodes, word);
                if (found == null) {
                    current = new TitleNode(word);
                    if (previous != null)
                        previous.nextWords.add(current);
                    else
                        this.rootNodes.add(current);
                }
                else
                    current = found;
                nextNodes = current.nextWords;
                previous = current;
            }
            if (current != null) {
                current.finalTitles.add(title);
            }

        }

        public List<String> getTitlesByQuery(String query) {
            String[] queryWords = query.split("\\s");
            List<String> words = Arrays.asList(queryWords);
            words = words.stream().distinct().collect(Collectors.toList());
            words.sort(String::compareTo);
            return findTitles(words);
        }

        public List<String> findTitles(List<String> words) {
            List<String> foundTitles = new ArrayList<>();
            Queue<TitleNodeProgress> queue = new LinkedList<>();
            int wordIndex = -1;
            for (TitleNode next: this.rootNodes) {
                TitleNodeProgress titleNodeProgress = new TitleNodeProgress();
                titleNodeProgress.node = next;
                if (next.word.equals(words.get(wordIndex + 1)))
                    titleNodeProgress.wordIndex = wordIndex + 1;
                else
                    titleNodeProgress.wordIndex = wordIndex;
                queue.add(titleNodeProgress);
            }

            while (!queue.isEmpty()) {
                TitleNodeProgress current = queue.poll();

                if (current.wordIndex == words.size() - 1 &&
                        current.node.word.equals(words.get(current.wordIndex))) {
                    if (!current.node.finalTitles.isEmpty()) {
                        foundTitles.addAll(current.node.finalTitles);
                    }
                    foundTitles.addAll(getTitlesFromSubtrie(current.node));
                }
                else {
                    for (TitleNode next: current.node.nextWords) {
                        if (current.wordIndex < words.size()) {
                            TitleNodeProgress titleNodeProgress = new TitleNodeProgress();
                            titleNodeProgress.node = next;
                            if (next.word.equals(words.get(current.wordIndex + 1)))
                                titleNodeProgress.wordIndex = current.wordIndex + 1;
                            else
                                titleNodeProgress.wordIndex = current.wordIndex;
                            if (titleNodeProgress.wordIndex <= words.size() - 1) {
                                queue.add(titleNodeProgress);
                            }
                        }
                    }
                }
            }

            return foundTitles;
        }

        public List<String> getTitlesFromSubtrie(TitleNode found) {
            List<String> foundTitles = new ArrayList<>();
            Queue<TitleNode> queue = new LinkedList<>();
            for (TitleNode next: found.nextWords) {
                if (!next.finalTitles.isEmpty()) {
                    foundTitles.addAll(next.finalTitles);
                }
                queue.add(next);
            }
            while (!queue.isEmpty()) {
                TitleNode node = queue.poll();
                for (TitleNode next: node.nextWords) {
                    if (!next.finalTitles.isEmpty()) {
                        foundTitles.addAll(next.finalTitles);
                    }
                    queue.add(next);
                }
            }
            return foundTitles;
        }

    }

    static class TitleNode implements Comparable<TitleNode> {
        List<TitleNode> nextWords;
        String word;
        List<String> finalTitles;

        public TitleNode(String word) {
            this.word = word;
            this.nextWords = new ArrayList<>();
            this.finalTitles = new ArrayList<>();
        }

        @Override
        public int compareTo(TitleNode o) {
            return this.word.compareTo(o.word);
        }

        @Override
        public String toString() {
            return word;
        }
    }

    static class TitleNodeProgress {
        TitleNode node;
        int wordIndex = -1;

        @Override
        public String toString() {
            return "TitleNodeProgress{" +
                    "node=" + node +
                    ", wordIndex=" + wordIndex +
                    '}';
        }
    }

}
