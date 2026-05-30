/*Author:Tomas Salaz    CS351L  Domino's Project 2      Package Console
 Methods creating an Domino object, contains 2 fields a left and a right point
 and has a Flip Method
 */
package console;
public class Domino {
    /*Fields and Constructor*/
    private int right;
    private int left;
    public Domino(int left, int right) {
        this.right = right;
        this.left = left;
    }
    /*Flip Method, Getters, and a toString*/
    public void flip(){
        int temp = right;
        right = left;
        left = temp;
    }
    public int getRight() {return right;}
    public int getLeft() {return left;}
    public String toString() {return "[" + left + " " + right + "]";}
}