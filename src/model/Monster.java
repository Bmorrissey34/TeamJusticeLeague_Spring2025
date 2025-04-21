package src.model;

import java.util.HashMap;
import java.util.Random;
import java.io.Serializable;
import src.view.GameView;

/**
 * Class: Monster
 * 
 * This class represents a monster in the game, including its health, strength,
 * and actions such as attacking and taking damage.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 * @author Jordan Laudun
 */
public class Monster extends GameModel implements Examine, Serializable {
    private static final long serialVersionUID = 1L; // Add serialVersionUID for serialization
    private int health; // Health of the monster
    private int maxHealth; // Full Health of the monster
    private int strength; // Strength of the monster
    private boolean isBoss; // Determines if the monster is a boss
    private HashMap<String, Monster> monsters; // Stores monsters
    private transient GameView view = new GameView(); // Mark as transient to exclude from serialization
    private boolean isPassive; // Determines if the monster is passive

    public Monster() {
        this.monsters = new HashMap<>();
    }

    public Monster(String name, String description, int health, int strength, boolean isBoss) {
        setName(name);
        setDescription(description);
        this.health = health;
        this.maxHealth = health;
        this.strength = strength;
        this.isBoss = isBoss;
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
        if (!isPassive && health > 0) {
            view.displayMessage(getName() + " attacks " + player.getName() + " for " + strength + " damage!");
            player.takeDamage(strength);
        }
    }

    /**
     * Method: takeDamage
     * 
     * Reduces the monster's health when it takes damage.
     * 
     * @param damage The amount of damage taken.
     */
    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
        view.displayMessage(getName() + " takes " + damage + " damage. Remaining health: " + health);
        if (health == 0) {
            view.displayMessage(getName() + " has been defeated!");
        }
    }

    @Override
    public String examine() {
    return "Monster: " + getName() +
           "\nDescription: " + getDescription() +
           "\nHealth: " + health +
           "\nStrength: " + strength;
    }

    public boolean isDefeated() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public void setBoss(boolean boss) {
        isBoss = boss;
    }

    public HashMap<String, Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(HashMap<String, Monster> monsters) {
        this.monsters = monsters;
    }

    public boolean isPassive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isPassive'");
    }
}