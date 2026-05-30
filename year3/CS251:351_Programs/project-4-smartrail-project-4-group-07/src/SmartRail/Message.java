package SmartRail;

/**
 * The Message class represents a communication message within the SmartRail system.
 * Messages are used to coordinate actions such as locking, unlocking, route requests,
 * and train arrivals or departures.
 *
 * Authors: Anthony Ivanov, Tomas Salaz
 */

public class Message {

    /**
     * Enum representing the different types of messages that can be sent.
     */
    public enum MessageType {
        LOCK, UNLOCK, ROUTE_REQUEST, ROUTE_CONFIRM, ERROR, TOGGLE, TRAIN_ARRIVAL, TRAIN_DEPARTURE
    }

    private final MessageType type;
    private final String senderID;
    private final String receiverID;
    private final Object task;

    /**
     * Constructs a new Message with specified type, sender ID, receiver ID, and task.
     *
     * @param type type of message.
     * @param senderID  identifier of the sender.
     * @param receiverID identifier of the receiver.
     * @param task task associated with the message.
     */
    public Message (MessageType type, String senderID, String receiverID, Object task){
        this.type = type;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.task = task;
    }

    // Getters
    public MessageType getType() {return type;}
    public String getSenderID() {return senderID;}
    public String getReceiverID() {return receiverID;}
    public Object getTask() {return task;}

    /**
     * Returns a string representation of the message for easy logging and debugging.
     *
     * @return a string describing the message details.
     */
    @Override
    public String toString() {
        return "Message: type = " + type + " || senderID = " + senderID +
                " || receiverID = " + receiverID + " || task = " + task;
    }
}
