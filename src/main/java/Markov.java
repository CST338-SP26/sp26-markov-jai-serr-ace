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

    //the constructor takes creates an empty ArrayList of strings, and it adds it to words HashMap using
    //BEGINS_SENTENCE as the key, then it assigns prevWord to BEGINS_SENTENCE. The constructor does take any
    //parameters.
    public Markov() {
        ArrayList<String> temp = new ArrayList<>();
        words.put(BEGINS_SENTENCE,temp);
        prevWord = BEGINS_SENTENCE;
    }

    //This method creates a String Builder object which is used to create a sentence.
    //First, current word is set to BEGINS_SENTENCE, then inside a while it takes a random word from
    //the randomWord method using current word as the parameter. If so such word does not end with a punctuation
    //it gets added to the string builder object sentence, and then it adds a space. If the word does end with
    //a punctation, the word gets added to sentence and the loop ends. Inside the loop, current word gets reassigned
    //to temp. Once the loop ends sentence.toString() is returned.
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

    //this method opens a file, then uses Scanner to read the file, and as long as there is a line, the line
    //is then pass into the addLine method. The method also catches if the file cannot be opened.
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

    //this method takes a String word, and it first checks if the string is empty and ends the function is so.
    //Then checks if the string prevWord ends with punctuation, if so, the string
    //BEGINS_SENTENCE is used as the key for the HashMap and the word is added to ArrayList being access.
    //If not, then it first checks if the prevWord is not a key in the HashMap and if so, an empty ArrayList is
    //added to the HashMap using the prevWord as the key. Then it access prevWord from the HashMap and it adds word
    // to accessed ArrayList. Lastly, it uses prevWord equal to the current word.
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

    //this method takes a string 'rand_word', which uses such string as the key for accessing the words HashMap
    //and then returns a random element from access ArrayList from the map.
    //the random index creating using Java's random method; using the ArrayList size as the parameter.
    String randomWord(String rand_word) {
        Random random = new Random();
        int rand_int = random.nextInt(words.get(rand_word).size());
        return words.get(rand_word).get(rand_int);
    }

    //this method uses the built-in Java method toString for the words HashMap and returns that as the
    //class toString method
    public String toString() {
        return getWords().toString();
    }

    //this method gets the HashMap words
    HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    //this method adds a line from the file to hashMap if the line is not empty. Then the line gets split
    //by space, tabs, and next line, and get stored inside String array temp. Then elements inside temp
    //are access one by one and then pass into method addWord.
    void addLine(String line){
        if(!line.isEmpty()) {
            String[] temp = line.trim().split("[ \n\t]");
            for(String a : temp){
                addWord(a);
            }
        }
    }

    //this method checks if the string phrase ends with a punctuation by checking the string's last
    //character if it is inside the string PUNCTUATION_MARKS and returning that boolean
    public static boolean endsWithPunctuation(String phrase){
        String temp = phrase.substring(phrase.length() - 1);
        return PUNCTUATION_MARKS.contains(temp);
    }
}
