package src.controller;

import src.data.DataLoader;
import src.model.Player;
import src.model.GameState;

public class GameController {
    private DataLoader dataLoader = new DataLoader();

    public void startGame() {
        // Load game data
        dataLoader.loadRooms("src/data/resources/Rooms.txt");
        dataLoader.loadItems("src/data/resources/Items.txt");
        dataLoader.loadPuzzles("src/data/resources/Puzzles.txt");
        dataLoader.loadMonsters("src/data/resources/Monsters.txt");

        // Additional game initialization logic
        System.out.println("Game data loaded successfully!");
    }

    public void saveGame(String saveFilePath) {
        GameState gameState = new GameState(
            new Player(), // Replace with the actual player object
            dataLoader.getRooms(),
            dataLoader.getItems(),
            dataLoader.getPuzzles(),
            dataLoader.getMonsters()
        );
        dataLoader.saveGame(saveFilePath, gameState);
    }

    public void loadGame(String saveFilePath) {
        GameState gameState = dataLoader.loadGame(saveFilePath);
        if (gameState != null) {
            // Restore game state
            System.out.println("Game loaded successfully!");
        }
    }

    public void displayMessage(String message) {}
    public void displayInventory(Player player) {}
    public void displayMap() {}
    public String getUserInput(String prompt) {
        return null;
    }
}