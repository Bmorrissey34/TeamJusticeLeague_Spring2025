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
import src.model.Weapon; 
import src.model.Room;
import src.model.SaveGame; 
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
    private HashMap<String, Room> rooms; // Stores the rooms in the game, using names as keys
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
            String fileName = gameView.getUserInput("Enter the name of the save file (e.g., gameState.dat):").trim();
            loadSavedGame(fileName); // Pass only the file name
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
        String startingRoomName = "Entrance"; // Replace with the actual starting room name
        Room startingRoom = dataLoader.getRooms().get(startingRoomName); // Use name as key
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
                case "pickup":
                    String itemToPickup = gameView.getUserInput("Enter the name of the item to pick up:").trim();
                    pickupItem(itemToPickup);
                    break;
                case "swap":
                    String itemToSwap = gameView.getUserInput("Enter the name of the item to swap:").trim();
                    swapItem(itemToSwap);
                    break;
                case "use":
                    String itemToUse = gameView.getUserInput("Enter the name of the item to use:").trim();
                    useItem(itemToUse);
                    break;
                case "fight":
                    Monster monster = player.getCurrentRoom().getMonster(); // Assuming Room has a getMonster method
                    player.fight(monster);
                    break;
                case "save":
                    String fileName = gameView.getUserInput("Enter the name of the save file:").trim();
                    saveGame(fileName);
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

    /**
     * Method: saveGame
     * 
     * Saves the current game state to the specified file path.
     * 
     * @param fileName The name of the file where the game state will be saved.
     */
    private void saveGame(String fileName) {
        SaveGame saveGame = new SaveGame();
        GameState gameState = new GameState(player, rooms, items, puzzles, monsters, inventory);
        saveGame.saveGame(fileName + ".dat", gameState); // Pass only the file name
    }

    /**
     * Method: useItem
     * 
     * Allows the player to use an item from their inventory.
     * 
     * @param itemName The name of the item to use.
     */
    private void useItem(String itemName) {
        int result = player.useItem(itemName);
        if (result > 0) {
            gameView.displayMessage("You used " + itemName + " and dealt " + result + " damage.");
        } else {
            gameView.displayMessage("Failed to use the item.");
        }
    }

    // Display available commands
    private void displayAvailableCommands() {
        gameView.displayMessage("Available commands: help, look, move, inventory, pickup, swap, use, fight, save, quit");
    }

    // Display available directions based on the current room
    private void displayAvailableDirections() {
        HashMap<String, Room> exits = player.getCurrentRoom().getExits();
        if (exits == null || exits.isEmpty()) {
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
     * @param fileName The name of the save file.
     */
    public void loadSavedGame(String fileName) {
        GameState gameState = continueGame.loadGame(fileName + ".dat"); // Pass only the file name
        if (gameState != null) {
            // Restore the game state
            this.player = gameState.getPlayer();
            this.rooms = gameState.getRooms();
            this.items = gameState.getItems();
            this.puzzles = gameState.getPuzzles();
            this.monsters = gameState.getMonsters();
            this.inventory = gameState.getInventory();

            if (this.rooms == null) {
                this.rooms = new HashMap<>(); // Ensure rooms is not null
                gameView.displayMessage("Warning: Rooms data was null. Initialized an empty map.");
            }

            if (this.inventory == null) {
                this.inventory = new Inventory(); // Ensure inventory is not null
                gameView.displayMessage("Warning: Inventory data was null. Initialized an empty inventory.");
            }

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

    /**
     * Method: pickupItem
     * 
     * Allows the player to pick up an item from the current room.
     * 
     * @param itemName The name of the item to pick up.
     */
    private void pickupItem(String itemName) {
        Room currentRoom = player.getCurrentRoom();
        Item item = currentRoom.getItem(itemName);
        if (item != null) {
            player.addItemToInventory(item);
            currentRoom.removeItem(item); // Simplified logic to remove item directly
            gameView.displayMessage("You picked up: " + itemName);
        } else {
            gameView.displayMessage("Item not found in the room.");
        }
    }

    /**
     * Method: swapItem
     * 
     * Allows the player to swap an item in their inventory with an item in the current room.
     * 
     * @param itemName The name of the item to swap.
     */
    private void swapItem(String itemName) {
        Room currentRoom = player.getCurrentRoom();
        Item roomItem = currentRoom.getItems().stream()
            .filter(i -> i.getName().equalsIgnoreCase(itemName))
            .findFirst()
            .orElse(null);

        if (roomItem == null) {
            gameView.displayMessage("Item not found in the room.");
            return;
        }

        if (!(roomItem instanceof Weapon)) {
            gameView.displayMessage("Item is not a weapon and cannot be swapped.");
            return;
        }

        if (!player.hasWeapon(player.getItems())) {
            gameView.displayMessage("You do not have a weapon to swap. Use pickup instead.");
            return;
        }

        Item inventoryWeapon = player.getItems().stream()
            .filter(i -> i instanceof Weapon)
            .findFirst()
            .orElse(null);

        if (inventoryWeapon != null) {
            player.removeItemFromInventory(inventoryWeapon);
            currentRoom.addItem(inventoryWeapon);
        }

        player.addItemToInventory(roomItem);
        currentRoom.removeItem(roomItem);
        gameView.displayMessage("You swapped your weapon with: " + itemName);
    }
}