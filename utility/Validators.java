package utility;

import java.util.*;

public class Validators {
    
    public boolean yesOrNo(Scanner sc, String question) {

        ArrayList <String> validInputs = new ArrayList <>(Arrays.asList("Y", "N", "y", "n", "yes", "no", "Yes", "No", "YES", "NO", "positive", "negative", "Positive", "Negative", 
        "POSITIVE", "NEGATIVE", "1", "0"));
        ArrayList <String> PositiveInputs = new ArrayList <>(Arrays.asList("Y", "y", "yes", "Yes", "YES", "positive", "Positive", "POSITIVE", "1"));

        PrintMethods.pln(question);
        String response = sc.nextLine().trim();

        if (!validInputs.contains(response)) {
            PrintMethods.pln("Invalid input. Please enter Y or N.");
            yesOrNo(sc, question);
        }

        return PositiveInputs.contains(response.trim());
    }

    public String Validator(Scanner sc, String question, ArrayList <String> validInputs) {
        
        PrintMethods.pln(question);
        String input = sc.nextLine().trim();

        if (!validInputs.contains(input)) {
            PrintMethods.pln("Invalid input. Please try again."
                                    + "\nValid inputs are: " + String.join(", ", validInputs));
            Validator(sc, question, validInputs);
        }
        return input;
    }
}
