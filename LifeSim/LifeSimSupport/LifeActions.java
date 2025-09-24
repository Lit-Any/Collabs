package LifeSim.LifeSimSupport;

import java.util.List;
import utility.*;
public class LifeActions {

    static java.util.Scanner SC = new java.util.Scanner(System.in);
    static java.util.Random RNG = new java.util.Random();
    static PrintMethods PrintMethods = new PrintMethods();

    public static void doStudyFor(Person p, List<String> log) {
        
        PrintMethods.pln("\nStudy options:");
        PrintMethods.pln("\n1) High School (+5 INT, -2 HAPPY; Chance to complete HS if age>=16)");
        PrintMethods.pln("2) College (+8 INT, -3 HAPPY; requires HS; chance to complete)");
        PrintMethods.pln("3) Short Course (+3 INT, -Rs.2000)");
        PrintMethods.pln("4) Back");

        PrintMethods.p("\nChoose: ");
        int sel = Helpers.readInt();

        switch (sel) {

            case 1:

                Helpers.spend(p, 10000, log, "school fees");


                    Person.intelligence += 5; Person.happiness -= 2;
                    if (RNG.nextInt(100) < 30 || p.CountOfEducationAttempts>=3) {

                        p.education = "HS"; PrintMethods.pln(ConsoleColors.ULTRA_BOLD.YELLOW + p.name+" completed HS." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                        log.add(p.name+" completed HS."); p.CountOfEducationAttempts=0;
                        p.education = "HS";

                    } else {PrintMethods.pln(ConsoleColors.ULTRA_BOLD.YELLOW + "\nNo graduation this year." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK); p.CountOfEducationAttempts++;}
                    if (p.nightmareMode) {
                            p.passNightmareYear(log);
                        } else {
                            p.passYear(log);
                        };
                
                break;
            case 2:

                Helpers.spend(p, 20000, log, "college fees");

                    if (!p.education.equals("HS")) { PrintMethods.pln("Need HS first."); return; }
                    Person.intelligence += 8; Person.happiness -= 3;
                    if (RNG.nextInt(100) < 40 && p.CountOfEducationAttempts<3) {
                        p.education = "College"; PrintMethods.pln(ConsoleColors.ULTRA_BOLD.YELLOW + p.name+" graduated college." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                        log.add(p.name+" graduated College."); p.CountOfEducationAttempts=0;
                        p.education = "College";
                    }
                    else {PrintMethods.pln(ConsoleColors.ULTRA_BOLD.YELLOW + "No graduation this year." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK); p.CountOfEducationAttempts++;}
                    if (p.nightmareMode) {
                        p.passNightmareYear(log);
                    } else {
                        p.passYear(log);
                    }

                break;

            case 3:

                long fee = 15000;
                Helpers.spend(p, fee, log, "short course");

                Person.intelligence += 3;
                if (p.nightmareMode) {
                    p.passNightmareYear(log);
                } else {
                    p.passYear(log);
                }
                
                break;

            case 4:
                break;

            default: PrintMethods.pln("Invalid."); break;
        }
    }

    public static void doWorkFor(Person p, List<String> log) {

        String[] jobs = {"Retail Worker","Teacher","Engineer","Doctor","Artist","Criminal","Keep current job","Quit job","Back"};

        PrintMethods.pln("\nWork options:");

        for (int i=0;i<jobs.length;i++) {
            PrintMethods.pln((i+1)+") "+jobs[i]);
        }

        PrintMethods.p("\nChoose: ");
        int sel = Helpers.readInt();

        if (sel < 1 || sel > jobs.length) {
            PrintMethods.pln("Invalid.");
            return;
        }

        String choice = jobs[sel-1];

        if (choice.equals("Keep current job")) {

            Person.happiness -= 1; 
            if (p.nightmareMode) {
                p.passNightmareYear(log);
            } else {
                p.passYear(log);
            }

        } else if (choice.equals("Quit job") && !Person.job.equals("Unemployed")) {

            PrintMethods.pln(p.name+" quit their job: "+Person.job);
            log.add(p.name+" quit their job: "+Person.job);
            Person.job = "Unemployed"; Person.happiness += 5;

            if (p.nightmareMode) {
                p.passNightmareYear(log);

            } else {
                p.passYear(log);
            }
        }

        if (Person.job.equals("Unemployed") && choice.equals("Quit job")) {

            PrintMethods.pln("You are already unemployed.");

            return;

        } else {

            // requirements (roughly matching Code2)
            if (choice.equals("Teacher") && (Person.intelligence < 60 || Helpers.eduRank(p.education) < Helpers.eduRank("HS"))) { Helpers.failJob(p, choice, log); return; }
            else if (choice.equals("Engineer") && (Person.intelligence < 75 || Helpers.eduRank(p.education) < Helpers.eduRank("College"))) { Helpers.failJob(p, choice, log); return; }
            else if (choice.equals("Doctor") && (Person.intelligence < 90 || Helpers.eduRank(p.education) < Helpers.eduRank("Masters"))) { Helpers.failJob(p, choice, log); return; }
            else if (choice.equals("Retail Worker") && Person.intelligence < 20) { Helpers.failJob(p, choice, log); return; }
            else if (choice.equals("Artist") && Person.looks < 30) { Helpers.failJob(p, choice, log); return; }
            else if (choice.equals("Back") ) { return; }
            // Criminal has no constraints

            Person.job = choice; Person.happiness += 5; PrintMethods.pln(ConsoleColors.ULTRA_BOLD.YELLOW + p.name+" got job: "+choice + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
            log.add(p.name+" got job: " + choice);

            if (p.nightmareMode) {
                p.passNightmareYear(log);
            } else {
                p.passYear(log);
            }
        }
    }

    public static void doImproveFor(Person p, List<String> log) {
        PrintMethods.pln("\nImprovement options:");
        PrintMethods.pln("\n1) Gym (+8 Health, +2 Happiness, -Rs.2000)");
        PrintMethods.pln("2) Study Hard (+6 Int, -3 Happiness, -Rs.1000)");
        PrintMethods.pln("3) Go on a Date (swingy)");
        PrintMethods.pln("4) Back");
        PrintMethods.p("\nChoose: ");
        int sel = Helpers.readInt();
        switch (sel) {
            case 1:
                PrintMethods.pln(ConsoleColors.HIGHLIGHT + "\nYou went to the gym " + p.modHealth(8) + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                p.modHappiness(2); Helpers.spend(p, 2000, log, "gym");
                if (p.nightmareMode) {
                    p.passNightmareYear(log);
                } else {
                    p.passYear(log);
                }
                break;
            case 2:
                PrintMethods.pln(ConsoleColors.HIGHLIGHT + "You studied hard " + p.modIntelligence(6) + p.modHappiness(-3) + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                Helpers.spend(p, 1000, log, "study materials");
                if (p.nightmareMode) {
                    p.passNightmareYear(log);
                } else {
                    p.passYear(log);
                }
                break;
            case 3:
                if (RNG.nextBoolean()) {
                    Helpers.spend(p, 3000, log, "date");
                    log.add(p.name+" had a great date.");
                    PrintMethods.pln(ConsoleColors.HIGHLIGHT + "Great date!" + p.modHappiness(8) + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                } else {
                    Helpers.spend(p, 2000, log, "bad date");
                    log.add(p.name+" had a bad date.");
                    PrintMethods.pln(ConsoleColors.HIGHLIGHT + "Bad date." + p.modHappiness(-5) + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                }
                if (p.nightmareMode) {
                    p.passNightmareYear(log);
                } else {
                    p.passYear(log);
                }
                break;
            case 4:
                    break;
            default: PrintMethods.pln("Invalid.");
        }
    }

    public static void doRiskyFor(Person p, List<String> log) {
        PrintMethods.pln("\nRisky options:");
        PrintMethods.pln("1) Life-side Gamble (quick)");
        PrintMethods.pln("2) Small Crime");
        PrintMethods.pln("3) Back");
        PrintMethods.p("\nChoose: ");
        int sel = Helpers.readInt();
        switch (sel) {
            case 1:
                if (RNG.nextInt(100) < 45) {
                    long win = (RNG.nextInt(10)+1)*1000L;
                    p.balance += win; p.addFlow(win); Person.happiness += 5;
                    log.add(p.name+" won gamble Rs."+win);
                } else {
                    long lose=(RNG.nextInt(20)+1)*1000L;
                    lose = Math.min(lose, p.balance);
                    p.balance -= lose; p.addFlow(lose);
                    PrintMethods.pln(ConsoleColors.HIGHLIGHT + p.name + " lost gamble Rs." + lose + p.modHappiness(-10) + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                    log.add(p.name+" lost gamble Rs."+lose + "(-10 happiness)");
                }
                if (p.nightmareMode) {
                    p.passNightmareYear(log);
                } else {
                    p.passYear(log);
                }
                break;
            case 2:
                if (RNG.nextInt(100) < 40) {
                    long loot=(RNG.nextInt(30)+1)*1000L;
                    p.balance += loot; p.addFlow(loot); Person.happiness -= 10;
                    PrintMethods.pln(ConsoleColors.HIGHLIGHT + p.name + " stole Rs." + loot + p.modHappiness(-10) + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                    log.add(p.name+" crime succeeded Rs."+loot);
                } else {
                    long fine=5000; fine = Math.min(fine, p.balance);
                    p.balance -= fine; p.addFlow(fine); p.health -= 10; Person.happiness -= 20;
                    PrintMethods.pln(ConsoleColors.HIGHLIGHT + p.name + " was caught and fined Rs." + fine + " (Health -10, Happiness -20)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                    log.add(p.name+" was caught committing crime (Health -10, Happiness -20).");
                }
                if (p.nightmareMode) {
                    p.passNightmareYear(log);
                } else {
                    p.passYear(log);
                }
                break;
            case 3:
                break;
            default: PrintMethods.pln("Invalid.");
        }
    }
}