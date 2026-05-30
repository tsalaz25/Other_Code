/* Board.java  Project 3: Scrabble
Author: Tomas Salaz
Description: Creates a Board object that is used in all Packages. Board is
stored as a 2D-Array of Tile objects, see Tile.java in same package. Has an
overloaded constructor
*/
package Scrabble;
import java.io.*;
import java.util.*;
public class Board {
    /*Global Variables*/
    Tile[][] board;
    LinkedList<int[]> anchors;
    public int dim;
    /**
     * Constructor #1
     * @param br Reader to read in a board configuration. Used in ScoreChecker and
     *           Solver
     * @param scoreMap is the Map used for assigning point values to the tiles
     *                 as the board is constructed
     * @throws IOException if the reader never reaches the end
     */
    public Board(BufferedReader br, ScoreMap scoreMap) throws IOException {
        String line = br.readLine();
        this.dim = Integer.parseInt(line);
        this.board = new Tile[dim][dim];
        this.anchors = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line + ' ');
        }
        configBoard(sb.toString(), scoreMap);
    }
    /**
     *Constructor #2
     * @param dim makes and empty board but assigns the dimensions
     */
    public Board(int dim) {
        this.dim = dim;
        board = new Tile[dim][dim];
        anchors = new LinkedList<>();
    }
    /**
     * @param s is the string representation of the board from a configuration
     *          file, is used to make tiles and assign Tiles to a Coords
     * @param scoreMap is used to assign the scores of every tile
     */
    public void configBoard(String s, ScoreMap scoreMap) {
        String[] in = s.split(" +");
        LinkedList<int[]> addAnchors = new LinkedList<>();
        int index = 0;
        while (in[index].length() == 0) {
            index++;
        }
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (in[index].length() == 2) {
                    board[r][c] = new Tile(r, c, in[index], 0);
                } else if (in[index].length() == 1) {
                    board[r][c] = new Tile(r, c, in[index], scoreMap.getScore(in[index].toLowerCase()));
                    addAnchors.add(new int[]{r, c});
                } else {
                    System.out.println("No Score Assigned");
                }
                index++;
            }
        }
        if (addAnchors.size() == 0) {
            anchors.add(new int[]{dim / 2, dim / 2});
        } else {
            anchors = updateAnchors(anchors, addAnchors);
        }
    }
    /**
     * @param anchors List<int[]> is the coords of the anchors that are already
     *                assigned to the current board
     * @param addAnchors List<int[]> is the coords of the anchors to add to the
     *                   board after a play has been made
     * @return a List<int[]> that is the new list of anchors
     */
    private LinkedList<int[]> updateAnchors(LinkedList<int[]> anchors, LinkedList<int[]> addAnchors) {
        LinkedList<int[]> newAnchors = anchors;
        for (int[] a : addAnchors) {
            if (anchors.contains(a)) {
                newAnchors.remove(a);
            } else {
                int row = a[0];
                int col = a[1];
                if (row - 1 >= 0 && (board[row - 1][col].isEmpty())) {
                    newAnchors.add(new int[]{row - 1, col});
                }
                if (row + 1 <= dim - 1 && (board[row + 1][col].isEmpty())) {
                    newAnchors.add(new int[]{row + 1, col});
                }
                if (col - 1 >= 0 && (board[row][col - 1].isEmpty())) {
                    newAnchors.add(new int[]{row, col - 1});
                }
                if (col + 1 <= dim - 1 && (board[row][col + 1].isEmpty())) {
                    newAnchors.add(new int[]{row, col + 1});
                }
            }
        }
        for (int i = 0; i < newAnchors.size(); i++) {
            int[] a = newAnchors.get(i);
            for (int j = i+1; j < newAnchors.size(); j++) {
                int[] b = newAnchors.get(j);
                if (a[0] == b[0] && a[1] == b[1]) {
                    newAnchors.remove(j);
                }
            }
        }
        return newAnchors;
    }
    /**
     * @return the String representation of the current Board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j].getLetter().length() == 2) {
                    sb.append(board[i][j].getLetter() + " ");
                } else {
                    sb.append(" " + board[i][j].getLetter() + " ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    /**
     * @return the String representation of the Board with the point values of
     * every tile
     */
    public String toStringVerbose() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j].getLetter().length() == 1) {
                    sb.append(' ' + board[i][j].getLetter() + "[" + board[i][j].getScore() + "] ");
                } else {
                    sb.append(board[i][j].getLetter() + "[" + board[i][j].getScore() + "] ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public Tile getTile(int x, int y) {
        return board[x][y];
    }
    public LinkedList<int[]> getAnchors() {
        return anchors;
    }

    /**
     * @param x Starting X coord of a New Play
     * @param y Starting Y coord os a New Play
     * @param word the Word that is being updated onto the Board
     * @param horizontal Boolean for the direction of the play
     * @param scoreMap Map for assigning the point values when updating the board
     */
    public void update (int x, int y, String word, boolean horizontal,ScoreMap scoreMap){
        int newLen = word.length();
        LinkedList addAnchors = new LinkedList();
        for (int i = 0; i < newLen; i++) {
            if (horizontal){
                board[x][y+i] = new Tile(x, y+i, String.valueOf(word.charAt(i)),scoreMap.getScore(String.valueOf(word.charAt(i))));
                addAnchors.add(new int[]{x, y+i});
            } else {
                board[x+i][y] = new Tile(x, y+i, String.valueOf(word.charAt(i)),scoreMap.getScore(String.valueOf(word.charAt(i))));
                addAnchors.add(new int[]{x+i, y});
            }
        }
        updateAnchors(anchors, addAnchors);
    }
}