/*Author:Tomas Salaz    CS351L  Domino's Project 2      Package Console
Methods for implementing the Arena Logic in the console game. Stores as a
LinkedList to make Adding and Removal easier.*/
package GUI;
import java.util.*;
public class Arena{
    /*Fields for making an Arena Object*/
    private LinkedList<Domino> arena;
    private Domino anchor;
    private int anchorIndex;
    /*Constructor for an Arena, Makes and empty LinkedList  */
    public Arena() {arena = new LinkedList<>();}
    /*Getters and Helper Functions*/
    public int getOpenRight(){
        if (this.arena.size() == 0){
            return 0;
        } else {
            return arena.getLast().getRight();
        }
    }
    public int getOpenLeft() { return arena.getFirst().getLeft(); }
    public void addRight(Domino d){arena.addLast(d);}
    public void addLeft (Domino d){
        arena.addFirst(d);
        anchorIndex++;
    }
    public LinkedList<Domino> getArena(){return arena;}
    public int getAnchorIndex(){return anchorIndex;}
}