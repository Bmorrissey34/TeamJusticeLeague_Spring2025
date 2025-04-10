package src.data;

import java.util.HashMap;

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
                    System.out.println("  Monster defeated: " + room.isMonsterDefeated());
                }

                // Check puzzle status
                if (room.getPuzzle() != null) {
                    System.out.println("  Puzzle in room: " + room.getPuzzle().getName());
                    System.out.println("  Puzzle completed: " + room.isPuzzleCompleted());
                }
            }
        }
    }

    public void assignItems(HashMap<String, Item> items) {
        int itemCount = 0;
        for (Item item : items.values()) {
            if (item.getName() == null || item.getDescription() == null) {
                System.err.println("Error: Item data is incomplete. Item ID: " + item.hashCode());
            } else {
                System.out.println("Item assigned: " + item.getName());
                itemCount++;
            }
        }
        System.out.println("Total items assigned: " + itemCount);
    }

    public void assignPuzzles(HashMap<String, Puzzle> puzzles) {
        int puzzleCount = 0;
        for (Puzzle puzzle : puzzles.values()) {
            if (puzzle.getName() == null || puzzle.getDescription() == null) {
                System.err.println("Error: Puzzle data is incomplete. Puzzle ID: " + puzzle.hashCode());
            } else {
                System.out.println("Puzzle assigned: " + puzzle.getName());
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
                monsterCount++;
            }
        }
        System.out.println("Total monsters assigned: " + monsterCount);
    }
}
