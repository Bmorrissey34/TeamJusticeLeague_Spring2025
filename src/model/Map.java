package src.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class: Map
 * 
 * This class handles map-related functionality, including reading and printing the map.
 * @author 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 * Author: Brendan Morrissey
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
     * Reads the map data from a file and dynamically creates rooms and their exits.
     * 
     * @param file The file containing map data.
     */
    public void readMap(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("//")) {
                    continue; // Skip empty lines and comments
                }

                String[] parts = line.split(",", 6); // Split into 6 parts
                if (parts.length < 2) {
                    System.out.println("Malformed room data: " + line);
                    continue;
                }

                // Parse room name and description
                String roomName = parts[0].trim();
                String description = parts[1].trim();

                // Create a new Room object
                Room room = new Room();
                room.setName(roomName);
                room.setDescription(description);

                // Parse exits
                if (parts.length > 2 && !parts[2].isEmpty()) {
                    String[] exits = parts[2].split("\\|");
                    for (String exit : exits) {
                        String[] exitParts = exit.split(":");
                        if (exitParts.length == 2) {
                            String direction = exitParts[0].trim().toUpperCase();
                            String connectedRoomName = exitParts[1].trim();
                            room.addExits(direction, null); // Temporarily set the exit to null
                        }
                    }
                }

                // Add the room to the map
                rooms.put(roomName, room);
            }

            // Second pass: Resolve exits
            for (Room room : rooms.values()) {
                HashMap<String, Room> exits = room.getExits();
                for (String direction : exits.keySet()) {
                    String connectedRoomName = exits.get(direction).getName();
                    if (rooms.containsKey(connectedRoomName)) {
                        exits.put(direction, rooms.get(connectedRoomName));
                    }
                }
            }

            // Set the starting room (e.g., the first room in the file)
            if (!rooms.isEmpty()) {
                this.startingRoomID = rooms.keySet().iterator().next();
            }

        } catch (IOException e) {
            System.out.println("Error reading map file: " + e.getMessage());
        }
    }

    /**
     * Method: printMap
     * 
     * Prints the map to the console.
     */
    public void printMap() {
        for (Room room : rooms.values()) {
            System.out.println("Room: " + room.getName());
            System.out.println("Description: " + room.getDescription());
            System.out.print("Exits: ");
            for (String direction : room.getExits().keySet()) {
                System.out.print(direction + " ");
            }
            System.out.println("\n");
        }
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public String getStartingRoomID() {
        return startingRoomID;
    }

    public void setRooms(HashMap<String, Room> rooms) {
        this.rooms = rooms;
    }

    public void setStartingRoomID(String startingRoomID) {
        this.startingRoomID = startingRoomID;
    }

    /**
     * Method: populateGrid
     * 
     * Populates a grid with rooms based on their connections and directions.
     * 
     * @param grid The grid to populate.
     * @param currentRoom The current room being processed.
     * @param x The x-coordinate in the grid.
     * @param y The y-coordinate in the grid.
     * @param visited A map to track visited rooms.
     */
    public void populateGrid(Room[][] grid, Room currentRoom, int x, int y, HashMap<Room, Boolean> visited) {
        if (currentRoom == null || visited.containsKey(currentRoom)) {
            return; // Stop if the room is null or already visited
        }

        visited.put(currentRoom, true); // Mark the room as visited

        // Place the current room in the grid
        grid[x][y] = currentRoom;
        System.out.println("Placed room: " + currentRoom.getName() + " at (" + x + ", " + y + ")");

        // Get the exits of the current room
        HashMap<String, Room> exits = currentRoom.getExits();

        // Place connected rooms in the grid based on their directions
        for (String direction : exits.keySet()) {
            Room connectedRoom = exits.get(direction);
            if (connectedRoom == null) {
                continue;
            }

            int newX = x, newY = y;

            switch (direction) {
                case "NORTH":
                    newX = x - 1;
                    break;
                case "SOUTH":
                    newX = x + 1;
                    break;
                case "EAST":
                    newY = y + 1;
                    break;
                case "WEST":
                    newY = y - 1;
                    break;
                case "NORTHEAST":
                    newX = x - 1;
                    newY = y + 1;
                    break;
                case "NORTHWEST":
                    newX = x - 1;
                    newY = y - 1;
                    break;
                case "SOUTHEAST":
                    newX = x + 1;
                    newY = y + 1;
                    break;
                case "SOUTHWEST":
                    newX = x + 1;
                    newY = y - 1;
                    break;
                case "UP":
                    // Handle UP direction (e.g., move to a different grid layer)
                    break;
                case "DOWN":
                    // Handle DOWN direction (e.g., move to a different grid layer)
                    break;
            }

            // Ensure the new position is within bounds
            if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length && grid[newX][newY] == null) {
                populateGrid(grid, connectedRoom, newX, newY, visited); // Recursively populate the grid
            }
        }
    }
}
