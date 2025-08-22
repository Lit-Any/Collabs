package MultiplayerCasino;

import java.util.*;
import MultiplayerCasino.MultiplayerCasinoSupport.*;
import utility.*;

public class MultiplayerCasino {
    static Random rand = new Random();

    // Pools
    public static double jackpotPool = 0.0;       // Gambling: all lost bets accumulate here until the next win
    public static double lotteryBonusPool = 0.0;  // lottery: 10% tax accumulates across rounds

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PrintMethods.pln("Enable win/loss condition? (Y/N)\nSaying no enables infinite play. ");
        boolean enableCondition = sc.next().trim().equalsIgnoreCase("Y") || sc.nextLine().trim().equalsIgnoreCase("YES");
        sc.nextLine(); // Consume newline after next()

        PrintMethods.p("Enter number of players: ");
        int n = sc.nextInt();
        sc.nextLine(); // Consume newline after nextInt()

        if (n < 1) {
            PrintMethods.pln("❌ Invalid number of players. Terminating program.");
            System.exit(0);;
        }

        Player[] players;
        if (n == 1) {
            players = new Player[2];
            players[0] = new Player("Human", 1000);
            players[1] = new Player("AI", 1000);
            n = 2; // Now we have 2 players
        } else {
            players = new Player[n];
            for (int i = 0; i < n; i++) {
                PrintMethods.p("Enter player " + (i+1) + " name: ");
                players[i] = new Player(sc.nextLine(), 1000);
            }
        }

        boolean running = true;
        double maxBalance = 0;
        int count = 0;

        PrintMethods.pln("\n🏦 Welcome to the Multi-Player Casino Simulator!");
        PrintMethods.pln("Each player starts with $1000.");

        while (running) {
            for (Player p : players) {
                PrintMethods.pln("\n--- " + p.name + "'s Turn ---");

                if (p.name.equals("AI")) {
                    // AI logic: random actions
                    int choice = rand.nextInt(10)+1; // Random choice from 1 to 10

                    choice = (count == 5) ? 1 : choice; // AI always checks balance on 5th turn

                    if (choice == 1) {
                        p.showStatus();
                    } else if (choice == 2) {
                        PrintMethods.p("Enter deposit amount: ");
                        p.deposit(sc.nextDouble());
                    } else if (choice == 3) {
                        PrintMethods.p("Enter withdrawal amount: ");
                        p.withdraw(sc.nextDouble());
                    } else if (choice == 4) {
                        PrintMethods.p("Enter loan amount: ");
                        p.takeLoan(sc.nextDouble());
                    } else if (choice == 5) {
                        PrintMethods.p("Enter repayment amount: ");
                        p.repayLoan(sc.nextDouble());
                    } else if (choice == 6) {
                        EconEvent.economicEvent(p);
                    } else if (choice == 7) {
                        GambleMenu.gambleMenu(p, sc);
                    } else if (choice == 8) {
                        StartCardPoll.startCardPoll(players, sc);
                    } else if (choice == 9) {
                        Lottery.lotterySystem(players, sc);
                    } else if (choice == 10) {
                        PrintMethods.pln("⏭ " + p.name + " skipped their turn.");
                    } else if (choice == 11) {
                        running = false;
                        PrintMethods.pln("👋 Game ended by " + p.name);
                        break;
                    } else {
                        PrintMethods.pln("❌ Invalid choice!");
                    }

                    count++;

                } else {
                    PrintMethods.pln(
                    "1. Check Balance\n" +
                    "2. Deposit Money\n" +
                    "3. Withdraw Money\n" +
                    "4. Take a Loan\n" +
                    "5. Repay Loan\n" +
                    "6. Economic Event\n" +
                    "7. Gamble\n" +
                    "8. Propose Card Games\n" +
                    "9. Lottery\n" +
                    "10. Skip Turn\n" +
                    "11. Exit Game"
                    );
                    PrintMethods.p("Choose: ");
                    int choice = sc.nextInt();

                    if (choice == 1) {
                        p.showStatus();
                    } else if (choice == 2) {
                        PrintMethods.p("Enter deposit amount: ");
                        p.deposit(sc.nextDouble());
                    } else if (choice == 3) {
                        PrintMethods.p("Enter withdrawal amount: ");
                        p.withdraw(sc.nextDouble());
                    } else if (choice == 4) {
                        PrintMethods.p("Enter loan amount: ");
                        p.takeLoan(sc.nextDouble());
                    } else if (choice == 5) {
                        PrintMethods.p("Enter repayment amount: ");
                        p.repayLoan(sc.nextDouble());
                    } else if (choice == 6) {
                        EconEvent.economicEvent(p);
                    } else if (choice == 7) {
                        GambleMenu.gambleMenu(p, sc);
                    } else if (choice == 8) {
                        StartCardPoll.startCardPoll(players, sc);
                    } else if (choice == 9) {
                        Lottery.lotterySystem(players, sc);
                    } else if (choice == 10) {
                        PrintMethods.pln("⏭ " + p.name + " skipped their turn.");
                    } else if (choice == 11) {
                        running = false;
                        PrintMethods.pln("👋 Game ended by " + p.name);
                        break;
                    } else {
                        PrintMethods.pln("❌ Invalid choice!");
                    }
                }

                if (p.balance > maxBalance && enableCondition) {
                    maxBalance = p.balance; // Track highest balance
                }

                if ((p.balance)*3 < maxBalance && enableCondition) {
                        PrintMethods.pln("🚨 " + p.name + " has fallen too far behind! Ending game.");
                        running = false;
                        break;
                }
            }
        }

        // 📊 Final Results
        PrintMethods.pln("\n📊 Final Results:");
        for (Player p : players) {
            p.showStatus();
            PrintMethods.pln("-----------------");
        }

        // 🏆 Leaderboard
        Leaderboard.showLeaderboard(players);

        PrintMethods.pln("\nProgram terminated.");
        // 🏁 End of game

        sc.close();
    }
}