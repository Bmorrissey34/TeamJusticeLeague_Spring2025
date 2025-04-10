package src.model;

import java.io.Serializable;
import java.util.HashMap;

public class GameState implements Serializable {
    private Player player;
    private HashMap<Integer, Room> rooms;
    private HashMap<String, Item> items;
    private HashMap<String, Puzzle> puzzles;
    private HashMap<String, Monster> monsters;

    public GameState(Player player, HashMap<Integer, Room> rooms, HashMap<String, Item> items,
                     HashMap<String, Puzzle> puzzles, HashMap<String, Monster> monsters) {
        this.player = player;
        this.rooms = rooms;
        this.items = items;
        this.puzzles = puzzles;
        this.monsters = monsters;
    }

    public Player getPlayer() {
        return player;
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