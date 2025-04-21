package src.controller;

import java.util.HashMap;
import src.model.ContinueGame;
import src.model.DataLoader;
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
        // Initialize a new player
        String playerName = gameView.getUserInput("Enter your name:");
        player = new Player(playerName);
        player.setHealth(100);
        player.setStrength(10);

        // Load the starting room
        DataLoader dataLoader = new DataLoader();
        String startingRoomId = "START"; // Replace with the actual starting room ID
        Room startingRoom = dataLoader.getRooms().get(startingRoomId.hashCode());
        if (startingRoom != null) {
            player.setCurrentRoom(startingRoom);
            gameView.displayMessage("Welcome, " + player.getName() + "! You are starting in: " + startingRoom.getName());
            gameView.displayMessage(startingRoom.getDescription());
        } else {
            gameView.displayMessage("Error: Starting room not found! Check your Rooms.txt file.");
            return; // Exit if the starting room is not found
        }

        // Start the game loop
        gameLoop();
    }

    private void gameLoop() {
        boolean isRunning = true;
        while (isRunning) {
            // Display available commands
            displayAvailableCommands();

            // Get player input
            String command = gameView.getUserInput("What would you like to do?").trim().toLowerCase();

            switch (command) {
                case "help":
                    displayHelp();
                    break;
                case "look":
                    gameView.displayMessage(player.getCurrentRoom().examine());
                    break;
                case "move":
                    displayAvailableDirections(); // Show available directions
                    String direction = gameView.getUserInput("Enter the direction to move (e.g., north, south):").trim().toUpperCase();
                    player.move(direction);
                    break;
                case "inventory":
                    player.getInventory();
                    break;
                case "quit":
                    gameView.displayMessage("Thanks for playing!");
                    isRunning = false;
                    break;
                default:
                    gameView.displayMessage("Unknown command. Type 'help' for a list of commands.");
                    break;
            }
        }
    }

    // Display available commands
    private void displayAvailableCommands() {
        gameView.displayMessage("Available commands: help, look, move, inventory, quit");
    }

    // Display available directions based on the current room
    private void displayAvailableDirections() {
        HashMap<String, Room> exits = player.getCurrentRoom().getExits();
        if (exits.isEmpty()) {
            gameView.displayMessage("There are no exits from this room.");
        } else {
            gameView.displayMessage("Available directions: " + String.join(", ", exits.keySet()));
        }
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