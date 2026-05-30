/* Checker.java  Project 3: Scrabble
Author: Tomas Salaz
Description: Class used for creating a Checker. Uses a .txt file to read in and
create 2 boards and get the score depending on the move that were made. Is
independent from the Solver and the Scrabble GUI
* */
package ScoreChecker;
import Scrabble.*;
import java.io.*;
import java.util.*;
public class Checker {
    /*Global Variables*/
    private static Checker scoreChecker;
    private static ScoreMap scoreMap;
    private static Dict dict;
    private Board b1, b2;
    LinkedList<int[]> coordsToScore = new LinkedList<>();
    /**
     * Constructor
     * @param br is a Reader for Reading in the .txt file used for testing and
     *           the program
     * @throws IOException if the reader never gets to the end of the file
     */
    public Checker(BufferedReader br) throws IOException {
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.length() == 0 && b1 == null && b2 == null) {continue;
            } else if (line.length() == 0 && b1 != null && b2 != null) {
                getScore(b1,b2);
                b1 = null;
                b2 = null;
                coordsToScore.clear();
            } else if (line.length() <= 3) {
                if (b1 == null) {
                    b1 = new Board(Integer.parseInt(line.trim()));
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < b1.dim; i++){
                        line = br.readLine();
                        sb.append(line + ' ');
                    }
                    b1.configBoard(sb.toString(), scoreMap);
                }
                else {
                    b2 = new Board(Integer.parseInt(line.trim()));
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < b2.dim; i++){
                        line = br.readLine();
                        sb.append(line + ' ');
                    }
                    b2.configBoard(sb.toString(), scoreMap);
                }
                if (b1 != null && b2 != null) {
                    getScore(b1,b2);
                    b1 = null;
                    b2 = null;
                }
            }
        }
    }
    /**
     * @param b1 the Original Board before a play is made
     * @param b2 the Resulting Board after the plat is made
     */
    public void getScore(Board b1, Board b2) {
        System.out.printf("original board:\n" + b1.toString());
        System.out.printf("result board:\n" + b2.toString());
        int dim = b1.dim;
        LinkedList<int[]> diffs = new LinkedList<>();
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (!(b1.getTile(r,c).getLetter().equals(b2.getTile(r,c).getLetter()))){
                    diffs.add(new int[]{r,c});
                }
            }
        }
        if (diffs.size() == 0) {
            System.out.printf("play is empty\nplay is not legal\n\n");
        } else {
            if (!boardsAreCompatible(b1,b2,diffs)){
                return;
            } else {
                StringBuilder sb = new StringBuilder();
                StringBuilder substring = new StringBuilder();
                sb.append("play is");
                for (int[] a : diffs) {
                    sb.append(' ' + b2.getTile(a[0], a[1]).getLetter() + " at (" + a[0] + ", " + a[1] + "),");
                    substring.append(b2.getTile(a[0], a[1]).getLetter());
                }
                String play = substring.toString();
                String str = sb.toString().trim();
                str = str.substring(0, str.length() - 1);
                System.out.println(str);
                if (!validPlay(b1.getAnchors(), diffs)){
                    System.out.printf("play is not legal\n\n");
                    return;
                } else {
                    handleLegalPlay(b2, diffs);
                }
            }
        }
    }
    /**
     * @param b2 is the Result Board after the play is made
     * @param diffs is an List<int[]> containing the modified coordinates
     * Name doesn't suggest this but this method is checking for the validity as
     * well as handling it when it is confirmed.
     */
    public void handleLegalPlay(Board b2, LinkedList<int[]> diffs) {
        char playDirection = ' ';
        if (diffs.size() == 0){
            playDirection = 'A';
        }else {
            if (diffs.size() == 1){
                int x = diffs.get(0)[0];
                int y = diffs.get(0)[1];
                if (b2.getTile(x+1,y).isEmpty() || b2.getTile(x-1,y).isEmpty()){
                    playDirection = 'H';
                } else {
                    playDirection = 'V';
                }
            }
            else if (diffs.get(0)[0] == diffs.get(1)[0]){playDirection = 'H';}
            else if (diffs.get(0)[1] == diffs.get(1)[1]){playDirection = 'V';}
            else {System.out.printf("Play Direction Not Saved");}
        }
        String playPrefix = getPrefix(b2, diffs.getFirst(), playDirection);
        String playSuffix = getSuffix(b2,diffs.getLast(),playDirection);
        String play = contWord(b1, b2, diffs.getFirst(), diffs.getLast(), playPrefix, playSuffix, playDirection);
        LinkedList<String> adjacentWords =  getAdjs(b2, diffs, playDirection);
        if (!dict.wordExists(play)){
            System.out.printf("play is not legal\n\n");
            return;
        }
        for (String s: adjacentWords){
            if (!dict.wordExists(s)){
                System.out.printf("play is not legal\n\n");
                return;
            }
        }
        int score = calculateScore(b1, b2, coordsToScore, play.length(), adjacentWords);
        System.out.printf("play is legal\nscore is %d \n\n", score);
    }
    /**
     * @param b1 Original Board used for counting the Multipliers
     * @param b2 Result Board used for scoring the newly added letters
     * @param score List<int[]> that are the coordinate to score
     * @param playLen The length of the word that is being scored, used for
     *                checking the incrementation of the loops in the methods
     * @param adjacentWords is the List<Strings> that accounts for cross-checking
     * @return the score of the play as an Integer
     */
    private int calculateScore(Board b1, Board b2, LinkedList<int[]> score, int playLen, LinkedList<String> adjacentWords) {
        ArrayList<Integer> letterScores = new ArrayList<>();
        ArrayList<Integer> wordMultis = new ArrayList<>();
        ArrayList<Integer> wordScores = new ArrayList<>();
        int c = 0;
        int endWord = playLen;
        while (score.size() > 0){
            int[] s = score.remove(0);
            int indivScore = b2.getTile(s[0],s[1]).getScore();
            if(b1.getTile(s[0],s[1]).hasMulti()){
                switch (b1.getTile(s[0],s[1]).getMultiType()){
                    case 'L' -> {
                        letterScores.add(indivScore * b1.getTile(s[0],s[1]).getMultiplier());
                    }
                    case 'W' -> {
                        letterScores.add(indivScore);
                        wordMultis.add(b1.getTile(s[0],s[1]).getMultiplier());
                    }
                }
            } else {letterScores.add(b2.getTile(s[0],s[1]).getScore());}
            if(b2.getTile(s[0],s[1]).hasAdj()){
                b2.getTile(s[0],s[1]).setAdj(false);
                score.add(s);
            }
            c++;
            if (c == endWord){
                c = 0;
                if (adjacentWords.size() > 0) {
                    endWord = adjacentWords.remove(0).length();
                }
                int indivWordScore = 0;
                int wordX = 0;
                for (int i: letterScores) {indivWordScore+=i;}
                for (int m: wordMultis) {wordX+=m;}
                if (wordX == 0){wordX = 1;}
                wordScores.add(indivWordScore*wordX);
                letterScores.clear();
                wordMultis.clear();
            }
        }
        int finScore = 0;
        for (int i: wordScores){finScore+= i;}
        return finScore;
    }
    /**
     * @param b1 Original Board
     * @param b2 Result Board
     * @param first Coordinate of the 1st difference, to update the Coords to Score
     * @param last Coordinate of the last difference, to update the Coords to Score
     * @param pre prefix of the word (***TilesAlreadyPlaced)
     * @param suf suffix of the word (TilesAlreadyPlaced***)
     * @param dir is the direction of the play
     * @return the String that is the word as a whole (***TilesAlreadyPlaced***)
     */
    private String contWord(Board b1, Board b2, int[] first, int[] last, String pre, String suf, char dir) {
        StringBuilder mid = new StringBuilder();
        switch (dir) {
            case 'H' -> {
                int row = first[0];
                int col = first[1];
                while (col <= last[1]){
                    mid.append(b2.getTile(row, col).getLetter());
                    coordsToScore.add(new int[]{row, col});
                    col++;
                }
                return pre + mid.toString() + suf;
            }
            case 'V' -> {
                int row = first[0];
                int col = first[1];
                while (row <= last[0]){
                    mid.append(b2.getTile(row, col).getLetter());
                    coordsToScore.add(new int[]{row, col});
                    row++;
                }
                return pre + mid.toString() + suf;
            }
            default -> System.out.printf("Cont Word Failure\n");
        }
        return null;
    }
    /**
     * @param b2 is the Result Board
     * @param diffs are the coordinates of the different files, List<int[]>
     * @param playDirection is the direction of the play
     * @return a List<String> that contains all the words played, to check validity
     */
    private LinkedList<String> getAdjs(Board b2, LinkedList<int[]> diffs, char playDirection){
        LinkedList<String> adjacentWords = new LinkedList<>();
        switch (playDirection) {
            case 'H' -> {
                for (int[] d : diffs) {
                    StringBuilder word = new StringBuilder();
                    String prefix = getPrefix(b2, d, 'V');
                    String suffix = getSuffix(b2, d,'V');
                    if (prefix.length() != 0){
                        word.append(prefix + b2.getTile(d[0],d[1]).getLetter());
                        b2.getTile(d[0],d[1]).setAdj(true);
                    }
                    if (suffix.length() != 0){
                        word.append(suffix);
                        b2.getTile(d[0],d[1]).setAdj(true);
                    }
                    if (word.toString().length() > 0) {
                        adjacentWords.add(word.toString());
                    }
                }
                return adjacentWords;
            }
            case 'V' -> {
                for (int[] d : diffs) {
                    StringBuilder word = new StringBuilder();
                    String prefix = getPrefix(b2, d, 'H');
                    String suffix = getSuffix(b2, d,'H');
                    if (prefix.length() != 0){
                        word.append(prefix + b2.getTile(d[0],d[1]).getLetter());
                        b2.getTile(d[0],d[1]).setAdj(true);
                    }
                    if (suffix.length() != 0){
                        word.append(suffix);
                        b2.getTile(d[0],d[1]).setAdj(true);
                    }
                    if (word.toString().length() > 0) {
                        adjacentWords.add(word.toString());
                    }
                }
                return adjacentWords;
            }
            default -> System.out.printf("Play Direction Not Saved\n");
        }
        return null;
    }

    /**
     * @param b2 the Result Board
     * @param last is the last coords of the new play
     * @param playDirection is the direction of the play
     * @return the Suffix, if there is one (NewlyAddedTiles***)
     */
    private String getSuffix(Board b2, int[] last, char playDirection) {
        StringBuilder suffix = new StringBuilder();
        switch (playDirection){
            case 'H' -> {
                if (last[1] == b2.dim-1){return suffix.toString();}
                int row = last[0];
                int step = last[1]+1;
                while(step <= b2.dim-1){
                    if (b2.getTile(row,step).isEmpty()){
                        return suffix.toString();
                    } else {
                        suffix.append(b2.getTile(row,step).getLetter());
                        coordsToScore.add(new int[]{row,step});
                    }
                    step++;
                }
            }
            case 'V' -> {
                if (last[0] == b2.dim-1){return suffix.toString();}
                int step = last[0]+1;
                int col = last[1];
                while(step <= b2.dim-1){
                    if (b2.getTile(step,col).isEmpty()){
                        return suffix.toString();
                    } else {
                        suffix.append(b2.getTile(step,col).getLetter());
                        coordsToScore.add(new int[]{step,col});
                    }
                    step++;
                }
            }
            default -> System.out.printf("Suffix Failure\n");
        }
        return null;
    }
    /**
     * @param b2 the Result Board
     * @param start is the Starting coords of the new play
     * @param playDirection is the direction of the play
     * @return the prefix, if it exits (***NewlyAddedTiles)
     */
    private String getPrefix(Board b2, int[] start, char playDirection) {
        StringBuilder prefix = new StringBuilder();
        switch (playDirection){
            case 'H' -> {
                if (start[1] == 0){return prefix.toString();}
                int row = start[0];
                int step = start[1]-1;
                while (step >= 0){
                    if (b2.getTile(row,step).isEmpty()){
                        return prefix.reverse().toString();
                    } else {
                        prefix.append(b2.getTile(row,step).getLetter());
                        coordsToScore.add(new int[]{row,step});
                    }
                    step--;
                }
            }
            case 'V' -> {
                if (start[0] == 0){return prefix.toString();}
                int step = start[0]-1;
                int col = start[1];
                while (step >= 0){
                    if (b2.getTile(step,col).isEmpty()){
                        return prefix.reverse().toString();
                    } else {
                        prefix.append(b2.getTile(step,col).getLetter());
                        coordsToScore.add(new int[]{step,col});
                    }
                    step--;
                }
            }
            default -> System.out.printf("Prefix Failure\n");
        }
        return null;
    }

    /**
     * @param anchors LinkedList<int[]> is the coords of the anchor points
     * @param diffs LinkesLsit<int[]> is the coords of the new play
     * @return boolean, for if the play was valid or not
     */
    private boolean validPlay(LinkedList<int[]> anchors, LinkedList<int[]> diffs) {
        for (int[] d : diffs) {
            for (int[] a : anchors) {
                if (a[0] == d[0] && a[1] == d[1]) { return true; }
            }
        }
        return false;
    }
    /**
     * @param b1 Original Board
     * @param b2 Result Board
     * @param diffs LinledList<int[]> of the newly added tiles
     * @return boolean, based on if the bord was modified in an illegal way
     */
    private boolean boardsAreCompatible(Board b1, Board b2, LinkedList<int[]> diffs) {
        int dim = b1.dim;
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (diffs.contains(new int[]{r,c})){continue;}
                else {
                    if (!b1.getTile(r,c).isEmpty() && b2.getTile(r,c).isEmpty()){
                        System.out.printf("Incompatible boards: tile removed at (%d, %d)\n",
                            r, c);
                        return false;
                    }
                    if ( (b1.getTile(r,c).isEmpty() && b2.getTile(r,c).isEmpty()) &&
                        (b1.getTile(r,c).hasMulti() && !b2.getTile(r,c).hasMulti()) ||
                        (!b1.getTile(r,c).hasMulti() && b2.getTile(r,c).hasMulti()) ){
                        System.out.printf("Incompatible boards: multiplier mismatch at (%d, %d)\n\n",
                            r, c);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    /**
     * @param args, if usage is correct, loads in the dictionary.txt file
     * Is also used for loading in the Tile Scores as a Map and
     * loads in the testing file and is the Entry Point for the scorechecker.jar
     */
    public static void main(String[] args) {
        /*Load in  the TileScores --> FROM: scrabble_tiles.txt*/
        String tileInfo = "/" + "scrabble_tiles.txt";
        InputStream in1 = Checker.class.getResourceAsStream(tileInfo);
        try (BufferedReader br1 = new BufferedReader(new InputStreamReader(in1))) {
            scoreMap = new ScoreMap(br1);
        } catch (Exception e) {
            System.out.println("File scrabble_tiles.txt not loaded in\n");
            e.printStackTrace();
        }
        String dictInfo = "/" + "sowpods.txt";
        if (args.length == 1) {dictInfo = args[0];}
        else {System.out.println("Usage: java -jar scorechecker.jar </'dictionary_file_.txt'>");}
        InputStream in2 = Checker.class.getResourceAsStream(dictInfo);
        try (BufferedReader br2 = new BufferedReader(new InputStreamReader(in2))){
            dict = new Dict(br2);
        }catch (Exception e){
            System.out.println("Dictionary not loaded in\n");
            e.printStackTrace();
        }
        String scoringFile = "/" + "example_score_input.txt";
        System.out.println("ENTER TESTING FILE ie '/example_score_input.txt'");
        scoringFile = new Scanner(System.in).nextLine();
        InputStream in3 = Checker.class.getResourceAsStream(scoringFile);
        try (BufferedReader br3 = new BufferedReader(new InputStreamReader(in3))) {
            scoreChecker = new Checker(br3);
        } catch (Exception e) {
            System.out.println("File score.txt not loaded in\n");
            e.printStackTrace();
        }
    }
}