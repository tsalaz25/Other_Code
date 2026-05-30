package MultiCooks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class Restaurant {
    private final AtomicBoolean exceptionOccurred = new AtomicBoolean(false);
    private final ConcurrentHashMap<String, Recipe> recipes;

    private final HashSet<CookingStation> cookingStations;

    private final HashSet<Chef> chefs;

    private final int numChefs;

    private final Queue<String> orderQueue;
    public Restaurant(int numChefs, ConcurrentHashMap<String, Recipe> recipes, HashSet<CookingStation> cookingStations, List<String> orders) {

        if(numChefs <= 0){
            throw new IllegalArgumentException("Number of chefs must be greater than 0");
        }

        this.numChefs = numChefs;
        this.recipes = recipes;
        this.cookingStations = cookingStations;
        this.orderQueue = createOrderQueue(orders);
        this.chefs = new HashSet<>(numChefs);
        initializeChefs();
    }

    public Queue<String> getOrderQueue() {
        return orderQueue;
    }

    public Map<String, Recipe> getRecipes() {
        return recipes;
    }

    public HashSet<CookingStation> getCookingStations() {
        return cookingStations;
    }

    private static Queue<String> createOrderQueue(List<String> orders){
        return new LinkedList<>(orders);
    }

    private void initializeChefs(){
        for(int i = 0; i < numChefs; i++){
            Chef newChef = new Chef(i, this);
            chefs.add(newChef);
        }
    }

    public long start() throws InterruptedException {
        List<Thread> threads = new ArrayList<>(numChefs);

        // Start timer
        long startTime = System.currentTimeMillis();

        for (Chef c : chefs) {
            Thread thread = new Thread(c);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Propagate the interrupt to the main thread
                break;
            }
            if (exceptionOccurred.get()) {
                // Interrupt all threads if an exception has occurred
                for (Thread t : threads) {
                    t.interrupt();
                }
                break;
            }
        }

        if (!exceptionOccurred.get()) {
            // Stop timer and calculate elapsed time
            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
        else {
            throw new InterruptedException("An exception occurred during execution");
        }
    }

    public AtomicBoolean getExceptionOccurred() {
        return exceptionOccurred;
    }

    public static Restaurant fromFile(String filePath) throws IOException {
        Path recipeFilePath = Path.of(filePath);
        List<String> lines = Files.readAllLines(recipeFilePath);

        // New Instance variables
        int numChefs = 0;
        ConcurrentHashMap<String, Recipe> recipes = new ConcurrentHashMap<>();
        HashSet<CookingStation> cookingStations = new HashSet<>();
        List<String> orders = new ArrayList<>();

        int readState = 0;

        for(String line : lines) {
            if(line.isEmpty()){
                readState++;
                continue;
            }

            switch (readState) {
                case 0 -> numChefs = Integer.parseInt(line);
                case 1 -> cookingStations.addAll(parseCookingStationLine(line));
                case 2 -> recipes.put(line.split(",")[0], Recipe.fromCSVLine(line));
                case 3 -> orders.add(line);
                default -> throw new IllegalStateException("Unexpected read state: " + readState);
            }
        }

        return new Restaurant(numChefs, recipes, cookingStations, orders);
    }

    private static List<CookingStation> parseCookingStationLine(String line) {
        List<CookingStation> cookingStations = new ArrayList<>();

        String[] cookingStationVars = line.split(",");
        int numStations = Integer.parseInt(cookingStationVars[0]);
        String stationName = cookingStationVars[1];

        for (int i = 0; i < numStations; i++) {
            CookingStation newStation = new CookingStation(stationName);
            cookingStations.add(newStation);
        }

        return cookingStations;
    }

}