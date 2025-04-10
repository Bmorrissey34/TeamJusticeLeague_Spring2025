package src.model;

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
 *          Author: Dino maksumic
 */
public class Room {
    private String name;
    private String description;
    private HashMap<String, Room> exits = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private Monster monster; // Single monster in the room
    private Puzzle puzzle; // Single puzzle in the room
    private boolean isMonsterDefeated = false; // Tracks if the monster is defeated
    private boolean isPuzzleCompleted = false; // Tracks if the puzzle is completed

    public Room() {
        this.items = new ArrayList<>();
        this.exits = new HashMap<>();
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

    /**
     * Method: addExits
     * 
     * Adds an exit to the room.
     * 
     * @param direction The direction of the exit.
     * @param room      The room connected to the exit.
     */
    public void addExits(String direction, Room room) {
        exits.put(direction.toUpperCase(), room);
    }

    /**
     * Method: addItem
     * 
     * Adds an item to the room.
     * 
     * @param item The item to add.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Method: removeItem
     * 
     * Removes an item from the room.
     * 
     * @param item The item to remove.
     */
    public void removeItem(Item item) {
        items.remove(item);
    }
}
