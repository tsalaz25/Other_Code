package SmartRail;

/**
 * The Station class represents a station in the SmartRail system, extending AbstractRailComponent
 * and implementing Runnable. A Station interacts with a RailwayManager to process route requests,
 * train arrivals, and train departures.
 *
 * Authors: Anthony Ivanov, Tomas Salaz
 */
public class Station extends AbstractRailComponent implements Runnable {

    private final RailwayManager railwayManager;

    /**
     * Constructs a Station with the specified location and railway manager.
     *
     * @param location the location of the station.
     * @param railwayManager RailwayManager handling this station's messages and interactions.
     */
    public Station(Location location, RailwayManager railwayManager) {
        super(location);
        this.railwayManager = railwayManager;
    }

    /**
     * Processes messages sent to the station. Handles route requests, train arrivals,
     * and train departures.Unrecognized messages are noted.
     *
     * @param message the message to process, containing type and sender details.
     */
    @Override
    protected void processMessage(Message message){

        switch (message.getType()) {
            case ROUTE_REQUEST:
                if (!isLocked()) {
                    lock();
                    System.out.println("Station " + getComponentID() + " locked for route request by " + message.getSenderID());
                    Message confirm = new Message(Message.MessageType.ROUTE_CONFIRM,
                            "Station-" + getComponentID(), message.getSenderID(), null);
                    railwayManager.sendMessage(confirm);
                } else {
                    System.out.println("Station " + getComponentID() + " is locked, route request denied for " + message.getSenderID());
                    Message error = new Message(Message.MessageType.ERROR,
                            "Station-" + getComponentID(), message.getSenderID(), null);
                    railwayManager.sendMessage(error);
                }
                break;
            case TRAIN_ARRIVAL:
                lock();
                System.out.println("Train " + message.getSenderID() + " has arrived at station " + getComponentID());
                break;
            case TRAIN_DEPARTURE:
                unlock();
                System.out.println("Train " + message.getSenderID() + " has departed from station " + getComponentID());
                break;
            default:
                System.out.println("Unrecognized message for station: " + message.getType());
        }
    }
}
