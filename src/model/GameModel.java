package src.model;

/**
 * Class: GameModel
 * 
 * This class serves as a base model for game objects, including attributes
 * such as ID, name, and description.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: April 15th, 2025
 *          Author: Dino maksumic
 */
public class GameModel {
    private String ID; // Unique identifier for the game object
    private String name; // Name of the game object
    private String description; // Description of the game object

    public GameModel() {
    }

    public GameModel(String ID, String name, String description) {
        this.ID = ID;
        this.name = name;
        this.description = description;
    }

   
    public String getID() {
        return ID;
    }

      public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

     public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

}