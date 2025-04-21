package src.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
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
public class ContinueGame implements Serializable {
    private static final long serialVersionUID = 1L; // Add serialVersionUID for serialization
    private static final String SAVE_DIRECTORY = "src/model/resources/data/"; // Single save location
    private transient GameView gameView = new GameView(); // Mark as transient to exclude from serialization

    /**
     * Method: loadGame
     * 
     * Loads a saved game state from the specified file name.
     * 
     * @param fileName The name of the saved game file.
     * @return The loaded GameState object, or null if an error occurs.
     */
    public GameState loadGame(String fileName) {
        String fullPath = SAVE_DIRECTORY + fileName; // Append directory path
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            gameView.displayMessage("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
