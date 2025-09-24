/*
  LifeSimulator.java — Unified Life + Economy (BlueJ-compatible, console)
  ----------------------------------------------------------
  - Merges Life Simulator (Code2) with the full Economic Framework (Code1)
  - One unified menu per player's turn:
      * Life actions (study, work, improve, risky, etc.)
      * Economic actions (banking, 20 gambling games, lottery, Rummy)
  - Player creation is handled here (Code1's player-adder removed)
  - Code1's leaderboard removed as requested (Who requested it? 🤨)
  - Money type unified to 'long' (integer currency). Doubles from Code1 are rounded.
  - Rummy card game preserved (consent poll among current players)
  - BlueJ-friendly, single file, console I/O

  Notes:
  - This version is console-based for a clean, unified flow.
  - All economic profits/losses immediately affect the same player's balance used by life actions.
*/

package LifeSim;

import java.util.*;
import LifeSim.LifeSimSupport.*;
import utility.*;

public class LifeSimulator {

    static final Scanner SC = new Scanner(System.in);
    static final Random RNG = new Random();
    static final StringBuilder sb = new StringBuilder();
    static PrintMethods PrintMethods = new PrintMethods();

    public static long lotteryBonusPool = 0L; // lottery: 10% tax accumulates across rounds
    public static long jackpotPool = 0L;       // gambling: all lost bets accumulate here until next win

    /* ===============================
       ========== GAME LOOP ==========
       =============================== */

    public static void main(String[] args) {

        char[] elements = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+[]{};:'\\\",.<>?/|`~".toCharArray();
        String randomText = new String("");
        int livePlayers = 0;

        PrintMethods.pln(ConsoleColors.ULTRA.WHITE + ConsoleColors.ULTRA_BG.BLACK + 
            "\n=========================================\n" +
            "      Welcome to Life Simulator!\n" +
            "=========================================" + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
        PrintMethods.pln(ConsoleColors.INFO + "Note: This is a console-based game. Please ensure your console supports ANSI colors for the best experience." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
        Tutorial.CheckTutorial();

        PrintMethods.p("\nEnter number of players: ");
        int n = Helpers.readInt();

        List<Person> players = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            PrintMethods.p("\nEnter player " + (i+1) + "'s name: ");
            String name = SC.next().trim();
            if (name.isEmpty()) name = "Player_" + (i+1);
            players.add(new Person(name));
        }

        List<String> log = new ArrayList<>();
        boolean running = true;

        if (n < 1) {
            PrintMethods.pln(ConsoleColors.ERROR + "\n❌ Number of players must be at least 1. Exiting.\n" + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
            return;
        }

        while (running) {
            for (Person p : players) {

                if (!p.alive) {
                    PrintMethods.pln(ConsoleColors.INFO + "\n--- " + p.name + " is deceased. Skipping turn. ---" + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                    if (n == 1) { PrintMethods.pln(ConsoleColors.INFO + "\nAll players deceased." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);   // single-player auto-exit
                    running = false; }
                    else {
                        for (Person pp : players) {
                            if (pp.alive) {
                                livePlayers++;
                            }
                        }
                        
                        if (livePlayers == 0) {
                            PrintMethods.pln(ConsoleColors.INFO + "\nAll players deceased." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                            running = false; // all players dead, end game
                            break;
                        }
                        livePlayers = 0;
                    }
                    continue;
                }
                
                while (p.backPressed) { // handle back action from sub-menus

                    p.backPressed = false;  // reset

                    PrintMethods.pln("\n==============================");
                    PrintMethods.pln("Turn — " + ConsoleColors.WARNING + p.name + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                    p.showStatus();
                    PrintMethods.pln("------------------------------");
                    PrintMethods.pln(ConsoleColors.ULTRA.WHITE + ConsoleColors.ULTRA_BG.BLACK + 
                        "1) Live year (auto)\n" +
                        "2) Study\n" +
                        "3) Work\n" +
                        "4) Improve stats\n" +
                        "5) Risky (life-side)\n" +
                        "6) Economic Actions (bank, gamble, lottery, rummy)\n" +
                        "7) Show recent log\n" +
                        "8) Exit game\n" +
                        ConsoleColors.ULTRA_BOLD.RED + "9)" + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK
                    );
                    
                    PrintMethods.p("\nChoose: ");
                    int choice = Helpers.readInt();

                    switch (choice) {
                        case 1:

                            if (Helpers.ForceEmploymentIfInDebt(p)) {
                                break;
                            }
                            if (p.nightmareMode) {
                                p.passNightmareYear(log);
                            } else {
                                p.passYear(log);
                            }

                            break;
                        case 2: 
                            if (Helpers.ForceEmploymentIfInDebt(p)) {
                                break;
                            }
                            if (p.nightmareMode) {
                                PrintMethods.pln(ConsoleColors.ULTRA_FG.ULTRA_RED_ON_BLACK + "⚠️  Nightmare Mode: Studying is disabled!" + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                                break;
                            }
                            LifeActions.doStudyFor(p, log); ; break;
                        case 3:
                            if (p.nightmareMode) {
                                PrintMethods.pln(ConsoleColors.ULTRA_FG.ULTRA_RED_ON_BLACK + "⚠️  Nightmare Mode: Job locked to Retail Worker" + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                                break;
                            }
                            LifeActions.doWorkFor(p, log);
                            break;
                        case 4: 
                            if (Helpers.ForceEmploymentIfInDebt(p)) {
                                break;
                            }
                            LifeActions.doImproveFor(p, log); break;
                        case 5:
                            if (Helpers.ForceEmploymentIfInDebt(p)) {
                                break;
                            }
                            LifeActions.doRiskyFor(p, log); break;
                        case 6:
                            if (Helpers.ForceEmploymentIfInDebt(p)) {
                                break;
                            }
                            Economy.economicMenu(p, players); Helpers.CompoundLoan(p); break;
                        case 7:
                            Helpers.showRecentLog(log); p.backPressed = true; break;
                        case 8:
                            running = false;
                            PrintMethods.pln(ConsoleColors.INFO + "\n👋 Game ended by " + p.name + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                            break;
                        case 9:
                            p.counterToNightmareMode++;

                            switch (p.counterToNightmareMode) {
                                case 1:
                                    PrintMethods.pln(ConsoleColors.WARNING + "Oops! You weren't supposed to do that." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                                    break;
                                case 2:
                                    PrintMethods.pln(ConsoleColors.WARNING + "I said, DO NOT do that." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                                    break;
                                case 3:
                                    Person.job = "Retail Worker"; // nightmare mode job lock
                                    p.education = "None"; // nightmare mode education lock
                                    PrintMethods.pln(ConsoleColors.WARNING + "Very well then." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                                    PrintMethods.pln(ConsoleColors.ULTRA_FG.ULTRA_RED_ON_BLACK + "\nN I G H T M A R E   M O D E   A C T I V A T E D" 
                                                    + "\nHint: The only way to survive now is through lucky gambles...good luck."+ ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                                    PrintMethods.pln(ConsoleColors.ULTRA_FG.ULTRA_RED_ON_BLACK + "⚠️  Nightmare Mode: Studying is disabled." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                                    PrintMethods.pln(ConsoleColors.ULTRA_FG.ULTRA_RED_ON_BLACK + "⚠️  Nightmare Mode: Job locked to Retail Worker - income: " + Person.incomePerYear() + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                                    p.nightmareMode = true;
                                    break;
                                default:

                                    for (int i = 0; i < 30; i++) {
                                        sb.append(elements[RNG.nextInt(elements.length)]);
                                        randomText = sb.toString();
                                    }

                                    PrintMethods.pln(ConsoleColors.ERROR + "\n" + randomText + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                                    PrintMethods.pln(ConsoleColors.ERROR + "Game advanced by one year." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                                    p.passNightmareYear(log);
                                    sb.setLength(0); // reset for next use
                            }

                            break;
                        default: PrintMethods.pln(ConsoleColors.WARNING + "\n❌ Invalid choice!" + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                    }

                }

                if (!running) break;
            }
        }

        // Final Results
        PrintMethods.pln(ConsoleColors.INFO + ConsoleColors.ULTRA_BG.BLACK + "\n📊 Final Results:");
        for (Person p : players) {
            p.showStatus();
            PrintMethods.pln(ConsoleColors.INFO + "   Gambles: played " + p.gamblesPlayed + ", won " + p.gamblesWon + ", lost " + p.gamblesLost);
            PrintMethods.pln("   Money Won: Rs." + p.moneyWon + " | Money Lost: Rs." + p.moneyLost + " | Lifetime Flows: Rs." + p.lifetimeTotal);
            PrintMethods.pln("   Biggest Lottery Win: Rs." + p.biggestLotteryWin + " | Lottery Contribution: Rs." + p.lotteryContribution);
            PrintMethods.pln("-------------------------------" + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
        }
    }
}