package src.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataLoader {
    private HashMap<Integer, Room> rooms = new HashMap<>();
    private HashMap<String, Item> items = new HashMap<>();
    private HashMap<String, Puzzle> puzzles = new HashMap<>();
    private HashMap<String, Monster> monsters = new HashMap<>();

    public void loadRooms(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the first line (format description)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { // Skip blank lines
                    continue;
                }

                String[] parts = line.split(",", 6); // Adjusted to handle up to 6 parts
                if (parts.length < 2) {
                    System.err.println("Malformed room data: " + line);
                    continue;
                }

                String name = parts[0];
                String description = parts[1];
                Room room = new Room();
                room.setName(name);
                room.setDescription(description);

                // Parse exits
                if (parts.length > 2 && !parts[2].isEmpty()) {
                    String[] exits = parts[2].split("\\|");
                    for (String exit : exits) {
                        String[] exitParts = exit.split(":");
                        if (exitParts.length == 2) {
                            room.addExits(exitParts[0].toUpperCase(), new Room(exitParts[1]));
                        }
                    }
                }

                // Parse items
                if (parts.length > 3 && !parts[3].isEmpty()) {
                    String[] itemNames = parts[3].split("\\|");
                    for (String itemName : itemNames) {
                        if (items.containsKey(itemName)) {
                            room.addItem(items.get(itemName));
                        }
                    }
                }

                // Parse puzzle
                if (parts.length > 4 && !parts[4].isEmpty()) {
                    String puzzleName = parts[4];
                    if (puzzles.containsKey(puzzleName)) {
                        room.setPuzzle(puzzles.get(puzzleName));
                    }
                }

                // Parse monster
                if (parts.length > 5 && !parts[5].isEmpty()) {
                    String monsterName = parts[5];
                    if (monsters.containsKey(monsterName)) {
                        room.setMonster(monsters.get(monsterName));
                    }
                }

                rooms.put(name.hashCode(), room);
            }
        } catch (IOException e) {
            System.err.println("Error loading rooms: " + e.getMessage());
        }
    }

    public void loadItems(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the first line (format description)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { // Skip blank lines
                    continue;
                }

                String[] parts = line.split(",", 2);
                if (parts.length < 2) {
                    System.err.println("Malformed item data: " + line);
                    continue;
                }

                String itemName = parts[0];
                String description = parts[1];
                Item item = new Item();
                item.setName(itemName);
                item.setDescription(description); // Assign description
                items.put(itemName, item);
            }
        } catch (IOException e) {
            System.err.println("Error loading items: " + e.getMessage());
        }
    }

    public void loadPuzzles(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the first line (format description)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { // Skip blank lines
                    continue;
                }

                String[] parts = line.split(",", 3);
                if (parts.length < 2) {
                    System.err.println("Malformed puzzle data: " + line);
                    continue;
                }

                String puzzleName = parts[0];
                String description = parts[1];
                boolean completed = parts.length > 2 && Boolean.parseBoolean(parts[2]);

                Puzzle puzzle = new Puzzle();
                puzzle.setName(puzzleName);
                puzzle.setDescription(description); // Assign description
                puzzle.setCompleted(completed);
                puzzles.put(puzzleName, puzzle);
            }
        } catch (IOException e) {
            System.err.println("Error loading puzzles: " + e.getMessage());
        }
    }

    public void loadMonsters(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the first line (format description)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { // Skip blank lines
                    continue;
                }

                String[] parts = line.split(",", 3);
                if (parts.length < 2) {
                    System.err.println("Malformed monster data: " + line);
                    continue;
                }

                String monsterName = parts[0];
                String description = parts[1];
                boolean defeated = parts.length > 2 && Boolean.parseBoolean(parts[2]);

                Monster monster = new Monster();
                monster.setName(monsterName);
                monster.setDescription(description); // Assign description
                monster.setDefeated(defeated);
                monsters.put(monsterName, monster);
            }
        } catch (IOException e) {
            System.err.println("Error loading monsters: " + e.getMessage());
        }
    }

    public HashMap<Integer, Room> getRooms() {
        return rooms;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public HashMap<String, Puzzle> getPuzzles() {
        return puzzles;
    }

    public HashMap<String, Monster> getMonsters() {
        return monsters;
    }
}