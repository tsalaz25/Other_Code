/*Author:Tomas Salaz    CS351L  Domino's Project 2      Package Console
Methods for implementing the Boneyard object. Stored as a LinkedList to use
collections method shuffle.
*/
package GUI;
import java.util.*;
public class Boneyard {
    /*LinkedList for making the object */
    private LinkedList<Domino> boneyard;
    public Boneyard(int n) {
        boneyard = new LinkedList<>();
        generateBoneyard(n);
    }
    /**
     * @param n is the max that a can have.
     *
     *Algorithm Summary: Uses Nested Loops to create the LinkedList, then shuffles
     *so drawwing from the boneyard is randomized.
     */
    private void generateBoneyard (int n) {
        while (n >= 0) {
            for (int r = n; r >= 0; r--) {
                boneyard.add(new Domino(n, r));
            }
            n--;
        }
        Collections.shuffle(boneyard);
    }
    /*Getters*/
    public Domino getDom (){return boneyard.pop();}
    public LinkedList<Domino> getBoneyard() {return boneyard;}
}