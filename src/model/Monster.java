package src.model;

import java.util.HashMap;

// Placeholder for Monster
public class Monster {
    private int health;
    private int strength;
    private HashMap<String, Monster> monsters;

    public Monster() {
        this.monsters = new HashMap<>();
    }

    public void attack(Player player) {}
    public void takeDamage(int damage) {}
    public int getHealth() {
        return health;
    }
}