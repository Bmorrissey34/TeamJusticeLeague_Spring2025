package src.data;

import src.model.GameState;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class SaveGame {
    public void saveGame(String saveFilePath, GameState gameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
            oos.writeObject(gameState);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }
}
