/* BoardGUI.java  Project 3: Scrabble
Author: Tomas Salaz
Description: Unfinished. Is used for making the GUI of the board. GUI would
 contain the Tiles as buttons.
 The only use for gameplay would be to click the starting Coordinate of the word
 the human was going to play and the Direction, then implementing an update
 method based on the current state of the game.
*/
package Scrabble;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
public class BoardGUI {
    private GridPane boardPane;
    private Board board;

    /**
     * @param board the Current state of the board during the game
     */
    public BoardGUI(Board board) {
        this.board = board;
        boardPane = new GridPane();
        intiBoardPane();
    }
    /**
     * Adds the Tiles, as Buttons, to the GridPane.
     */
    private void intiBoardPane() {
        int dim = board.dim;
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                Tile t = board.getTile(x, y);
                Button button = new Button();
                button.setMinSize(30,30);
                int finalX = x;
                int finalY = y;
                button.setOnAction(event -> handleTileClick(finalX, finalY));
                if (t.isEmpty()){
                    if (t.hasMulti()){
                        switch (t.getMultiType()) {
                            case 'W' -> {
                                button.setText("x"+t.getMultiplier());
                                button.setStyle("-fx-background-color: red; -fx-text-fill: black;" +
                                "-fx-border-color: white");
                            }
                            case 'L' -> {
                                button.setText("x"+t.getMultiplier());
                                button.setStyle("-fx-background-color: hotpink; -fx-text-fill: black;"
                                + "-fx-border-color: white");
                            }
                        }
                    } else {
                        button.setStyle("-fx-background-color: darkgray; -fx-border-color: white");
                    }
                } else {
                    button.setText(t.getLetter());
                    button.setStyle("-fx-background-color: sandybrown; -fx-text-fill: black;"
                    + "-fx-border-color: white");
                }
                boardPane.add(button, x, y);
            }
        }
    }
    /**
     * @param x the X coordinate for the Button
     * @param y the Y coordinate for the Button
     * Has the functionality and would be used to access the coords for the start
     * of a play
     */
    private void handleTileClick(int x, int y) {
        System.out.println("Tile clicked at: " + x + ", " + y);
    }
    /*Get the GridPane to put on the GUI*/
    public GridPane getBoardPane() {
        return boardPane;
    }
}