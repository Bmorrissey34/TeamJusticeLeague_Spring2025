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
    private HashMap<String, Room> rooms = new HashMap<>(); // Stores loaded rooms
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

                String[] parts = line.split("~", 6); // Adjusted to split into 6 fields
                if (parts.length < 6) { // Validate the number of fields
                    System.err.println("Malformed room data: " + line);
                    continue;
                }

                String name = parts[0].trim();
                String description = parts[1].trim();
                Room room = new Room();
                room.setName(name);
                room.setDescription(description);

                // Parse exits
                HashMap<String, String> exits = new HashMap<>();
                if (!parts[2].isEmpty()) {
                    String[] exitData = parts[2].split("\\|");
                    for (String exit : exitData) {
                        String[] exitParts = exit.split(":");
                        if (exitParts.length == 2) {
                            exits.put(exitParts[0].toUpperCase().trim(), exitParts[1].trim());
                        } else {
                            System.err.println("Malformed exit data: " + exit);
                        }
                    }
                }
                tempExits.put(name, exits);

                // Parse items
                if (!parts[3].isEmpty()) {
                    String[] itemIDs = parts[3].split("\\|");
                    for (String itemID : itemIDs) {
                        Item item = items.get(itemID.trim());
                        if (item != null) {
                            room.addItem(item);
                        } else {
                            System.err.println("Warning: Item not found for ID: " + itemID);
                        }
                    }
                }

                // Parse puzzle
                if (!parts[4].isEmpty()) {
                    Puzzle puzzle = puzzles.get(parts[4].trim());
                    if (puzzle != null) {
                        room.setPuzzle(puzzle);
                    } else {
                        System.err.println("Warning: Puzzle not found for ID: " + parts[4].trim());
                    }
                }

                // Parse monster
                if (!parts[5].isEmpty()) {
                    Monster monster = monsters.get(parts[5].trim());
                    if (monster != null) {
                        room.setMonster(monster);
                    } else {
                        System.err.println("Warning: Monster not found for ID: " + parts[5].trim());
                    }
                }

                rooms.put(name, room); // Use name as the key
            }

            // Resolve exits
            for (Room room : rooms.values()) {
                HashMap<String, Room> resolvedExits = new HashMap<>();
                HashMap<String, String> exits = tempExits.get(room.getName());
                if (exits != null) {
                    for (String direction : exits.keySet()) {
                        Room connectedRoom = rooms.get(exits.get(direction));
                        if (connectedRoom != null) {
                            resolvedExits.put(direction, connectedRoom);
                        } else {
                            System.err.println("Error: Exit points to a non-existent room: " + exits.get(direction));
                        }
                    }
                }
                room.setExits(resolvedExits);
            }

            // Check for starting room
            if (!rooms.containsKey("Entrance")) {
                System.err.println("Error: Starting room not found! Check your Rooms.txt file.");
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

                String[] parts = line.split(";", 4); // Adjusted to split into 4 fields
                if (parts.length < 4) { // Validate the number of fields
                    System.err.println("Malformed item data: " + line);
                    continue;
                }

                String type = parts[0].trim();
                String name = parts[1].trim();
                String description = parts[2].trim();
                int value;
                try {
                    value = Integer.parseInt(parts[3].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid value for item: " + line);
                    continue;
                }

                Item item;
                if (type.equalsIgnoreCase("Weapon")) {
                    item = new Weapon(name, name, description, value);
                } else if (type.equalsIgnoreCase("Consumable")) {
                    item = new Consumable(name, name, description, value);
                } else {
                    System.err.println("Unknown item type: " + type);
                    continue;
                }

                items.put(name, item); // Use name as the key
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

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public void setRooms(HashMap<String, Room> rooms) {
        this.rooms = rooms;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Item> items) {
        this.items = items;
    }

    public HashMap<String, Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(HashMap<String, Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

    public HashMap<String, Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(HashMap<String, Monster> monsters) {
        this.monsters = monsters;
    }

    public Item getItem(String itemName) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getItem'");
    }

    
}