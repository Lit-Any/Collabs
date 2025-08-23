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

public class LifeSimulator {

    static final Scanner SC = new Scanner(System.in);
    static final Random RNG = new Random();

    public static long lotteryBonusPool = 0L; // lottery: 10% tax accumulates across rounds
    public static long jackpotPool = 0L;       // gambling: all lost bets accumulate here until next win

    /* ===============================
       ========== GAME LOOP ==========
       =============================== */

    public static void main(String[] args) {

        System.out.println("🏦 Welcome to the Life Simulator!");
        System.out.print("Enter number of players: ");
        int n = Helpers.readInt();

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
                if (!p.alive) {
                    System.out.println("\n--- " + p.name + " is deceased. Skipping turn. ---");
                    if (n == 1) { System.out.println("\nAll players deceased. Terminating program. \n==============================\n"); System.exit(0); }  // single-player auto-exit
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
                    "8) Exit game"
                );
                System.out.print("Choose: ");
                int choice = Helpers.readInt();

                switch (choice) {
                    case 1: p.passYear(log); break;
                    case 2: LifeActions.doStudyFor(p, log); break;
                    case 3: LifeActions.doWorkFor(p, log); break;
                    case 4: LifeActions.doImproveFor(p, log); break;
                    case 5: LifeActions.doRiskyFor(p, log); break;
                    case 6: Economy.economicMenu(p, players); break;
                    case 7: Helpers.showRecentLog(log); break;
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
}