package src.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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
public class Player implements Serializable {
    private static final long serialVersionUID = 1L; // Add serialVersionUID for serialization
    private String name; // Name of the player
    private int health; // Health of the player
    private int strength; // Strength of the player
    private Inventory inventory; // Player's inventory
    private Room currentRoom; // Current room the player is in
    private transient GameView view = new GameView(); // Mark as transient to exclude from serialization
    private String dataLoader;

    // Constructor accepting playerName
    public Player(String playerName) {
        this.name = playerName;
        this.health = 100; // Set default health
        this.strength = 10; // Optional: Set default strength
        this.inventory = new Inventory();
    }

    /**
     * Method: move
     * 
     * Moves the player in a specified direction.
     * 
     * @param direction The direction to move.
     * @return String message indicating the result of the move.
     * @author Dino Maksumic
     */
    public String move(String direction) {
        Room nextRoom = currentRoom.getExits().get(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            return "You moved " + direction + " to " + currentRoom.getName();
        } else {
            return "You can't go that way.";
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
     * @return String message listing the items in the inventory.
     * @author William Stein
     */
    public String getInventory() {
        List<Item> itemsOwned = inventory.getItems();
        if (!itemsOwned.isEmpty()) {
            StringBuilder inventoryList = new StringBuilder();
            for (int i = 0; i < itemsOwned.size(); i++) {
                inventoryList.append(itemsOwned.get(i).getName());
                if (i < itemsOwned.size() - 1) {
                    inventoryList.append(", ");
                }
            }
            return inventoryList.toString();
        } else {
            return "You didn't pick up any items yet.";
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
        List<Item> itemsOwned = inventory.getItems();
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
    public boolean hasWeapon(List<Item> inventory) {
        for (Item item : inventory) {
            if (item instanceof Weapon) {
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
        List<Item> itemsOwned = inventory.getItems();
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
     * @return String message indicating the result of the pickup.
     * @author William Stein
     */
    public String pickupItem(String itemName) {
        for (Item item : currentRoom.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (!hasWeapon(inventory.getItems())) {
                    inventory.addItem(item);
                    currentRoom.removeItem(item);
                    return item.getName() + " has been picked up and added to your inventory.";
                } else {
                    return "You already have a weapon. Please use the swap command instead.";
                }
            }
        }
        return "Item is not in the current room.";
    }

    /**
     * Method: dropItem
     * 
     * Drops item from inventory and adds it to current room.
     * 
     * @param itemName The item to drop.
     * @return String message indicating the result of the drop.
     * @author William Stein
     */
    public String dropItem(String itemName) {
        if (hasItem(itemName)) {
            for (Item item : inventory.getItems()) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    currentRoom.addItem(item);
                    inventory.removeItem(item);
                    return itemName + " has been dropped in the room.";
                }
            }
        }
        return "Item is not in your inventory.";
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
        if (!hasWeapon(inventory.getItems())) {
            view.displayMessage("You do not have a weapon. Please use pickup method instead.");
            return;
        }

        for (Item roomItem : currentRoom.getItems()) {
            if (roomItem.getName().equalsIgnoreCase(itemName)) {
                if (!(roomItem instanceof Weapon)) {
                    view.displayMessage("Item is not a weapon and cannot be swapped out for current weapon.");
                    return;
                } else {
                    inventory.addItem(roomItem);
                    currentRoom.removeItem(roomItem);
                    break;
                }
            }
        }

        for (Item ownedItem : inventory.getItems()) {
            if (ownedItem instanceof Weapon) {
                currentRoom.addItem(ownedItem);
                inventory.removeItem(ownedItem);
                break;
            }
        }
    }

    /**
     * Method: consumeItem
     * 
     * Uses consumable to restore player's health.
     * 
     * @param itemName The item to consume.
     * @author William Stein
     */
    public int consumeItem(String itemName) {
        if (!hasItem(itemName)) {
            view.displayMessage("Consumable is not in your inventory.");
            return -1;
        }

        for (Item item : inventory.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (!(item instanceof Consumable)) {
                    view.displayMessage("Item is not a consumable.");
                    return -1;
                } else if (getHealth() == 100) {
                    view.displayMessage("Your health is already full.");
                    return -1;
                } else {
                    Consumable consumable = (Consumable) item;
                    int healedAmount = consumable.getHealth();
                    int newHealth = Math.min(100, getHealth() + healedAmount);
                    setHealth(newHealth);

                    view.displayMessage(
                            "You used " + consumable.getName() + " and are now at " + newHealth + " health.");

                    inventory.removeItem(item);
                    return healedAmount;
                }
            }
        }
        view.displayMessage("Consumable not found in inventory.");
        return -1;
    }

    /**
     * Method: useItem
     * 
     * Uses an item from the player's inventory.
     * If this method returns -1, don't deal damage to monster and asks for a
     * correct input.
     * 
     * @param itemName The item to use.
     * @return Int that represents damage dealt.
     * @author William Stein
     */
    public int useItem(String itemName) {
        if (!hasItem(itemName)) {
            view.displayMessage("Item is not in your inventory.");
            return -1;
        }

        for (Item item : inventory.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                if (item instanceof Weapon) {
                    // Equip the weapon (if you want only one weapon equipped, handle accordingly)
                    view.displayMessage("You equipped " + item.getName() + ".");
                    // Optionally, set a field for equipped weapon if you want to track it
                    return 0;
                } else if (item instanceof Consumable) {
                    return consumeItem(itemName); // Delegate to consumeItem and return health restored
                } else {
                    view.displayMessage("This item cannot be used.");
                    return -1;
                }
            }
        }
        view.displayMessage("Item not found in inventory.");
        return -1;
    }

    /**
     * Method: fight
     * 
     * Engages the player in combat with a monster.
     * 
     * @param monster The monster to fight.
     * @author Dino Maksumic
     */
    public void fight(Monster monster, GameView gameView) {
    Scanner scanner = new Scanner(System.in);
    gameView.displayMessage("You are fighting " + monster.getName() + "!");

    while (!monster.isDefeated() && this.health > 0) {
        // Player's Turn
        gameView.displayMessage("\n--- Your Turn ---");
        gameView.displayMessage("Your Health: " + this.health + " | Monster Health: " + monster.getHealth());
        gameView.displayMessage("Choose an action: [attack, use, flee]");
        String input = scanner.nextLine().trim().toLowerCase();

        switch (input) {
            case "attack":
                int damage = getStrength();  // weapon strength included
                gameView.displayMessage("You attack " + monster.getName() + " for " + damage + " damage.");
                monster.takeDamage(damage);
                break;

            case "use":
                String itemName = gameView.getUserInput("Enter item name to use:").trim();
                int result = useItem(itemName);
                if (result == -1) {
                    gameView.displayMessage("You can't use that item.");
                } else if (result == 0) {
                    gameView.displayMessage("You equipped a new weapon.");
                } else {
                    gameView.displayMessage("You used a consumable and restored " + result + " health.");
                }
                break;

            case "flee":
                gameView.displayMessage("You fled the battle!");
                return;

            default:
                gameView.displayMessage("Invalid action. Try again.");
                continue;  // Skip monster's turn if player input was invalid
        }

        // Monster's Turn (if still alive)
        if (!monster.isDefeated()) {
            gameView.displayMessage("\n--- Monster's Turn ---");
            monster.attack(this);
        }
    }

    // End of Battle
    if (this.health <= 0) {
        gameView.displayMessage("You were defeated by " + monster.getName() + "...");
    } else {
        gameView.displayMessage("You defeated " + monster.getName() + "!");
        currentRoom.removeMonster();  // Optional: mark monster as defeated
    }
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
        this.currentRoom = room;
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

    /**
     * Method: getStength
     * 
     * Returns player's base strength plus the weapon they have in their inventory.
     * 
     * @return Int representing strength of player plus weapon strengthn if applicable.
     * @author William Stein
     */
    public int getStrength() {
        int weaponStrength = 0;
        for (Item item : inventory.getItems()) {
            if (item instanceof Weapon) {
                Weapon weapon = (Weapon) item;
                weaponStrength = weapon.getStrength();
            }
        }
        return strength + weaponStrength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void addItemToInventory(Item item) {
        inventory.addItem(item);
    }

    public void removeItemFromInventory(Item item) {
        inventory.removeItem(item);
    }

    public List<Item> getItems() {
        return inventory.getItems(); 
    }

}