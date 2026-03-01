import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class Markov {
    private static final String BEGINS_SENTENCE = "__$";
    private String prevWord;
    private final HashMap<String, ArrayList<String>> words = new HashMap<>();
    private static final String PUNCTUATION_MARKS = ".!?$";

    public Markov() {
        ArrayList<String> temp = new ArrayList<>();
        words.put(BEGINS_SENTENCE,temp);
        prevWord = BEGINS_SENTENCE;
    }

    public String getSentence(){
        StringBuilder sentence = new StringBuilder();
        String current_word = BEGINS_SENTENCE;
        while(true) {
            String temp = randomWord(current_word);
            if (!endsWithPunctuation(temp)) {
                sentence.append(temp);
                sentence.append(" ");
            }
            else{
                sentence.append(temp);
                break;
            }
            current_word = temp;
        }
        return sentence.toString();
    }

    public void addFromFile(String filename){
        File fl = new File(filename);
        try (Scanner scan = new Scanner(fl)){
            while(scan.hasNextLine()){
                addLine(scan.nextLine());
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Error has occurred");
        }

    }

    void addWord(String word){
        if(word.isEmpty()){
            return;
        }
        if(endsWithPunctuation(prevWord)){
            words.get(BEGINS_SENTENCE).add(word);
        }
        else {
            if(!words.containsKey(prevWord)){
                //adding word to key
                ArrayList<String> empt = new ArrayList<>();
                words.put(prevWord,empt);
            }
            words.get(prevWord).add(word);
        }
        prevWord = word;
    }

    String randomWord(String rand_word) {
        Random random = new Random();
        int rand_int = random.nextInt(words.get(rand_word).size());
        return words.get(rand_word).get(rand_int);
    }

    public String toString() {
        return getWords().toString();
    }

    HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    void addLine(String line){
        if(!line.isEmpty()) {
            String[] temp = line.trim().split("[ \n\t]");
            for(String a : temp){
                addWord(a);
            }
        }
    }

    public static boolean endsWithPunctuation(String phrase){
        String temp = phrase.substring(phrase.length() - 1);
        return PUNCTUATION_MARKS.contains(temp);
    }
}
