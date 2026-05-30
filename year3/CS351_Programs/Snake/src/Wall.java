/**
 * Author: Tomas Salaz  File: Wall.java
 * CS251L   Lab 10: Snake Game Part 2: GameManager
 *
 * Small Class for making a Wall Object. Extend the Point Class since a wall is
 * a point on the board.
 */
public class Wall extends Point{
    //Constructor for making a Wall object
    public Wall (int x, int y, PTC type){
        super(x,y,type);
    }
    public int getX() {return super.getX();}
    public int getY() {return super.getY();}
    public char getSymbol() {return super.getSymbol();}
}