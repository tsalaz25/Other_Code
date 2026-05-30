package SmartRail;
import java.io.*;
import java.util.*;
/**
 * RailwayManager class manages all components of the railway system.  It handles
 * initialization, configuration, and operation of  components like  starting
 * and stopping simulations, routing trains, and locking/unlocking tracks by
 * sending messages  between components.  implements an observer/listener pattern
 * for monitoring train movements and representing in the GUI
 *
 * Authors: Anthony Ivanov, Tomas Salaz
 */
public class RailwayManager {
    private Map<Location, Switch> switches;
    private Map<Location, Station> stations;
    private List<Track> trackSegments;
    private Map<Location, List<Track>> trackMap;
    private Map<String, Train> trains;
    private boolean componentsStarted = false;
    private TrainListener trainListener;

    /**
     * Constructs for RailwayManager
     * Initializes its components by loading configuration from the  file.
     *
     * @param path the absolute path to the configuration file
     * @throws IOException if the file does not exist or cannot be read
     */
    public RailwayManager(String path) throws IOException {
        switches = new HashMap<>();
        stations = new HashMap<>();
        trackSegments = new ArrayList<>();
        trackMap = new HashMap<>();
        trains = new HashMap<>();
        loadConfig(path);
    }

    /**
     * Loads the railway configuration from a file.
     * The configuration file specifies the components and their locations
     *
     * @param filepath the path to the configuration file
     * @throws IOException if the file cannot be read
     */
    public void loadConfig(String filepath) throws IOException {
        System.out.println("Loading config file: " + filepath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length < 3) continue;
                String type = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                Location location = new Location(x, y);
                switch (type) {
                    case "switch":
                        Switch swit = new Switch(location);
                        switches.put(location, swit);
                        break;
                    case "station":
                        Station station = new Station(location, this);
                        stations.put(location, station);
                        break;
                    case "track":
                        int startX = Integer.parseInt(parts[1]);
                        int startY = Integer.parseInt(parts[2]);
                        int endX = Integer.parseInt(parts[3]);
                        int endY = Integer.parseInt(parts[4]);
                        Location start = new Location(startX, startY);
                        Location end = new Location(endX, endY);
                        int segmentNum = parts.length == 5 ? 1 : Integer.parseInt(parts[5]);
                        int deltaX = (end.getX() - start.getX()) / segmentNum;
                        int deltaY = (end.getY() - start.getY()) / segmentNum;
                        Location currentLocation = start;
                        for (int i = 0; i < segmentNum; i++) {
                            Location nextLocation = new Location(
                                    currentLocation.getX() + deltaX,
                                    currentLocation.getY() + deltaY);

                            Track segment = new Track(currentLocation, nextLocation);
                            trackSegments.add(segment);
                            // Add track segment to both start and end locations for bidirectional movement
                            trackMap.computeIfAbsent(currentLocation, k -> new ArrayList<>()).add(segment);
                            trackMap.computeIfAbsent(nextLocation, k -> new ArrayList<>()).add(segment);

                            System.out.println("Added Track from " + currentLocation + " to " + nextLocation);
                            currentLocation = nextLocation;
                        }
                        break;
                    default:
                        System.out.println("Unknown component type: " + type);
                        break;
                }
            }
        }
    }

    /**
     * Starts the simulation component by initializing threads for each component,
     * creates trains based on the provided routes from the GUI
     *
     * @param routeMap a map of start and end locations for each train
     */
    public void startComponents(Map<Location, Location> routeMap){
        if (componentsStarted){
            System.out.println("Components have already been started.");
            return;
        }
        componentsStarted = true;
        System.out.println("Starting components...");
        for (Station station : stations.values()) {
            new Thread(station).start();
        }
        for (Switch swit : switches.values()) {
            new Thread(swit).start();
        }
        for (Track track : trackSegments) {
            new Thread(track).start();
        }
        int trainCounter = 1;
        for (Map.Entry<Location, Location> route : routeMap.entrySet()){
            Location start = route.getKey();
            Location end = route.getValue();
            if (stations.containsKey(start) && stations.containsKey(end)){
                String trainID = "Train " + trainCounter++;
                Train train = new Train(trainID, start, end, this);
                trains.put(trainID, train);
                if (trainListener!= null){
                    trainListener.onTrainMoved(trainID,start,train.getStatus());
                }
                new Thread(train).start();
                System.out.println("Initialized " + trainID + " from " + start + " to " + end);
            } else {
                System.out.println("Invalid route: Start or end location not found for " + start + " to " + end);
            }
        }
    }

    /**
     * Stops all running components in the simulation.
     */
    public void stopComponents(){
        System.out.println("Stopping components...");
        for (Train t: trains.values()) {
            t.stop();
        }
        trains.clear();
    }

    /**
     * Finds a path between two locations in the railway system using breadth-first
     * search (BFS) algorithm to find the shortest path between the start and end
     * locations.
     *
     * @param start the starting location
     * @param end the ending location
     * @return a list of tracks representing the path
     */
    public List<Track> findPath(Location start, Location end){
        System.out.println("Finding path from " + start + " to " + end);
        if (start.equals(end)) return new ArrayList<>();
        Queue<Location> queue = new LinkedList<>();
        Map<Location, Track> previousTrackMap = new HashMap<>();
        queue.add(start);
        previousTrackMap.put(start, null);
        while (!queue.isEmpty()){
            Location current = queue.poll();
            // get all tracks for the current location
            List<Track> currentTracks = trackMap.get(current);
            if (currentTracks == null) continue;
            // loop over each track associated with the current location
            for (Track currentTrack : currentTracks){
                System.out.println("Visiting: " + current + " via track " + currentTrack.getComponentID());
                if (current.equals(end)){
                    System.out.println("Path found to destination: " + end);
                    return makePath(previousTrackMap, currentTrack);
                }
                // iterate through connected locations for each track
                for (Location adjacent : getConnectedLocations(currentTrack)){
                    if (!previousTrackMap.containsKey(adjacent)){
                        previousTrackMap.put(adjacent, currentTrack);
                        queue.add(adjacent);
                        System.out.println("Adding to queue: " + adjacent + " from " + current);
                    }
                }
            }
        }
        System.out.println("No path found from " + start + " to " + end);
        return null;
    }

    /**
     * Constructs a path by back tracking from the ending to the starting location using a map.
     *
     * @param previousTrackMap a map of locations to their corresponding tracks
     * @param endTrack the last track in the path
     * @return a list of Track objects representing the path
     */
    private List<Track> makePath(Map<Location, Track> previousTrackMap, Track endTrack){
        List<Track> path = new LinkedList<>();
        Track track = endTrack;
        while (track != null){
            path.add(0, track);
            track = previousTrackMap.get(track.getStartingLocation());
        }
        return path;
    }

    /**
     * Retrieves the locations connected by a given track.
     *
     * @param track to find adjacent to
     * @return a list of locations connected by the track
     */
    private List<Location> getConnectedLocations(Track track){
        List<Location> adjacent = new ArrayList<>();
        Location start = track.getStartingLocation();
        Location end = track.getFinalLocation();
        if (!adjacent.contains(start)){
            adjacent.add(start);
        }
        if (!adjacent.contains(end)){
            adjacent.add(end);
        }
        System.out.println("Track " + track.getComponentID() + " connects " + start + " <-> " + end);
        return adjacent;
    }

    /**
     * Sends a message to the appropriate railway component based on the receiver ID or location.
     *
     * @param message is the outgoing message
     */
    public void sendMessage(Message message) {
        String receiverID = message.getReceiverID();
        Object task = message.getTask();
        switch (message.getType()){
            case ROUTE_REQUEST:
                handleRouteRequest(message);
                break;
            case UNLOCK:
                handleUnlockRequest(message);
                break;
            default:
                if (stations.containsKey(receiverID)) {
                    stations.get(receiverID).receiveMessage(message);
                } else if (switches.containsKey(receiverID)) {
                    switches.get(receiverID).receiveMessage(message);
                } else if (trains.containsKey(receiverID)) {
                    trains.get(receiverID).receiveMessage(message);
                } else if (task instanceof Location target && trackMap.containsKey(target)) {
                    trackMap.get(target).forEach(t -> t.receiveMessage(message));
                } else {
                    System.out.println("No component found, ID: " + receiverID + ", Location: " + task);
                }
        }
    }

    /**
     * Handles an unlock request message by unlocking tracks at a specific location.
     *
     * @param message with the unlock request
     */
    private void handleUnlockRequest(Message message) {
        Location location = (Location) message.getTask();
        List<Track> tracksAtLocation = trackMap.get(location);
        if (tracksAtLocation != null){
            for (Track track : tracksAtLocation){
                if (track.isLocked()){
                    track.unlock();
                    System.out.println("RailwayManager: Track " + track.getComponentID() +
                            " unlocked by request from train: " + message.getSenderID());
                }
            }
        } else {
            System.out.println("Warning: No track found at location " + location + " to unlock.");
        }
    }

    /**
     * Handles a route request message by attempting to lock all tracks in the requested path.
     *
     * @param message with the route request
     */
    private void handleRouteRequest(Message message) {
        List<Track> path = (List<Track>) message.getTask();
        String trainID = message.getSenderID();
        boolean allLocked = true;
        for (Track track : path){
            if (!track.isLocked() && track.lock()){
                System.out.println("Track " + track.getComponentID() + " locked by Train " + trainID);
            } else {
                allLocked = false;
                break;
            }
        }
        if (allLocked) {
            System.out.println("RailwayManager: route locked successfully for train " + trainID);
            Message confirm = new Message(Message.MessageType.ROUTE_CONFIRM,
                    "RailwayManager", trainID, null);
            trains.get(trainID).receiveMessage(confirm);
        } else {
            for (Track track : path){
                if (track.isLocked()){
                    track.unlock();
                    System.out.println("Track " + track.getComponentID() + " unlocked.");
                }
            }
            Message error = new Message(Message.MessageType.ERROR,
                    "RailwayManager", trainID, null);
            trains.get(trainID).receiveMessage(error);
        }
    }

    /**
     * Getters
     */
    public Map<Location, Switch> getSwitches() {return switches;}
    public Map<Location, Station> getStations() {return stations;}
    public List<Track> getTrackSegments() {return trackSegments;}
    public Map<String, Train> getTrains() {return trains;}

    /**
     * Listener interface for tracking train movements. Called when a train has
     * moved to a new location.
     */
    public interface TrainListener {
        /**
         * @param trainID the ID of the train that moved
         * @param loc the new location of the train
         * @param status the status of the train
         */
        void onTrainMoved (String trainID, Location loc, Train.TrainStatus status);
    }

    /**
     * Sets the listener for monitoring train movements.
     */
    public void setTrainListener(TrainListener listener) {
        this.trainListener = listener;
    }

    /**
     * Updates the positions of all trains and notifies the listener of their new locations.
     *
     * @param gui for updating train positions visually
     */
    public void moveTrains (SmartRail gui){
       for (Train t: trains.values()){
           Location newLoc = t.updatePosition();
           if (trainListener!=null){
               trainListener.onTrainMoved(t.getTrainID(), newLoc, t.getStatus());
           }
       }
    }

    /**
    * Retrieves a train by its ID.
    */
    public Train getTrain(String TrainID){
        return trains.get(TrainID);
    }
}