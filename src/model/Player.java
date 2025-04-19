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
    private GameView view = new GameView();

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
            view.displayMessage("You move " + direction + " into: " + nextRoom.getName());
            view.displayMessage(nextRoom.getDescription());
        } else {
            view.displayMessage("You can't go " + direction + " from here.");
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
                view.displayMessage(itemsOwned.get(i).getName());
                if (i < itemsOwned.size() - 1) {
                    view.displayMessage(", ");
                }
            }
        } else {
            view.displayMessage("You didn't pickup any items yet.");
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
     * Method: hasWeapon
     * 
     * Accessory method to pickup and swap to check if player has weapon.
     * 
     * @param inventory Player's inventory.
     * @author William Stein
     */
    public boolean hasWeapon(ArrayList<Item> inventory) {
        for (Item item: inventory) {
            if (item instanceof Weapon) {
                return true;
            } else {
                return false;
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
                    view.displayMessage(item.getDescription());
                }
            }
        } else {
            view.displayMessage("Item not found in inventory.");
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
                if (!hasWeapon(inventory.getItems())) {
                    inventory.addItem(typedItem);
                    view.displayMessage(item.getName()
                            + " has been picked up from the room and successfully added to the player's inventory");
                    currentRoom.removeItem(item);
                    return;
                } else {
                    view.displayMessage("You already have a weapon. Please use the swap command instead.");
                }

            }
        }
        view.displayMessage("Item is not in the current room.");
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
            view.displayMessage("Item is not in your inventory.");
        }
    }

    /**
     * Method: swapItem
     * 
     * Only one weapon can be in player's inventory. Use this to swap current weapon
     * for the one in current room.
     * 
     * @param itemName
     */

    public void swapItem(String itemName) {
        for (Item item : currentRoom.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {

            }
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
            view.displayMessage("Consumable is not in your inventory.");
        }

        for (Item item : inventory.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (!(item instanceof Consumable)) {
                    view.displayMessage("Item is not a consumable.");
                } else if (getHealth() == 100) {
                    view.displayMessage("Your health is already full.");
                } else {
                    Consumable consumable = (Consumable) item;
                    int healedAmount = consumable.getHealth();
                    int newHealth = Math.min(100, getHealth() + healedAmount);
                    setHealth(newHealth);

                    view.displayMessage(
                            "You used " + consumable.getName() + " and are now at " + newHealth + " health.");

                    inventory.removeItem(item);
                }
            }
        }
    }

    /**
     * Method: useItem
     * 
     * Uses weapon from the player's inventory to attack monster.
     * If this method returns -1, don't deal damage to monster and asks for a
     * correct input.
     * 
     * @param itemName The item to use.
     * @return Int that represents damage dealt.
     * @author William Stein
     */
    public int useItem(String itemName) {
        if (!hasItem(itemName)) {
            view.displayMessage("Weapon is not in your inventory.");
            return -1;
        }

        ArrayList<Item> itemsOwned = inventory.getItems();
        for (Item item : itemsOwned) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (item instanceof Weapon) {
                    Weapon weapon = (Weapon) item;
                    return getStrength() + weapon.getStrength();
                } else {
                    view.displayMessage("You cannot use a consumable to deal damage to a monster.");
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
            view.displayMessage(examinable.examine());
        } else {
            view.displayMessage("You can't examine that.");
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