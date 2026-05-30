/*Author:Tomas Salaz    CS351L  Domino's Project 2      Package GUI
 Methods creating an Domino object, contains 2 fields a left and a right point
 and has a Flip Method
 */
package GUI;
public class Domino {
    /*Fields and Constructor*/
    private int right;
    private int left;
    private String pngString;
    public Domino(int right, int left) {
        this.right = right;
        this.left = left;
        pngString = left + "_" +  right + ".png";
    }
    /*Flip Method, Getters, and a toString*/
    public void flip(){
        int temp = right;
        right = left;
        left = temp;
    }
    public int getRight() {return right;}
    public int getLeft() {return left;}
    public String getPNGstring()  {return pngString;}
}