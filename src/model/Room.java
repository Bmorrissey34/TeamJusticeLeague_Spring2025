package src.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private ArrayList<Item> items;
    private Puzzle puzzle;
    private Monster monster;
    private HashMap<String, Room> exits;

    public Room() {
        this.items = new ArrayList<>();
        this.exits = new HashMap<>();
    }

    public void addExits(String direction, Room room) {}
    public void addItem(Item item) {}
    public void removeItem(Item item) {}
    public void removePuzzle() {}
    public void removeMonster() {}
}