/**
 * CS 152 Lab 5 - Wordle
 * Implement the methods needed to play a Wordle-like game.
 * Student name: Tomas Salaz
 */
import java.util.Arrays;
import java.util.Scanner;

/** Contains methods for a wordle clone */
public class Wordle {

    /**
     * Represents a letter in the guess in the correct position.
     */
    public static final char CORRECT = 'X';

    /**
     * Represents a letter in the guess that is present, but in wrong place.
     */
    public static final char PRESENT = 'o';

    /**
     * Represents a letter in the guess does not occur in the word at all.
     */
    public static final char MISSING = '.';

    /**
     * How many guesses do we get to solve the puzzle?
     */
    public static final int NUMBER_OF_GUESSES = 6;

    /**
     * Picks a random word from the dictionary.
     *
     * @param dictionary An array of words.
     * @return Randomly chosen word from dictionary.
     */
    //Int 'i' is the random variable, For Loop is made to iterate once to
    //get random num, return random index of dictionary.

    public static String selectRandomWord(String[] dictionary) {
        int i = 0;
        for (int x = 0; x < 1; x++) {
            i = (int) (Math.random() * dictionary.length);
        }
        return dictionary[i];
    }


    /**
     * Is the guess a recognized word?
     * @param guess      The guess word.
     * @param dictionary Array of known words.
     * @return True if guess is in dictionary, false if not.
     */
    //result starts as 'False' since we are testing for a 'True' bool. The
    // for loop is a Liner Search through the dictionary. If the users
    // 'guess' is found, the result var changes to true
    public static boolean isValidWord(String guess, String[] dictionary) {
        boolean res = false;
        for (int x = 0; x <= dictionary.length - 1; x++) {
            if (guess.equals(dictionary[x])) {
                res = true;
            }
        }
        return res;
    }

    /**
     * How close is the guess to the secret word?
     *
     * @param guess Guessed word
     * @param word  The hidden word
     * @return Array of characters corresponding to the letters of guess,
     * where the char at a given index is:
     * CORRECT if the guess letter appears at that position in word
     * PRESENT if the guess letter is present in word elsewhere
     * MISSING if the guess letter does not occur in word
     */
    //getGuessResult uses a Method and 2 loops to work. Loops will be
    // explained above the loop.
    public static char[] getGuessResult(String guess, String word) {
        char[] resArr = new char[word.length()];
        char[] guessArr = guess.toCharArray();
        char[] wordArr = word.toCharArray();

        //Array Fill Method, 1st parameter is what array we are filling, 2nd
        // parameter is what I char im filling the Array with. resArr is now
        // filled with all missing chars.
        Arrays.fill(resArr, MISSING);

        //This loop looks at the indices of the guess compared to the secret
        // word. If the char and index is the same fot both the guess and the
        // word, CORRECT gets assigned to the resArr at the same index of the
        // guessArr, and we remove the char in the wordArr so it isn' counted
        // twice.
        int x = 0;
        while(x < guessArr.length){
            for (int i = 0 ; i < wordArr.length; i++){
                if (guessArr[x] == wordArr[i] && x == i){
                    resArr[x] = CORRECT;
                    wordArr[i] = ' ';
                    break;
                }
            }
            x++;
        }
        //This loop essentially does the same thing as the last loop. It
        // checks for similar chars, but the indices can be different. Once a
        // similar char is found, PRESENT is assigned to the same index in
        // the resArr as the guessArr. At the same time, the wordArr index
        // where the char is found is updated to be empty, so that way
        // nothing is counted twice.
        int z = 0;
        while (z < guessArr.length){
            for (int i = 0 ; i < wordArr.length; i ++){
                if (guessArr[z] == wordArr[i]){
                    resArr[z] = PRESENT;
                    wordArr[i] =' ';
                }
            }
            z++;
        }
        return resArr;
    }

    /**
     * Is this a winning result?
     *
     * @param guessResult Array as returned by getGuessResult
     * @return True if all places are CORRECT, false if not
     */
    //This method contains one while loop testing to see if the resArr
    // contains PRESENT or MISSING chars. The res starts as true so we are
    // testing for 'False' in the loop. If the while statement true the
    // result changes to False, since the statement being true would result
    // in a false answer. 
    public static boolean isWinningResult(char[] guessResult) {
        boolean res = true;
        int i = 0;
        while (i < guessResult.length) {
            if (guessResult[i] == PRESENT || guessResult[i] == MISSING) {
                res = false;
            }
            i++;
        }
        return res;
    }

    /**
     * Plays a console based Wordle game
     *
     * @param args Ignored
     */
    public static void main(String[] args) {

        System.out.println("Let's play Wordle!\n");
        System.out.println();
        // The big array of words is in a separate file
        String[] words = WordleDictionary.FIVE_LETTER_WORDS;

        Scanner in = new Scanner(System.in);

        String secret = selectRandomWord(words);
        int guesses = NUMBER_OF_GUESSES;

        boolean winning;

        do {
            System.out.println("Guesses remaining: " + guesses);
            System.out.println("What is your guess?");

            String guess = in.nextLine().trim().toLowerCase();

            while (!isValidWord(guess, words)) {
                System.out.println("Not a recognized word! Try again");
                guess = in.nextLine().trim().toLowerCase();
            }

            char[] guessResult = getGuessResult(guess, secret);
            System.out.println(new String(guessResult));

            winning = isWinningResult(guessResult);
            guesses--;

        } while (guesses > 0 && !winning);

        System.out.println("The word was " + secret);
    }
}