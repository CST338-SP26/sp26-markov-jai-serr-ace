import java.util.ArrayList;
import java.util.HashMap;

public class Markov {
    private static String BEGINS_SENTENCE;
    private String prevWord;
    private HashMap<String, ArrayList<String>> words;
    private static String PUNCTUATION_MARKS;

    Markov() {}

    public String getSentence(){}

    public void addFromFile(String filename){}

    void addWord(String word){}

    String randomWord(String rand_word) {}

    public String toString() {}

    HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    void addLine(String line){}

    public boolean endsWithPunctuation(String phrase){}
}
