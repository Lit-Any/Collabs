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

import java.util.*;
import LifeSim.LifeSimSupport.*;
import utility.*;

public class LifeSimulator {

    static final Scanner SC = new Scanner(System.in);
    static final Random RNG = new Random();

    public static long lotteryBonusPool = 0L; // lottery: 10% tax accumulates across rounds
    public static long jackpotPool = 0L;       // gambling: all lost bets accumulate here until next win
    public static boolean nightmareMode = false; // nightmare mode flag

    /* ===============================
       ========== GAME LOOP ==========
       =============================== */

    public static void main(String[] args) {

        int counterToNightmareMode = 0;
        String[] elements = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+[]{};:'\\\",.<>?/|`~".split("");
        String[] randomText = new String[30];

        System.out.println("\n🏦 Welcome to the Life Simulator!");
        System.out.print("\nEnter number of players: ");
        int n = Helpers.readInt();

        List<Person> players = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter player " + (i+1) + " name: ");
            String name = SC.nextLine().trim();
            if (name.isEmpty()) name = "Player_" + (i+1);
            players.add(new Person(name));
        }

        List<String> log = new ArrayList<>();
        boolean running = true;

        while (running) {
            for (Person p : players) {
                if (!p.alive) {
                    System.out.println("\n--- " + p.name + " is deceased. Skipping turn. ---");
                    if (n == 1) { System.out.println("\nAll players deceased."); }  // single-player auto-exit
                    running = false;
                    continue;
                }

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
                    "8) Exit game\n" +
                    ConsoleColors.ULTRA_BOLD.RED + "9) " + ConsoleColors.RESET
                );
                
                System.out.print("\nChoose: ");
                int choice = Helpers.readInt();

                switch (choice) {
                    case 1:

                        if (nightmareMode) {
                            p.passNightmareYear(log);
                        } else {
                            p.passYear(log);
                        }

                        Helpers.CompoundLoan(p);
                        break;

                    case 2: 
                        if (nightmareMode) {
                            System.out.println(ConsoleColors.ULTRA_FG.ULTRA_RED_ON_BLACK + "⚠️  Nightmare Mode: Studying is disabled!" + ConsoleColors.RESET);
                            break;
                        }
                        LifeActions.doStudyFor(p, log); ; break;
                    case 3: 
                        if (nightmareMode) {
                            System.out.println(ConsoleColors.ULTRA_FG.ULTRA_RED_ON_BLACK + "⚠️  Nightmare Mode: Job locked to Retail Worker" + ConsoleColors.RESET);
                            break;
                        }
                        LifeActions.doWorkFor(p, log);
                        Helpers.CompoundLoan(p);
                        break;
                    case 4: LifeActions.doImproveFor(p, log); Helpers.CompoundLoan(p); break;
                    case 5: LifeActions.doRiskyFor(p, log); Helpers.CompoundLoan(p); break;
                    case 6: Economy.economicMenu(p, players); Helpers.CompoundLoan(p); break;
                    case 7: Helpers.showRecentLog(log); Helpers.CompoundLoan(p); break;
                    case 8:
                        running = false;
                        System.out.println("\n👋 Game ended by " + p.name);
                        break;
                    case 9:
                        counterToNightmareMode++;

                        switch (counterToNightmareMode) {
                            case 1:
                                System.out.println(ConsoleColors.WARNING + "Oops! You weren't supposed to do that." + ConsoleColors.RESET);
                                break;
                            case 2:
                                System.out.println(ConsoleColors.WARNING + "I said, DO NOT do that." + ConsoleColors.RESET);
                                break;
                            case 3:
                                System.out.println(ConsoleColors.WARNING + "Very well then." + ConsoleColors.RESET);
                                PrintMethods.pln(ConsoleColors.ULTRA_FG.ULTRA_RED_ON_BLACK + "\nN I G H T M A R E   M O D E   A C T I V A T E D" + ConsoleColors.RESET);
                                p.job = "Retail Worker"; // nightmare mode job lock
                                p.education = "None"; // nightmare mode education lock
                                nightmareMode = true;
                                break;
                            default:

                                for (int i = 0; i < RNG.nextInt(randomText.length); i++) {
                                    randomText[i] = elements[RNG.nextInt(elements.length)];
                                }

                                System.out.println(ConsoleColors.ERROR + randomText + ConsoleColors.RESET);
                        }

                        break;
                    default: System.out.println("\n❌ Invalid choice!");
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
}