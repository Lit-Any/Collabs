import java.util.*;

public class Temp {
    // Utility method for printing output
    private static void p(Object x) {
        System.out.println(x);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ask user if they know how many conversions they want to perform
        p("Do you know how many times would you like to convert?(Y/N)");
        String response = sc.next().toUpperCase();

        if (response.equalsIgnoreCase("Y")) { 
            // User knows the number of conversions
            p("How many times would you like to convert?");
            int n = sc.nextInt();
            
            //Ensures that a valid no. of conversions is provided.
            if (n <= 0) {
                p("Invalid number of conversions. Please enter a positive number.");
                sc.close();
                return;
            }

            // Ask if units should change every time
            p("Would you like to change the units every time?(Y/N)");
            String changeUnits = sc.next().toUpperCase();

            if (changeUnits.equals("N")) {

                // Units remain constant for all conversions
                p("Please enter the unit of the input temperature (C for Celsius, F for Fahrenheit, K for Kelvin):");
                String unit = sc.next().toUpperCase();
                p("What would you like to convert the temperature to? (C for Celsius, F for Fahrenheit, K for Kelvin):");
                String convertTo = sc.next().toUpperCase();

                for (int i = 0; i < n; i++) {

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
            } else if (changeUnits.equals("Y")) {

                // Units can change for each conversion
                for (int i = 0; i < n; i++) {

                    p("Please enter the unit of the input temperature (C for Celsius, F for Fahrenheit, K for Kelvin):");
                    String unit = sc.next().toUpperCase();
                    p("What would you like to convert the temperature to? (C for Celsius, F for Fahrenheit, K for Kelvin):");
                    String convertTo = sc.next().toUpperCase();
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
                            
                    }
                }
            } else {

                // Invalid input for changing units
                p("Invalid input. Please enter Y or N.");
                sc.close();
                return;

            }
        } else if (response.equalsIgnoreCase("N")) {

            // User does not know the number of conversions; allow indefinite conversions
            p("Would you like to change the units every time?(Y/N)");
            String changeUnits = sc.next().toUpperCase();
            boolean continueConversion = true;

            while (continueConversion) {

                if (changeUnits.equals("N")) {

                    // Units remain constant for all conversions in this session
                    p("Please enter the unit of the input temperature (C for Celsius, F for Fahrenheit, K for Kelvin):");
                    String unit = sc.next().toUpperCase();
                    p("What would you like to convert the temperature to? (C for Celsius, F for Fahrenheit, K for Kelvin):");
                    String convertTo = sc.next().toUpperCase();
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
                        } else if (changeUnits.equals("Y")) {

                            // Units can change for each conversion in this session
                            p("Please enter the unit of the input temperature (C for Celsius, F for Fahrenheit, K for Kelvin):");
                            String unit = sc.next().toUpperCase();
                            p("What would you like to convert the temperature to? (C for Celsius, F for Fahrenheit, K for Kelvin):");
                            String convertTo = sc.next().toUpperCase();
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

                            }
                } else {

                    // Invalid input for "Would you like to change the units every time?(Y/N)"
                    p("Invalid input. Please enter Y or N.");

                }

                // Ask if the user wants to perform another conversion
                p("Would you like to convert another temperature? (Y/N)");
                String again = sc.next().toUpperCase();
                if (!again.equals("Y")) {
                    continueConversion = false;
                }

            }
        } else {

            // Invalid input for initial question - "Do you know how many times would you like to convert?(Y/N)"
            p("Invalid input. Please enter Y or N.");

        }

        // Close the scanner and end the program.
        sc.close();
        p("Program ended.");
        System.exit(0);
    }
}