package src.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class DataLoader {
    private HashMap<Integer, Room> rooms = new HashMap<>();
    private HashMap<String, Item> items = new HashMap<>();
    private HashMap<String, Puzzle> puzzles = new HashMap<>();
    private HashMap<String, Monster> monsters = new HashMap<>();

    public void loadRooms(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 5);
                String name = parts[0];
                String description = parts[1];
                Room room = new Room();
                room.setName(name);
                room.setDescription(description);

                if (parts.length > 2) {
                    String[] exits = parts[2].split("\\|");
                    for (String exit : exits) {
                        String[] exitParts = exit.split(":");
                        if (exitParts.length == 2) {
                            room.addExits(exitParts[0].toUpperCase(), new Room());
                        }
                    }
                }

                rooms.put(name.hashCode(), room);
            }
        } catch (IOException e) {
            System.out.println("Error loading rooms: " + e.getMessage());
        }
    }

    public void loadItems(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                String itemName = parts[0];
                String description = parts[1];
                items.put(itemName, new Item());
            }
        } catch (IOException e) {
            System.out.println("Error loading items: " + e.getMessage());
        }
    }

    public void loadPuzzles(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                String puzzleName = parts[0];
                String description = parts[1];
                boolean completed = parts.length > 2 && Boolean.parseBoolean(parts[2]);

                Puzzle puzzle = new Puzzle();
                puzzle.setCompleted(completed);
                puzzles.put(puzzleName, puzzle);
            }
        } catch (IOException e) {
            System.out.println("Error loading puzzles: " + e.getMessage());
        }
    }

    public void loadMonsters(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                String monsterName = parts[0];
                String description = parts[1];
                boolean defeated = parts.length > 2 && Boolean.parseBoolean(parts[2]);

                Monster monster = new Monster();
                monster.setDefeated(defeated);
                monsters.put(monsterName, monster);
            }
        } catch (IOException e) {
            System.out.println("Error loading monsters: " + e.getMessage());
        }
    }

    public void saveGame(String saveFilePath, GameState gameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
            oos.writeObject(gameState);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public GameState loadGame(String saveFilePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFilePath))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
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