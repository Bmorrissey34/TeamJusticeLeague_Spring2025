package src.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class: Room
 * 
 * This class represents a room in the game, including its items, puzzle,
 * monster, and exits.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 *          Author: Dino Maksumic
 */
public class Room extends GameModel implements Examine, Serializable {
    private static final long serialVersionUID = 1L; // Add serialVersionUID for serialization
    private String name; // Use name as the unique identifier
    private String description;
    private HashMap<String, Room> exits; // Stores exits as direction -> connected room
    private ArrayList<Item> items = new ArrayList<>();
    private Monster monster;
    private Puzzle puzzle;
    private boolean isMonsterDefeated = false;
    private boolean isPuzzleCompleted = false;
    private List<String> itemIDs = new ArrayList<>();

    public Room() {
        this.items = new ArrayList<>();
        this.exits = new HashMap<>();
    }

    // Getters and setters for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and setters for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getters and setters for monster
    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public boolean isMonsterDefeated() {
        return isMonsterDefeated;
    }

    public void setMonsterDefeated(boolean isMonsterDefeated) {
        this.isMonsterDefeated = isMonsterDefeated;
    }

    public void removeMonster() {
        this.isMonsterDefeated = true; // Mark monster as defeated
    }

    // Getters and setters for puzzle
    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public boolean isPuzzleCompleted() {
        return isPuzzleCompleted;
    }

    public void setPuzzleCompleted(boolean isPuzzleCompleted) {
        this.isPuzzleCompleted = isPuzzleCompleted;
    }

    public void removePuzzle() {
        this.isPuzzleCompleted = true; // Mark puzzle as completed
    }

    /**
     * Method: addExits
     * 
     * Adds an exit to the room.
     * 
     * @param direction The direction of the exit (e.g., "NORTH", "SOUTH").
     * @param room      The room connected to this exit.
     */
    public void addExits(String direction, Room room) {
        exits.put(direction, room);
    }

    /**
     * Method: getExits
     * 
     * Retrieves the exits of the room.
     * 
     * @return A HashMap of exits (direction -> connected room).
     * @author William Stein
     */
    public HashMap<String, Room> getExits() {
        return exits;
    }

    /**
     * Method: getItems
     * 
     * Returns contents of items ArrayList
     * 
     * @return ArrayList of items in room
     * @author William Stein
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Method: getItemNames
     * 
     * Returns all item names from items ArrayList
     * 
     * @return A ArrayList of item names
     * @author William Stein
     */
    public ArrayList<String> getItemNames() {
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : items) {
            itemNames.add(item.getName());
        }
        return itemNames;
    }

    /**
     * Method: addItem
     * 
     * Adds an item to the room which is dropped by the player.
     * 
     * @param item The item to add.
     * @author William Stein
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Method: removeItem
     * 
     * Removes an item from the room that is picked up by the player.
     * 
     * @param item The item to remove.
     * @author William Steinn
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Method: getItem
     * 
     * Retrieves an item from the room by its name.
     * 
     * @param itemName The name of the item to retrieve.
     * @return The item if found, otherwise null.
     */
    public Item getItem(String itemName) {
        return items.stream()
                .filter(item -> item.getName() != null && item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Method: addItemID
     * 
     * Adds an item ID to the room's list of item IDs.
     * 
     * @param itemID The ID of the item to add.
     */
    public void addItemID(String itemID) {
        itemIDs.add(itemID);
    }

    @Override
    public String examine() {
        return "Room: " + getName() +
                "\nDescription: " + getDescription() +
                "\nItems: " + (items.isEmpty() ? "None" : items) +
                "\nMonster: " + (monster != null && !isMonsterDefeated ? monster.getName() : "None") +
                "\nPuzzle: " + (puzzle != null && !isPuzzleCompleted ? puzzle.getQuestion() : "None");
    }

    public String getID() {
        return name; // Return name as the unique identifier
    }

    public void setID(String iD) {
        this.name = iD; // Set name as the unique identifier
    }

    public void setExits(HashMap<String, Room> exits) {
        this.exits = exits;
    }

    public boolean isValidDirection(String direction) {
        return direction.equals("NORTH") || direction.equals("SOUTH") ||
                direction.equals("EAST") || direction.equals("WEST") ||
                direction.equals("NORTHEAST") || direction.equals("NORTHWEST") ||
                direction.equals("SOUTHEAST") || direction.equals("SOUTHWEST") ||
                direction.equals("UP") || direction.equals("DOWN");
    }
}
