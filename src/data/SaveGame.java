package src.data;

import java.io.FileOutputStream;
import java.io.IOException; // Added import for GameView
import java.io.ObjectOutputStream;
import src.model.GameState;
import src.view.GameView;

public class SaveGame {
    private GameView gameView = new GameView(); // Added GameView instance

    public void saveGame(String saveFilePath, GameState gameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
            oos.writeObject(gameState);
            gameView.displayMessage("Game saved successfully!");
        } catch (IOException e) {
            gameView.displayMessage("Error saving game: " + e.getMessage());
        }
    }
}
