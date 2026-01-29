import java.util.*;

public class WordFrequencyCounter {
    String[] text;
    Map<String, Integer> wordCount;
    PriorityQueue<Map.Entry<String, Integer>> topWordHeap;
    final int MAX_TOP_ELEMENTS = 100;
    Set<String> STOP_WORDS = Set.of(
            "a","i","the","and","to","of","in","for","on","at","by","as","with","from",
            "it","its","this","that","these","those",
            "is","am","are","was","were","be","been","being","have","has","had","will",
            "do","did","does",
            "we","you","me","my","our","your","she","her",
            "but","or","if","so","because",
            "not","up","out","there","here",
            "all","one","more","much",
            "etc"
    );


    public WordFrequencyCounter(String text) {
        this.text = text.split(" ");
        wordCount = new HashMap<>(1000);
        topWordHeap = new PriorityQueue<>(MAX_TOP_ELEMENTS+1,
                Comparator.comparingInt(
                        Map.Entry<String, Integer>::getValue)
                        .thenComparing(Map.Entry::getKey));
    }

    void processText() {
        for (String word : this.text) {
            if (STOP_WORDS.contains(word)) { continue ; }

            wordCount.put(word, wordCount.getOrDefault(word, 0)+1 );
        }

        for (Map.Entry<String, Integer> element : wordCount.entrySet()) {
            topWordHeap.offer(element);

            if (topWordHeap.size() > MAX_TOP_ELEMENTS) {
                topWordHeap.poll(); // Remove smallest element
            }
        }
    }

    List<String> topWords() {
        List<String> result = new ArrayList<>(MAX_TOP_ELEMENTS);
        PriorityQueue<Map.Entry<String,Integer>> copy =
                new PriorityQueue<>(topWordHeap);

        if (copy.isEmpty()) {
            return List.of();
        }

        while (!copy.isEmpty()) {
            result.add(copy.remove().getKey());
        }

        // Since heap goes from low to high word count priority
        // return a reversed arraylist
        return result.reversed();
    }

    int getFrequencyOfWord(String word) {
        return wordCount.getOrDefault(word, 0);
    }

    public static void main(String[] args) {
        FileLoader loader = new FileLoader(".gitignore");
        String cleanFileContents = loader.getAllRawCharacters();

        WordFrequencyCounter counter = new WordFrequencyCounter(cleanFileContents.toLowerCase());
        counter.processText();

        List<String> topWords = counter.topWords();
        System.out.println("Top Words: " + String.join(" ", topWords));

        for (String word : topWords ) {
            System.out.println(word + ": " + counter.getFrequencyOfWord(word));
        }
    }
}
