package src.model;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {}
    public void removeItem(Item item) {}
    public boolean checkItem(Item item) {
        return false;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
}