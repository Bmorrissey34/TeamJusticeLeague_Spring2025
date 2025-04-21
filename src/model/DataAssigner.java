package src.model;

import java.util.HashMap;
import src.view.GameView;

/**
 * Class: DataAssigner
 * 
 * This class is responsible for assigning loaded data (rooms, items, puzzles, monsters) 
 * to their respective game objects and validating the data.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 *          Author: Brendan Morrissey
 */
public class DataAssigner {
    private GameView gameView = new GameView(); // Used for displaying messages to the player

    /**
     * Method: assignRooms
     * 
     * Assigns and validates room data, including their monsters, puzzles, and items.
     * 
     * @param rooms A HashMap containing room objects.
     * @param monsters A HashMap containing monster objects.
     * @param items A HashMap containing item objects.
     */
    public void assignRooms(HashMap<String, Room> rooms, HashMap<String, Monster> monsters, HashMap<String, Item> items) {
        for (Room room : rooms.values()) {
            if (room.getName() == null || room.getDescription() == null) {
                gameView.displayMessage("Error: Room data is incomplete. Room Name: " + room.getName());
            } else {
                gameView.displayMessage("Room assigned: " + room.getName());

                // Assign monster to room
                if (room.getMonsterID() != null && monsters.containsKey(room.getMonsterID())) {
                    room.setMonster(monsters.get(room.getMonsterID()));
                    gameView.displayMessage("  Monster in room: " + room.getMonster().getName());
                }

                // Assign items to room
                for (String itemID : room.getItemIDs()) {
                    if (items.containsKey(itemID)) {
                        room.addItem(items.get(itemID));
                    } else {
                        gameView.displayMessage("  Warning: Item ID not found: " + itemID);
                    }
                }

                if (!room.getItems().isEmpty()) {
                    gameView.displayMessage("  Items in room: " + room.getItemNames());
                }

                // Assign puzzle to room
                if (room.getPuzzle() != null) {
                    gameView.displayMessage("  Puzzle in room: " + room.getPuzzle().getQuestion());
                }
            }
        }
    }

    /**
     * Method: assignItems
     * 
     * Assigns and validates item data.
     * 
     * @param items A HashMap containing item objects.
     */
    public void assignItems(HashMap<String, Item> items) {
        for (Item item : items.values()) {
            if (item.getName() == null || item.getDescription() == null) {
                gameView.displayMessage("Error: Item data is incomplete. Item ID: " + item.getID());
            } else {
                gameView.displayMessage("Item assigned: " + item.getName());
            }
        }
    }

    /**
     * Method: assignPuzzles
     * 
     * Assigns and validates puzzle data.
     * 
     * @param puzzles A HashMap containing puzzle objects.
     */
    public void assignPuzzles(HashMap<String, Puzzle> puzzles) {
        for (Puzzle puzzle : puzzles.values()) {
            if (puzzle.getQuestion() == null || puzzle.getAnswer() == null) {
                gameView.displayMessage("Error: Puzzle data is incomplete. Puzzle ID: " + puzzle.hashCode());
            } else {
                gameView.displayMessage("Puzzle assigned: " + puzzle.getQuestion());
            }
        }
    }

    /**
     * Method: assignMonsters
     * 
     * Assigns and validates monster data.
     * 
     * @param monsters A HashMap containing monster objects.
     */
    public void assignMonsters(HashMap<String, Monster> monsters) {
        for (Monster monster : monsters.values()) {
            if (monster.getName() == null || monster.getDescription() == null) {
                gameView.displayMessage("Error: Monster data is incomplete. Monster ID: " + monster.hashCode());
            } else {
                gameView.displayMessage("Monster assigned: " + monster.getName());
            }
        }
    }
}
