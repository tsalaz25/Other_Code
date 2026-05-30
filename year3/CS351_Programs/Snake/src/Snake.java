/**
 * Author: Tomas Salaz  File: Snake.java
 * CS251L   Lab 10: Snake Game Part 2: GameManager
 *
 * The GameManager class is responsible for Constructing and overseeing the
 * Snake Objects in a given Board. The methods in this class are for movement
 * and Growing the Snake
 * */
import java.util.*;
public class Snake {
    //Member Variables
    private LinkedList<Point> SNAKE;
    private Direction Dir;
    private int maxLen = 3;
    private int currentParts = 0;
    public Boolean isSelfHit = false;
    //Makes a SNAKE LinkedList<Point> that also sets a random direction
    public Snake() {
        this.SNAKE = new LinkedList<>();
        Random rng = new Random();
        int rand = rng.nextInt(4);
        switch (rand){
            case 0 -> {Dir = Direction.UP;}
            case 1 -> {Dir = Direction.DOWN;}
            case 2 -> {Dir = Direction.LEFT;}
            default -> {Dir = Direction.RIGHT;}
        }
    }
    //Step Function for the Snake, Handles Incrementation and Length Control
    public void step(Direction Dir){
        Point Head = SNAKE.getFirst();
        Point nextHead = new Point(Head.getX()+Dir.getXStep(),
        Head.getY()+Dir.getYStep(), PTC.SNAKE);
        nextHead.setSymbol('0');
        SNAKE.addFirst(nextHead);
        currentParts++;
        while (currentParts > maxLen){
            SNAKE.remove(maxLen);
            currentParts--;
        }
        for (int i =0; i < currentParts; i++){
            if (i == 0){SNAKE.get(i).setSymbol('0');}
            else {SNAKE.get(i).setSymbol('o');}
        }
        checkSelfHit(nextHead);
    }
    //Sets the head, and reassigns the Character for the Head
    public void setHead (int x, int y){
        Point P = new Point(x,y,PTC.SNAKE);
        P.setSymbol('0');
        SNAKE.addFirst(P);
        currentParts++;
    }

    public void checkSelfHit(Point head){
        for (int i = 3; i < currentParts; i++){
            if (head.getX() == SNAKE.get(i).getX() && head.getY() == SNAKE.get(i).getY()){
                isSelfHit = true;
                break;
            }
        }
    }
    //Helpers, Getters and Setters
    public void grow (){
        maxLen += 2;
    }
    public void setDir(Direction D){this.Dir = D;}
    public LinkedList<Point> getSNAKE (){return SNAKE;}
    public int getXat (int i){return SNAKE.get(i).getY();}
    public int getYat (int i){return SNAKE.get(i).getY();}
    public Direction getDir(){return Dir;}
    public int getCurrentParts(){return currentParts;}
}