package src.model;

import java.util.ArrayList;

/**
 * Class: Inventory
 * 
 * This class represents the player's inventory, which stores items and provides
 * methods to manage them.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 * @author William Stein
 */
public class Inventory {
    private ArrayList<Item> items; // List of items in the inventory

    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Method: addItem
     * 
     * Adds an item to the inventory that is picked up by player.
     * 
     * @param item The item to add.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Method: removeItem
     * 
     * Removes an item from the inventory that is dropped by the player.
     * 
     * @param item The item to remove.
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Method: getItems
     * 
     * Retrieves the list of items in the inventory.
     * 
     * @return The list of items.
     */
    public ArrayList<Item> getItems() {
        return items;
    }
}