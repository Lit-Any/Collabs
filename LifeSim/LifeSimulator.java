/*
  LifeSimulator.java — Unified Life + Economy (BlueJ-compatible, console)
  ----------------------------------------------------------
  - Merges Life Simulator (Code2) with the full Economic Framework (Code1)
  - One unified menu per player's turn:
      * Life actions (study, work, improve, risky, etc.)
      * Economic actions (banking, 20 gambling games, lottery, Rummy)
  - Player creation is handled here (Code1's player-adder removed)
  - Code1's leaderboard removed as requested
  - Money type unified to 'long' (integer currency). Doubles from Code1 are rounded.
  - Rummy card game preserved (consent poll among current players)
  - BlueJ-friendly, single file, console I/O

  Notes:
  - This version is console-based for a clean, unified flow.
  - All economic profits/losses immediately affect the same player's balance used by life actions.
*/

package LifeSim;

import java.io.*;
import java.util.*;

public class LifeSimulator {

    /* =========================
       ========== CORE =========
       ========================= */

    static final Scanner SC = new Scanner(System.in);
    static final Random RNG = new Random();

    /* ===============================
       ========== ECONOMY ============
       =============================== */

    

    /* ===============================
       ========== GAME LOOP ==========
       =============================== */

    public static void main(String[] args) {
        System.out.println("🏦 Welcome to the Unified Life & Economy Simulator!");
        System.out.print("Enter number of players: ");
        int n = readInt();
        SC.nextLine(); // consume endline

        List<Person> players = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter player " + (i+1) + " name: ");
            String name = SC.nextLine().trim();
            if (name.isEmpty()) name = "Player_" + (i+1);
            players.add(new Person(name));
        }

        List<String> log = new ArrayList<>();
        boolean running = true;

        while (running) {
            for (Person p : players) {
                if (!p.alive) { System.out.println("\n--- " + p.name + " is deceased. Skipping turn. ---"); continue; }

                System.out.println("\n==============================");
                System.out.println("Turn — " + p.name);
                p.showStatus();
                System.out.println("------------------------------");
                System.out.println(
                    "1) Live year (auto)\n" +
                    "2) Study\n" +
                    "3) Work\n" +
                    "4) Improve stats\n" +
                    "5) Risky (life-side)\n" +
                    "6) Economic Actions (bank, gamble, lottery, rummy)\n" +
                    "7) Show recent log\n" +
                    "8) Exit game"
                );
                System.out.print("Choose: ");
                int choice = readInt();

                switch (choice) {
                    case 1: p.passYear(log); break;
                    case 2: doStudyFor(p, log); break;
                    case 3: doWorkFor(p, log); break;
                    case 4: doImproveFor(p, log); break;
                    case 5: doRiskyFor(p, log); break;
                    case 6: Economy.economicMenu(p, players); break;
                    case 7: showRecentLog(log); break;
                    case 8:
                        running = false;
                        System.out.println("👋 Game ended by " + p.name);
                        break;
                    default: System.out.println("❌ Invalid choice!");
                }

                if (!running) break;
            }
        }

        // Final Results
        System.out.println("\n📊 Final Results:");
        for (Person p : players) {
            p.showStatus();
            System.out.println("   Gambles: played " + p.gamblesPlayed + ", won " + p.gamblesWon + ", lost " + p.gamblesLost);
            System.out.println("   Money Won: Rs." + p.moneyWon + " | Money Lost: Rs." + p.moneyLost + " | Lifetime Flows: Rs." + p.lifetimeTotal);
            System.out.println("   Biggest Lottery Win: Rs." + p.biggestLotteryWin + " | Lottery Contribution: Rs." + p.lotteryContribution);
            System.out.println("-------------------------------");
        }
    }

    /* ===============================
       ======= LIFE ACTIONS UI =======
       =============================== */

    static void doStudyFor(Person p, List<String> log) {
        System.out.println("\nStudy options:");
        System.out.println("1) High School (+5 INT, -2 HAPPY; chance to complete HS if age>=16)");
        System.out.println("2) College (+8 INT, -3 HAPPY; requires HS; chance to complete)");
        System.out.println("3) Short Course (+3 INT, -Rs.2000)");
        System.out.print("Choose: ");
        int sel = readInt();
        switch (sel) {
            case 1:
                p.intelligence += 5; p.happiness -= 2;
                if (p.age >= 16 && RNG.nextInt(100) < 30) { p.education = "HS"; log.add(p.name+" completed HS."); }
                p.passYear(log);
                break;
            case 2:
                if (!p.education.equals("HS")) { System.out.println("Need HS first."); return; }
                p.intelligence += 8; p.happiness -= 3;
                if (RNG.nextInt(100) < 40) { p.education = "College"; log.add(p.name+" graduated College."); }
                p.passYear(log);
                break;
            case 3:
                p.intelligence += 3; long fee = 2000; spend(p, fee, log, "short course fee");
                p.passYear(log);
                break;
            default: System.out.println("Invalid."); break;
        }
    }

    static void doWorkFor(Person p, List<String> log) {
        String[] jobs = {"Retail Worker","Teacher","Engineer","Doctor","Artist","Criminal","Keep current job","Quit job"};
        System.out.println("\nWork options:");
        for (int i=0;i<jobs.length;i++) System.out.println((i+1)+") "+jobs[i]);
        System.out.print("Choose: ");
        int sel = readInt();
        if (sel < 1 || sel > jobs.length) { System.out.println("Invalid."); return; }
        String choice = jobs[sel-1];
        if (choice.equals("Keep current job")) { p.happiness -= 1; p.passYear(log); }
        else if (choice.equals("Quit job")) { p.job = "Unemployed"; p.happiness += 5; p.passYear(log); }
        else {
            // requirements (roughly matching Code2)
            if (choice.equals("Teacher") && (p.intelligence < 60 || eduRank(p.education) < eduRank("HS"))) { failJob(p, choice, log); return; }
            else if (choice.equals("Engineer") && (p.intelligence < 75 || eduRank(p.education) < eduRank("College"))) { failJob(p, choice, log); return; }
            else if (choice.equals("Doctor") && (p.intelligence < 90 || eduRank(p.education) < eduRank("Masters"))) { failJob(p, choice, log); return; }
            else if (choice.equals("Retail Worker") && p.intelligence < 20) { failJob(p, choice, log); return; }
            else if (choice.equals("Artist") && p.looks < 30) { failJob(p, choice, log); return; }
            // Criminal has no constraints
            p.job = choice; p.happiness += 5; log.add(p.name+" got job: " + choice);
            p.passYear(log);
        }
    }

    static void doImproveFor(Person p, List<String> log) {
        System.out.println("\nImprove options:");
        System.out.println("1) Gym (+8 Health, +2 Happy, -Rs.2000)");
        System.out.println("2) Study Hard (+6 Int, -3 Happy, -Rs.1000)");
        System.out.println("3) Go on a Date (swingy)");
        System.out.print("Choose: ");
        int sel = readInt();
        switch (sel) {
            case 1:
                p.health += 8; p.happiness += 2; spend(p, 2000, log, "gym");
                p.passYear(log);
                break;
            case 2:
                p.intelligence += 6; p.happiness -= 3; spend(p, 1000, log, "study materials");
                p.passYear(log);
                break;
            case 3:
                if (RNG.nextBoolean()) {
                    p.happiness += 8; spend(p, 3000, log, "date");
                    log.add(p.name+" had a great date.");
                } else {
                    p.happiness -= 5; spend(p, 2000, log, "bad date");
                    log.add(p.name+" had a bad date.");
                }
                p.passYear(log);
                break;
            default: System.out.println("Invalid.");
        }
    }

    static void doRiskyFor(Person p, List<String> log) {
        System.out.println("\nRisky options:");
        System.out.println("1) Life-side Gamble (quick)");
        System.out.println("2) Small Crime");
        System.out.println("3) Do Nothing");
        System.out.print("Choose: ");
        int sel = readInt();
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
                p.passYear(log);
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
                p.passYear(log);
                break;
            case 3:
                // no passYear here—staying idle in risky panel; let user decide separate Live Year if desired
                break;
            default: System.out.println("Invalid.");
        }
    }

    /* ===============================
       =========== HELPERS ===========
       =============================== */

    static void spend(Person p, long amt, List<String> log, String what) {
        long pay = Math.min(amt, p.balance);
        p.balance -= pay; p.addFlow(pay);
        if (pay > 0) log.add(p.name+" spent Rs." + pay + " on " + what + ".");
    }

    static int eduRank(String e) {
        if (e.equals("None")) return 0;
        if (e.equals("HS")) return 1;
        if (e.equals("College")) return 2;
        if (e.equals("Masters")) return 3;
        return 0;
        }

    static void failJob(Person p, String job, List<String> log) {
        p.happiness -= 5;
        log.add(p.name+" failed job application: " + job);
        p.passYear(log);
    }

    static void showRecentLog(List<String> log) {
        System.out.println("\n----- Recent Log -----");
        int start = Math.max(0, log.size() - 30);
        for (int i=start; i<log.size(); i++) System.out.println("• " + log.get(i));
        if (log.isEmpty()) System.out.println("(empty)");
        System.out.println("----------------------");
    }

    static int readInt() {
        while (true) {
            try { return Integer.parseInt(SC.next()); }
            catch (Exception e) { System.out.print("Enter a number: "); }
        }
    }

    static long readLong() {
        while (true) {
            try { return Long.parseLong(SC.next()); }
            catch (Exception e) { System.out.print("Enter a whole amount: "); }
        }
    }
}