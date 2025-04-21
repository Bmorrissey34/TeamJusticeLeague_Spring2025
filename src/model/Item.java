package src.model;

import java.io.Serializable;

/**
 * Class: Item
 * 
 * This class represents an item in the game, including its effects and usage.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 * @author William Stein
 */
public class Item extends GameModel implements Examine, Serializable {
    private static final long serialVersionUID = 1L; // Add serialVersionUID for serialization
    private String type;
    private String id;

    /**
     * Constructor: Item
     * 
     * @param ID Unique number for item
     * @param name Item name
     * @param description Description of item
     */
    public Item(String ID, String name, String description) {
        super(ID, name, description);
        this.type = "Generic"; // Default type if not set
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String examine() {
        return "Item: " + getName() + "\nDescription: " + getDescription();
    }

    @Override
    public String toString() {
        return getName() + ": " + getDescription();
    }
}
