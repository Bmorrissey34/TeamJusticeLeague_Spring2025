package src;

import src.controller.GameController;

public class start {
    public static void main(String[] args) {
        System.out.println("Initializing the game...");

        try {
            // Create and start the GameController
            GameController gameController = new GameController();
            gameController.initializeGame(); // Start the game through the controller
        } catch (Exception e) {
            System.err.println("An error occurred during game initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}