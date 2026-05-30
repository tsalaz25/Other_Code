/**
 * Author: Tomas Salaz  File: GameManager.java
 * CS251L   Lab 10: Snake Game Part 2: GameManager
 *
 * The GameManager class is responsible for Constructing and overseeing the
 * objects within the Snake Game. This Class Stores a BOARD in a
 * LinkedList<Points> to keep track of the objects. This Class also initialized
 * other custom objects
 * */
import java.util.*;
public class GameManager {
    //Member Variables
    private final int width;
    private final int height;
    private FileGetter File;
    Random rng = new Random();
    PTC type = PTC.NONE;
    private LinkedList<Point> BOARD = new LinkedList<>();
    private final LinkedList<Wall> WALLS = new LinkedList<>();
    private Snake SNAKE;
    private Food FOOD;
    public int Score = 0;
    public boolean gameOver = false;
    /**
     * Game Manager Constructor
     * */
    public GameManager(FileGetter F) {
        if (F.S != null){
            width = F.getWidth();
            height = F.getHeight();
            File = F;
            SNAKE = new Snake();
            StartGame();
        } else {
            width = F.getWidth();
            height = F.getHeight();
            File = F;
            SNAKE = new Snake();
            StartGame();
        }
    }
    /**
     * 2 Helper Methods both for starting the Game
     * */
    public void StartGame() {
        setWalls(File.getWallsIn());
        setSnake();
        addFood();
    }
    /**
     * Step Function, Keeps track of Food, Snake Object, and the Walls on the
     * BOARD. Print statement for Checking Direction
     * */
    public void step(Direction nextDir) {
        if (SNAKE.getDir().validDirChange(nextDir)){
            BOARD.removeAll(SNAKE.getSNAKE());
            SNAKE.setDir(nextDir);
            SNAKE.step(nextDir);
            BOARD.addAll(SNAKE.getSNAKE());
            if (isCollision()){collisionHandler(type);}
        } else {
            SNAKE.setDir(SNAKE.getDir());
        }
    }
    /**
     * Helper method for Getting a Boolean Value for Collisions. Also sets the
     * type of Collision to get next step of game progression
     * */
    public boolean isCollision() {
        Point Head = SNAKE.getSNAKE().get(0);
        for (Point W: WALLS){
            if (Head.getX() == W.getX() && Head.getY()==W.getY()){
                this.type = PTC.WALL;
                return true;
            }
        }
        for (int i =3 ;i < SNAKE.getCurrentParts(); i++){
            if (Head.getX()==SNAKE.getXat(i) && Head.getY()==SNAKE.getYat(i)){
                this.type = PTC.SNAKE;
                return true;
            }
        }
        if (Head.getX() == FOOD.getX() && Head.getY()==FOOD.getY()) {
            this.type = PTC.FOOD;
            return true;
        } else {
            this.type = PTC.NONE;
            return false;
        }
    }
    /**
     * Collision Handler is a Helper Method used for Game Progression.
     * */
    public void collisionHandler(PTC C) {
        switch (C) {
            case FOOD -> {
                BOARD.remove(FOOD);
                Score+=5;
                SNAKE.grow();
                addFood();
            }
            case WALL -> {
                gameOver = true;
            }
            case SNAKE -> {
                if (SNAKE.isSelfHit){
                    //gameOver = true;<-- If uncommented snake game will stop
                }
            }
            default -> {
                step(SNAKE.getDir());
            }
        }
    }
    /**
     * Sets the Walls for all Games, rather that be default walls of a File
     * Configuration
     * */
    public void setWalls(LinkedList<int[]> wallCoords) {
        for (int[] Arr : wallCoords) {
            int x1 = Arr[0];
            int x2 = Arr[1];
            int y1 = Arr[2];
            int y2 = Arr[3];
            for (int y = y1; y <= y2; y++) {
                for (int x = x1; x <= x2; x++) {
                    WALLS.add(new Wall(x,y,PTC.WALL));
                }
            }
            BOARD.addAll(WALLS);
        }
    }
    //addFood to add Food for Game initialization and progression
    public void addFood() {
        boolean open =  false;
        while (!open){
            int x = rng.nextInt(width);
            int y = rng.nextInt(height);
            boolean isWall = false;
            boolean isSnake = false;
            for (Point W: WALLS){
                if ((x == W.getX() && y == W.getY())){
                    isWall = true;
                }
            }
            for (Point S: SNAKE.getSNAKE()){
                if (x== S.getX() && y == S.getY()){
                    isSnake =  true;
                }
            }
            if (!isWall && !isSnake){
                this.FOOD = new Food(x,y,PTC.FOOD);
                BOARD.add(FOOD);
                open = true;
            }
        }
    }
    //Sets the Snake on the Board, for initialization
    public void setSnake(){
        boolean open = false;
        while (!open){
            int x = rng.nextInt(width);
            int y = rng.nextInt(height);
            for (Point P: WALLS){
                if (x != P.getX() ||  y != P.getY()){
                    SNAKE.setHead(x,y);
                    BOARD.addAll(SNAKE.getSNAKE());
                    open = true;
                }
            }
        }
    }
    //String Representation of the Current Board
    @Override
    public String toString() {
        StringBuilder SB = new StringBuilder();
        //Making Board Array with the Empty Char-Rep
        char[][] BoardArray = new char[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                BoardArray[x][y] = '.';
            }
        }
        //Gets all the Points in the Board[][]
        for (Point P : BOARD) {
            int x = P.getX();
            int y = P.getY();
            char c = P.getSymbol();
            BoardArray[x][y] = c;
        }
        //Loops through the updates Board[][] and adds everything to a String
        for (int y = 0; y < BoardArray[0].length; y++) {
            for (int x = 0; x < BoardArray.length; x++) {
                SB.append(BoardArray[x][y]);
            }
            SB.append('\n');
        }
        return SB.toString();
    }
    //For GUI Implementation
    public int getHeight() {return height;}
    public int getWidth() {return width;}
    public int getScore(){return Score;}
    public LinkedList<Point> getBOARD() {return BOARD;}
}