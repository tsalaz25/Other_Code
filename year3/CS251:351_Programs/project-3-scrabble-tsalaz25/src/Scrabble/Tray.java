/* Tray.java  Project 3: Scrabble
Author: Tomas Salaz
Description: Object for making a Tray or a Hand for the Sctabble Game
*/
package Scrabble;
import java.util.*;
public class Tray {
    /*Global Variables*/
    private ArrayList<Tile> tray;
    private ScoreMap score;
    /**
     * @param s String of the Letters a Player has in their tray
     * @param score the map for assigning score to teh Tiles in the Tray
     */
    public Tray (String s,ScoreMap score){
        this.score = score;
        tray = new ArrayList<>();
        char[] letters = s.toCharArray();
        for (int i = 0; i < letters.length; i++){
            tray.add(new Tile(-1,-1,
            String.valueOf(letters[i]), score.getScore(String.valueOf(letters[i]))));
        }
    }
    /**
     * @param tray Array List of tiles that are in a players string hand
     * @param score Map, just to make all the constructors the same
     */
    public Tray (ArrayList<Tile> tray, ScoreMap score){
        this.score = score;
        this.tray = new ArrayList<>();
        this.tray.addAll(tray);
    }
    /**
     * @return teh current tray for a player
     */
    public ArrayList<Tile> getTray(){return tray;}
    /**
     * @param s the string of Tile(s) to add to a players tray
     */
    public void addToTray(String s){
        if (tray.size()<7) {
            tray.add(new Tile(-1,-1,s,score.getScore(s)));
        }
    }
    /**
     * @param t a tile to add to a tray
     */
    public void addToTray(Tile t){
        tray.add(t);
    }

    /**
     * @return a string of the tiles in a tray
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Tile s: tray){
            sb.append(s.getLetter());

        }
        return sb.toString();
    }
    /**
     * @param score the score map that we want to assign to keep the scoring consistent
     */
    public void setScore(ScoreMap score) {
        this.score = score;
    }
}