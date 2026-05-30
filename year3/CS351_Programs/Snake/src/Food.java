/**
 * Author: Tomas Salaz  File: Food.java
 * CS251L   Lab 10: Snake Game Part 2: GameManager
 *
 * Small class for making Food object. Basically just a named Point object
 */
public class Food extends Point{
    public Food (int x, int y,PTC type){
        super(x,y,type);
    }
    //Coordinate getters
    public int getX(){return super.getX();}
    public int getY() {return super.getY();}
    public char getSymbol() {return super.getSymbol();}
}