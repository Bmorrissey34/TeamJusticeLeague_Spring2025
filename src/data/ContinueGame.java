package src.data;

import src.model.GameState;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

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
