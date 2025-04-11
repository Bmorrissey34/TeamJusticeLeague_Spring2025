package src.data;

import java.util.HashMap; // Added import for GameView
import src.model.Item;
import src.model.Monster;
import src.model.Puzzle;
import src.model.Room;
import src.view.GameView;

public class DataAssigner {
    private GameView gameView = new GameView(); // Added GameView instance

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
                    gameView.displayMessage("  Puzzle answer: " + room.getPuzzle().getAnswer());
                    gameView.displayMessage("  Puzzle attempts: " + room.getPuzzle().getAttempts());
                    gameView.displayMessage("  Puzzle solved: " + room.getPuzzle().isSolved());
                }
            }
        }
    }

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

    public void assignPuzzles(HashMap<String, Puzzle> puzzles) {
        int puzzleCount = 0;
        for (Puzzle puzzle : puzzles.values()) {
            if (puzzle.getQuestion() == null || puzzle.getAnswer() == null) {
                gameView.displayMessage("Error: Puzzle data is incomplete. Puzzle ID: " + puzzle.hashCode());
            } else {
                gameView.displayMessage("Puzzle assigned: " + puzzle.getQuestion());
                gameView.displayMessage("  Puzzle answer: " + puzzle.getAnswer());
                gameView.displayMessage("  Puzzle attempts: " + puzzle.getAttempts());
                gameView.displayMessage("  Puzzle solved: " + puzzle.isSolved());
                puzzleCount++;
            }
        }
        gameView.displayMessage("Total puzzles assigned: " + puzzleCount);
    }

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
