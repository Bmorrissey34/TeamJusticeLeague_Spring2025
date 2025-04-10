package src.controller;

import src.data.ContinueGame;
import src.model.GameState;
import src.model.Player;
import src.model.Room;
import src.model.Item;
import src.model.Puzzle;
import src.model.Monster;

import java.util.HashMap;

/**
 * Class: GameController
 * 
 * This class manages the main game flow, including loading saved games,
 * displaying help, and starting the game.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 */
public class GameController {
    private ContinueGame continueGame = new ContinueGame();

    // Game state variables
    private Player player; // Represents the player in the game
    private HashMap<Integer, Room> rooms; // Stores the rooms in the game
    private HashMap<String, Item> items; // Stores the items in the game
    private HashMap<String, Puzzle> puzzles; // Stores the puzzles in the game
    private HashMap<String, Monster> monsters; // Stores the monsters in the game

    /**
     * Method: loadSavedGame
     * 
     * Loads a saved game state from the specified file path.
     * 
     * @param saveFilePath The file path of the saved game.
     */
    public void loadSavedGame(String saveFilePath) {
        GameState gameState = continueGame.loadGame(saveFilePath);
        if (gameState != null) {
            // Restore the game state
            this.player = gameState.getPlayer();
            this.rooms = gameState.getRooms();
            this.items = gameState.getItems();
            this.puzzles = gameState.getPuzzles();
            this.monsters = gameState.getMonsters();

            System.out.println("Game loaded successfully!");
            System.out.println("Player: " + player.getName());
            System.out.println("Rooms loaded: " + rooms.size());
            System.out.println("Items loaded: " + items.size());
            System.out.println("Puzzles loaded: " + puzzles.size());
            System.out.println("Monsters loaded: " + monsters.size());
        } else {
            System.out.println("Failed to load the saved game.");
        }
    }

    /**
     * Method: displayHelp
     * 
     * Displays a list of available commands to the player.
     */
    public void displayHelp() {
        System.out.println("Available Commands:");
        System.out.println("- <direction>: Move in a direction (e.g., N, NW, S, SW, etc.).");
        System.out.println("- pickup <item>: Pick up an item in the room.");
        System.out.println("- use <item>: Use an item from your inventory.");
        System.out.println("- map: Display the map.");
        System.out.println("- help: Display this help menu.");
        System.out.println("- save: Save the game.");
        System.out.println("- quit: Quit the game.");
    }

    /**
     * Method: displayMap
     * 
     * Displays the game map with room names and descriptions.
     * 
     * @param rooms A HashMap containing the rooms in the game.
     */
    public void displayMap(HashMap<Integer, Room> rooms) {
        System.out.println("Game Map:");
        for (Room room : rooms.values()) {
            System.out.println("- " + room.getName() + ": " + room.getDescription());
        }
    }

    /**
     * Method: startGame
     * 
     * Starts or resumes the game based on the current game state.
     */
    public void startGame() {
        if (player == null || rooms == null) {
            System.out.println("No game state loaded. Starting a new game...");
            // Initialize a new game state here
        } else {
            System.out.println("Resuming game for player: " + player.getName());
            // Continue the game using the loaded state
        }
    }
}

