/* Dict.java  Project 3: Scrabble
Author: Tomas Salaz
Description: is used for saving in the Dictionary used in the Scrabble Game, Is
saved a PrefixTree/Trie and is loaded in from a .txt file using a reader
*/
package Scrabble;
import java.io.*;
public class Dict {
    private Trie trie;

    /**
     * @param br Reader for the .txt file the dictionary is stored in
     */
    public Dict(BufferedReader br){
        trie = new Trie();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                trie.insert(line);
            }
        } catch (Exception e) {
            System.out.println("Dictionary File not Passed in");
        }
    }

    /**
     * @param word the word to search for
     * @return is the Word Exists in the Dictoinary
     */
    public boolean wordExists(String word) {
        return trie.isWord(word);
    }
    /**
     * @return the Dictionary
     */
    public Trie getTrie() {return trie;}
}