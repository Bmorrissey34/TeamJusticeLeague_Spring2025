package src.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import src.view.GameView;

/**
 * Class: SaveGame
 * 
 * This class handles saving the current game state to a file.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 */
public class SaveGame {
    private GameView gameView = new GameView(); // Used for displaying messages to the player

    /**
     * Method: saveGame
     * 
     * Saves the current game state to the specified file path.
     * 
     * @param saveFilePath The file path where the game state will be saved.
     * @param gameState    The current game state to save.
     */
    public void saveGame(String saveFilePath, GameState gameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
            oos.writeObject(gameState);
            gameView.displayMessage("Game saved successfully!");
        } catch (IOException e) {
            gameView.displayMessage("Error saving game: " + e.getMessage());
        }
    }
}
