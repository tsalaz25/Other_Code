package SmartRail;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The AbstractRailComponent class is an abstract base class for components in the SmartRail system.
 * Each component has a location, a unique component ID, a lock state, and a message inbox.
 * Components that extend this class can process messages received through the inbox.
 *
 * Authors: Anthony Ivanov, Tomas Salaz
 */

public abstract class AbstractRailComponent {

    private final Location location;
    private final int componentID;
    private boolean lock;
    private static int IDCounter = 0;
    protected final BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();

    /**
     * Constructs an AbstractRailComponent with the specified location and assigns a unique ID.
     *
     * @param location the location of the rail component.
     */
    public AbstractRailComponent(Location location){
        this.location = location;
        this.componentID = IDCounter++;
        this.lock = false;
    }

    // Getters
    public Location getLocation() {return location;}
    public int getComponentID(){return componentID;}

    // Checks if the component is currently locked
    public boolean isLocked(){return lock;}

    /**
     * @return true if the component was successfully locked; false if it was already locked.
     */
    public synchronized boolean lock() {
        if (!lock) {
            this.lock = true;
            return true;
        }
        return false;
    }

    /**
     * Unlocks the component, making it available for other trains.
     */
    public synchronized void unlock(){
        this.lock = false;
    }

    /**
     * @param message the message to be added to the inbox.
     */
    public void receiveMessage(Message message){
        inbox.add(message);
    }

    /**
     * Continuously retrieves and processes messages from the inbox in a separate thread.
     * The run loop ends if interrupted.
     */
    public void run(){
        try {
            while (true){
                Message message = inbox.take();
                processMessage(message);
            }
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Processes an incoming message. This method is abstract and must be implemented
     * by subclasses.
     *
     * @param message the message to process.
     */
    protected abstract void processMessage(Message message);

    @Override
    public String toString(){
        return "AbstractRailComponent {ID: " + componentID + ", location: "
                + location + ", lock status: " + lock + "}";
    }
}
