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
 *          Written: April 18, 2025
 *          Author: Ademola Akiwowo
 */
public class Puzzle extends GameModel implements Examine {
    private String question;
    private String answer;
    private String location;
    private int attempts;
    private boolean solved;

    private static int solvedCount = 0;
    private static int totalPuzzleCount = 0;

    public Puzzle() {
        this.solved = false;
        this.attempts = 0;
    }

    /**
     * Starts the puzzle interaction flow (text-driven).
     */
    public void start(Room currentRoom, Player player) {
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
                    attempt(userAnswer, currentRoom, player);
                }
    
            } else if (choice.equals("skip")) {
                skip(player);
            } else {
                System.out.println("Invalid choice.");
            }
    
        } else if (userInput.equals("skip")) {
            skip(player);
        } else {
            System.out.println("Invalid input. Exiting puzzle.");
        }
    }
    

    /**
     * Attempts to solve the puzzle.
     */
    public void attempt(String userAnswer, Room currentRoom, Player player) {
        if (solved) {
            System.out.println("You have already solved this puzzle.");
            return;
        }

        attempts++;

        if (userAnswer.equalsIgnoreCase(answer)) {
            solved = true;
            solvedCount++;
            System.out.println("Correct! Puzzle solved. (+10 health)");

            if (solvedCount == totalPuzzleCount) {
                System.out.println("You feel a surge of power... Thor's Hammer has dropped in this room!");

                Weapon thorsHammer = new Weapon("W10", "Thor’s Hammer",
                        "A legendary weapon that deals -80 health to a monster.", 80);

                currentRoom.addItem(thorsHammer);
            }

        } else {
            System.out.println("Incorrect. Try again or type 'Skip' to skip the puzzle. (-10 health)");
        }
    }

    /**
     * Skips the puzzle with a health penalty.
     */
    public void skip(Player player) {
        System.out.println("You have chosen to skip this puzzle. (-20 health)");
        player.takeDamage(-20); 
    }

    @Override
    public String examine() {
        return "Puzzle: " + question + "\nSolved: " + solved;
    }

    // === Getters & Setters ===
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public static int getSolvedCount() {
        return solvedCount;
    }

    public static void resetSolvedCount() {
        solvedCount = 0;
    }

    public static int getTotalPuzzleCount() {
        return totalPuzzleCount;
    }

    public static void setTotalPuzzleCount(int count) {
        totalPuzzleCount = count;
    }
}