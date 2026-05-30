/* TrayGUI.java  Project 3: Scrabble
Author: Tomas Salaz
Description: Class for making the Tray representation in the GUI.
Is unfinished but the Clicking functions work, In the Game This would be used to
click tiles in order to form a word, and have a button for when the word is finished
*/
package Scrabble;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
public class TrayGUI {
    /*Global Variables*/
    private HBox trayPane;
    private char[] letters;
    /**
     * Creating a new Tray GUI, In a fininished implemntatinos, it would take in
     * a Tray Object and use those tiles
     */
    public TrayGUI() {
        trayPane = new HBox(10);
        letters = new char[]{'a','b','c','d','e','f','g'};
        initTray();
    }
    /**
     *Creates the GUI representation as a HBox
     */
    private void initTray() {
        for (char c : letters) {
            Button button = new Button(Character.toString(c));
            button.setMinSize(30,30);
            button.setOnAction(event -> handleTrayClick(c));
            button.setText(String.valueOf(c));
            button.setStyle("-fx-background-color: sandybrown; -fx-text-fill: black;"
                + "-fx-border-color: white");
            trayPane.getChildren().add(button);
        }
    }
    /**
     * @param c the character that is clicked on, would be used to construct the
     *          string representation of the word that is going to be played
     */
    public void handleTrayClick(char c) {
        System.out.println("Tray clicked: " + c);
    }
    /**
     * @return the HBox to put onto the main Stage of the GUI
     */
    public HBox getTrayPane() {
        return trayPane;
    }
}
