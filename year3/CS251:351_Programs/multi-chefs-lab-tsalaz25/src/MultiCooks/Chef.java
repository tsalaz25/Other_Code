package MultiCooks;

public class Chef implements Runnable {
    private final int id;
    private final Restaurant restaurant;

    public Chef(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        CookingStation currentStation;

        try {
            while (!restaurant.getExceptionOccurred().get()) {
                String dish = restaurant.getOrderQueue().poll();

                if(dish == null)
                    break;

                System.out.println(this + " has taken an order to cook " + dish);

                // Get the recipe for the order
                Recipe recipe = restaurant.getRecipes().get(dish);

                // Move to a corresponding cooking station
                // Will try until a station is available
                currentStation = this.occupyMatchingStation(recipe.station());

                // Cook the dish at the station
                System.out.println(this + " is cooking " + dish);
                currentStation.useStation(recipe, this);
                System.out.println(this + " has finished cooking " + dish);

                // Leave the cooking station
                currentStation.releaseStation();
            }
        } catch (InterruptedException e) {
            System.err.println(this + " was interrupted: " + e.getMessage());
            restaurant.getExceptionOccurred().set(true);
        } catch (Exception e) {
            System.err.println(this + " encountered an exception: " + e.getMessage());
            restaurant.getExceptionOccurred().set(true);
        }
    }

    private CookingStation occupyMatchingStation(String stationName){
        System.out.println(this + " is waiting for an available " + stationName);
        while(true) {
            for (CookingStation station : restaurant.getCookingStations()) {
                if (station.getName().equals(stationName)) {
                    if (station.occupyStation(this)) {
                        return station;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Chef{" + "id=" + id + '}';
    }
}
