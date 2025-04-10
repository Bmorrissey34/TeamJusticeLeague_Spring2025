package src.view;

import java.util.HashMap;
import java.io.File;

public class Map {
    private HashMap<String, Room> rooms;
    private String startingRoomID;

    public Map() {
        this.rooms = new HashMap<>();
    }

    public void readMap(File file) {}
    public void createRoom() {}
    public void printMap() {}
}
