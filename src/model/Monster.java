package src.model;

import java.util.HashMap;

/**
 * Class: Monster
 * 
 * This class represents a monster in the game, including its health, strength,
 * and actions such as attacking and taking damage.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 */
public class Monster {
    private int health; // Health of the monster
    private int strength; // Strength of the monster
    private HashMap<String, Monster> monsters; // Stores monsters

    public Monster() {
        this.monsters = new HashMap<>();
    }

    /**
     * Method: attack
     * 
     * Allows the monster to attack a player.
     * 
     * @param player The player being attacked.
     */
    public void attack(Player player) {
        // ...existing code...
    }

    /**
     * Method: takeDamage
     * 
     * Reduces the monster's health when it takes damage.
     * 
     * @param damage The amount of damage taken.
     */
    public void takeDamage(int damage) {
        // ...existing code...
    }

    /**
     * Method: getHealth
     * 
     * Returns the current health of the monster.
     * 
     * @return The health of the monster.
     */
    public int getHealth() {
        return health;
    }
}