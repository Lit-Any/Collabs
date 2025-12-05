import java.util.*;

public class Temp_Alt {
    
    // Utility method for printing output
    private static void p(Object x) {
        System.out.println(x);
    }

    //Method to check if the user wants to specify the number of  conversions.
    private static boolean specifyConversions(Scanner sc) {
        p("Do you want to specify the number of conversions? (Y/N)");
        String response = sc.next().trim().toUpperCase();
        return response.equals("Y");
    }

    // Method to check if the user wants to change units
    private static boolean changeUnits(Scanner sc) {
        p("Do you want to change the units each time? (Y/N)");
        String response = sc.next().trim().toUpperCase();
        return response.equals("Y");
    }

    // Main method to run the temperature conversion
    private static void Converter(Scanner sc, String unit, String convertTo) {

        // Get temperature value and perform conversion
                    p("Please enter a temperature as a number without units:");
                    double temp = sc.nextDouble();

                    if (temp < -273.15 && unit.equalsIgnoreCase("C")) {
                        p("Temperature cannot be below absolute zero in Celsius.");
                        System.exit(0);
                    } else if (temp < -459.67 && unit.equalsIgnoreCase("F")) {
                        p("Temperature cannot be below absolute zero in Fahrenheit.");
                        System.exit(0);
                    } else if (temp < 0 && unit.equalsIgnoreCase("K")) {
                        p("Temperature cannot be below absolute zero in Kelvin.");
                        System.exit(0);
                    }

            switch (unit) {

                            case "C":

                                if (convertTo.equals("F")) {
                                    p(temp * 9/5 + 32 + " degrees F.");
                                } else if (convertTo.equals("K")) {
                                    p(temp + 273.15 + " K");
                                } else {
                                    p(temp + " degrees C.");
                                }
                                break;

                            case "F":

                                if (convertTo.equals("C")) {
                                    p((temp - 32) * 5/9 + " degrees C.");
                                } else if (convertTo.equals("K")) {
                                    p((temp - 32) * 5/9 + 273.15 + " K");
                                } else {
                                    p(temp + " degrees F.");
                                }
                                break;
                                
                            case "K":
                                if (convertTo.equals("C")) {
                                    p(temp - 273.15 + " degrees C.");
                                } else if (convertTo.equals("F")) {
                                    p((temp - 273.15) * 9/5 + 32 + " degrees F.");
                                } else {
                                    p(temp + " K");
                                }
                                break;

                            default:

                                p("Invalid unit provided. Please use C, F, or K.");
                                break;

                        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        if(changeUnits(sc)) {

            if(specifyConversions(sc)) {
                p("How many conversions do you want to perform?");
                int numConversions = sc.nextInt();
                sc.next(); // Consume the newline character

                for (int i = 0; i < numConversions; i++) {

                    p("Enter the unit you want to convert from (C, F, K):");
                    String unit = sc.next().trim().toUpperCase();
                    // Validate the input unit
                    if (!unit.equals("C") && !unit.equals("F") && !unit.equals("K")) {
                        p("Invalid unit. Please enter C, F, or K.");
                        System.exit(0);
                    }

                    p("Enter the unit you want to convert to (C, F, K):");
                    String convertTo = sc.next().trim().toUpperCase();
                    // Validate the ouput unit
                    if (!convertTo.equals("C") && !convertTo.equals("F") && !convertTo.equals("K")) {
                        p("Invalid unit. Please enter C, F, or K.");
                        System.exit(0);
                    }

                    Converter(sc, unit, convertTo);
                }
            } else {
                boolean continueConversion = true;
                while (continueConversion) {

                    p("Enter the unit you want to convert from (C, F, K):");
                    String unit = sc.next().trim().toUpperCase();
                    // Validate the input unit
                    if (!unit.equals("C") && !unit.equals("F") && !unit.equals("K")) {
                        p("Invalid unit. Please enter C, F, or K.");
                        System.exit(0);
                    }

                    p("Enter the unit you want to convert to (C, F, K):");
                    String convertTo = sc.next().trim().toUpperCase();
                    // Validate the ouput unit
                    if (!convertTo.equals("C") && !convertTo.equals("F") && !convertTo.equals("K")) {
                        p("Invalid unit. Please enter C, F, or K.");
                        System.exit(0);
                    }

                    Converter(sc, unit, convertTo);
                    
                    p("Do you want to perform another conversion? (Y/N)");
                    String response = sc.next().trim().toUpperCase();
                    if (!response.equals("Y")) {
                        continueConversion = false;
                    }
                }
            }

        } else {

            if(specifyConversions(sc)) {
                p("How many conversions do you want to perform?");
                int numConversions = sc.nextInt();
                sc.next(); // Consume the newline character

                for (int i = 0; i < numConversions; i++) {

                    p("Enter the unit you want to convert from (C, F, K):");
                    String unit = sc.next().trim().toUpperCase();
                    // Validate the input unit
                    if (!unit.equals("C") && !unit.equals("F") && !unit.equals("K")) {
                        p("Invalid unit. Please enter C, F, or K.");
                        System.exit(0);
                    }

                    p("Enter the unit you want to convert to (C, F, K):");
                    String convertTo = sc.next().trim().toUpperCase();
                    // Validate the ouput unit
                    if (!convertTo.equals("C") && !convertTo.equals("F") && !convertTo.equals("K")) {
                        p("Invalid unit. Please enter C, F, or K.");
                        System.exit(0);
                    }

                    Converter(sc, unit, convertTo);
                }
            } else {

                boolean continueConversion = true;
                while (continueConversion) {

                    p("Enter the unit you want to convert from (C, F, K):");
                    String unit = sc.next().trim().toUpperCase();
                    // Validate the input unit
                    if (!unit.equals("C") && !unit.equals("F") && !unit.equals("K")) {
                        p("Invalid unit. Please enter C, F, or K.");
                        System.exit(0);
                    }

                    p("Enter the unit you want to convert to (C, F, K):");
                    String convertTo = sc.next().trim().toUpperCase();
                    // Validate the ouput unit
                    if (!convertTo.equals("C") && !convertTo.equals("F") && !convertTo.equals("K")) {
                        p("Invalid unit. Please enter C, F, or K.");
                        System.exit(0);
                    }

                    Converter(sc, unit, convertTo);
                    p("Do you want to perform another conversion? (Y/N)");
                    String response = sc.next().trim().toUpperCase();
                    if (!response.equals("Y")) {
                        continueConversion = false;
                    }
                }
            }

        }
        
        // Close the scanner
        sc.close();
        p("Program ended.");
    }
}
