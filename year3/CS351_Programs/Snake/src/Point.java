/**
 * Author: Tomas Salaz  File: Point.java
 * CS251L   Lab 10: Snake Game Part 2: GameManager
 *
 * Small Class for making point objects, Stores X and Y coordinates and has the
 * ability to assign a Character Symbol
 */
public class Point {
    //Member Variables
    int x;
    int y;
    char c;
    PTC type;
    //Constuctor
    public Point (int x, int y, PTC type){
        this.x = x;
        this.y = y;
        this.type = type;
        switch (type){
            case WALL -> {this.c = 'X';}
            case FOOD -> {this.c = '*';}
            case SNAKE -> {this.c = 'o';}
            case NONE -> {this.c = '.';}
            default -> {this.c = '!';}
        }
    }
    //Setter and Getters
    public char getSymbol() {return c;}
    public int getX(){return x;}
    public int getY(){return y;}
    public void setType (PTC type){this.type = type;}
    public void setSymbol (char c){this.c = c;}
}