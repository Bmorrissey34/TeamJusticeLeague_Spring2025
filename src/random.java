package src;
public class Random {
    // This is a blank class
    // This is Dino
    // This is Will
    // This is Jordan
    // This is Ademola


	// TEST CHANGE FROM WILLIAM

// blah blah blah

}

// Example method to generate a random number between a given range
public static int generateRandomNumber(int min, int max) {
    if (min > max) {
        throw new IllegalArgumentException("Min cannot be greater than Max");
    }
    return (int) (Math.random() * (max - min + 1)) + min;
}

// Example main method to test the random number generator
public static void main(String[] args) {
    int randomNum = generateRandomNumber(1, 100);
    System.out.println("Generated Random Number: " + randomNum);
}