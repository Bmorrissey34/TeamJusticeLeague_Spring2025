package src.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class: Room
 * 
 * This class represents a room in the game, including its items, puzzle,
 * monster, and exits.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 */
public class Room {
    private ArrayList<Item> items; // Items in the room
    private Puzzle puzzle; // Puzzle in the room
    private Monster monster; // Monster in the room
    private HashMap<String, Room> exits; // Exits from the room

    public Room() {
        this.items = new ArrayList<>();
        this.exits = new HashMap<>();
    }

    /**
     * Method: addExits
     * 
     * Adds an exit to the room.
     * 
     * @param direction The direction of the exit.
     * @param room The room connected to the exit.
     */
    public void addExits(String direction, Room room) {}

    /**
     * Method: addItem
     * 
     * Adds an item to the room.
     * 
     * @param item The item to add.
     */
    public void addItem(Item item) {}

    /**
     * Method: removeItem
     * 
     * Removes an item from the room.
     * 
     * @param item The item to remove.
     */
    public void removeItem(Item item) {}

    /**
     * Method: removePuzzle
     * 
     * Removes the puzzle from the room.
     */
    public void removePuzzle() {}

    /**
     * Method: removeMonster
     * 
     * Removes the monster from the room.
     */
    public void removeMonster() {}
}