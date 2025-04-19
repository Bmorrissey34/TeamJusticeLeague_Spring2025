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
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 */
public class Player {
    private String name; // Name of the player
    private int health; // Health of the player
    private int strength; // Strength of the player
    private Inventory inventory; // Player's inventory
    private Room currentRoom; // Current room the player is in
    private Item equippedItem; // The currently equipped item (weapon, etc.)


    public Player() {
        this.inventory = new Inventory();
    }

    /**
     * Method: move
     * 
     * Moves the player in a specified direction.
     * 
     * @param direction The direction to move.
     *                  Author: Dino Maksumic
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
     * @author William Stein
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
        } else {
            System.out.println("You didn't pickup any items yet.");
        }
    }
 
    /**
     * Method: hasItem
     * 
     * Verifies if player has an item in their inventory.
     * 
     * @param itemName The item name to search inventory for.
     * @author William Stein
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
     * Method: checkItem
     * 
     * Returns item description.
     * 
     * @param itemName The item to check.
     * @author William Stein
     */
    public void checkItem(String itemName) {
        ArrayList<Item> itemsOwned = inventory.getItems();
        if (hasItem(itemName)) {
            for (Item item : itemsOwned) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    System.out.println(item.getDescription());
                }
            }
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    /**
     * Method: pickupItem
     * 
     * Adds an item to the player's inventory.
     * 
     * @param itemName The item to pick up.
     * @author William Stein
     */
    public void pickupItem(String itemName) {
        DataLoader gameItems = new DataLoader();

        for (Item item : currentRoom.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                Item typedItem = gameItems.getItem(itemName);
                inventory.addItem(typedItem);
                System.out.println(item.getName()
                        + " has been picked up from the room and successfully added to the player's inventory");
                currentRoom.removeItem(item);
                return;
            }
        }
        System.out.println("Item is not in the current room.");
    }

    /**
     * Method: dropItem
     * 
     * Drops item from inventory and adds it to current room.
     * 
     * @param itemName The item to drop.
     * @author William Stein
     */
    public void dropItem(String itemName) {
        if (hasItem(itemName)) {
            ArrayList<Item> itemsOwned = inventory.getItems();
            for (Item item : itemsOwned) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    currentRoom.addItem(item);
                    inventory.removeItem(item);
                }
            }
        } else {
            System.out.println("Item is not in your inventory.");
        }
    }

    /**
     * Method: consumeItem
     * 
     * Uses consumable to restor player's health.
     * 
     * @param itemName The item to consume.
     * @author William Stein
     */
    public void consumeItem(String itemName) {
        if (!hasItem(itemName)) {
            System.out.println("Consumable is not in your inventory.");
        }

        for (Item item : inventory.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (!(item instanceof Consumable)) {
                    System.out.println("Item is not a consumable.");
                } else if (getHealth() == 100) {
                    System.out.println("Your health is already full.");
                } else {
                    Consumable consumable = (Consumable) item;
                    int healedAmount = consumable.getHealth();
                    int newHealth = Math.min(100, getHealth() + healedAmount);
                    setHealth(newHealth);

                    System.out
                            .println("You used " + consumable.getName() + " and are now at " + newHealth + " health.");

                    inventory.removeItem(item);
                }
            }
        }
    }

    /**
     * Method: useItem
     * 
     * Uses weapon from the player's inventory to attack monster. 
     * If this method returns -1, don't deal damage to monster and asks for a correct input.
     * 
     * @param itemName The item to use.
     * @return Int that represents damage dealt. 
     * @author William Stein
     */
    public int useItem(String itemName) {
        if (!hasItem(itemName)) {
            System.out.println("Weapon is not in your inventory.");
            return -1;
        }

        ArrayList<Item> itemsOwned = inventory.getItems();
        for (Item item : itemsOwned) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (item instanceof Weapon) {
                    Weapon weapon = (Weapon) item;
                    return getStrength() + weapon.getStrength();
                } else {
                    System.out.println("You cannot use a consumable to deal damage to a monster.");
                    return -1;
                }
            }
        }

        return -1;
    }

    /**
     * Method: fight
     * 
     * Engages the player in combat with a monster.
     * 
     * @param monster The monster to fight.
     */
    public void fight(Monster monster) {
    }

    /**
     * Method: flee
     * 
     * Allows the player to flee from combat.
     */
    public void flee() {
    }

    /**
     * Method: takeDamage
     * 
     * Reduces the player's health when they take damage.
     * 
     * @param damage The amount of damage taken.
     * @author William Stein
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
    public void setCurrentRoom(Room room) {
    }

    /**
     * Method: examine
     * 
     * Examines an object in the game.
     * 
     * @param object The object to examine.
     * @author Dino Maksumic
     */
    public void examine(Object object) {
        if (object instanceof Examine) {
            Examine examinable = (Examine) object;
            System.out.println(examinable.examine());
        } else {
            System.out.println("You can't examine that.");
        }
    }

    /**
     * Method: getEquippedItem, setEquippedItem, unequipItem
     * 
     * 
     * 
     * Author: Ademola Akiwowo
     */

    public Item getEquippedItem() {
        return equippedItem;
    }
    
    public void setEquippedItem(Item item) {
        this.equippedItem = item;
        System.out.println("You have equipped: " + item.getName());
    }

    public void unequipItem() {
        if (equippedItem != null) {
            System.out.println("You unequipped: " + equippedItem.getName());
            equippedItem = null;
        } else {
            System.out.println("You don't have anything equipped.");
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