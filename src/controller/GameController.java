package src.controller;

import src.data.ContinueGame;
import src.model.GameState;
import src.model.Player;
import src.model.Room;
import src.model.Item;
import src.model.Puzzle;
import src.model.Monster;

import java.util.HashMap;

public class GameController {
    private ContinueGame continueGame = new ContinueGame();

    // Game state variables
    private Player player;
    private HashMap<Integer, Room> rooms;
    private HashMap<String, Item> items;
    private HashMap<String, Puzzle> puzzles;
    private HashMap<String, Monster> monsters;

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

    // Additional methods to use the restored state in gameplay
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