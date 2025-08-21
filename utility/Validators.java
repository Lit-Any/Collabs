package utility;

import java.util.*;
import utility.*;
public class Validators {
    public static void yesOrNo(Scanner sc, String question) {
        pln(question);
        String response = sc.next().trim().toUpperCase();
        if (!response.equals("Y") && !response.equals("N")) {
            pln("Invalid input. Please enter Y or N.");
            yesOrNo(sc, question);
        }
    }

    public static void Validator(Scanner sc, String question, ArrayList <String> validInputs, boolean isBinary) {
        if (isBinary) {
            ArrayList <String> binaryInputs = new ArrayList <>(Arrays.asList("Y", "N", "y", "n", "yes", "no", "Yes", "No"));
            for (String input : binaryInputs) {
                validInputs.add(input);
            }
            yesOrNo(sc, question);
            return;
        } else {
            pln(question);
            String input = sc.nextLine().trim();
            if (!validInputs.contains(input)) {
                pln("Invalid input. Please try again.");
                Validator(sc, question, validInputs, false);
            }
        }
    }
}
