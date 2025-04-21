package src.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class: GameState
 * 
 * This class represents the current state of the game, including the player,
 * rooms, items, puzzles, monsters, and the player's inventory.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 *          Author: Brendan Morrissey
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = -8441044017004447895L; // Match the serialVersionUID from the error

    private Player player; // The player in the game
    private HashMap<String, Room> rooms = new HashMap<>(); // Ensure rooms is initialized
    private HashMap<String, Item> items = new HashMap<>(); // Ensure items is initialized
    private HashMap<String, Puzzle> puzzles = new HashMap<>(); // Ensure puzzles is initialized
    private HashMap<String, Monster> monsters = new HashMap<>(); // Ensure monsters is initialized
    private Inventory inventory = new Inventory(); // Ensure inventory is initialized

    public GameState(Player player, HashMap<String, Room> rooms, HashMap<String, Item> items,
            HashMap<String, Puzzle> puzzles, HashMap<String, Monster> monsters, Inventory inventory) {
        this.player = player;
        this.rooms = rooms != null ? rooms : new HashMap<>(); // Ensure rooms is not null
        this.items = items != null ? items : new HashMap<>(); // Ensure items is not null
        this.puzzles = puzzles != null ? puzzles : new HashMap<>(); // Ensure puzzles is not null
        this.monsters = monsters != null ? monsters : new HashMap<>(); // Ensure monsters is not null
        this.inventory = inventory != null ? inventory : new Inventory(); // Ensure inventory is not null
    }

    /**
     * Method: getPlayer
     * 
     * Retrieves the player in the game state.
     * 
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Method: setPlayer
     * 
     * Sets the player in the game state.
     * 
     * @param player The player to set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Method: getRooms
     * 
     * Retrieves the rooms in the game state.
     * 
     * @return A HashMap of rooms.
     */
    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    /**
     * Method: setRooms
     * 
     * Sets the rooms in the game state.
     * 
     * @param rooms A HashMap of rooms to set.
     */
    public void setRooms(HashMap<String, Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Method: getItems
     * 
     * Retrieves the items in the game state.
     * 
     * @return A HashMap of items.
     */
    public HashMap<String, Item> getItems() {
        return items;
    }

    /**
     * Method: setItems
     * 
     * Sets the items in the game state.
     * 
     * @param items A HashMap of items to set.
     */
    public void setItems(HashMap<String, Item> items) {
        this.items = items;
    }

    /**
     * Method: getPuzzles
     * 
     * Retrieves the puzzles in the game state.
     * 
     * @return A HashMap of puzzles.
     */
    public HashMap<String, Puzzle> getPuzzles() {
        return puzzles;
    }

    /**
     * Method: setPuzzles
     * 
     * Sets the puzzles in the game state.
     * 
     * @param puzzles A HashMap of puzzles to set.
     */
    public void setPuzzles(HashMap<String, Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

    /**
     * Method: getMonsters
     * 
     * Retrieves the monsters in the game state.
     * 
     * @return A HashMap of monsters.
     */
    public HashMap<String, Monster> getMonsters() {
        return monsters;
    }

    /**
     * Method: setMonsters
     * 
     * Sets the monsters in the game state.
     * 
     * @param monsters A HashMap of monsters to set.
     */
    public void setMonsters(HashMap<String, Monster> monsters) {
        this.monsters = monsters;
    }

    /**
     * Method: getInventory
     * 
     * Retrieves the player's inventory.
     * 
     * @return The inventory object.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Method: setInventory
     * 
     * Sets the player's inventory.
     * 
     * @param inventory The inventory to set.
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}