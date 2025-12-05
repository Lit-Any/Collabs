import java.util.*;
import utility.*;

/**
 * This program checks whether a given positive integer is an Armstrong (Angstrom) number.
 * An Armstrong number is a number that is equal to the sum of its own digits each raised
 * to the power of the number of digits.
 * The program allows repeated checks until the user chooses to exit.
 */
public class Num_Angstrom {

    /**
     * Main method: handles user input, validation, and Armstrong number checking loop.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isValid = true, repeat = true;
        int num = 0;

        // Main loop for repeated checks
        while (repeat) {
            // Input validation loop
            while (isValid) {
                x.p("Enter a positive integer.");
                num = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                // Check for valid positive integer
                if (num < 0) {
                    x.p("Please enter a valid input.");
                    continue;
                } else {
                    isValid = false; // Exit validation loop if input is valid
                }
            }

            // Calculate the Armstrong sum
            int sum = 0;
            String numStr = String.valueOf(num);
            int numDigits = numStr.length();
            for (int idx = 0; idx < numDigits; idx++) {
                int digit = Character.getNumericValue(numStr.charAt(idx));
                sum += Math.pow(digit, numDigits);
            }

            // Output result
            if (sum == num) {
                x.p(num + " is an Angstrom number.");
            } else {
                x.p(num + " is not an Angstrom number.");
            }

            // Ask user if they want to check another number
            x.p("Do you want to check another? (Y/N)");
            String choice = sc.nextLine().trim().toUpperCase();
            repeat = choice.equals("Y") || choice.equals("YES");
            isValid = true; // Reset validation for next iteration
        }
        sc.close();
    }
}