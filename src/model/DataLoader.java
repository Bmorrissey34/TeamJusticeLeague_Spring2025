package src.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import src.view.GameView;

/**
 * Class: DataLoader
 * 
 * This class is responsible for loading game data (rooms, items, puzzles, monsters) 
 * from external files and populating the corresponding data structures.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 *          Author: Brendan Morrissey
 */
public class DataLoader {
    private GameView gameView = new GameView(); // Used for displaying messages to the player
    private HashMap<Integer, Room> rooms = new HashMap<>(); // Stores loaded rooms
    private HashMap<String, Item> items = new HashMap<>(); // Stores loaded items
    private HashMap<String, Puzzle> puzzles = new HashMap<>(); // Stores loaded puzzles
    private HashMap<String, Monster> monsters = new HashMap<>(); // Stores loaded monsters

    /**
     * Constructor: DataLoader
     * 
     * No args constructor to load items into HashMap. Used to retrieve typed items.
     */
    public DataLoader() {
        loadItems("src/model/resources/Items.txt");
        loadPuzzles("src/model/resources/Puzzles.txt");
        loadMonsters("src/model/resources/Monsters.txt");
        loadRooms("src/model/resources/Rooms.txt");
    }

    /**
     * Method: loadRooms
     * 
     * Reads room data from a file and populates the rooms HashMap.
     * 
     * @param filePath The file path of the room data file.
     */
    public void loadRooms(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the header line
            HashMap<String, HashMap<String, String>> tempExits = new HashMap<>();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";", 7); // Split by ';'
                if (parts.length < 6) {
                    System.err.println("Malformed room data: " + line);
                    continue;
                }

                String roomId = parts[0].trim();
                String name = parts[1].trim();
                String description = parts[2].trim();
                Room room = new Room();
                room.setID(roomId);
                room.setName(name);
                room.setDescription(description);

                // Parse exits
                HashMap<String, String> exits = new HashMap<>();
                if (!parts[3].isEmpty()) {
                    String[] exitData = parts[3].split("\\|");
                    for (String exit : exitData) {
                        String[] exitParts = exit.split(":");
                        if (exitParts.length == 2) {
                            exits.put(exitParts[0].toUpperCase().trim(), exitParts[1].trim());
                        }
                    }
                }
                tempExits.put(roomId, exits);

                // Parse items
                if (!parts[4].isEmpty()) {
                    String[] itemIds = parts[4].split("\\|");
                    for (String itemId : itemIds) {
                        Item item = items.get(itemId.trim());
                        if (item != null) room.addItem(item);
                    }
                }

                // Parse puzzle
                if (!parts[5].isEmpty()) {
                    Puzzle puzzle = puzzles.get(parts[5].trim());
                    if (puzzle != null) room.setPuzzle(puzzle);
                }

                // Parse monster
                if (!parts[6].isEmpty()) {
                    Monster monster = monsters.get(parts[6].trim());
                    if (monster != null) room.setMonster(monster);
                }

                rooms.put(roomId.hashCode(), room);
            }

            // Resolve exits
            for (Room room : rooms.values()) {
                HashMap<String, Room> resolvedExits = new HashMap<>();
                HashMap<String, String> exits = tempExits.get(room.getID());
                if (exits != null) {
                    for (String direction : exits.keySet()) {
                        Room connectedRoom = rooms.get(exits.get(direction).hashCode());
                        if (connectedRoom != null) resolvedExits.put(direction, connectedRoom);
                    }
                }
                room.setExits(resolvedExits);
            }
        } catch (IOException e) {
            System.err.println("Error loading rooms: " + e.getMessage());
        }
    }

    /**
     * Method: loadItems
     * 
     * Reads item data from a file and populates the items HashMap.
     * 
     * @param filePath The file path of the item data file.
     */
    public void loadItems(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("//")) continue; // Skip blank lines and comments

                String[] parts = line.split(";", 5); // Split by ';'
                if (parts.length < 5) {
                    System.err.println("Malformed item data: " + line);
                    continue;
                }

                String type = parts[0].trim();
                String itemId = parts[1].trim();
                String name = parts[2].trim();
                String description = parts[3].trim();
                int value;
                try {
                    value = Integer.parseInt(parts[4].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid value for item: " + line);
                    continue;
                }

                Item item;
                if (type.equalsIgnoreCase("Weapon")) {
                    item = new Weapon(itemId, name, description, value);
                } else if (type.equalsIgnoreCase("Consumable")) {
                    item = new Consumable(itemId, name, description, value);
                } else {
                    System.err.println("Unknown item type: " + type);
                    continue;
                }

                items.put(itemId, item); // Use item ID as the key
            }
        } catch (IOException e) {
            System.err.println("Error loading items: " + e.getMessage());
        }
    }

    /**
     * Method: loadPuzzles
     * 
     * Reads puzzle data from a file and populates the puzzles HashMap.
     * 
     * @param filePath The file path of the puzzle data file.
     */
    public void loadPuzzles(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";", 4); // Split by ';'
                if (parts.length < 4) {
                    System.err.println("Malformed puzzle data: " + line);
                    continue;
                }

                String puzzleName = parts[0].trim();
                String description = parts[1].trim();
                String answer = parts[2].trim();
                boolean solved = Boolean.parseBoolean(parts[3].trim());

                Puzzle puzzle = new Puzzle();
                puzzle.setQuestion(description);
                puzzle.setAnswer(answer);
                puzzle.setSolved(solved);

                puzzles.put(puzzleName, puzzle);
            }
        } catch (IOException e) {
            System.err.println("Error loading puzzles: " + e.getMessage());
        }
    }

    /**
     * Method: loadMonsters
     * 
     * Reads monster data from a file and populates the monsters HashMap.
     * 
     * @param filePath The file path of the monster data file.
     */
    public void loadMonsters(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";", 3); // Split by ';'
                if (parts.length < 3) {
                    System.err.println("Malformed monster data: " + line);
                    continue;
                }

                String monsterName = parts[0].trim();
                String description = parts[1].trim();
                boolean defeated = Boolean.parseBoolean(parts[2].trim());

                Monster monster = new Monster();
                monster.setName(monsterName);
                monster.setDescription(description);
                monster.setHealth(defeated ? 0 : 100);

                monsters.put(monsterName, monster);
            }
        } catch (IOException e) {
            System.err.println("Error loading monsters: " + e.getMessage());
        }
    }

    // Getter methods for accessing the loaded data
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

    public Item getItem(String itemName) {
        return items.get(itemName);
    }
}