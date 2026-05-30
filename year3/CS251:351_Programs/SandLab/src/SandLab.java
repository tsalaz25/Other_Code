import java.awt.*;
import java.util.Random;

/**
 * Sand lab adapted from http://nifty.stanford.edu/2017/feinberg-falling-sand/
 *
 * Student name: Tomas Salaz
 *
 * TODO: Document expected behavior of various materials here
 * Sand: Falls into piles like it would Naturally, falls through Liquids.
 *
 * Liquids (Water/Oil): Flows like liquid would,fills out empty spaces.
 *
 * Water: Will Fall through Oil and level out while underneath Oil. Will fill
 * a 'container' if placed on top of Sand or Metal.
 *
 * Oil: Will sit on top of water and level out, it water fall through it, it
 * will re-level. Oil will also float to be be on top of water if placed
 * underwater
 *
 * Acid: Acid behaves like a liquid and will even out on the last row if
 * there is only acid. Acid will delete one particle if it comes into contact
 * with it. If a Material is dropped onto acid, the material will acid will
 * eat that material until there is no more acid, then the material will be
 * able to accumulate again.
 */
public class SandLab {

    /**
     * Enum for material types of the particles
     */
    public enum Material {
        EMPTY,
        METAL,
        SAND,
        WATER,
        OIL,
        ACID

        //TODO: add constants for additional particle types here

    }

    /** grid of particles of various materials*/
    private Material[][] grid;

    /** The display window */
    private SandDisplay display;

    /**
     * Create a new SandLab of given size.
     * @param numRows number of rows
     * @param numCols number of columns
     */
    public SandLab(int numRows, int numCols) {
        // TODO: Include names for all Materials used in simulation
        //       (Can you do it without manually listing them all?)
        String[] names = new String[]{"EMPTY", "METAL", "SAND", "WATER",
                "OIL", "ACID"};

        display = new SandDisplay("Falling Sand", names, numRows, numCols);

        grid = new Material[numRows][numCols];

        for (int rows = 0; rows < numRows; rows++){
            for (int cols = 0; cols < numCols; cols++){
                grid[rows][cols] = Material.EMPTY;
            }
        }
    }

    /**
     * Called after the user clicks on a location using the given tool
     * @param row Row of location
     * @param col Column of location
     * @param tool Name of selected tool
     */
    public void updateFromUser(int row, int col, String tool) {
        grid[row][col] = Material.valueOf(tool);
    }

    /**
     * copies each element of grid into the display
     */
    public void refreshDisplay() {
        //int[] tempCoords = display.getMouseLocation();

        for (int i = 0;  i < grid.length; i++){
            for (int j = 0 ; j < grid[0].length; j++){
                switch (grid[i][j]){
                    case EMPTY -> display.setColor(i, j, Color.black);
                    case SAND -> display.setColor(i , j , Color.yellow);
                    case METAL -> display.setColor(i, j , Color.gray);
                    case WATER -> display.setColor(i, j, Color.blue);
                    case OIL -> display.setColor(i, j, Color.darkGray);
                    case ACID -> display.setColor(i, j ,Color.orange);
                }
            }

        }
    }

    /**
     * Update the simulation by one step.
     * Called repeatedly.
     * Causes one random particle to maybe do something
     */
    public void updateRandomLocation() {
        int currCol = (int)(Math.random() * grid[0].length);
        int currRow = (int)(Math.random() * grid.length);

        Material currMat = grid[currRow][currCol];

        //Falling Behavior
        int nextRow = currRow + 1;
        int nextCol = currCol;

        int randCol = (int)(Math.random() * 3);
        switch (randCol % 3){
            case 0 -> nextCol = currCol - 1;
            case 1 -> nextCol = currCol + 1;
            case 2 -> nextCol = currCol;
        }

        if (!currMat.equals(Material.METAL) && !currMat.equals(Material.EMPTY)
        && nextRow < grid.length && nextCol >= 0 && nextCol <grid[0].length) {

            Material nextMat = grid[nextRow][nextCol];
            Material linearNextMat = grid[nextRow][currCol];

            switch (currMat) {
                case SAND:
                    if (nextMat.equals(Material.EMPTY)) {
                        grid[nextRow][nextCol] = currMat;
                        grid[currRow][currCol] = nextMat;
                    }
                    if (nextMat.equals(Material.WATER)){
                        grid[nextRow][nextCol] = currMat;
                        grid[currRow][currCol] = nextMat;
                    }
                    if (nextMat.equals(Material.METAL)){
                        grid[currRow][currCol] = currMat;
                        grid[nextRow][nextCol] = nextMat;
                    }
                    if (nextMat.equals(Material.OIL)){
                        grid[currRow][currCol] = nextMat;
                        grid[nextRow][nextCol] = currMat;
                    }
                    if (nextMat.equals(Material.ACID)){
                        grid[currRow][currCol] = Material.EMPTY;
                        grid[nextRow][nextCol] = Material.EMPTY;
                    }

                    break;

                case WATER:
                    if (nextMat.equals(Material.EMPTY)) {
                        grid[nextRow][nextCol] = currMat;
                        grid[currRow][currCol] = nextMat;
                    }

                    if (nextMat.equals(Material.ACID)){
                        grid[currRow][currCol] = Material.EMPTY;
                        grid[nextRow][nextCol] = Material.EMPTY;
                    }

                    if (nextMat.equals(Material.WATER) || nextMat.equals
                    (Material.OIL)){
                        if (grid[nextRow][nextCol].equals(Material.EMPTY)){
                            grid[nextRow][nextCol] = Material.WATER;
                            grid[currRow][currCol] = Material.EMPTY;
                        }else if(grid[currRow][nextCol].equals(Material.EMPTY)){
                            grid[currRow][nextCol] = Material.WATER;
                            grid[currRow][currCol] = Material.EMPTY;
                        }else if(grid[nextRow][nextCol].equals(Material.OIL)){
                            grid[nextRow][nextCol] = Material.WATER;
                            grid[currRow][currCol] = Material.OIL;
                        }else if(grid[currRow][nextCol].equals(Material.OIL)){
                            grid[currRow][nextCol] = Material.WATER;
                            grid[currRow][currCol] = Material.OIL;
                        }
                    }
                    break;

                case OIL:
                    if (nextMat.equals(Material.EMPTY)) {
                        grid[nextRow][nextCol] = currMat;
                        grid[currRow][currCol] = nextMat;
                    }

                    if (nextMat.equals(Material.ACID)){
                        grid[currRow][currCol] = Material.EMPTY;
                        grid[nextRow][nextCol] = Material.EMPTY;
                    }

                    if (nextMat.equals(Material.OIL)){
                        if (grid[nextRow][nextCol].equals(Material.EMPTY)){
                            grid[nextRow][nextCol] = Material.OIL;
                            grid[currRow][currCol] = Material.EMPTY;
                        }else if(grid[currRow][nextCol].equals(Material.EMPTY)){
                            grid[currRow][nextCol] = Material.OIL;
                            grid[currRow][currCol] = Material.EMPTY;
                        }

                    }
                    break;

                case ACID:
                    if (nextMat.equals(Material.EMPTY)) {
                        grid[nextRow][nextCol] = currMat;
                        grid[currRow][currCol] = nextMat;
                    }
                    if (nextMat.equals(Material.ACID)){
                        if (grid[nextRow][nextCol].equals(Material.EMPTY)){
                            grid[nextRow][nextCol] = Material.ACID;
                            grid[currRow][currCol] = Material.EMPTY;
                        }else if(grid[currRow][nextCol].equals(Material.EMPTY)){
                            grid[currRow][nextCol] = Material.ACID;
                            grid[currRow][currCol] = Material.EMPTY;
                        }

                    }

                    if (!nextMat.equals(Material.EMPTY) &&
                    !nextMat.equals(Material.ACID)){
                        grid[currRow][currCol] = Material.EMPTY;
                        grid[nextRow][nextCol] = Material.ACID;
                    }
                    break;
            }
        }
    }

    /**
     * Run the SandLab particle simulation.
     *
     * DO NOT MODIFY THIS METHOD!
     */
    public void run() {
        // keep updating as long as the program is running
        while (true) {
            // update some number of particles, as determined by the speed slider
            for (int i = 0; i < display.getSpeed(); i++) {
                updateRandomLocation();
            }
            // Update the display object's colors
            refreshDisplay();
            // wait for redrawing and for mouse events
            display.repaintAndPause(1);

            int[] mouseLoc = display.getMouseLocation();
            //test if mouse clicked
            if (mouseLoc != null) {
                updateFromUser(mouseLoc[0], mouseLoc[1], display.getToolString());
            }
        }
    }

    /** Creates a new SandLab and sets it running */
    public static void main(String[] args) {
        SandLab lab = new SandLab(120, 80);
        lab.run();
    }
}
