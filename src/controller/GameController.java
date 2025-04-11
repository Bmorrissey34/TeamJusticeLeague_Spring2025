package src.controller;

import java.util.HashMap;
import src.data.ContinueGame;
import src.model.GameState;
import src.model.Inventory;
import src.model.Item;
import src.model.Monster;
import src.model.Player;
import src.model.Puzzle;
import src.model.Room;
import src.view.GameView;

/**
 * Class: GameController
 * 
 * This class manages the main game flow, including loading saved games,
 * displaying help, and starting the game.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 *          Author: Brendan(Saving & Loading) & Ademola(GameLoop)
 */
public class GameController {
    private ContinueGame continueGame = new ContinueGame();
    private GameView gameView = new GameView(); // Use GameView for input/output

    // Game state variables
    private Player player; // Represents the player in the game
    private HashMap<Integer, Room> rooms; // Stores the rooms in the game
    private HashMap<String, Item> items; // Stores the items in the game
    private HashMap<String, Puzzle> puzzles; // Stores the puzzles in the game
    private HashMap<String, Monster> monsters; // Stores the monsters in the game
    private Inventory inventory; // The player's inventory

    /**
     * Method: initializeGame
     * 
     * Initializes the game by either loading a saved game or starting a new one.
     */
    public void initializeGame() {
        gameView.displayMessage("Welcome to the game!");
        String choice = gameView.getUserInput("Would you like to load a saved game? (yes/no)").trim().toLowerCase();

        if (choice.equals("yes")) {
            String filePath = "src/data/resources/gameState.dat"; // Default save file path
            loadSavedGame(filePath);
            if (player == null || rooms == null) {
                gameView.displayMessage("Failed to load the saved game. Starting a new game...");
                startGame();
            } else {
                gameView.displayMessage("Game loaded successfully!");
                gameView.displayMessage("Resuming game for player: " + player.getName());
            }
        } else {
            startGame();
        }
    }

    /**
     * Method: startGame
     * 
     * Initializes a new game state.
     */
    private void startGame() {
    }

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
            this.inventory = gameState.getInventory();

            gameView.displayMessage("Game loaded successfully!");
            gameView.displayMessage("Player: " + player.getName());
            gameView.displayMessage("Rooms loaded: " + rooms.size());
            gameView.displayMessage("Items loaded: " + items.size());
            gameView.displayMessage("Puzzles loaded: " + puzzles.size());
            gameView.displayMessage("Monsters loaded: " + monsters.size());
            gameView.displayMessage("Inventory items: " + inventory.getItems().size());
        } else {
            gameView.displayMessage("Failed to load the saved game.");
        }
    }

    /**
     * Method: displayHelp
     * 
     * Displays a list of available commands to the player.
     */
    public void displayHelp() {
        gameView.displayHelpMenu();
    }

    /**
     * Method: displayMap
     * 
     * Displays the game map with room names and descriptions.
     * 
     * @param rooms A HashMap containing the rooms in the game.
     */
    public void displayMap(HashMap<Integer, Room> rooms) {
        // Implementation for displaying the map
    }
}
