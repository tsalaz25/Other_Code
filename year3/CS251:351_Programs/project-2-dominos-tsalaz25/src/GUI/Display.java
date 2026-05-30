/*Author:Tomas Salaz    CS351L  Domino's Project 2      Package GUI
Display class for playing the game. Handles the GUI elemnts and the use Input.
MouseEvents are the only handlers in the game, also contains the GameLoop and the
Main method which is the entry point for the program
* */
package GUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;
public class Display extends Application {
    /*Fields*/
    Manager M = new Manager(6);
    Canvas arena;
    Canvas hand;
    BorderPane root;
    /*Start method for creating the GUI display and the GameLoop see Line 44 */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            root = new BorderPane();
            arena = new Canvas(1500, 250);
            hand = new Canvas(1500, 150);
            root.setTop(updateInfo());
            root.setCenter(arena);
            root.setBottom(hand);
            Scene scene = new Scene(root, 1500, 500);
            primaryStage.setTitle("D O M I N O S");
            primaryStage.setScene(scene);
            primaryStage.show();
            GraphicsContext arenaGC = arena.getGraphicsContext2D();
            GraphicsContext handGC = hand.getGraphicsContext2D();
            /*Initial Game State*/
            updateArena(arenaGC);
            updateHand(handGC);
            /*Braking in the Game Loop*/
            //gameLoop(arenaGC, handGC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*Called in the start method , is Broken. Is supposed to use the same structure
    * as teh CLI MainLoop, and repaint teh GUI as the game pregresses, The game works,
    * but the GUI never update*/
    private void gameLoop (GraphicsContext arenaGC, GraphicsContext handGC) {
        if (M.HumanTurn){
            if (M.isHumanMoveAvail()){
                hand.setOnMouseClicked(event -> {
                    int x = (int)event.getX()/110;
                    M.humanMove(x);
                    updateArena(arenaGC);
                    updateHand(handGC);
                });
                gameLoop(arenaGC,handGC);
                root.setTop(updateInfo());
            } else {
                M.giveDomToHuman();
                updateHand(handGC);
                gameLoop(arenaGC,handGC);
                root.setTop(updateInfo());
            }
        } else {
            M.makeCompMove();
            updateArena(arenaGC);
            updateHand(handGC);
            gameLoop(arenaGC, handGC);
            root.setTop(updateInfo());
        }
        if (M.isGameOver){
            Label l = new Label("GAME OVER");
            root.getChildren().clear();
            root.setTop(l);
            if (M.getHuman().getHandSum() < M.getComp().getHandSum()){
                Label winner = new Label("YOU WIN");
                root.setCenter(winner);
            } else if(M.getHuman().getHandSum() > M.getComp().getHandSum()){
                Label winner = new Label("COMPUTER WINS");
                root.setCenter(winner);
            }else {
                Label winner = new Label("DRAW");
                root.setCenter(winner);
            }
        }
    }

    /**
     * Updates the OthrtInfo HBox that shows the Boneyard and Computers amount
     * of Domino's
     */
    private HBox updateInfo (){
        HBox info = new HBox();
        info.setPrefSize(1500, 100);
        info.setStyle("-fx-background-color: Black");
        Label l = new Label(
            "\tComputer has " + M.getCompSize() + " Domino's\t" +
                "\tBoneyard has " + M.getBoneyardSize() + " Domino's");
        l.setStyle("-fx-font-size: 48px; -fx-text-fill: white");
        info.getChildren().add(l);
        return info;
    }
    /*Repaints the arena, works, was tested with multiple static arena
    * Uses a similar structure ot the Arena,getString in the CLI version of the
    * game, is more complex do to the use of PNG's and flipping them based on how
    * the arena is structures at a given moment.
    * */
    private void updateArena(GraphicsContext gc) {
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, 1500, 250);
        LinkedList<Domino> A = M.getArenaManager().getArena();
        final int TopRow = 50;
        final int BotRow = 150;
        int topCol = 0;
        int botCol = 0;
        int anchor = M.getArenaManager().getAnchorIndex();
        if (anchor % 2 == 0) {
            gc.setFill(Color.TRANSPARENT);
            gc.fillRect(0, 150, 50, 50);
            botCol += 50;
        } else {
            gc.fillRect(0, 50, 50, 50);
            topCol += 50;
        }
        int size = A.size();
        for (int i = 0; i < size; i++) {
            Domino D = M.getArenaManager().getArena().get(i);
            if (anchor % 2 == 0) { /*Even Index on Top*/
                if (i % 2 == 0 && D.getLeft() <= D.getRight()) {
                    Image img = new Image(getClass().getResourceAsStream(
                        "/rsc/" + D.getPNGstring()));
                    gc.drawImage(img, TopRow, topCol,100 ,50);
                    topCol += 50;
                } else if (i % 2 != 0 && D.getLeft() <= D.getRight()) {
                    Image img = new Image(getClass().getResourceAsStream(
                        "/rsc/" + D.getPNGstring()));
                    gc.drawImage(img, BotRow, botCol,100,50);
                    botCol += 50;
                } else if (i % 2 == 0 && D.getLeft() >= D.getRight()) {
                    D.flip();
                    Image img = new Image(getClass().getResourceAsStream(
                        "/rsc/" + D.getPNGstring()));
                    gc.save();
                    gc.translate(TopRow + 25, topCol + 50);
                    gc.rotate(180);
                    gc.drawImage(img, TopRow, topCol,100,50);
                    gc.restore();
                    topCol += 50;
                } else if (i % 2 != 0 && D.getLeft() >= D.getRight()) {
                    D.flip();
                    Image img = new Image(getClass().getResourceAsStream(
                        "/rsc/" + D.getPNGstring()));
                    gc.save();
                    gc.translate(BotRow + 25, botCol + 50);
                    gc.rotate(180);
                    gc.drawImage(img, BotRow, botCol,100,50);
                    gc.restore();
                    botCol += 50;
                }
            } else { /*Odd Index on Top*/
                if (i % 2 == 0 && D.getLeft() <= D.getRight()) {
                    Image img = new Image(getClass().getResourceAsStream(
                        "/rsc/" + D.getPNGstring()));
                    gc.drawImage(img, BotRow, botCol,100,50);
                    botCol += 50;
                } else if (i % 2 != 0 && D.getLeft() <= D.getRight()) {
                    Image img = new Image(getClass().getResourceAsStream(
                        "/rsc/" + D.getPNGstring()));
                    gc.drawImage(img, TopRow, topCol,100,50);
                    topCol += 50;
                } else if (i % 2 == 0 && D.getLeft() >= D.getRight()) {
                    D.flip();
                    Image img = new Image(getClass().getResourceAsStream(
                        "/rsc/" + D.getPNGstring()));
                    gc.save();
                    gc.translate(BotRow + 25, botCol + 50);
                    gc.rotate(180);
                    gc.drawImage(img, BotRow, botCol,100,50);
                    gc.restore();
                    botCol += 50;
                } else if (i % 2 != 0 && D.getLeft() >= D.getRight()) {
                    D.flip();
                    Image img = new Image(getClass().getResourceAsStream(
                        "/rsc/" + D.getPNGstring()));
                    gc.save();
                    gc.translate(TopRow + 25, topCol + 50);
                    gc.rotate(180);
                    gc.drawImage(img, TopRow, topCol,100,50);
                    gc.restore();
                    topCol += 50;
                }
            }
        }
    }

    /* Repaints the graphics for the current hand that the player has. This will
    show in teh GUI since the game has been initialized and a hand exists.
     */
    private void updateHand(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0,0,1500,150);
        final int row = 75/2;
        int col = 10;
        LinkedList<Domino> H = M.getHuman().getHand();
        for (Domino D: H){
            System.out.println("/rsc/" + D.getPNGstring());
            Image img = new Image(getClass().getResourceAsStream(
                "/rsc/" + D.getPNGstring() ));
            gc.drawImage(img,col,row,100,50 );
            gc.save();
            col+=110;
        }
    }
    /*Main method, entry point of the Game*/
    public static void Main(String[] args) throws Exception {
        launch(args);
    }
}