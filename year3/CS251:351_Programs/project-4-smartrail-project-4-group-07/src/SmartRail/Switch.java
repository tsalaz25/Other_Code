package SmartRail;

/**
 * The Switch class represents a rail switch in the SmartRail system.
 * A Switch can toggle between primary and secondary lanes and processes
 * messages to control its behavior.
 *
 * Authors: Anthony Ivanov, Tomas Salaz
 */
public class Switch extends AbstractRailComponent implements Runnable {

    private boolean usingPrimary = true;

    /**
     * Constructs a Switch at the specified location.
     *
     * @param location of the switch.
     */
    public Switch(Location location) {
        super(location);
    }

    /**
     * Toggles the lane used by the switch.
     */
    public void toggleLane(){
        usingPrimary = !usingPrimary;
    }

    /**
     * Processes messages sent to the switch. Unrecognized messages are noted.
     *
     * @param message to process (TOGGLE).
     */
    @Override
    protected void processMessage(Message message){
        switch(message.getType()){
            case TOGGLE:
                toggleLane();
                System.out.println("Switch " + getComponentID() + " toggled.");
                break;
            default:
                System.out.println("Unrecognized switch message: " + message.getType());
                break;
        }
    }
}
