package LifeSim.LifeSimSupport;

import java.util.List;
import utility.*;

public class LifeActions {

    static java.util.Scanner SC = new java.util.Scanner(System.in);
    static java.util.Random RNG = new java.util.Random();

    public static void doStudyFor(Person p, List<String> log) {
        
        PrintMethods.pln("\nStudy options:");
        PrintMethods.pln("1) High School (+5 INT, -2 HAPPY; Chance to complete HS if age>=16)");
        PrintMethods.pln("2) College (+8 INT, -3 HAPPY; requires HS; chance to complete)");
        PrintMethods.pln("3) Short Course (+3 INT, -Rs.2000)");

        PrintMethods.p("\nChoose: ");
        int sel = Helpers.readInt();

        switch (sel) {

            case 1:

                Helpers.spend(p, 10000, log, "school fees");

                if (p.loan == 0) {
                    p.intelligence += 5; p.happiness -= 2;
                    if (RNG.nextInt(100) < 30 || p.CountOfEducationAttempts>=3) { p.education = "HS"; PrintMethods.pln(p.name+" completed HS."); log.add(p.name+" completed HS."); p.CountOfEducationAttempts=0; }
                    else {PrintMethods.pln("No graduation this year."); p.CountOfEducationAttempts++;}
                    if (p.nightmareMode) {
                            p.passNightmareYear(log);
                        } else {
                            p.passYear(log);
                        };
                }
                
                break;
            case 2:

                Helpers.spend(p, 20000, log, "college fees");

                if (p.loan == 0) {
                    if (!p.education.equals("HS")) { PrintMethods.pln("Need HS first."); return; }
                    p.intelligence += 8; p.happiness -= 3;
                    PrintMethods.pln(p.name+" spent 20000 on college fees.");
                    if (RNG.nextInt(100) < 40 && p.CountOfEducationAttempts<3) { p.education = "College"; PrintMethods.pln(p.name+" graduated college.");log.add(p.name+" graduated College."); p.CountOfEducationAttempts=0; }
                    else PrintMethods.pln("No graduation this year."); p.CountOfEducationAttempts++;
                    if (p.nightmareMode) {
                        p.passNightmareYear(log);
                    } else {
                        p.passYear(log);
                    }
                }

                break;

            case 3:

                long fee = 15000;
                Helpers.spend(p, fee, log, "short course fee");

                if (p.loan == 0) {
                p.intelligence += 3;
                PrintMethods.pln(p.name+" spent "+fee+" on a short course.");
                if (p.nightmareMode) {
                            p.passNightmareYear(log);
                        } else {
                            p.passYear(log);
                        };
                }
                
                break;

            default: PrintMethods.pln("Invalid."); break;
        }
    }

    public static void doWorkFor(Person p, List<String> log) {
        String[] jobs = {"Retail Worker","Teacher","Engineer","Doctor","Artist","Criminal","Keep current job","Quit job"};
        PrintMethods.pln("\nWork options:");
        for (int i=0;i<jobs.length;i++) PrintMethods.pln((i+1)+") "+jobs[i]);
        PrintMethods.p("\nChoose: ");
        int sel = Helpers.readInt();
        if (sel < 1 || sel > jobs.length) { PrintMethods.pln("Invalid."); return; }
        String choice = jobs[sel-1];
        if (choice.equals("Keep current job")) { p.happiness -= 1; 
            if (p.nightmareMode) {
            p.passNightmareYear(log);
            } else {
                p.passYear(log);
            }
        } else if (choice.equals("Quit job")) { p.job = "Unemployed"; p.happiness += 5; if (p.nightmareMode) {
            p.passNightmareYear(log);
        } else {
            p.passYear(log);
        }
    }
        else {
            // requirements (roughly matching Code2)
            if (choice.equals("Teacher") && (p.intelligence < 60 || Helpers.eduRank(p.education) < Helpers.eduRank("HS"))) { Helpers.failJob(p, choice, log); return; }
            else if (choice.equals("Engineer") && (p.intelligence < 75 || Helpers.eduRank(p.education) < Helpers.eduRank("College"))) { Helpers.failJob(p, choice, log); return; }
            else if (choice.equals("Doctor") && (p.intelligence < 90 || Helpers.eduRank(p.education) < Helpers.eduRank("Masters"))) { Helpers.failJob(p, choice, log); return; }
            else if (choice.equals("Retail Worker") && p.intelligence < 20) { Helpers.failJob(p, choice, log); return; }
            else if (choice.equals("Artist") && p.looks < 30) { Helpers.failJob(p, choice, log); return; }
            // Criminal has no constraints
            p.job = choice; p.happiness += 5; PrintMethods.pln(p.name+" got job: "+choice);log.add(p.name+" got job: " + choice);
            if (p.nightmareMode) {
                p.passNightmareYear(log);
            } else {
                p.passYear(log);
            }
        }
    }

    public static void doImproveFor(Person p, List<String> log) {
        PrintMethods.pln("\nImprove options:");
        PrintMethods.pln("1) Gym (+8 Health, +2 Happy, -Rs.2000)");
        PrintMethods.pln("2) Study Hard (+6 Int, -3 Happy, -Rs.1000)");
        PrintMethods.pln("3) Go on a Date (swingy)");
        PrintMethods.p("\nChoose: ");
        int sel = Helpers.readInt();
        switch (sel) {
            case 1:
                p.health += 8; p.happiness += 2; Helpers.spend(p, 2000, log, "gym");
                if (p.nightmareMode) {
                            p.passNightmareYear(log);
                        } else {
                            p.passYear(log);
                        };
                break;
            case 2:
                p.intelligence += 6; p.happiness -= 3; Helpers.spend(p, 1000, log, "study materials");
                if (p.nightmareMode) {
                            p.passNightmareYear(log);
                        } else {
                            p.passYear(log);
                        };
                break;
            case 3:
                if (RNG.nextBoolean()) {
                    p.happiness += 8; Helpers.spend(p, 3000, log, "date");
                    log.add(p.name+" had a great date.");
                    PrintMethods.pln("Great date!");
                } else {
                    p.happiness -= 5; Helpers.spend(p, 2000, log, "bad date");
                    log.add(p.name+" had a bad date.");
                    PrintMethods.pln("Bad date.");
                }
                if (p.nightmareMode) {
                            p.passNightmareYear(log);
                        } else {
                            p.passYear(log);
                        };
                break;
            default: PrintMethods.pln("Invalid.");
        }
    }

    public static void doRiskyFor(Person p, List<String> log) {
        PrintMethods.pln("\nRisky options:");
        PrintMethods.pln("1) Life-side Gamble (quick)");
        PrintMethods.pln("2) Small Crime");
        PrintMethods.pln("3) Do Nothing");
        PrintMethods.p("\nChoose: ");
        int sel = Helpers.readInt();
        switch (sel) {
            case 1:
                if (RNG.nextInt(100) < 45) {
                    long win = (RNG.nextInt(10)+1)*1000L;
                    p.balance += win; p.addFlow(win); p.happiness += 5;
                    log.add(p.name+" won gamble Rs."+win);
                } else {
                    long lose=(RNG.nextInt(20)+1)*1000L;
                    lose = Math.min(lose, p.balance);
                    p.balance -= lose; p.addFlow(lose); p.happiness -= 10;
                    log.add(p.name+" lost gamble Rs."+lose);
                }
                if (p.nightmareMode) {
                            p.passNightmareYear(log);
                        } else {
                            p.passYear(log);
                        };
                break;
            case 2:
                if (RNG.nextInt(100) < 40) {
                    long loot=(RNG.nextInt(30)+1)*1000L;
                    p.balance += loot; p.addFlow(loot); p.happiness -= 10;
                    log.add(p.name+" crime succeeded Rs."+loot);
                } else {
                    long fine=5000; fine = Math.min(fine, p.balance);
                    p.balance -= fine; p.addFlow(fine); p.health -= 10; p.happiness -= 20;
                    log.add(p.name+" was caught committing crime.");
                }
                if (p.nightmareMode) {
                            p.passNightmareYear(log);
                        } else {
                            p.passYear(log);
                        };
                break;
            case 3:
                // no passYear here—staying idle in risky panel; let user decide separate Live Year if desired
                break;
            default: PrintMethods.pln("Invalid.");
        }
    }
}
