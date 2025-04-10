package src.data;

import java.util.HashMap;
import src.model.Item;
import src.model.Monster;
import src.model.Puzzle;
import src.model.Room;

public class DataAssigner {
    public void assignRooms(HashMap<Integer, Room> rooms) {
        for (Room room : rooms.values()) {
            if (room.getName() == null || room.getDescription() == null) {
                System.err.println("Error: Room data is incomplete. Room ID: " + room.hashCode());
            } else {
                System.out.println("Room assigned: " + room.getName());

                // Check monster status
                if (room.getMonster() != null) {
                    System.out.println("  Monster in room: " + room.getMonster().getName());
                    System.out.println("  Monster description: " + room.getMonster().getDescription());
                    System.out.println("  Monster health: " + room.getMonster().getHealth());
                    System.out.println("  Monster strength: " + room.getMonster().getStrength());
                    System.out.println("  Monster defeated: " + room.isMonsterDefeated());
                }

                // Check puzzle status
                if (room.getPuzzle() != null) {
                    System.out.println("  Puzzle in room: " + room.getPuzzle().getQuestion());
                    System.out.println("  Puzzle answer: " + room.getPuzzle().getAnswer());
                    System.out.println("  Puzzle attempts: " + room.getPuzzle().getAttempts());
                    System.out.println("  Puzzle solved: " + room.getPuzzle().isSolved());
                }
            }
        }
    }

    public void assignItems(HashMap<String, Item> items) {
        int itemCount = 0;
        for (Item item : items.values()) {
            if (item.getItems() == null) {
                System.err.println("Error: Item data is incomplete. Item ID: " + item.hashCode());
            } else {
                System.out.println("Item assigned: " + item.getItems());
                System.out.println("  Item effect: " + item.getEffect());
                itemCount++;
            }
        }
        System.out.println("Total items assigned: " + itemCount);
    }

    public void assignPuzzles(HashMap<String, Puzzle> puzzles) {
        int puzzleCount = 0;
        for (Puzzle puzzle : puzzles.values()) {
            if (puzzle.getQuestion() == null || puzzle.getAnswer() == null) {
                System.err.println("Error: Puzzle data is incomplete. Puzzle ID: " + puzzle.hashCode());
            } else {
                System.out.println("Puzzle assigned: " + puzzle.getQuestion());
                System.out.println("  Puzzle answer: " + puzzle.getAnswer());
                System.out.println("  Puzzle attempts: " + puzzle.getAttempts());
                System.out.println("  Puzzle solved: " + puzzle.isSolved());
                puzzleCount++;
            }
        }
        System.out.println("Total puzzles assigned: " + puzzleCount);
    }

    public void assignMonsters(HashMap<String, Monster> monsters) {
        int monsterCount = 0;
        for (Monster monster : monsters.values()) {
            if (monster.getName() == null || monster.getHealth() <= 0) {
                System.err.println("Error: Monster data is incomplete or invalid. Monster ID: " + monster.hashCode());
            } else {
                System.out.println("Monster assigned: " + monster.getName());
                System.out.println("  Monster description: " + monster.getDescription());
                System.out.println("  Monster health: " + monster.getHealth());
                System.out.println("  Monster strength: " + monster.getStrength());
                monsterCount++;
            }
        }
        System.out.println("Total monsters assigned: " + monsterCount);
    }
}
