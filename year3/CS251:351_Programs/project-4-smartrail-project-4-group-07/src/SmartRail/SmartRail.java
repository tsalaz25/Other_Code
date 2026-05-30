package SmartRail;
import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.*;
import java.util.*;

/**
 * SmartRail class is  main entry point for the SmartRail simulation.
 * It initializes the GUI, manages the graphical representation of the railway system,
 * and listens for train movement events to update the GUI. Extends Application
 * and implements Listener/Observer interface to observe and handle train movements.
 *
 * Authors: Anthony Ivanov, Tomas Salaz
 */
public class SmartRail extends Application implements RailwayManager.TrainListener {
    private final int SCALE = 50;
    private final int PADDING = 25;
    private RailwayManager railwayManager;
    private final Color IDLE_COLOR = Color.GRAY;
    private final Color SEARCHING_PATH_COLOR = Color.ORANGE;
    private final Color LOCKING_PATH_COLOR = Color.PURPLE;
    private final Color MOVING_COLOR = Color.GREEN;
    private Map<String, Circle> trainNodes = new HashMap<>();
    private Stage primaryStage;

    /**
     * Entry for the JavaFX application.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if there is an error during initialization
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        String path = getParameters().getRaw().getFirst();
        System.out.println(path);
        railwayManager =  new RailwayManager(path);
        railwayManager.setTrainListener(this);
        BorderPane root = new BorderPane();
        Pane mapPane = new Pane(); //Displays the Map components
        mapPane.setPrefSize(550,550);
        root.setCenter(mapPane);
        InfoGUI infoGUI = new InfoGUI(railwayManager);
        root.setRight(infoGUI);
        for (Track t: railwayManager.getTrackSegments()) {
            Location start = t.getStartingLocation();
            Location end = t.getFinalLocation();
            System.out.printf("TRACK IN: %d, %d, %d, %d\n", start.getX(), start.getY(), end.getX(), end.getY() );
            Line outline = new Line((start.getX()*SCALE)+PADDING, (start.getY()*SCALE)+PADDING, (end.getX()*SCALE)+PADDING, (end.getY()*SCALE)+PADDING);
            Line line = new Line((start.getX()*SCALE)+PADDING, (start.getY()*SCALE)+PADDING, (end.getX()*SCALE)+PADDING, (end.getY()*SCALE)+PADDING);
            outline.setStroke(Color.BLACK);
            line.setStroke(Color.GRAY);
            outline.setStrokeWidth(7.5);
            line.setStrokeWidth(5);
            mapPane.getChildren().add(outline);
            mapPane.getChildren().add(line);
        }
        for (Station s: railwayManager.getStations().values()){
            Location location = s.getLocation();
            System.out.printf("STATIONS IN: %d, %d\n", location.getX(), location.getY() );
            Circle circle = new Circle((location.getX()*SCALE)+PADDING, (location.getY()*SCALE)+PADDING, 10, Color.RED);
            mapPane.getChildren().add(circle);
        }
        for (Switch sw: railwayManager.getSwitches().values()){
            Location location = sw.getLocation();
            System.out.printf("SWITCHES IN: %d, %d\n", location.getX(), location.getY() );
            Rectangle rect = new Rectangle((location.getX()*SCALE)+PADDING-5,(location.getY()*SCALE)+PADDING-5, 10,10);
            rect.setStroke(Color.BLACK);
            mapPane.getChildren().add(rect);
        }
        configSwitches(new ArrayList<>(railwayManager.getSwitches().keySet()), mapPane);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SmartRail");
        primaryStage.show();
        Timeline time = new Timeline(new KeyFrame(Duration.seconds(.25),
            event -> railwayManager.moveTrains(this)));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();
    }

    /**
     * Configures the switch connections by drawing the shortest paths.
     *
     * @param s  list of Locations for switches
     * @param mp Pane to draw the tracks on
     */
    private void configSwitches(ArrayList<Location> s, Pane mp) {
        Collections.sort(s,(loc1, loc2) -> {
            if (loc1.getY() != loc2.getY()){
                return Integer.compare(loc1.getY(), loc2.getY());
            }
            return Integer.compare(loc1.getX(), loc2.getX());
            });
        for (Location l: s) {
            System.out.println("set: " + l.getX() + ',' + l.getY());
        }
        while (!s.isEmpty()) {
            if (s.size() == 1){
                break;
            }
            if (s.size() == 2){
                Location l1 = s.get(0);
                Location l2 = s.get(1);
                if (l1.getX() == l2.getX() || l1.getY() == l2.getY()){break;}
            }
            Location loc = s.removeFirst();
            int x1 = loc.getX();
            int y1 = loc.getY();
            double shortest = 0;
            int remInd = 0;
            for (int index = 0; index < s.size(); index++) {
                int x2 = s.get(index).getX();
                int y2 = s.get(index).getY();
                if (x1 != x2 && y1 != y2) {
                    int dx = Math.abs(x1 - x2);
                    int dy = Math.abs(y1 - y2);
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    if (shortest == 0) {
                        shortest = dist;
                        remInd = index;
                    } else if (shortest > dist) {
                        shortest = dist;
                        remInd = index;
                    }
                }
            }
            Location l2 = s.remove(remInd);
            int x2 = l2.getX();
            int y2 = l2.getY();
            Line outline = new Line((x1*SCALE)+PADDING,(y1*SCALE)+PADDING,(x2*SCALE)+PADDING,(y2*SCALE)+PADDING);
            Line line =  new Line((x1*SCALE)+PADDING,(y1*SCALE)+PADDING,(x2*SCALE)+PADDING,(y2*SCALE)+PADDING);
            outline.setStroke(Color.BLACK);
            line.setStroke(Color.GRAY);
            outline.setStrokeWidth(7.5);
            line.setStrokeWidth(5);
            mp.getChildren().add(outline);
            mp.getChildren().add(line);
        }
    }

    /**
     * Handles the train movement event and updates the train's graphical representation in the GUI.
     *
     * @param trainID the ID of the train that moved
     * @param loc the new Location of the train
     * @param status the current Status  of the train
     */
    @Override
    public void onTrainMoved(String trainID, Location loc, Train.TrainStatus status) {
        System.out.println("Updating Train " + trainID + "on GUI");
        Train train = railwayManager.getTrain(trainID);
        if (train == null) System.out.println("Train not found");
        Pane mapPane = (Pane)((BorderPane) this.primaryStage.getScene().getRoot()).getCenter();
        Circle circle = trainNodes.computeIfAbsent(trainID, id -> {
            Circle updated = new Circle((loc.getX()*SCALE)+PADDING,(loc.getY()*SCALE)+PADDING,10,IDLE_COLOR);
            mapPane.getChildren().add(updated);
            return updated;
        });
        circle.setCenterX((loc.getX()*SCALE) + PADDING);
        circle.setCenterY((loc.getY()*SCALE) + PADDING);
        switch (status){
            case SEARCHING_PATHS -> {
                circle.setFill(SEARCHING_PATH_COLOR);
            }
            case LOCKING_PATH -> {
                circle.setFill(LOCKING_PATH_COLOR);
            }
            case MOVING -> circle.setFill(MOVING_COLOR);
            case IDLE -> circle.setFill(IDLE_COLOR);
        }
        Rectangle trainRectangle = (Rectangle) mapPane.getChildren().stream()
            .filter(node -> node instanceof Rectangle && node.getId() != null && node.getId().equals(trainID))
            .findFirst()
            .orElseGet(() -> {
                Rectangle newRect = new Rectangle((train.getCurrLoc().getX() * SCALE) + PADDING - 10, (train.getCurrLoc().getY() * SCALE) + PADDING - 5, 20, 10);
                newRect.setFill(Color.BLACK);
                newRect.setId(trainID);
                mapPane.getChildren().add(newRect);
                return newRect;
            });
        if (loc.equals(train.getDestination())) {
            trainRectangle.setX((loc.getX() * SCALE) + PADDING - 10);
            trainRectangle.setY((loc.getY() * SCALE) + PADDING - 5);
            trainRectangle.setFill(Color.BLACK); // Indicate that the train has arrived
            System.out.println("Train " + trainID + " has reached its destination.");
        }
    }

    /**
     * The main method to launch the SmartRail application.
     *
     * @param args command-line argument for the file path
     */
    public static void main(String[] args) {
        System.out.println(args.length);
        try {
            if (args.length == 1) {
                launch(args);
            } else {
                System.out.println("Usage: SmartRail <config file>");
            }
        } catch (Exception e) {
            System.out.println("Error During the Launch");
            e.printStackTrace();
        }
    }
}