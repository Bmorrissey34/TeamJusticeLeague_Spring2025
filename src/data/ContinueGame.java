package src.data;

import java.io.FileInputStream;
import java.io.IOException; // Added import for GameView
import java.io.ObjectInputStream;
import src.model.GameState;
import src.view.GameView;

public class ContinueGame {
    private GameView gameView = new GameView(); // Added GameView instance

    public GameState loadGame(String saveFilePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFilePath))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            gameView.displayMessage("Error loading game: " + e.getMessage());
            return null;
        }
    }
}
