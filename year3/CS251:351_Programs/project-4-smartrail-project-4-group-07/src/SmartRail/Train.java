package SmartRail;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Train class represents a train in the SmartRail system.
 * Each train has a unique ID, a current location, a destination, a status, and a path it follows.
 * Trains communicate with the RailwayManager to find paths, lock routes, and move along the tracks.
 *
 * Authors: Anthony Ivanov, Tomas Salaz
 */
public class Train implements Runnable {

    // Enum for maintaining consistent train statuses
    public enum TrainStatus {IDLE, SEARCHING_PATHS, LOCKING_PATH, MOVING, COMPLETED}

    private final String trainID;
    private Location currLocation;
    private final Location destination;
    private TrainStatus status = TrainStatus.IDLE;
    private final BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
    private final RailwayManager railwayManager;
    private List<Track> path;
    private volatile boolean running = true;

    /**
     * @param trainID is the String/Int to ID the Component
     * @param currLocation Starting Location of the Train
     * @param destination Where the Train wants to get to
     * @param railwayManager Reference to the Manager
     */
    public Train (String trainID, Location currLocation, Location destination, RailwayManager railwayManager) {
        this.trainID = trainID;
        this.currLocation = currLocation;
        this.destination = destination;
        this.railwayManager = railwayManager;
    }

    /**
     * Starts the Thread for the Train
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                switch (status) {
                    case IDLE -> { seekPath(); }
                    case SEARCHING_PATHS -> { requestRouteLock(); }
                    case LOCKING_PATH -> { checkLockedPath(); }
                    case MOVING -> { moveAlongPath(); }
                    case COMPLETED -> {

                    }
                }

                processIncomingMessage();

                // Sleep to simulate movement, takes 1/2 seconds
                Thread.sleep(500);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Handles the logic for seeking a path from the RailwayManager.
     */
    private void seekPath() {
        path = railwayManager.findPath(currLocation, destination);
        if (path != null) {
            System.out.println("Train " + trainID + " found a path.");
            status = TrainStatus.SEARCHING_PATHS;
        } else {
            System.out.println("Train " + trainID + " cannot find a path.");
            status = TrainStatus.IDLE;
        }
    }

    /**
     * Requests the RailwayManager to lock the route for the train.
     */
    private void requestRouteLock() {
        System.out.println("Train " + trainID + " requesting route lock.");
        Message routeRequest = new Message(Message.MessageType.ROUTE_REQUEST, trainID, "RailwayManager", path);
        railwayManager.sendMessage(routeRequest);
        status = TrainStatus.LOCKING_PATH;
    }

    /**
     * Checks if the train's path is fully locked.
     */
    private void checkLockedPath() {
        System.out.println("Train " + trainID + " checking path.");
        if (routeIsLocked()) {
            status = TrainStatus.MOVING;
        } else {
            status = TrainStatus.SEARCHING_PATHS;
        }
    }

    /**
     * Moves the train along the path and handles segment locking and unlocking.
     */
    private void moveAlongPath() {
        System.out.println("Train " + trainID + " moving along path...");

        if (path == null || path.isEmpty()) {
            status = TrainStatus.IDLE;
            return;
        }

        for (int i = 0; i < path.size(); i++) {
            Track segment = path.get(i);
            if (!segment.isLocked()) {
                System.out.println("Train " + trainID + " stopped, segment " + segment + " not locked.");
                status = TrainStatus.SEARCHING_PATHS;
                return;
            }
            currLocation = segment.getFinalLocation();
            System.out.println("Train " + trainID + " moved to " + currLocation);

            if (i > 0) {
                Track prevSegment = path.get(i - 1);
                prevSegment.unlock();
                System.out.println("Train " + trainID + " has unlocked segment " + prevSegment.getComponentID());
            }
        }
        if (atDestination()) {
            releaseLockedPath();
            System.out.println("Train " + trainID + " has reached its destination and is now COMPLETED.");
            status = TrainStatus.COMPLETED;
        }
    }

    /**
     *  Checks if all tracks in the path are locked
     */
    private boolean routeIsLocked() {
        for (Track track : path) {
            if (!track.isLocked()) return false;
        }
        return true;
    }

    /**
     *  Checks if the train has reached its destination
     */

    private boolean atDestination() {
        return currLocation.equals(destination);
    }

    /**
     *  Releases all locked tracks in the path
     */
    private void releaseLockedPath() {
        System.out.println("Train " + trainID + " releasing path...");
        for (Track track : path) {
            Message release = new Message(Message.MessageType.UNLOCK, trainID, String.valueOf(track.getComponentID()), null);
            railwayManager.sendMessage(release);
        }
    }

    /**
     * Processes incoming messages from the inbox
     */
    private void processIncomingMessage() {
        while (!inbox.isEmpty()) {
            Message msg = inbox.poll();
            handleMessage(msg);
        }
    }

    /**
     * Handles specific messages sent to the train
     */
    private void handleMessage(Message message) {
        switch (message.getType()) {
            case ROUTE_CONFIRM -> {
                if (status == TrainStatus.LOCKING_PATH) {
                    System.out.println("Train " + trainID + " received route confirmation.");
                    status = TrainStatus.MOVING;
                }
            }
            case ERROR -> {
                System.out.println("Train " + trainID + " received error, resetting to search path.");
                path = null;
                status = TrainStatus.SEARCHING_PATHS;
            }
            default -> System.out.println("Unrecognized message type: " + message.getType());
        }
    }

    /**
     * Adds a message to the train's inbox for processing.
     *
     * @param message to add.
     */
    public void receiveMessage(Message message) {
        inbox.offer(message);
    }

    /**
     * Updates the train's position and removes the completed path segment.
     *
     * @return the updated current location of the train.
     */
    public Location updatePosition() {
        if (path == null || path.isEmpty()) {
            return currLocation;
        }

        Track currSegment = path.get(0);
        currLocation = currSegment.getFinalLocation();
        path.remove(0);

        return currLocation;
    }

    /**
     *  Stops the train by interrupting its thread
     */
    public void stop() {
        running = false;
        Thread.currentThread().interrupt();
    }

    /**
     *  Getters
     */
    public String getTrainID() {return trainID;}
    public TrainStatus getStatus() {return status;}
    public Location getDestination() {return destination;}
    public Location getCurrLoc() {return currLocation;}
}