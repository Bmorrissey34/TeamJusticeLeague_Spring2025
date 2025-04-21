package src.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Class: SaveGame
 * 
 * This class handles saving the current game state to a file.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 */
public class SaveGame implements Serializable {
    private static final long serialVersionUID = 1L; // Add serialVersionUID for serialization
    private static final String SAVE_DIRECTORY = "src/model/resources/data/"; // Single save location

    /**
     * Method: saveGame
     * 
     * Saves the current game state to the specified file name.
     * 
     * @param fileName  The name of the file where the game state will be saved.
     * @param gameState The current game state to save.
     * @return String message indicating success or failure.
     */
    public String saveGame(String fileName, GameState gameState) {
        try {
            // Ensure the directory exists
            File saveFile = new File(SAVE_DIRECTORY + fileName);
            File parentDir = saveFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // Create the directory if it does not exist
            }

            // Save the game state
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                oos.writeObject(gameState);
                return "Game saved successfully to: " + saveFile.getAbsolutePath();
            }
        } catch (IOException e) {
            return "Error saving game: " + e.getMessage();
        }
    }
}
