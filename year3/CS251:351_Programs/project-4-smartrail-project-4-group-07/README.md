
# **SmartRail Project**

The SmartRail is a simulation of a dynamic rail network. This application models rail
components and trains, and their interactions to move trains along the track to desired
destinations. 

---

## **Authors**

- Anthony Ivanov
- Tomas Salaz

---

## **How to Use**

**Example Usage for SmartRail.jar**

    java -jar SmartRail.jar <example_config/config_file.txt>
* 'config_file.txt' must exist in the 'example_config' folder in the GitHub




1. **Assigning Direction**
    - Each station is given a labeled dropdown bar on the right hand side of the GUI. Stations are ordered Left to Right
      in descending order.
    - Once clicked, there are options of other station locations for the user to select.
    - Multiple dropdowns can be set, and by starting the application, train(s) begin their path.

2. **Console Updates**
    - Since the program is not completely functioning, there are helpful debugging clues left
   in the console to partly show what is occurring.

3. **Graphical User Interface (GUI)**
    - The network is visualized in a user-friendly GUI.
    - Color-coded Train Node Statuses:
        - *Idle:* Gray
        - *Searching Path:* Orange
        - *Locking Path:* Purple
        - *Moving:* Green
        - *Train(s):* Black

---

## **Known Issues and Future Enhancements**

1. **Current Limitations**
    - There is currently no way to do multiple simulations after one another. Once a train has pathed and
   completed its route to a station, the program must be re-run to test another path.
    - While trains can do right-to-left, the consistency of this is far from desired, so they often get stuck
   at IDLE position, claiming there is no valid path.
    - This is also the case for trains "dropping" to a lower level, such as in the sample.txt test file.
    - Trains do not get animated but the nodes show their path finding process and internal functions.
    - Double's do not get passed into the configuration correctly, will be partially correct in some spots. 

2. **Future Enhancements**
    - Allowing for multiple simulations, one after another, so that it is fluid.
    - Ensure consistent behavior for trains going backward.
    - Better handling of complex switch configurations.