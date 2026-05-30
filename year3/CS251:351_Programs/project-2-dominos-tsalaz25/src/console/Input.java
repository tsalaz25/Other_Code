/*Author:Tomas Salaz    CS351L  Domino's Project 2      Package Console
Methods for implementing methods for Getting Input from the user for the Console
version of the Game. Uses a Scanner, Scanner Methods.
*/
package console;
import java.util.*;
public class Input {
    /*Field and Constructor*/
    Scanner s;
    public Input() {s = new Scanner(System.in);}
    /**
     * Prompts the user to make a play, and takes in the char. Has Error handling
     * built into method.
     *
     * @return the char that represent what the player wants to do 'p,d,q'
     */
    public char getHumanPlay() {
        System.out.println("[p] Play Domino\n[d] Draw from Boneyard\n[q] Quit");
        String str = s.nextLine();
        if (str.length() == 1 && (str.equals("p") || str.equals("d"))) {
            System.out.println("Which Domino?");
            return str.charAt(0);
        } else if (str.equals("q")) {
            System.out.println("Enter '0' to Quit");
            return 'q';
        }else {
            System.out.println("Invalid Input\n");
            return getHumanPlay();
        }
    }
    /**
     * @param i Max index the player can play, passed in from the Manager
     * @return the index that the user wants to play if the index is valid
     */
    public int getHumanDomIndex(int i){
        String str = s.nextLine();
        if (str.length() == 1 && Integer.parseInt(str) < i){
            return Integer.parseInt(str);
        } else {
            System.out.println("Invalid Input, enter Domino staring from 0");
            return getHumanDomIndex(i);
        }
    }
}