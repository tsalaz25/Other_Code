package MultiCooks;

public class CookingStation {
    private final String name;
    private Chef currentChef = null;

    public CookingStation(String name) {
        this.name = name;
    }

    /**
     * Attempts to have the provided chef occupy the current station.
     *
     * @param chef Chef attempting to occupy the station
     * @return true if the chef is able to occupy the station, false if it's already occupied.
     */
    public boolean occupyStation(Chef chef) {
        //Sync's the block of code, No Race Conditions
        synchronized (this) {
            while (currentChef != null) {
                //Current Chef waits until open
                try {
                    wait();
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        //Only executes once stations is available
        currentChef = chef;
        return true;
    }

    /**
     * Make the station available for use.
     */
    public void releaseStation() {
        //Sync's the block of code, No Race Conditions
        synchronized (this) {
            if (currentChef != null) {
                //Release and Notify waiting Chefs
                currentChef = null;
                notifyAll();
            }
        }
    }

    public void useStation(Recipe recipe, Chef chef) throws InterruptedException {
        if(chef != currentChef){
            throw new IllegalArgumentException(chef + " does not occupy " + this);
        }
        //Simming Cooking Time
        Thread.sleep(recipe.cookTime());
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "CookingStation{" +
                "name='" + name + '\'' + "," +
                "currentChef=" + currentChef +
                '}';
    }
}
