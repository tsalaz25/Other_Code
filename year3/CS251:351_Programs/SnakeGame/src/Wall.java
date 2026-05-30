/**
 * Author: Tomas Salaz  File: Wall.java
 * CS251L   Lab 9: Snake Game Part 1: GameManager
 *
 * Wall class for creating a Wall Object. Wall objects are stored into a LinkedList
 * in 'GameManager.java' file. Wall objects are basically point objects with a name.
 * */

public class Wall {
    public int x;
    public int y;

    public Wall (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){return x;}
    public int getY(){return y;}

    /**
     * Overrides Equals, so we can compare the memory addresses of objects
     * */
    @Override
    public boolean equals(Object object){
        boolean res;
        if (this == object) {
            return true;
        } else if (object == null || getClass() !=
            object.getClass()){
            return false;
        }
        Wall newWall = (Wall)object;
        res = (x == newWall.x && y == newWall.y);
        return res;
    }
}
