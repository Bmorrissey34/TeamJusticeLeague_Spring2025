package src.model;

/**
 * Class: Player
 * 
 * This class represents the player in the game, including their attributes,
 * inventory, and actions such as moving, fighting, and using items.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 */
public class Player {
    private String name; // Name of the player
    private int health; // Health of the player
    private int strength; // Strength of the player
    private Inventory inventory; // Player's inventory
    private Room currentRoom; // Current room the player is in

    public Player() {
        this.inventory = new Inventory();
    }

    /**
     * Method: move
     * 
     * Moves the player in a specified direction.
     * 
     * @param direction The direction to move.
     */
    public void move(String direction) {}

    /**
     * Method: help
     * 
     * Displays help information for the player.
     */
    public void help() {}

    /**
     * Method: pickup
     * 
     * Adds an item to the player's inventory.
     * 
     * @param item The item to pick up.
     */
    public void pickup(Item item) {}

    /**
     * Method: use
     * 
     * Uses an item from the player's inventory.
     * 
     * @param item The item to use.
     */
    public void use(Item item) {}

    /**
     * Method: fight
     * 
     * Engages the player in combat with a monster.
     * 
     * @param monster The monster to fight.
     */
    public void fight(Monster monster) {}

    /**
     * Method: flee
     * 
     * Allows the player to flee from combat.
     */
    public void flee() {}

    /**
     * Method: takeDamage
     * 
     * Reduces the player's health when they take damage.
     * 
     * @param damage The amount of damage taken.
     */
    public void takeDamage(int damage) {}

    /**
     * Method: setCurrentRoom
     * 
     * Sets the current room the player is in.
     * 
     * @param room The room to set as the current room.
     */
    public void setCurrentRoom(Room room) {}

    /**
     * Method: examine
     * 
     * Examines an object in the game.
     * 
     * @param object The object to examine.
     */
    public void examine(Object object) {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}