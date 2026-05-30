package SmartRail;

import java.util.Objects;

/**
 * The Location class represents a coordinate (x,y) in a 2D space within the SmartRail system.
 *
 * Authors: Anthony Ivanov, Tomas Salaz
 */

public class Location {
    private final int x;
    private final int y;

    /**
     * Constructs a Location with specified x and y coordinates.
     *
     * @param x the x-coordinate of the location.
     * @param y the y-coordinate of the location.
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters for x and y coordinates
    public int getX() {return x;}
    public int getY() {return y;}

    // Correcting equals method
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }

    @Override
    public String toString(){
        return "Location: [" + x + ", " + y + "]";
    }
}
