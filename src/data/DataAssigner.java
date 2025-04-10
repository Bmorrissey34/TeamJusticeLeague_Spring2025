package src.data;

import java.util.HashMap;

public class DataAssigner {
    public void assignRooms(HashMap<Integer, Room> rooms) {
        for (Room room : rooms.values()) {
            System.out.println("Room assigned: " + room.getName());
        }
    }

    public void assignItems(HashMap<String, Item> items) {
        for (Item item : items.values()) {
            System.out.println("Item assigned: " + item);
        }
    }

    public void assignPuzzles(HashMap<String, Puzzle> puzzles) {
        for (Puzzle puzzle : puzzles.values()) {
            System.out.println("Puzzle assigned: " + puzzle);
        }
    }

    public void assignMonsters(HashMap<String, Monster> monsters) {
        for (Monster monster : monsters.values()) {
            System.out.println("Monster assigned: " + monster.getHealth());
        }
    }
}
