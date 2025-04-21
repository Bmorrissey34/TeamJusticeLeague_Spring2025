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
import java.io.IOException;

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

        // Load rooms from DataLoader
        DataLoader dataLoader = new DataLoader();
        this.rooms = dataLoader.getRooms();  // ← Make sure to assign this

        String startingRoomName = "Entrance";
        Room startingRoom = this.rooms.get(startingRoomName);
        if (startingRoom != null) {
            player.setCurrentRoom(startingRoom);
            gameView.displayMessage("Welcome, " + player.getName() + 
                "! You are starting in: " + startingRoom.getName());
            gameView.displayMessage(startingRoom.getDescription());
        } else {
            gameView.displayMessage("Error: Starting room not found! Check your Rooms.txt file.");
            return;
        }

        // Start the game loop
        gameLoop();
    }

    private void gameLoop() {
        boolean isRunning = true;
        while (isRunning) {
            displayAvailableCommands();

            String command = gameView.getUserInput("What would you like to do?").trim().toLowerCase();

            switch (command) {
                case "help":
                    displayHelp();
                    break;
                case "look":
                    gameView.displayMessage(player.getCurrentRoom().examine());
                    break;
                case "move":
                    displayAvailableDirections();
                    String direction = gameView.getUserInput("Enter the direction to move (e.g., north, south):").trim().toUpperCase();
                    gameView.displayMessage(player.move(direction));
                    break;
                case "solve":
                    solvePuzzle();
                    break;
                case "inventory":
                    gameView.displayMessage(player.getInventory());
                    break;
                case "pickup":
                    String itemToPickup = gameView.getUserInput("Enter the name of the item to pick up:").trim();
                    pickupItem(itemToPickup);
                    break;
                case "drop":
                    String itemToDrop = gameView.getUserInput("Enter the name of the item to drop:").trim();
                    gameView.displayMessage(player.dropItem(itemToDrop));
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
                    Monster monster = player.getCurrentRoom().getMonster();
                    if (monster != null && !monster.isDefeated()) {
                        player.fight(monster, gameView);
                    } else {
                        gameView.displayMessage("There is no monster to fight in this room.");
                    }
                    break;
                case "save":
                    String saveFileName = gameView.getUserInput("Enter the name of the save file:").trim();
                    saveGame(saveFileName);
                    break;
                case "map":
                    displayMap(rooms);
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

        if (result == -1) {
            gameView.displayMessage("Failed to use the item.");
        } else if (result == 0) {
            gameView.displayMessage("You equipped " + itemName + " as a weapon.");
        } else {
            gameView.displayMessage("You used " + itemName + " and restored " + result + " health.");
        }
    }

    // Display available commands
    private void displayAvailableCommands() {
        gameView.displayMessage("Available commands: help, look, move, inventory, pickup, drop, swap, use, fight, solve, save, map, quit");
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
        GameState gameState = null;
        try {
            gameState = continueGame.loadGame(fileName + ".dat"); // Pass only the file name
        } catch (IOException e) {
            gameView.displayMessage("Error loading game: " + e.getMessage());
        }
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
     * Displays the game map in a 3x3 grid format through GameView.
     * 
     * @param rooms A HashMap containing the rooms in the game.
     */
    public void displayMap(HashMap<String, Room> rooms) {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom == null) {
            gameView.displayMessage("Error: Current room is null.");
            return;
        }

        Room[][] localGrid = new Room[3][3];
        localGrid[1][1] = currentRoom;

        for (String direction : currentRoom.getExits().keySet()) {
            Room connected = currentRoom.getExits().get(direction);
            if (connected == null) continue;

            int x = 1, y = 1;
            switch (direction.toUpperCase()) {
                case "NORTH":      x = 0; y = 1; break;
                case "SOUTH":      x = 2; y = 1; break;
                case "EAST":       x = 1; y = 2; break;
                case "WEST":       x = 1; y = 0; break;
                case "NORTHEAST":  x = 0; y = 2; break;
                case "NORTHWEST":  x = 0; y = 0; break;
                case "SOUTHEAST":  x = 2; y = 2; break;
                case "SOUTHWEST":  x = 2; y = 0; break;
                case "UPSTAIRS":   x = 0; y = 1; break; // treat as NORTH for grid
                case "DOWNSTAIRS": x = 2; y = 1; break; // treat as SOUTH for grid
                case "UP":         x = 0; y = 1; break; // treat as NORTH for grid
                case "DOWN":       x = 2; y = 1; break; // treat as SOUTH for grid
            }
            if (x >= 0 && x < 3 && y >= 0 && y < 3) {
                localGrid[x][y] = connected;
            }
        }

        StringBuilder mapOutput = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (localGrid[i][j] != null) {
                    mapOutput.append("[").append(localGrid[i][j].getName()).append("] ");
                } else {
                    mapOutput.append("[Empty] ");
                }
            }
            mapOutput.append("\n");
        }

        gameView.displayMessage(mapOutput.toString());
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
            currentRoom.removeItem(item);
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

    /**
     * Method: solvePuzzle
     * 
     * Allows the player to solve a puzzle in the current room.
     */
    private void solvePuzzle() {
        Room currentRoom = player.getCurrentRoom();
        Puzzle puzzle = currentRoom.getPuzzle();

        if (puzzle == null || puzzle.isSolved()) {
            gameView.displayMessage("There is no unsolved puzzle in this room.");
            return;
        }

        String answer = gameView.displayPuzzleInteraction(puzzle);

        if (answer.equalsIgnoreCase("skip")) {
            puzzle.skip(player, gameView);
        } else {
            puzzle.attempt(answer, currentRoom, player, gameView);
        }

        // Check if the puzzle is solved and remove it from the room
        if (puzzle.isSolved()) {
            currentRoom.setPuzzle(null); // Remove the puzzle from the room
            gameView.displayMessage("The puzzle has been solved and removed from the room.");
        }
    }
}