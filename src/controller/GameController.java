package src.controller;

import java.util.Random;
import java.util.Scanner;
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
 *          @author Jordan Laudun
 */
public class GameController {
    private ContinueGame continueGame = new ContinueGame();
    private GameView gameView = new GameView(); // Use GameView for input/output
    private Scanner scanner = new Scanner(System.in); // For user command input

    // Game state variables
    private Player player; // Represents the player in the game
    private HashMap<Integer, Room> rooms; // Stores the rooms in the game
    private HashMap<String, Item> items; // Stores the items in the game
    private HashMap<String, Puzzle> puzzles; // Stores the puzzles in the game
    private HashMap<String, Monster> monsters; // Stores the monsters in the game
    private Monster currentMonster; // Represents the currently fought monster
    private Inventory inventory; // The player's inventory

    private Room previousRoom; // Tracks the last room visited before moving

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

        // Ask for player name
        String playerName = gameView.getUserInput("Enter your name, brave adventurer:");
        this.player = new Player(playerName, 100); // Starting health = 100

        // Load game data using DataLoader
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadMonsters("src/data/monsters.txt");
        dataLoader.loadPuzzles("src/data/puzzles.txt");
        dataLoader.loadRooms("src/data/rooms.txt");

        this.rooms = dataLoader.getRooms();
        this.items = dataLoader.getItems();
        this.puzzles = dataLoader.getPuzzles();
        this.monsters = dataLoader.getMonsters();
        this.inventory = new Inventory();

        // Set player's starting location (Room 8: Entrance)
        Room startingRoom = rooms.get("8".hashCode());
        player.setCurrentRoom(startingRoom);

        gameView.displayMessage("Welcome, " + playerName + "! You find yourself in the haunted Ravenshade Manor...");

        // Main game loop
        boolean gameRunning = true;
        while (gameRunning && player.getHealth() > 0) {
            Room currentRoom = player.getCurrentRoom();
            gameView.displayRoom(currentRoom);

            // Check for monster
            Monster monster = currentRoom.getMonster();
            if (monster != null && !monster.isDefeated()) {
                setCurrentMonster(monster);
                gameView.displayMessage("A sinister presence is here... It's " + monster.getName() + "!");
            } else {
                setCurrentMonster(null);
            }

            // Player command input
            String command = gameView.getUserInput("What will you do?");
            if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit")) {
                gameRunning = false;
            } else {
                handleCommand(command);
            }
        }

        // Game Over Sequence
        if (player.getHealth() <= 0) {
            gameView.displayMessage("You have died... The mansion claims another soul.");
        } else {
            gameView.displayMessage("Thanks for playing!");
        }
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
        gameView.displayMessage("Here is the layout of the mansion:");

        File mapFile = new File("src/data/map.txt");
        gameMap.readMap(mapFile);
        gameMap.printMap();
    }
     /**
     * Method: setCurrentMonster
     * 
     * Sets the monster currently encountered by the player.
     * 
     * @param monster The monster in the current room.
     */
    public void setCurrentMonster(Monster monster) {
        this.currentMonster = monster;
    }

    /**
     * Method: handleCommand
     * 
     * Handles player commands such as "examine", "fight", and "flee".
     * 
     * @param input The player's typed command.
     */
    public void handleCommand(String input) {
        switch (input.toLowerCase()) {
            case "examine":
                if (currentMonster != null) {
                    gameView.displayMessage(currentMonster.examine());
                } else {
                    gameView.displayMessage("There is nothing to examine here.");
                }
                break;

            case "fight":
                if (currentMonster != null && !currentMonster.isPassive()) {
                    fightMonster();
                } else {
                    gameView.displayMessage("There is nothing hostile to fight.");
                }
                break;

            case "flee":
                handleFlee();
                break;

            case "go back":
                goBack();
                break;
    
            case "help":
                displayHelp();
                break;
            
            case "go north":
            case "go south":
            case "go east":
            case "go west":
            case "go upstairs":
            case "go downstairs":
            case "go northwest":
            case "go northeast":
            case "go southwest":
            case "go southeast":
                String direction = input.substring(3).trim();
                movePlayer(direction);
                break;

            default:
                gameView.displayMessage("Unknown command.");
        }
    }

    /**
     * Method: fightMonster
     * 
     * Handles turn-based combat between the player and a monster.
     */
    private void fightMonster() {
        while (!currentMonster.isDefeated() && player.getHealth() > 0) {
            currentMonster.attack(player);
            if (player.getHealth() <= 0) break;

            String input = gameView.getUserInput("Your turn. Type the amount of damage to deal:");
            try {
                int damage = Integer.parseInt(input);
                currentMonster.takeDamage(damage);
            } catch (NumberFormatException e) {
                gameView.displayMessage("Invalid number. You missed your turn.");
            }
        }

        if (currentMonster.isDefeated()) {
            gameView.displayMessage("You defeated the monster!");
        } else {
            gameView.displayMessage("You have been defeated...");
        }
    }

    /**
     * Method: goBack
     * 
     * Moves the player back to the previously visited room, if available.
    */
    public void goBack() {
        if (previousRoom != null) {
            Room current = player.getCurrentRoom();
            player.setCurrentRoom(previousRoom);
            previousRoom = current; // allow toggling back again if needed
            gameView.displayMessage("You return to the previous room: " + player.getCurrentRoom().getName());
        } else {
            gameView.displayMessage("There is no previous room to return to.");
        }  
    }

    /**
     * Method: movePlayer
     * 
     * Moves the player to the room in the inputted direction, if available.
    */
    private void movePlayer(String direction) {
        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getExits().get(direction.toUpperCase());
    
        if (nextRoom != null) {
            previousRoom = currentRoom; // Track where you came from
            player.setCurrentRoom(nextRoom);
            gameView.displayMessage("You move " + direction + " to " + nextRoom.getName() + ".");
        } else {
            gameView.displayMessage("You can't go that way.");
        }
    }

    /**
     * Method: handleFlee
     * 
     * Attempts to flee from combat. Bosses cannot be fled from.
     */
    private void handleFlee() {
        if (currentMonster != null && currentMonster.isBoss()) {
            gameView.displayMessage("You cannot flee from a boss fight!");
        } else {
            int roll = new Random().nextInt(100);
            if (roll < 50) {
                gameView.displayMessage("You successfully fled!");
                goBack(); // Move to previous room
            } else {
                gameView.displayMessage("Failed to flee!");
                currentMonster.attack(player);
            }
        }
    }
}
