package src.data;

import java.io.BufferedReader; 
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import src.model.Item;
import src.model.Monster;
import src.model.Puzzle;
import src.model.Room;
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
     * Method: loadRooms
     * 
     * Reads room data from a file and populates the rooms HashMap.
     * 
     * @param filePath The file path of the room data file.
     */
    public void loadRooms(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the first line (format description)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { // Skip blank lines
                    continue;
                }

                String[] parts = line.split(",", 6); // Adjusted to handle up to 6 parts
                if (parts.length < 2) {
                    gameView.displayMessage("Malformed room data: " + line);
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
                            room.addExits(exitParts[0].toUpperCase(), rooms.get(exitParts[1].hashCode()));
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
                        Monster monster = monsters.get(monsterName);
                        room.setMonster(monster);
                        room.setMonsterDefeated(monster.getHealth() <= 0); // Set defeated status
                    }
                }

                rooms.put(name.hashCode(), room);
            }
        } catch (IOException e) {
            gameView.displayMessage("Error loading rooms: " + e.getMessage());
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
            String line = br.readLine(); // Skip the first line (format description)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { // Skip blank lines
                    continue;
                }

                String[] parts = line.split(",", 2);
                if (parts.length < 2) {
                    gameView.displayMessage("Malformed item data: " + line);
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
            gameView.displayMessage("Error loading items: " + e.getMessage());
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
            String line = br.readLine(); // Skip the first line (format description)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { // Skip blank lines
                    continue;
                }

                String[] parts = line.split(",", 3);
                if (parts.length < 2) {
                    gameView.displayMessage("Malformed puzzle data: " + line);
                    continue;
                }

                String puzzleName = parts[0];
                String description = parts[1];
                boolean completed = parts.length > 2 && Boolean.parseBoolean(parts[2]);

                Puzzle puzzle = new Puzzle();
                puzzle.setName(puzzleName);
                puzzle.setDescription(description); // Assign description
                puzzle.setSolved(completed); // Updated to match Puzzle attributes
                puzzles.put(puzzleName, puzzle);
            }
        } catch (IOException e) {
            gameView.displayMessage("Error loading puzzles: " + e.getMessage());
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
            String line = br.readLine(); // Skip the first line (format description)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) { // Skip blank lines
                    continue;
                }

                String[] parts = line.split(",", 3);
                if (parts.length < 2) {
                    gameView.displayMessage("Malformed monster data: " + line);
                    continue;
                }

                String monsterName = parts[0];
                String description = parts[1];
                boolean defeated = parts.length > 2 && Boolean.parseBoolean(parts[2]);

                Monster monster = new Monster();
                monster.setName(monsterName);
                monster.setDescription(description); // Assign description
                monster.setHealth(defeated ? 0 : 100); // Set health based on defeated status
                monsters.put(monsterName, monster);
            }
        } catch (IOException e) {
            gameView.displayMessage("Error loading monsters: " + e.getMessage());
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
}