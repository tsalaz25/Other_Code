/*Author:Tomas Salaz    CS351L  Domino's Project 2      Package GUI
* Player Object for keeping track of the players hands, stored as LinkedLists
so remove, addFirst and addLast methods can be used in other classes for easy
modification
* */
package GUI;
import java.util.*;
public class Player {
    /*Fields and Constructor*/
    private LinkedList<Domino> hand;
    boolean isComputerPlayer = false;
    public Player(char c){
        if (c == 'h') {
            hand = new LinkedList<>();
        }else if (c == 'c') {
            hand = new LinkedList<>();
            isComputerPlayer = true;
        }
    }
    /*Getters and Helper Method, all really simple*/
    public LinkedList<Domino> getHand(){return hand;}
    public int getHandSum(){
        int sum = 0;
        for (Domino D: hand){
            int DS = D.getLeft() + D.getRight();
            sum += DS;
        }
        return sum;
    }
    public void addToHand (Domino D){hand.add(D);}
}