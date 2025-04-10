package src.model;

import java.util.HashMap;

public class Item {
    private int effect;
    private HashMap<String, Item> items;

    public Item() {
        this.items = new HashMap<>();
    }

    public void use(Player player) {}
}