/*Author:Tomas Salaz    CS351L  Domino's Project 2      Package Console
Methods for implementing the Arena Logic in the console game. Stores as a
LinkedList to make Adding and Removal easier.*/
package console;
import java.util.*;
public class Arena {
    /*Fields for making an Arena Object*/
    private LinkedList<Domino> arena;
    private Domino anchor;
    private int anchorIndex;
    /*Constructor for an Arena, Makes and empty LinkedList  */
    public Arena() {arena = new LinkedList<>();}
    /**
     * @param d: 1st Domino when making an Arena
     * Saves the 1st domino in the LinkedList to create an accurate String Rep
     */
    public void setAnchorDom(Domino d) {
        anchor = d;
        anchorIndex = 0;
    }
    /*Getters and Helper Functions*/
    public int getOpenRight(){ return arena.getLast().getRight(); }
    public int getOpenLeft() { return arena.getFirst().getLeft(); }
    public void addRight(Domino d){arena.addLast(d);}
    public void addLeft (Domino d){
        arena.addFirst(d);
        anchorIndex++;
    }
    public LinkedList<Domino> getArena(){return arena;}
    /**
     * Algorithm Summary: Uses AnchorIndex and 2 StringBuilders.
     * If anchor is even , add space to bottom, evens to top.
     * If anchor is odd, add space to top, add evens to bottom.
     *
     * @returns the String that is the representation for the Arena
     */
    public String getArenaString(){
        StringBuilder top = new StringBuilder();
        StringBuilder bot = new StringBuilder();
        /*Stagger Handling*/
        if (anchorIndex%2 == 0){
            bot.append("  ");
            for (int i = 0; i < arena.size(); i++){
                if (i%2==0){
                    top.append(arena.get(i).toString());
                } else {
                    bot.append(arena.get(i).toString());
                }
            }
        } else if (anchorIndex%2 == 1){
            top.append("  ");
            for (int i = 0; i < arena.size(); i++){
                if (i%2==0){
                    bot.append(arena.get(i).toString());
                } else {
                    top.append(arena.get(i).toString());
                }
            }
        }
        return top.toString() + "\n" + bot.toString();
    }
}