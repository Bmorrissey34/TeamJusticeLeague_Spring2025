package src.model;

/**
 * Class: Consumable
 * 
 * This class represents a consumable item in the game, which can increase the
 * player's health.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 * Author: William Stein
 */
public class Consumable extends Item {
    private int health; // The health value restored by the consumable

    /**
     * Constructor: Consumable
     * 
     * @param ID Unique number for consumable
     * @param name Item consumable
     * @param description Description of consumable
     * @param health Amount of health restored upon using consumable
     */
    public Consumable(String ID, String name, String description, int health) {
        super(ID, name, description);
        this.health = health;
    }

    /**
     * Method: increaseHealth
     * 
     * Increases the player's health when the consumable is used.
     */
    public void increaseHealth() {}
}
