package src.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLoader {
    public void readRooms(File file) {}
    public void readItems(File file) {}
    public void readPuzzles(File file) {}
    public void readMonsters(File file) {}

    public List<String> loadDataFromTxt(File file) {
        List<String> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getPath());
        }
        return data;
    }
}