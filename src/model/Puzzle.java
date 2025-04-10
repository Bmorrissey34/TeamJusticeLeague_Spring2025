package src.model;

import java.util.HashMap;

/**
 * Class: Puzzle
 * 
 * This class represents a puzzle in the game, including its question, answer,
 * attempts, and solved state.
 * 
 * @version 1.0
 * Course: ITEC XXXX Spring 2025
 * Written: January 6, 2025
 */
public class Puzzle {
    private String question; // The question of the puzzle
    private String answer; // The answer to the puzzle
    private int attempts; // Number of attempts made by the player
    private boolean solved; // Whether the puzzle is solved
    private HashMap<String, Puzzle> puzzles; // Stores puzzles

    public Puzzle() {
        this.puzzles = new HashMap<>();
    }

    /**
     * Method: start
     * 
     * Starts the puzzle interaction.
     */
    public void start() {}

    /**
     * Method: attempt
     * 
     * Allows the player to attempt solving the puzzle.
     * 
     * @param userAnswer The player's answer to the puzzle.
     */
    public void attempt(String userAnswer) {}

    /**
     * Method: skip
     * 
     * Allows the player to skip the puzzle.
     */
    public void skip() {}
}