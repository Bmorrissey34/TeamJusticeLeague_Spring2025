package src.model;

import java.util.HashMap;

public class Puzzle {
    private String question;
    private String answer;
    private int attempts;
    private boolean solved;
    private HashMap<String, Puzzle> puzzles;

    public Puzzle() {
        this.puzzles = new HashMap<>();
    }

    public void start() {}
    public void attempt(String userAnswer) {}
    public void skip() {}
}