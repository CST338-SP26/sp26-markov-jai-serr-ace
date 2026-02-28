import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class Markov {
    private static String BEGINS_SENTENCE;
    private String prevWord;
    private HashMap<String, ArrayList<String>> words;
    private static String PUNCTUATION_MARKS;

    public Markov() {}

    public String getSentence(){

        StringBuilder sentence = new StringBuilder();
        while(true) {
            String temp = randomWord(BEGINS_SENTENCE);
            if (!endsWithPunctuation(temp)) {
                sentence.append(temp);
                sentence.append(" ");
            }
            else{
                sentence.append(temp);
                break;
            }
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
            System.out.println("Error has occured");
        }

    }

    void addWord(String word){
        if(endsWithPunctuation(prevWord)){
            words.get(BEGINS_SENTENCE).add(word);
        }
        else {
            if(!words.containsKey(prevWord)){
                //adding word to key
                words.put(prevWord,null);
            }
            else {
                words.get(prevWord).add(word);
            }
        }
    }

    String randomWord(String rand_word) {
        Random random = new Random();
        int rand_int = random.nextInt(words.get(rand_word).size()-1);
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
            String[] temp = line.split(" ");
            for(String a : temp){
                addWord(a);
            }
        }
    }

    public boolean endsWithPunctuation(String phrase){
        String temp = phrase.substring(phrase.length() - 1);
        return PUNCTUATION_MARKS.contains(temp);
    }
}
