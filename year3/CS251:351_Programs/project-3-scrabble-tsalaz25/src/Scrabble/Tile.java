/* Tile.java  Project 3: Scrabble
Author: Tomas Salaz
Description: Tile objects that are used in the Scrabble Game. All tiles are
made using this structure, regardless of if the tile is full or empty
*/
package Scrabble;
public class Tile {
    /*Global Variables*/
    private int row,col, multiplier, score;
    private String letter;
    private char multiType;
    private boolean isEmpty, hasMulti, hasAdj;
    /**
     * @param row the tile is played at
     * @param col the tile is played at
     * @param letter the letter/value of the current tile
     * @param score the score of the tile
     */
    public Tile (int row , int col, String letter, int score){
        this.row = row;
        this.col = col;
        this.letter = letter;
        this.score = score;
        this.hasAdj = false;
        switch(letter.length()){
            case 1 -> {
                this.isEmpty = false;
                this.hasMulti = false;
                this.multiplier = 1;
                this.multiType = 'N';
            }
            case 2 -> {
                this.isEmpty = true;
                if (letter.equals("..")){
                    this.hasMulti = false;
                    this.multiType = 'N';
                    this.multiplier = 1;
                } else if (letter.charAt(0) == '.'){
                    this.multiType = 'L';
                    this.hasMulti = true;
                    multiplier = Integer.parseInt(letter.substring(1));
                }else if(letter.charAt(1) == '.'){
                    this.multiType = 'W';
                    hasMulti = true;
                    multiplier = Integer.parseInt(String.valueOf(letter.charAt(0)));
                } else {System.out.println("Tile Erred");}
            }
            default -> System.out.println("Invalid Tile Args");
        }
    }
    /*Setters and Getters for the Tile Objects*/
    public int getRow() {return row;}
    public int getCol() {return col;}
    public String getLetter() {return letter;}
    public char getMultiType() {return multiType;}
    public int getMultiplier() {return multiplier;}
    public int getScore() {return score;}
    public boolean hasMulti() {return hasMulti;}
    public boolean isEmpty() {return isEmpty;}
    public boolean hasAdj() {return hasAdj;}
    public void setAdj(boolean b) {this.hasAdj = b;}
}