package src.view;

import java.util.List;
import java.util.Scanner;

import src.model.Item;
import src.model.Monster;
import src.model.Player;



/**
 * Class: GameView
 * 
 * 
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: April 18, 2025
 *          Author: Ademola Akiwowo
 */

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
        System.out.println(message);
    }

    // Method to display the player's inventory
    public void displayInventory(List<Item> inventory) {
        if (inventory.isEmpty()) {
            displayMessage(" Your inventory is empty.");
        } else {
            displayMessage(" Inventory:");
            for (Item item : inventory) {
                System.out.println("- " + item.getName());
            }
        }
    }

    // Method to display the current map
    public void displayMap() {
        displayMessage(" ");
    }

    // Method to display combat-related information
    public void displayCombatInfo(Monster monster) {
        if (monster == null) {
            displayMessage("No monster in this room.");
        } else {
            displayMessage(" Monster encountered: " + monster.getName());
            displayMessage(monster.getDescription());
            displayMessage("Health: " + monster.getHealth());
            displayMessage("Attack Power: " + monster.getStrength()
            );
        }
    }

    // Method to display the player's current status (e.g., health, stats)
    public void displayPlayerStatus(Player player) {
        displayMessage(" Player Status:");
        displayMessage("Health: " + player.getHealth());
        displayMessage("Equipped Item: " + 
            (player.getEquippedItem() != null ? player.getEquippedItem().getName() : "None"));
    }

    // Method to display a puzzle or riddle
    public void displayPuzzle(String puzzleDescription) {
        displayMessage(" Puzzle: " + puzzleDescription);
    }

    // Method to display the result of a player's action
    public void displayActionResult(String result) {
        displayMessage(" " + result);
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