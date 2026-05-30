/**
 * Author: Tomas Salaz  File: Snake.java
 * CS251L   Lab 9: Snake Game Part 1: GameManager
 *
 * Snake class for making a Snake Object. Snakes is stored using a LinkedList and
 * is given a Direction. Snake has Initialization and Game Progression Methods.
 * Snake also has methods for checking conditions like collisions and food eating.
 * */
import java.util.*;
public class Snake {
    //Member Variables for a Snake Object.
    private LinkedList<Point> Snake;
    private Dir Dir;

    /**
     * Constructor for a Snake object
     * Makes a Snake Object at a Start X and Y, with the initial Snake Length
     * */
    public Snake (int xStart, int yStart, Dir dirStart, int startLen){
        this.Snake =  new LinkedList<>();
        this.Dir = dirStart;

        for (int i = 0; i < startLen; i++){
            Snake.add(new Point(xStart,yStart));
        }
    }

    /**
     * snakeStep is used for Game Progression. In this method the Snake Head is
     * always added to the 0th index, so it is easy to Identify and the last index
     * is removed
     * */
    public void snakeStep(){
        Point snakeHead = Snake.getFirst();
        Point newHead = new Point(snakeHead.getX() + Dir.getStep()[0],
        snakeHead.getY() + Dir.getStep()[1]);

        Snake.removeLast();
        Snake.addFirst(newHead);

    }

    /**
     * ateFood is used for Game Progression. Adds the point of that the Food was
     * eaten at to the end of the Snake Object.
     * */
    public void ateFood (Food Food){
        Snake.addLast(new Point(Food.getX(),Food.getY()));
    }

    /**
     * Collision Methods all function in a Similar way. The implementation is
     * to assign a snake head and check is it has the same coords of any other
     * object.
     * */
    public boolean foodCollision(Food Food){
        boolean res = false;
        Point head = Snake.getFirst();
        if (head.equals(new Point(Food.getX(),Food.getY()))){
            res = true;
        }
        return res;
    }

    public boolean snakeCollision (){
        boolean res = false;
        Point head = Snake.getFirst();
        for (int snakeBody = 1; snakeBody < Snake.size(); snakeBody++){
            if (head.equals(Snake.get(snakeBody))){
                res = true;
            }
        }
        return res;
    }

    public boolean wallCollision(Set<Wall> Wall){
        boolean res = false;
        Point head = Snake.getFirst();
        if (Wall.contains(new Wall(head.getX(),head.getY()))){
            res = true;
        }
        return res;
    }


    //setDir Method to set the Direction
    public void setDir (Dir changeDir){
        if (!Dir.validDirChange(changeDir)){
            this.Dir = changeDir;
        }
    }

    //getSnake Method to get the list of Snake points
    public LinkedList<Point> getSnake(){
        return Snake;
    }

    /**
     * Overriding methods for equals and hashCode. Equals compare the memory
     * address of the object passed in. hashCode is used to look at both
     * coordinates to make sure it is empty using bucket locations.
     * */
    @Override
    public boolean equals (Object object){
        boolean res;
        if (this == object) {
            return true;
        } else if (object == null || getClass() !=
            object.getClass()){
            return false;
        }
        Snake newSnake = (Snake) object;
        res = (Snake.equals(newSnake.Snake) && Dir == newSnake.Dir);
        return res;
    }
    @Override
    public int hashCode(){
        return Objects.hash(Snake, Dir);
    }
}