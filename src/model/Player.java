package src.model;

public class Player {
    private String name;
    private int health;
    private int strength;
    private Inventory inventory;
    private Room currentRoom;

    public Player() {
        this.inventory = new Inventory();
    }

    public void move(String direction) {}
    public void help() {}
    public void pickup(Item item) {}
    public void use(Item item) {}
    public void fight(Monster monster) {}
    public void flee() {}
    public void takeDamage(int damage) {}
    public void setCurrentRoom(Room room) {}
    public void examine(Object object) {}
}