package SmartRail;

/**
 * The Track class represents a section of rail track in the SmartRail system.
 * It extends AbstractRailComponent and implements Runnable to support threaded execution.
 *
 * Authors: Anthony Ivanov, Tomas Salaz
 */

public class Track extends AbstractRailComponent implements Runnable {

    private final Location startingLocation;
    private final Location finalLocation;

    /**
     * Constructs a Track with specified starting and final locations.
     *
     * @param startingLocation starting location of the track.
     * @param finalLocation ending location of the track.
     */
    public Track(Location startingLocation, Location finalLocation){
        super(startingLocation);
        this.startingLocation = startingLocation;
        this.finalLocation = finalLocation;
    }

    // Location getters
    public Location getFinalLocation(){return finalLocation;}
    public Location getStartingLocation(){return startingLocation;}

    /**
     * Processes messages sent to the track component. The message type determines
     * if the track should be locked or unlocked. Unrecognized messages are noted.
     *
     * @param message The message to process, containing details such as type and sender.
     */
    @Override
    protected void processMessage(Message message){
        switch(message.getType()){
            case LOCK:
                if(lock()){
                    System.out.println("Track " + getComponentID() + " now locked by " + message.getSenderID());
                } else {
                    System.out.println("Track " + getComponentID() + " is already locked.");
                }
                break;
            case UNLOCK:
                if(isLocked()){
                    unlock();
                    System.out.println("Track " + getComponentID() + " now unlocked by " + message.getSenderID());
                }
                break;
            default:
                System.out.println("Unrecognized track message: " + message.getType());
                break;
        }
    }
}
