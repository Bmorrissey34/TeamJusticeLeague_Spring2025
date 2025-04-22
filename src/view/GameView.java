package src.view;

import java.util.Scanner;
import src.model.*;

public class GameView {
    private Scanner scanner;

    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    // Method to get user input
    public String getUserInput(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }

    // Method to display a message to the player
    public void displayMessage(String message) {
        clearConsole(); // Clear the console before displaying the message
        System.out.println(message);
        displaySeparator(); // Add separator after each message
    }

    // Method to display a separator
    private void displaySeparator() {
        System.out.println("=============================");
    }

    /**
     * Clears the console screen.
     */
    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Method to display the player's inventory
    public void displayInventory(Player player) {
        displayMessage("Inventory: " + player.getInventory());
    }

    // Method to display the current map
    public void displayMap() {
        Map map = new Map();
        map.printMap(); // Display the map
        displaySeparator(); // Add separator after the map
    }

    // Method to display combat options and menu
    public void displayCombatOptions() {
        displayMessage("Choose an action: [attack, use, flee]");
    }

    // Method to display combat-related information
    public void displayCombatInfo(Monster monster) {
        if (monster == null) {
            displayMessage("No monster in this room.");
        } else {
            displayMessage("Monster encountered: " + monster.getName());
            displayMessage(monster.getDescription());
            displayMessage("Health: " + monster.getHealth());
            displayMessage("Attack Power: " + monster.getStrength());
        }
    }

    // Method to display the player's current status (e.g., health, stats)
    public void displayPlayerStatus(Player player) {
        displayMessage("Player Status:");
        displayMessage("Health: " + player.getHealth());
        displayMessage("Strength: " + player.getStrength());
    }

    // Method to display a puzzle or riddle
    public void displayPuzzle(String puzzleDescription) {
        displayMessage("Puzzle: " + puzzleDescription);
    }

    /**
     * Method: displayPuzzleInteraction
     * 
     * Handles the interaction for solving a puzzle.
     * 
     * @param puzzle The puzzle to solve.
     * @return The player's answer to the puzzle.
     */
    public String displayPuzzleInteraction(Puzzle puzzle) {
        displayMessage("Puzzle: " + puzzle.getQuestion());
        return getUserInput("Enter your answer (or type 'skip' to skip the puzzle):");
    }

    // Method to display the result of a player's action
    public void displayActionResult(String result) {
        displayMessage(result);
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

    /**
     * Method: displayRoom
     * 
     * Displays the details of the current room to the player.
     * 
     * @param room The room to display.
     */
    public void displayRoom(Room room) {
        if (room != null) {
            System.out.println("You are in: " + room.getName());
            System.out.println(room.getDescription());
        } else {
            System.out.println("The room is undefined.");
        }
        displaySeparator(); // Add separator after room details
    }

    /**
     * Method: displayGameOver
     * 
     * Displays the game over message.
     */
    public void displayGameOver() {
        displayMessage("You have tragically died, your nightmares have become your reality.");
    }
}