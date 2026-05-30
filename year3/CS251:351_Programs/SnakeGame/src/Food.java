/**
 * Author: Tomas Salaz  File: Food.java
 * CS251L   Lab 9: Snake Game Part 1: GameManager
 *
 * Food class for creating a Food instance. Food is a basically a Special Point
 * that is used for progressing the Game.
 * */

public class Food {
    private int x;
    private int y;

    public Food (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){return x;}
    public int getY(){return y;}
}
