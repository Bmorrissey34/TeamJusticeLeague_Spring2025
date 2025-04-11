package src.view;

import java.util.Scanner;

public class GameView {
    private Scanner scanner;

    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    // Method to get user input
    public String getUserInput(String prompt) {
        // Placeholder for getting user input
        return null;
    }

    // Method to display a message to the player
    public void displayMessage(String message) {
        // Placeholder for displaying a message
    }

    // Method to display the player's inventory
    public void displayInventory() {
        // Placeholder for displaying the player's inventory
    }

    // Method to display the current map
    public void displayMap() {
        // Placeholder for displaying the map
    }

    // Method to display combat-related information
    public void displayCombatInfo() {
        // Placeholder for displaying combat information
    }

    // Method to display the player's current status (e.g., health, stats)
    public void displayPlayerStatus() {
        // Placeholder for displaying the player's status
    }

    // Method to display a puzzle or riddle
    public void displayPuzzle(String puzzleDescription) {
        // Placeholder for displaying a puzzle
    }

    // Method to display the result of a player's action
    public void displayActionResult(String result) {
        // Placeholder for displaying the result of an action
    }

    // Method to display the help menu
    public void displayHelpMenu() {
        displayMessage("Available Commands:");
        displayMessage("- <direction>: Move in a direction (e.g., N, NW, S, SW, etc.).");
        displayMessage("- pickup <item>: Pick up an item in the room.");
        displayMessage("- use <item>: Use an item from your inventory.");
        displayMessage("- map: Display the map.");
        displayMessage("- help: Display this help menu.");
        displayMessage("- save: Save the game.");
        displayMessage("- quit: Quit the game.");
    }
}