package src.model;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Class: Puzzle
 * 
 * This class represents a puzzle in the game, including its question, answer,
 * attempts, and solved state.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 */
public class Puzzle extends GameModel implements Examine {
    private String question; // The question of the puzzle
    private String answer; // The answer to the puzzle
    private int attempts; // Number of attempts made by the player
    private boolean solved; // Whether the puzzle is solved
    private HashMap<String, Puzzle> puzzles; // Stores puzzles
    private static int solvedCount = 0; // Shared solved count across puzzles (for item drop condition)

    public Puzzle() {
        this.puzzles = new HashMap<>();
        this.solved = false;
        this.attempts = 0;
    }

    /**
     * Starts the puzzle interaction.
     */
    public void start() {
        if (solved) {
            System.out.println("You've already solved this puzzle.");
            return;
        }

        Scanner input = new Scanner(System.in);
        System.out.println("To play the puzzle, type 'Start puzzle' or you can skip by using the command 'Skip'");
        String userInput = input.nextLine().trim().toLowerCase();

        if (userInput.equals("start puzzle")) {
            System.out.println("Type 'Examine' to view puzzle or 'Skip' to ignore (-20 health per skip).");
            String choice = input.nextLine().trim().toLowerCase();

            if (choice.equals("examine")) {
                System.out.println(examine());
                System.out.println("Do you want to attempt the puzzle? Type 'Attempt puzzle' to continue.");
                String attemptInput = input.nextLine().trim().toLowerCase();

                if (attemptInput.equals("attempt puzzle")) {
                    System.out.println("Enter your answer:");
                    String userAnswer = input.nextLine().trim().toLowerCase();
                    attempt(userAnswer);
                }
            } else if (choice.equals("skip")) {
                skip();
            } else {
                System.out.println("Invalid choice.");
            }
        } else if (userInput.equals("skip")) {
            skip();
        } else {
            System.out.println("Invalid input. Exiting puzzle.");
        }
    }

    /**
     * Allows the player to attempt solving the puzzle.
     * @param userAnswer The player's answer to the puzzle.
     */
    public void attempt(String userAnswer) {
        if (solved) {
            System.out.println("You have already solved this puzzle.");
            return;
        }

        attempts++;

        if (userAnswer.equals(answer.toLowerCase())) {
            solved = true;
            solvedCount++;
            System.out.println("Correct! Puzzle solved. (+10 health)");

            if (solvedCount >= 4) {
                System.out.println("You feel a surge of power... Thor's Hammer has dropped in this room!");
                // TODO: Add logic to insert item into room
            }
        } else {
            System.out.println("Incorrect. Try again or type 'Skip' to skip the puzzle. (-10 health)");
        }
    }

    /**
     * Allows the player to skip the puzzle.
     */
    public void skip() {
        System.out.println("You have chosen to skip this puzzle. (-20 health)");
    }

    @Override
    public String examine() {
        return "Puzzle: " + question + "\nSolved: " + solved;
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public HashMap<String, Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(HashMap<String, Puzzle> puzzles) {
        this.puzzles = puzzles;
    }
}
