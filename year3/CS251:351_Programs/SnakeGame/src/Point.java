/**
 * Author: Tomas Salaz  File: Point.java
 * CS251L   Lab 9: Snake Game Part 1: GameManager
 *
 * Makes a Point Object. Used in the implementation of Snake and Wall objects.
 * Is a Better alternative to using and int[2]
 * */

public class Point {
    private int x;
    private int y;

    public Point (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){return x;}
    public int getY() {return y;}
}
