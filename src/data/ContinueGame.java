package src.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import src.model.GameState;
import src.view.GameView;

/**
 * Class: ContinueGame
 * 
 * This class handles loading a previously saved game state from a file.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 *          Author: Brendan Morrissey
 */
public class ContinueGame {
    private GameView gameView = new GameView(); // Used for displaying messages to the player

    /**
     * Method: loadGame
     * 
     * Loads a saved game state from the specified file path.
     * 
     * @param saveFilePath The file path of the saved game state.
     * @return The loaded GameState object, or null if an error occurs.
     */
    public GameState loadGame(String saveFilePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFilePath))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            gameView.displayMessage("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
