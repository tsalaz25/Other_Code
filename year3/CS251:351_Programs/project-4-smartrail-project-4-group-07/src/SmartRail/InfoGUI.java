package SmartRail;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.*;
/**
 *InfoGUI class represents the user interface for managing the
 * simulation routes and controlling the start and stop actions in the SmartRail application.
 * This class extends VBox and creates  components based on the stations managed
 * by the RailwayManager, allowing user to set routes between stations
 * and start or stop the simulation.
 *
 * Authors: Anthony Ivanov, Tomas Salaz
 */
public class InfoGUI extends VBox {
    private RailwayManager railwayManager;
    private Map <Location,Location> routeMap = new HashMap <Location,Location> ();
    private ArrayList<Location> sortedStations;

    /**
     * Constructor for InfoGUI.
     * @param railwayManager that manages the simulation logic
     */
    public InfoGUI(RailwayManager railwayManager) {
       this.railwayManager = railwayManager;
       this.sortedStations = new ArrayList<Location>(railwayManager.getStations().keySet());
       Collections.sort(sortedStations,(loc1, loc2) -> {
           if (loc1.getY() != loc2.getY()){
               return Integer.compare(loc1.getY(), loc2.getY());
           }
           return Integer.compare(loc1.getX(), loc2.getX());
       });
       this.setBackground(Background.fill(Color.DARKSLATEGREY));
       initComponents();
   }

    /**
     * Initializes the GUI components creating ComboBoxes for selecting end
     * stations for each available station, adds buttons for starting and stopping
     * the simulation.
     */
   private void initComponents() {
        for (Location l : sortedStations) {
            ComboBox<Location> endBox = new ComboBox<Location>();
            endBox.getItems().addAll(railwayManager.getStations().keySet());
            endBox.getItems().remove(l);
            endBox.setPromptText("Station " + l.toString());
            endBox.setPrefSize(200,30);
            endBox.setOnAction(e -> {routeMap.put(l,endBox.getValue());});
            this.getChildren().add(endBox);
        }
        Button startButton = new Button("Start Simulation");
        startButton.setPrefSize(200,30);
        startButton.setBackground(Background.fill(Color.GREEN));
        this.getChildren().add(startButton);
        startButton.setOnAction(event -> HandleSetRoutes());
        Button stopButton = new Button("Stop Simulation");
        stopButton.setPrefSize(200,30);
        stopButton.setBackground(Background.fill(Color.RED));
        this.getChildren().add(stopButton);
        stopButton.setOnAction(event -> handleStopSim());
   }

    /**
     * EventHandler Method  for starting the simulation, based on configured routes
     * in the RailwayManager
     *  If the RailwayManager is null, an error message is printed.
     */
   private void HandleSetRoutes() {
       if (railwayManager != null) {
           System.out.println("Starting simulation with set routes...");
           railwayManager.startComponents(routeMap);
       } else {
           System.out.println("Error: RailwayManager not found.");
       }
   }

    /**
     * Handles the action for stopping the simulation by calling method in the
     * RailwayManager. If  null, an error message is printed.
     */
   private void handleStopSim() {
        if (railwayManager != null) {
            railwayManager.stopComponents();
        } else {
            System.out.println("Error: RailwayManager not found.");
        }
   }
}