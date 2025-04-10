package src.data;

import java.util.HashMap;

public class DataAssigner {
    public void assignRooms(HashMap<Integer, Room> rooms) {
        for (Room room : rooms.values()) {
            if (room.getName() == null || room.getDescription() == null) {
                System.out.println("Error: Room data is incomplete.");
            } else {
                System.out.println("Room assigned: " + room.getName());
            }
        }
    }

    public void assignItems(HashMap<String, Item> items) {
        for (Item item : items.values()) {
            if (item.getName() == null || item.getDescription() == null) {
                System.out.println("Error: Item data is incomplete.");
            } else {
                System.out.println("Item assigned: " + item.getName());
            }
        }
    }

    public void assignPuzzles(HashMap<String, Puzzle> puzzles) {
        for (Puzzle puzzle : puzzles.values()) {
            if (puzzle.getName() == null || puzzle.getDescription() == null) {
                System.out.println("Error: Puzzle data is incomplete.");
            } else {
                System.out.println("Puzzle assigned: " + puzzle.getName());
            }
        }
    }

    public void assignMonsters(HashMap<String, Monster> monsters) {
        for (Monster monster : monsters.values()) {
            if (monster.getName() == null || monster.getHealth() <= 0) {
                System.out.println("Error: Monster data is incomplete or invalid.");
            } else {
                System.out.println("Monster assigned: " + monster.getName());
            }
        }
    }
}
