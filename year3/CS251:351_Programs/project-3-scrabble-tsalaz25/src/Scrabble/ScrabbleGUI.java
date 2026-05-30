package Scrabble;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
public class ScrabbleGUI extends Application {
    private static Board board;
    private static ScoreMap scoreMap;
    private static Dict dictionary;
    /**
     * @param primaryStage the primary stage for the Scrabble GUI.
     * @throws Exception
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        BoardGUI boardView = new BoardGUI(board);
        TrayGUI trayView = new TrayGUI();
        root.setCenter(boardView.getBoardPane());
        root.setBottom(trayView.getTrayPane());
        Scene scene = new Scene(root,450,500);
        primaryStage.setTitle("Scrabble");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args takes in no args
     * Is the entry point for scrabble.jar file. No Arguments, loads in the
     * basic scrabble Board and Tiles, and uses the sowpods dicitonary
     */
    public static void main(String[] args) {
        InputStream tileIn = ScrabbleGUI.class.getResourceAsStream( "/scrabble_tiles.txt");
        InputStream boardIn = ScrabbleGUI.class.getResourceAsStream("/scrabble_board.txt");
        InputStream dictionaryIn = ScrabbleGUI.class.getResourceAsStream("/sowpods.txt");
        try {
            BufferedReader tileBR = new BufferedReader(new InputStreamReader(tileIn));
            BufferedReader boardBR = new BufferedReader(new InputStreamReader(boardIn));
            BufferedReader dictionaryBR = new BufferedReader(new InputStreamReader(dictionaryIn));
            scoreMap = new ScoreMap(tileBR);
            dictionary = new Dict(dictionaryBR);
            board = new Board(boardBR, scoreMap);
        } catch (Exception e){
            System.out.println("Setup Failed");
            e.printStackTrace();
        }
        launch(args);
    }
}
