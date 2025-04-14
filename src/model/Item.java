package src.model;

import java.util.HashMap;

/**
 * Class: Item
 * 
 * This class represents an item in the game, including its effects and usage.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 */
public class Item extends GameModel implements Examine {
    private int effect; // Effect of the item
    private HashMap<String, Item> items; // Stores items

    public Item() {
        this.items = new HashMap<>();
    }

    /**
     * Method: use
     * 
     * Uses the item and applies its effect to the player.
     * 
     * @param player The player using the item.
     */
    public void use(Player player) {}

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Item> items) {
        this.items = items;
    }

    @Override
    public String examine() {
        return "Item: " + getName() + "\nDescription: " + getDescription();
    }
}
