package src.view;

import java.util.HashMap;
import java.io.File;

/**
 * Class: Map
 * 
 * This class handles map-related functionality, including reading and printing the map.
 * @author 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 */
public class Map {
    private HashMap<String, Room> rooms; // Stores the rooms in the map
    private String startingRoomID; // ID of the starting room

    public Map() {
        this.rooms = new HashMap<>();
    }

    /**
     * Method: readMap
     * 
     * Reads the map data from a file.
     * 
     * @param file The file containing map data.
     */
    public void readMap(File file) {}

    /**
     * Method: createRoom
     * 
     * Creates a new room in the map.
     */
    public void createRoom() {}

    /**
     * Method: printMap
     * 
     * Prints the map to the console.
     */
    public void printMap() {}
}
