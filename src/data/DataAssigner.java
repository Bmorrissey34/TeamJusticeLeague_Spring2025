package src.data;

import java.util.HashMap;
import src.model.Item;
import src.model.Monster;
import src.model.Puzzle;
import src.model.Room;
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
 */
public class DataAssigner {
    private GameView gameView = new GameView(); // Used for displaying messages to the player

    /**
     * Method: assignRooms
     * 
     * Assigns and validates room data, including their monsters and puzzles.
     * 
     * @param rooms A HashMap containing room objects.
     */
    public void assignRooms(HashMap<Integer, Room> rooms) {
        for (Room room : rooms.values()) {
            if (room.getName() == null || room.getDescription() == null) {
                gameView.displayMessage("Error: Room data is incomplete. Room ID: " + room.hashCode());
            } else {
                gameView.displayMessage("Room assigned: " + room.getName());

                // Check monster status
                if (room.getMonster() != null) {
                    gameView.displayMessage("  Monster in room: " + room.getMonster().getName());
                    gameView.displayMessage("  Monster description: " + room.getMonster().getDescription());
                    gameView.displayMessage("  Monster health: " + room.getMonster().getHealth());
                    gameView.displayMessage("  Monster strength: " + room.getMonster().getStrength());
                    gameView.displayMessage("  Monster defeated: " + room.isMonsterDefeated());
                }

                // Check puzzle status
                if (room.getPuzzle() != null) {
                    gameView.displayMessage("  Puzzle in room: " + room.getPuzzle().getQuestion());
                    gameView.displayMessage("  Puzzle attempts: " + room.getPuzzle().getAttempts());
                    gameView.displayMessage("  Puzzle solved: " + room.getPuzzle().isSolved());
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
        int itemCount = 0;
        for (Item item : items.values()) {
            if (item.getItems() == null) {
                gameView.displayMessage("Error: Item data is incomplete. Item ID: " + item.hashCode());
            } else {
                gameView.displayMessage("Item assigned: " + item.getItems());
                gameView.displayMessage("  Item effect: " + item.getEffect());
                itemCount++;
            }
        }
        gameView.displayMessage("Total items assigned: " + itemCount);
    }

    /**
     * Method: assignPuzzles
     * 
     * Assigns and validates puzzle data.
     * 
     * @param puzzles A HashMap containing puzzle objects.
     */
    public void assignPuzzles(HashMap<String, Puzzle> puzzles) {
        int puzzleCount = 0;
        for (Puzzle puzzle : puzzles.values()) {
            if (puzzle.getQuestion() == null || puzzle.getAnswer() == null) {
                gameView.displayMessage("Error: Puzzle data is incomplete. Puzzle ID: " + puzzle.hashCode());
            } else {
                gameView.displayMessage("Puzzle assigned: " + puzzle.getQuestion());
                gameView.displayMessage("  Puzzle attempts: " + puzzle.getAttempts());
                gameView.displayMessage("  Puzzle solved: " + puzzle.isSolved());
                puzzleCount++;
            }
        }
        gameView.displayMessage("Total puzzles assigned: " + puzzleCount);
    }

    /**
     * Method: assignMonsters
     * 
     * Assigns and validates monster data.
     * 
     * @param monsters A HashMap containing monster objects.
     */
    public void assignMonsters(HashMap<String, Monster> monsters) {
        int monsterCount = 0;
        for (Monster monster : monsters.values()) {
            if (monster.getName() == null || monster.getHealth() <= 0) {
                gameView.displayMessage("Error: Monster data is incomplete or invalid. Monster ID: " + monster.hashCode());
            } else {
                gameView.displayMessage("Monster assigned: " + monster.getName());
                gameView.displayMessage("  Monster description: " + monster.getDescription());
                gameView.displayMessage("  Monster health: " + monster.getHealth());
                gameView.displayMessage("  Monster strength: " + monster.getStrength());
                monsterCount++;
            }
        }
        gameView.displayMessage("Total monsters assigned: " + monsterCount);
    }
}
