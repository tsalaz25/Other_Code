/* Solver.java  Project 3: Scrabble
Author: Tomas Salaz
Description: Creates a Solver Object used for finding the best play. The Solver
reads in a Board and a Tray from a .txt file and find the best play using a common
backtracking algorithm.
NOTE: For the GUI version: Implement 'setBoard, setTray, and clearInfo' to make
a Solver usable in a Game situation.
*/
package Solver;
import Scrabble.*;
import java.util.*;
import java.io.*;
public class Solver {
    /*Global Variables*/
    private static Solver solver;
    private static ScoreMap scoreMap;
    private static Dict dict;
    private Board board;
    private Tray tray;
    /**
     * Constructor
     * @param br BufferedReader for accessing the .txt file and using .readline()
     *           to parse information for usage
     * @throws IOException if Reader never gets to the end of the file.
     */
    public Solver(BufferedReader br) throws IOException {
        String line;
        while((line = br.readLine())!=null){
            if (line.length() < 3){
                int dim = Integer.parseInt(line);
                board = new Board(dim);
                StringBuilder config = new StringBuilder();
                for (int i = 0; i < dim; i++){
                    line = br.readLine();
                    config.append(line + ' ');
                }
                board.configBoard(config.toString(), scoreMap);
            }
            line = br.readLine();
            tray = new Tray(line,scoreMap);
            if (board != null && tray != null){
                findBestPlay(board, tray);
                board = null;
                tray = null;
            }
        }
    }
    /**
     * @param board is the current state of the board
     * @param t is the current players tray
     * Finds the best play, Board has methods for getting 'anchors'. Other
     * tasks of method is to save the best score and print the info about the
     * best score.
     */
    private void findBestPlay(Board board, Tray t) {
        if (board == null || t == null) {
            System.out.println("Board/ Tray is Null");
        }
        System.out.println("Input Board:");
        System.out.printf(board.toString());
        System.out.println("Tray: " + t.toString());
        LinkedList<int[]> anchors = board.getAnchors();
        LinkedList<String> possibleWords =  generateAllWords(t);
        int maxScore = 0;
        String bestWord = "";
        int bestX = -1;
        int bestY = -1;
        boolean horizontal = true;
        for (int[] a: anchors){
            int x = a[0];
            int y = a[1];
            for (String word: possibleWords){
                if (isValidPlacement(board,word,x,y,true)){
                    int score = getScore(board,word,x,y,true);
                    if (score > maxScore){
                        maxScore = score;
                        bestWord = word;
                        bestX = x;
                        bestY = y;
                        horizontal = true;
                    }
                }
                if (isValidPlacement(board,word,x,y,false)){
                    int score = getScore(board,word,x,y,false);
                    if (score > maxScore){
                        maxScore = score;
                        bestWord = word;
                        bestX = x;
                        bestY = y;
                        horizontal = false;
                    }
                }
            }
        }
        System.out.printf("Solution %s has %d points\n",bestWord,maxScore);
        board.update(bestX, bestY, bestWord, horizontal,scoreMap);
        System.out.println("Solution Board: ");
        System.out.printf(board.toString() + '\n');
    }
    /**
     * @param t is the current tray used for finding all the words that the tray
     *          can make, independent of the available spaces on the board
     * @return LinkedList of String containing all the words
     */
    public LinkedList<String> generateAllWords(Tray t) {
        LinkedList<String> words = new LinkedList<>();
        StringBuilder currentWord = new StringBuilder();
        boolean[] used  = new boolean[t.getTray().size()];
        backtrack(t, dict.getTrie().getRoot(), currentWord,used,words);
        return words;
    }
    /**
     * @param t is the current tray
     * @param node is the root node of the dictionary, allows access to every word
     *             within the method
     * @param currentWord is the partial word that has been constructed
     * @param used boolean array for tracking the used tray indices
     * @param words is the List that is updated with the word that are found
     */
    public void backtrack(Tray t, Trie.Node node, StringBuilder currentWord, boolean[] used, List<String> words ) {
        if (node.isWord()){words.add(currentWord.toString());}
        for (int i = 0; i < t.getTray().size(); i++){
           if (used[i]){continue;}
           char letter = (t.getTray().get(i).getLetter().charAt(0));
           if (letter == '*'){
               for (char ch = 'a';ch <= 'z';ch++){
                   if (node.contains(ch)){
                       used[i] = true;
                       currentWord.append(ch);
                       backtrack(t, node, currentWord,used,words);
                       currentWord.deleteCharAt(currentWord.length()-1);
                       used[i] = false;
                   }
               }
           } else if (node.contains(letter)){
               used[i] = true;
               currentWord.append(letter);
               backtrack(t, node, currentWord,used,words);
               currentWord.deleteCharAt(currentWord.length()-1);
               used[i] = false;
           }
        }
    }
    /**
     * @param board is the current board
     * @param word is the word that is being tested, to see if it can be placed
     * @param x is the x coordinate of the 1st letter of the word
     * @param y is the y coordinate of the 1st letter of the word
     * @param horizontal boolean used for checking the correct direction
     * @return Valid or Invalid Placement
     */
    public boolean isValidPlacement (Board board, String word, int x, int y, boolean horizontal){
        int length = word.length();
        if (horizontal){
            if (y + length > board.dim) {return false;}
            for (int i = 0; i < length; i++){
                Tile tile = board.getTile(x, y +i);
                if(!tile.isEmpty() && tile.getLetter() != String.valueOf(word.charAt(i))){
                    return false;
                }
            }
        } else {
            if (x+length > board.dim) {return false;}
            for (int i = 0; i < length; i++){
                Tile tile = board.getTile(x+i, y);
                if (!tile.isEmpty() && tile.getLetter() != String.valueOf(word.charAt(i))){
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * @param board is the current board
     * @param word is the word that is being scored
     * @param x is the x coordinate of the 1st letter of the word
     * @param y is the y coordinate of the 1st letter of the word
     * @param horizontal boolean used for checking the correct direction
     * @return the score, including multipliers
     */
    public int getScore (Board board, String word, int x, int y, boolean horizontal) {
        int score = 0;
        int wordMulti = 1;
        for (int i = 0; i < word.length(); i++){
            char letter = word.charAt(i);
            int letterScore = scoreMap.getScore(String.valueOf(letter));
            Tile tile;
            if (horizontal){
                tile = board.getTile(x, y+i);
            } else {
                tile = board.getTile(x+i, y);
            }
            if (tile.hasMulti()){
                if (tile.getMultiType() == 'L'){
                    score += letterScore * tile.getMultiplier();
                }
                if (tile.getMultiType() == 'W'){
                    wordMulti *= tile.getMultiplier();
                }
            } else {
                score += letterScore;
            }
        }
        return score*wordMulti;
    }
    /**
     * @param args, if usage is correct, loads in the dictionary.txt file
     * Is also used for loading in the Tile Scores and Frequency as 2 Maps,
     * loading in the testing file and is the Entry Point for the solver.jar
     */
    public static void main (String[] args){
        /*Load in  the TileScores --> FROM: scrabble_tiles.txt*/
        String tileInfo = "/" + "scrabble_tiles.txt";
        InputStream in1 = Solver.class.getResourceAsStream(tileInfo);
        try (BufferedReader br1 = new BufferedReader(new InputStreamReader(in1))) {
            scoreMap = new ScoreMap(br1);
        } catch (Exception e) {
            System.out.println("File scrabble_tiles.txt not loaded in\n");
            e.printStackTrace();
        }
        String dictInfo = "/" + "sowpods.txt";
        if (args.length == 1) {dictInfo = args[0];}
        else {System.out.println("Usage: java -jar scorechecker.jar </'dictionary_file_.txt'>");}
        InputStream in2 = Solver.class.getResourceAsStream(dictInfo);
        try (BufferedReader br2 = new BufferedReader(new InputStreamReader(in2))){
            dict = new Dict(br2);
        }catch (Exception e){
            System.out.println("Dictionary not loaded in\n");
            e.printStackTrace();
        }
        System.out.println("ENTER TESTING FILE: ex '/example_input.txt'");
        String testInfo = new Scanner(System.in).nextLine();
        InputStream inTest = Solver.class.getResourceAsStream(testInfo);
        try (BufferedReader br3 = new BufferedReader(new InputStreamReader(inTest))){
            solver = new Solver(br3);
        } catch (Exception e) {
            System.out.println("The Test File Not Found");
            e.printStackTrace();
        }
    }
}