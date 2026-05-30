package MultiCooks;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        if (args.length < 1)
            throw new IllegalArgumentException("Insufficient Number of Args Supplied");

        Restaurant restaurant = Restaurant.fromFile(args[0]);

        try {
            long elapsedTime = restaurant.start();
            System.out.println("All orders have been completed in: " + elapsedTime + "ms.");
        } catch (InterruptedException e) {
            System.err.println("Main thread was interrupted: " + e.getMessage());
        }
    }
}
