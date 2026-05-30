/* ScoreMap.java  Project 3: Scrabble
Author: Tomas Salaz
Description: Object for making the Maps used for Scoring and the Frequency of the
tiles for a scrabble game, read in from a .txt file using a reader
*/
package Scrabble;
import java.io.*;
import java.util.*;
public class ScoreMap {
    /*Global Maps*/
    public Map<String, Integer>  scores = new HashMap<>();;
    public Map<String, Integer> frequency = new HashMap<>();

    /**
     * Constructor
     * @param br Reader for going through the .txt file and making the maps
     * @throws IOException if the reader never reaches the end
     */
    public ScoreMap(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(" ");
            scores.put(tokens[0], Integer.parseInt(tokens[1]));
            frequency.put(tokens[0], Integer.parseInt(tokens[2]));
        }
    }

    /**
     * @param c the character (as a String) that we want to get the score of
     * @return the score value
     */
    public int getScore(String c) {
        return scores.get(c);
    }

    /**
     * @param c the character (as a String) that we want to get the frequency of
     * @return teh amount of letters that should be in the game
     */
    public int getFrequency(String c) {
        return frequency.get(c);
    }
}