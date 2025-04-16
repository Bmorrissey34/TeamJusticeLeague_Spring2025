package src.model;

/**
 * Class: Weapon
 * 
 * This class represents a weapon in the game, which is a type of item with
 * additional strength attributes.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 * William Strin
 */
public class Weapon extends Item {
    private int strength; // Strength of the weapon

    /**
     * Constructor: Weapon
     * 
     * @param ID Unique number for weapon
     * @param name Item weapon
     * @param description Description of weapon
     * @param strength Amount of strength added to player when the weapon is equipped
     */
    public Weapon(String ID, String name, String description, int strength) {
        super(ID, name, description);
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int Strength) {
        this.strength = strength;
    }
}
