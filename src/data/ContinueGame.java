package src.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import src.model.GameState;

public class ContinueGame {
    public GameState loadGame(String saveFilePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFilePath))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
