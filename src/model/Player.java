package src.model;

import java.util.ArrayList;

import src.view.GameView;

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
     * Author: Dino Maksumic
     */
    public void move(String direction) {
        Room nextRoom = currentRoom.getExits().get(direction.toUpperCase());
    
        if (nextRoom != null) {
            setCurrentRoom(nextRoom);
            System.out.println("You move " + direction + " into: " + nextRoom.getName());
            System.out.println(nextRoom.getDescription());
        } else {
            System.out.println("You can't go " + direction + " from here.");
        }
    }

    /**
     * Method: help
     * 
     * Displays help information for the player.
     */
    public void help() {
        GameView gameView = new GameView();
        gameView.displayHelpMenu();
    }

    /**
     * Method: getInventory
     * 
     * Prints item names from player's inventory
     * 
     * @return void
     * Author: William Stein
     */
    public void getInventory() {
        ArrayList<Item> itemsOwned = inventory.getItems();
        if (!itemsOwned.isEmpty()) {
            for (int i = 0; i < itemsOwned.size(); i++) {
                System.out.print(itemsOwned.get(i).getName());
                if (i < itemsOwned.size() - 1) {
                    System.out.print(", ");
                }
            }
        }
        else {
            System.out.println("You didn't pickup any items yet.");
        }
    }

    /**
     * Method: hasItem
     * 
     * Verifies if player has an item in their inventory.
     * 
     * @param itemName The item name to search inventory for.
     * Author: William Stein
     */
    public boolean hasItem(String itemName) {
        ArrayList<Item> itemsOwned = inventory.getItems();
        for (Item item : itemsOwned) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method: pickupItem
     * 
     * Adds an item to the player's inventory.
     * 
     * @param item The item to pick up.
     * Author: William Stein
     */
    public void pickupItem(Item item) {

    }

    /**
     * Method: consumeItem
     * 
     * Uses weapon from the player's inventory to attack monster.
     * 
     * @param item The item to use.
     * Author: William Stein
     */
    public void consumeItem(Item item) {

    }

    /**
     * Method: useItem
     * 
     * Uses consumable from the player's inventory to restor health.
     * 
     * @param item The item to use.
     * Author: William Stein
     */
    public void useItem(Item item) {

    }

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
     * Author: William Stein
     */
    public void takeDamage(int damage) {
        int playerHealth = getHealth();
        setHealth(playerHealth - damage);
    }

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
     * Author: Dino Maksumic
     */
    public void examine(Object object) {
        if (object instanceof Examine) {
            Examine examinable = (Examine) object;
            System.out.println(examinable.examine());
        } else {
            System.out.println("You can't examine that.");
        }
    }

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

    



    public Room getCurrentRoom() {
        return currentRoom;
    }
}