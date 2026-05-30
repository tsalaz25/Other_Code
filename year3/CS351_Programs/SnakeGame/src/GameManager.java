/**
 * Author: Tomas Salaz  File: GameManger.java
 * CS251L   Lab 9: Snake Game Part 1: GameManager
 *
 * The Game Manager class handles the Actions of the Game (Initialization,
 * Incrementation, and General Game Progression). Overrides toString to print
 * current game status to the console. Uses LinkedLists of custom Objects for
 * tracking.
 * */
import java.util.*;
import java.io.*;
public class GameManager {
    //Private Member Variable: Private since they only belong to a particular instance
    private int width;
    private int height;
    private boolean collision;
    private final int initSnakeLen = 4;
    private Snake Snake;
    private Food Food;
    private Set<Wall> Wall;

    /**
     * Constructor for a Game Manager
     * Creates an Empty Game Manager that will later be filled by calling
     * Initialization Methods
     */
    public GameManager(int width, int height) {
        System.out.println("Created a Game Manager object");
        this.width = width;
        this.height = height;
        this.Wall = new HashSet<>();
        initGame();
    }

//    /**
//     * fileConfiguration method passes in the Dimensions and Wall Locations from
//     * the File passed.
//     * */
    public void fileConfiguration(String S) throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(S);
            String[] Dims = scanner.nextLine().split(" ");
            int width = Integer.parseInt(Dims[0]);
            int height = Integer.parseInt(Dims[1]);
            this.width = width;
            this.height = height;

            while (scanner.hasNextLine()) {
                String[] temp = scanner.nextLine().split(" ");
                if (temp.length == 4) {
                    int x1 = Integer.parseInt(temp[0]);
                    int x2 = Integer.parseInt(temp[1]);
                    int y1 = Integer.parseInt(temp[2]);
                    int y2 = Integer.parseInt(temp[3]);
                    initWalls(x1, x2, y1, y2);
                }
                initGame();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //initGame passes in the bare minimum to start a game. Just a Snake and a Food
    public void initGame() {
        System.out.println("In initGame");
        this.Snake = new Snake(1, 1, Dir.DOWN, initSnakeLen);
        collision = false;
        newFood();
    }

    //initWalls make Walls for the Game by Looping between the bound given
    public void initWalls(int x1, int x2, int y1, int y2) {
        for (int x = x1 ; x < x2 ; x++){
            for (int y = y1 ; y < y2; y++){
                Wall wall = new Wall(x,y);
                Wall.add(wall);
            }
        }
    }

    //newFood makes a new Food Object that will randomly be placed at an Open Spot
    public void newFood() {
        System.out.println("Made new Food");
        Random rng = new Random(25);
        int x =0;
        int y = 0;
        boolean openSpot = false;

        while (!openSpot) {
            x = rng.nextInt();
            y = rng.nextInt();
            if (!Wall.contains(new Wall(x, y)) &&
                !Snake.getSnake().contains(new Point(x, y))) {
                openSpot = true;
            }
        }
        this.Food = new Food(x, y);
    }

    /**
     * step is to progress the game. Calls the snake.Step Method from the Snake
     * then checks for collisions
     * */
    public boolean step (){
        System.out.println("Snake Stepped");
        Snake.snakeStep();

        if (Snake.foodCollision(Food)){
            System.out.println("FOOD HIT!!");
            newFood();
        }
        if (Snake.snakeCollision()){
            System.out.println("SNAKE HIT!!");
            collision = true;
        }
        if (Snake.wallCollision(Wall)){
            System.out.println("WALL HIT!!");
            collision = true;
        }
        return collision;
    }

    //Snake Getter
    public Snake getSnake(){
        return Snake;
    }

    /**
     * Overrides toString to print a representation of the Current Game Status
     * to the console.
     * */
    @Override
    public String toString(){
        System.out.println("In the ToString");
        StringBuilder SB = new StringBuilder();

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++) {
                Point point = new Point(x, y);
                Point head = Snake.getSnake().getFirst();

                boolean hasSnake = Snake.getSnake().contains(point);
                boolean hasHead = head.equals(point);
                boolean hasFood = (x == Food.getX() && y == Food.getY());
                boolean hasWall = false;
                for (Wall wall : Wall) {
                    if (wall.x == x && wall.y == y) {
                        hasWall = true;
                        break;
                    }
                }

                if (hasWall) {SB.append("X ");
                } else if (hasHead) {SB.append("0 ");
                } else if (hasSnake) {SB.append("o ");
                } else if (hasFood) {SB.append("* ");
                } else {SB.append(". ");
                }
            }
            SB.append('\n');
        }
        return SB.toString();
    }
}