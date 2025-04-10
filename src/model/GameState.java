package src.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class: GameState
 * 
 * This class represents the current state of the game, including the player,
 * rooms, items, puzzles, and monsters.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 */
public class GameState implements Serializable {
    private Player player; // The player in the game
    private HashMap<Integer, Room> rooms; // The rooms in the game
    private HashMap<String, Item> items; // The items in the game
    private HashMap<String, Puzzle> puzzles; // The puzzles in the game
    private HashMap<String, Monster> monsters; // The monsters in the game

    public GameState(Player player, HashMap<Integer, Room> rooms, HashMap<String, Item> items,
            HashMap<String, Puzzle> puzzles, HashMap<String, Monster> monsters) {
        this.player = player;
        this.rooms = rooms;
        this.items = items;
        this.puzzles = puzzles;
        this.monsters = monsters;
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
     * Method: getRooms
     * 
     * Retrieves the rooms in the game state.
     * 
     * @return A HashMap of rooms.
     */
    public HashMap<Integer, Room> getRooms() {
        return rooms;
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
     * Method: getMonsters
     * 
     * Retrieves the monsters in the game state.
     * 
     * @return A HashMap of monsters.
     */
    public HashMap<String, Monster> getMonsters() {
        return monsters;
    }
}