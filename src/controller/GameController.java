package src.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import src.model.SaveGame; 
import src.model.Weapon;
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
                gameLoop();
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

        // Load data from DataLoader and assign all fields
        DataLoader dataLoader = new DataLoader();
        this.rooms = dataLoader.getRooms();
        this.items = dataLoader.getItems();         // assign items
        this.puzzles = dataLoader.getPuzzles();       // assign puzzles
        this.monsters = dataLoader.getMonsters();     // assign monsters

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
        while (isRunning && player.getHealth() > 0) {
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
                    gameView.displayMessage(player.getInventoryDescription());
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
                case "stats":
                    gameView.displayPlayerStatus(player);
                    break;
                case "examine":
                    String objectToExamine = gameView.getUserInput("What would you like to examine? (room, monster, puzzle, item)").trim().toLowerCase();
                    switch (objectToExamine) {
                        case "room":
                            gameView.displayMessage(player.getCurrentRoom().examine());
                            break;
                        case "monster":
                            Monster monster = player.getCurrentRoom().getMonster();
                            if (monster != null && !monster.isDefeated()) {
                                gameView.displayMessage(monster.examine());
                            } else {
                                gameView.displayMessage("There is no monster to examine in this room.");
                            }
                            break;
                        case "puzzle":
                            Puzzle puzzle = player.getCurrentRoom().getPuzzle();
                            if (puzzle != null && !puzzle.isSolved()) {
                                gameView.displayMessage(puzzle.examine());
                            } else {
                                gameView.displayMessage("There is no puzzle to examine in this room.");
                            }
                            break;
                        case "item":
                            String itemName = gameView.getUserInput("Enter the name of the item to examine:").trim();
                            Item item = findItemToExamine(itemName);
                            if (item != null) {
                                gameView.displayMessage(item.examine());
                            } else {
                                gameView.displayMessage("Item not found in the room or your inventory.");
                            }
                            break;
                        default:
                            gameView.displayMessage("Invalid option. You can examine 'room', 'monster', 'puzzle', or 'item'.");
                            break;
                    }
                    break;
                case "check":
                    String itemToCheck = gameView.getUserInput("What item in your inventory would you like to check out?");
                    player.checkItem(itemToCheck);
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
                    return;
                case "map":
                    displayMap(rooms);
                    break;
                case "quit":
                    gameView.displayMessage("Thanks for playing!");
                    isRunning = false;
                    return;    
                default:
                    gameView.displayMessage("Unknown command. Type 'help' for a list of commands.");
                    break;
            }
        }
        gameView.displayGameOver();
    }

    /**
     * Finds an item to examine either in the current room or the player's inventory.
     *
     * @param itemName The name of the item to find.
     * @return The item if found, otherwise null.
     */
    private Item findItemToExamine(String itemName) {
        // Check the current room for the item
        Item roomItem = player.getCurrentRoom().getItem(itemName);
        if (roomItem != null) {
            return roomItem;
        }

        // Check the player's inventory for the item
        for (Item inventoryItem : player.getItems()) {
            if (inventoryItem.getName().equalsIgnoreCase(itemName)) {
                return inventoryItem;
            }
        }

        return null; // Item not found
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
        // Use player's inventory instead of the controller's inventory field
        GameState gameState = new GameState(player, rooms, items, puzzles, monsters, player.getInventory());
        saveGame.saveGame(fileName + ".dat", gameState);
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
        Room currentRoom = player.getCurrentRoom();
        ArrayList<String> availableCommands = new ArrayList<>();

        // Basic commands always available
        availableCommands.add("help");
        availableCommands.add("look");
        availableCommands.add("inventory");
        availableCommands.add("save");
        availableCommands.add("quit");

        // Add "move" if there are exits in the current room
        if (currentRoom.getExits() != null && !currentRoom.getExits().isEmpty()) {
            availableCommands.add("move");
        }

        // Add "pickup" if there are items in the current room
        if (!currentRoom.getItems().isEmpty()) {
            availableCommands.add("pickup");
        }

        // Add "drop" if the player has items in their inventory
        if (!player.getItems().isEmpty()) {
            availableCommands.add("drop");
        }

        // Add "use" if the player has items in their inventory
        if (!player.getItems().isEmpty()) {
            availableCommands.add("use");
        }

        // Add "swap" if the player has a weapon and there is a weapon in the room
        boolean hasWeaponInRoom = currentRoom.getItems().stream().anyMatch(item -> item instanceof Weapon);
        if (player.hasWeapon(player.getItems()) && hasWeaponInRoom) {
            availableCommands.add("swap");
        }

        // Add "fight" if there is a monster in the room and it is not defeated
        if (currentRoom.getMonster() != null && !currentRoom.getMonster().isDefeated()) {
            availableCommands.add("fight");
        }

        // Add "solve" if there is an unsolved puzzle in the room
        if (currentRoom.getPuzzle() != null && !currentRoom.getPuzzle().isSolved()) {
            availableCommands.add("solve");
        }

        // Add "map" if the map feature is available
        availableCommands.add("map");

        // Display the available commands
        gameView.displayMessage("Available commands: " + String.join(", ", availableCommands));
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
            gameState = continueGame.loadGame(fileName + ".dat");
        } catch (IOException e) {
            gameView.displayMessage("Error loading game: " + e.getMessage());
        }
        if (gameState != null) {
            this.player = gameState.getPlayer();
            this.rooms = gameState.getRooms();
            this.items = gameState.getItems();
            this.puzzles = gameState.getPuzzles();
            this.monsters = gameState.getMonsters();

            Inventory loadedInventory = gameState.getInventory();
            if (loadedInventory != null) {
                player.setInventory(loadedInventory);
            }
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