package src.model;

import java.io.Serializable;
import java.util.Scanner;
import src.view.GameView;

/**
 * Class: Puzzle
 * 
 * This class represents a puzzle in the game, including its question, answer,
 * attempts, and solved state.
 * 
 * @version 1.0
 *          Course: ITEC XXXX Spring 2025
 *          Written: January 6, 2025
 * 
 */
public class Puzzle extends GameModel implements Examine, Serializable {
    private static final long serialVersionUID = 1L; // Add serialVersionUID for serialization

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
    public void start(Room currentRoom, Player player, GameView gameView) {
        if (solved) {
            gameView.displayMessage("You've already solved this puzzle.");
            return;
        }

        Scanner input = new Scanner(System.in);
        gameView.displayMessage("To play the puzzle, type 'Start puzzle' or you can skip by using the command 'Skip'");
        String userInput = input.nextLine().trim().toLowerCase();

        if (userInput.equals("start puzzle")) {
            gameView.displayMessage("Type 'Examine' to view puzzle or 'Skip' to ignore (-20 health per skip).");
            String choice = input.nextLine().trim().toLowerCase();

            if (choice.equals("examine")) {
                gameView.displayMessage(examine());
                gameView.displayMessage("Do you want to attempt the puzzle? Type 'Attempt puzzle' to continue.");
                String attemptInput = input.nextLine().trim().toLowerCase();

                if (attemptInput.equals("attempt puzzle")) {
                    gameView.displayMessage("Enter your answer:");
                    String userAnswer = input.nextLine().trim().toLowerCase();
                    attempt(userAnswer, currentRoom, player, gameView);
                }

            } else if (choice.equals("skip")) {
                skip(player, gameView);
            } else {
                gameView.displayMessage("Invalid choice.");
            }

        } else if (userInput.equals("skip")) {
            skip(player, gameView);
        } else {
            gameView.displayMessage("Invalid input. Exiting puzzle.");
        }
    }

    /**
     * Attempts to solve the puzzle.
     */
    public void attempt(String userAnswer, Room currentRoom, Player player, GameView gameView) {
        if (solved) {
            gameView.displayMessage("You have already solved this puzzle.");
            return;
        }

        attempts++;

        if (userAnswer.equalsIgnoreCase(answer)) {
            solved = true;
            solvedCount++;
            player.setHealth(player.getHealth()+10);
            gameView.displayMessage("Correct! Puzzle solved. (+10 health)");

            if (solvedCount == totalPuzzleCount) {
                gameView.displayMessage("You feel a surge of power... Thor's Hammer has dropped in this room!");

                Weapon thorsHammer = new Weapon("W10", "Thor’s Hammer",
                        "A legendary weapon that deals -80 health to a monster.", 80);

                currentRoom.addItem(thorsHammer);
            }

        } else {
            gameView.displayMessage("Incorrect. Try again or type 'Skip' to skip the puzzle. (-10 health)");
            player.takeDamage(10);
        }
    }

    /**
     * Skips the puzzle with a health penalty.
     */
    public void skip(Player player, GameView gameView) {
        gameView.displayMessage("You have chosen to skip this puzzle. (-20 health)");
        player.takeDamage(20);
    }

    @Override
    public String examine() {
        return "Puzzle: " + question +
               "\nSolved: " + (solved ? "Yes" : "No") +
               "\nAttempts: " + attempts;
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