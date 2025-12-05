import java.util.*;

/**
 * A simple calculator program that performs addition, subtraction,
 * multiplication, and division on a list of operands entered by the user.
 */
public class Calculator {

    /**
     * Utility method for printing output.
     * @param message The object to print.
     */
    private static void p(Object message){
        System.out.println(message);
    }

    /**
     * Adds all operands in the list and prints the sum.
     * @param operands List of Double values to add.
     */
    private static void add(List<Double> operands){
        double sum = 0;
        for (double num : operands) {
            sum += num;
        }
        p("Sum: " + sum);
    }

    /**
     * Subtracts all operands in the list from the first operand and prints the result.
     * Requires at least two operands.
     * @param operands List of Double values to subtract.
     */
    private static void subtract(List<Double> operands){
        if (operands.size() < 2) {
            p("At least two operands are required for subtraction.");
            return;
        }
        double result = operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            result -= operands.get(i);
        }
        p("Difference: " + result);
    }

    /**
     * Multiplies all operands in the list and prints the product.
     * @param operands List of Double values to multiply.
     */
    private static void multiply(List<Double> operands){
        double product = 1;
        for (double num : operands) {
            product *= num;
        }
        p("Product: " + product);
    }

    /**
     * Divides the first operand by each subsequent operand in the list and prints the result.
     * Requires at least two operands. Checks for division by zero.
     * @param operands List of Double values to divide.
     */
    private static void divide(List<Double> operands){
        if (operands.size() < 2) {
            p("At least two operands are required for division.");
            return;
        }
        double result = operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            if (operands.get(i) == 0) {
                p("Division by zero is not allowed.");
                return;
            }
            result /= operands.get(i);
        }
        p("Quotient: " + result);
    }

    /**
     * Asks the user if they want to perform another operation.
     * Accepts "yes" or "y" (case-insensitive) as affirmative.
     * @param sc Scanner object for user input.
     * @return true if user wants to continue, false otherwise.
     */
    private static boolean yesOrNo(Scanner sc) {
        p("Do you want to perform another operation? (Y/N)");
        String response = sc.next().trim().toLowerCase();
        sc.nextLine(); // Consume the leftover newline character
        if (response.isEmpty()) {
            p("No response provided. Assuming 'no'.");
            return false;
        }
        return response.equals("yes") || response.equals("y");
    }

    /**
     * Main method: handles user input and operation selection loop.
     */
    public static void main(String[] args) {
        List<Double> operands = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        boolean continueCalculating = true;

        // Main calculation loop
        while (continueCalculating) {
            // Prompt user for operands
            p("Enter the operands separated by spaces (Eg. 1 2 3 4 5):");
            String[] inputOperands = sc.nextLine().trim().split(" ");

            // Parse operands and add to list
            for (String i : inputOperands) {
                if (!i.isEmpty()) {
                    operands.add(Double.valueOf(i));
                }
            }

            // Operation selection loop (runs once, but repeats if invalid operation)
            boolean validOperation = false;
            while (!validOperation) {
                if (operands.isEmpty()) {
                    p("No operands entered. Program terminated.");
                    sc.close();
                    System.exit(0);
                }

                // Prompt user for operation
                p("Choose an operation: Add(A), Subtract(S), Multiply(M), Divide(D)");
                String operation = sc.next().trim().toLowerCase();
                sc.nextLine(); // Consume the leftover newline character

                // Perform selected operation
                switch (operation) {
                    case "a":
                        add(operands);
                        validOperation = true;
                        break;
                    case "s":
                        subtract(operands);
                        validOperation = true;
                        break;
                    case "m":
                        multiply(operands);
                        validOperation = true;
                        break;
                    case "d":
                        divide(operands);
                        validOperation = true;
                        break;
                    default:
                        p("Invalid operation. Please try again.");
                        // Loop repeats until a valid operation is entered
                }
            }

            operands.clear(); // Clear operands for next calculation
            continueCalculating = yesOrNo(sc); // Ask if user wants to continue
        }
        sc.close();
        p("Program terminated.");
    }

}