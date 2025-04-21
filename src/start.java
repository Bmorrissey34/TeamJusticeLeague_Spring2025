package src;

import src.controller.GameController;
import src.view.GameView;

public class start {
    public static void main(String[] args) {
        GameView gameView = new GameView();
        gameView.displayMessage("Initializing the game...");

        try {
            GameController gameController = new GameController();
            gameController.initializeGame();
        } catch (Exception e) {
            gameView.displayMessage("An error occurred during game initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}