package LifeSim.LifeSimSupport;
import java.util.*;
import utility.*;

public class Tutorial {

    static Validators v = new Validators();
    static Scanner sc = new Scanner(System.in);

    public static void CheckTutorial() {
        PrintMethods.pln("\nWelcome to LifeSim!");
        if (v.yesOrNo(sc, "Start the tutorial?")) {
            showBasics();
        } else {
            PrintMethods.pln("Skipping tutorial. You can access it later from the main menu.");
        }
    }

    @SuppressWarnings("static-access")
    public static void showBasics() {

        PrintMethods.pln("\nIn LifeSim, you will make choices that affect your character's life.");
        PrintMethods.pln("You can choose different careers, manage finances, and navigate life events.");
        PrintMethods.pln("Your character has attributes like health, happiness, and wealth that will change based on your decisions.");
        Person samplePerson = new Person("Alex");
        samplePerson.showStatus();
        PrintMethods.pln("\nAs you make choices, these attributes will increase or decrease.");
        PrintMethods.pln("The numbers in brackets indicate the change for that attribute in the past 1 turn.");
        PrintMethods.pln("Let's go through a quick example.");
        PrintMethods.pln("You have two options:");
        PrintMethods.pln("\n1. Go for a run (+5 Health, +2 Happiness)");
        PrintMethods.pln("\n2. Watch TV (-2 Health, +1 Happiness)");
        if (v.Validator(sc, "\nChoose 1 or 2:", new ArrayList<>(Arrays.asList("1", "2"))).equals("1")) {
            samplePerson.health += 5; samplePerson.healthChange = 5;
            samplePerson.happiness += 2; samplePerson.happinessChange = 2;
            PrintMethods.pln("You chose to go for a run!");
        } else {
            samplePerson.health -= 2; samplePerson.healthChange = -2;
            samplePerson.happiness += 1; samplePerson.happinessChange = 1;
            PrintMethods.pln("You chose to watch TV!");
        }
        samplePerson.showStatus();
        PrintMethods.pln("\nGreat! You've completed the tutorial.");
        PrintMethods.pln("Have a good run!");
    }
}
