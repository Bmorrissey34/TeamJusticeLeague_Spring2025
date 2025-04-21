package src.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    /**
     * Method: assignRooms
     * 
     * Assigns and validates room data, including their monsters, puzzles, and items.
     * 
     * @param rooms A HashMap containing room objects.
     * @param monsters A HashMap containing monster objects.
     * @param items A HashMap containing item objects.
     * @return List of validation messages.
     */
    public List<String> assignRooms(HashMap<String, Room> rooms) {
        List<String> messages = new ArrayList<>();
    
        for (Room room : rooms.values()) {
            if (room.getName() == null || room.getDescription() == null) {
                messages.add("Error: Room data is incomplete. Room Name: " + room.getName());
            } else {
                messages.add("Room assigned: " + room.getName());
    
                if (room.getMonster() != null) {
                    messages.add("  Monster in room: " + room.getMonster().getName());
                }
    
                if (!room.getItems().isEmpty()) {
                    messages.add("  Items in room: " + room.getItemNames());
                }
    
                if (room.getPuzzle() != null) {
                    messages.add("  Puzzle in room: " + room.getPuzzle().getQuestion());
                }
            }
        }
    
        return messages;
    }

    /**
     * Method: assignItems
     * 
     * Assigns and validates item data.
     * 
     * @param items A HashMap containing item objects.
     * @return List of validation messages.
     */
    public List<String> assignItems(HashMap<String, Item> items) {
        List<String> messages = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getName() == null || item.getDescription() == null) {
                messages.add("Error: Item data is incomplete. Item ID: " + item.getID());
            } else {
                messages.add("Item assigned: " + item.getName());
            }
        }
        return messages;
    }

    /**
     * Method: assignPuzzles
     * 
     * Assigns and validates puzzle data.
     * 
     * @param puzzles A HashMap containing puzzle objects.
     * @return List of validation messages.
     */
    public List<String> assignPuzzles(HashMap<String, Puzzle> puzzles) {
        List<String> messages = new ArrayList<>();
        for (Puzzle puzzle : puzzles.values()) {
            if (puzzle.getQuestion() == null || puzzle.getAnswer() == null) {
                messages.add("Error: Puzzle data is incomplete. Puzzle ID: " + puzzle.hashCode());
            } else {
                messages.add("Puzzle assigned: " + puzzle.getQuestion());
            }
        }
        return messages;
    }

    /**
     * Method: assignMonsters
     * 
     * Assigns and validates monster data.
     * 
     * @param monsters A HashMap containing monster objects.
     * @return List of validation messages.
     */
    public List<String> assignMonsters(HashMap<String, Monster> monsters) {
        List<String> messages = new ArrayList<>();
        for (Monster monster : monsters.values()) {
            if (monster.getName() == null || monster.getDescription() == null) {
                messages.add("Error: Monster data is incomplete. Monster ID: " + monster.hashCode());
            } else {
                messages.add("Monster assigned: " + monster.getName());
            }
        }
        return messages;
    }
}
