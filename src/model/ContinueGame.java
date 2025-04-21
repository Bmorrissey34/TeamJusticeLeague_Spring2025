package src.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

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
    private static final long serialVersionUID = 1L; 
    private static final String SAVE_DIRECTORY = "src/model/resources/data/"; 

    /**
     * Method: loadGame
     * 
     * Loads a saved game state from the specified file name.
     * 
     * @param fileName The name of the saved game file.
     * @return The loaded GameState object, or null if an error occurs.
     * @throws IOException If an error occurs while loading the game.
     */
    public GameState loadGame(String fileName) throws IOException {
        String fullPath = SAVE_DIRECTORY + fileName; // Append directory path
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
            return (GameState) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error loading game: Class not found.");
        }
    }
}
