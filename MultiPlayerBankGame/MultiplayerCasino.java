package MultiPlayerBankGame;

import java.util.*;

import MultiPlayerBankGame.MultiplayerCasinoSupport.*;



public class MultiplayerCasino {
    static Random rand = new Random();

    // Pools
    public static double jackpotPool = 0.0;       // Gambling: all lost bets accumulate here until the next win
    public static double lotteryBonusPool = 0.0;  // lottery: 10% tax accumulates across rounds

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enable win/loss condition? (Y/N): ");
        boolean enableCondition = sc.nextLine().trim().equalsIgnoreCase("Y") || sc.nextLine().trim().equalsIgnoreCase("YES");
        System.out.print("Enter number of players: ");
        int n = sc.nextInt();
        sc.nextLine();

        Player[] players = new Player[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter player " + (i+1) + " name: ");
            players[i] = new Player(sc.nextLine(), 1000);
        }

        if (n==1) {
            players[1] = new Player("AI", 1000); // Add a computer player for single-player mode
        }

        boolean running = true;
        double maxBalance = 0;

        System.out.println("\n🏦 Welcome to the Multi-Player Casino Simulator!");
        System.out.println("Each player starts with $1000.");

        while (running) {
            for (Player p : players) {
                System.out.println("\n--- " + p.name + "'s Turn ---");

                if (p.name.equals("AI")) {
                    // AI logic: random actions
                    int action = rand.nextInt(10);
                    if (action < 2) {
                        p.deposit(rand.nextDouble() * 500); // Random deposit
                    } else if (action < 5) {
                        p.withdraw(rand.nextDouble() * 500); // Random withdrawal
                    } else if (action < 7) {
                        p.takeLoan(rand.nextDouble() * 300); // Random loan
                    } else if (action < 8) {
                        EconEvent.economicEvent(p); // Random economic event
                    } else {
                        GambleMenu.gambleMenu(p, sc); // Random gamble
                    }
                }
                
                System.out.println(
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
                System.out.print("Choose: ");
                int choice = sc.nextInt();

                if (choice == 1) {
                    p.showStatus();
                } else if (choice == 2) {
                    System.out.print("Enter deposit amount: ");
                    p.deposit(sc.nextDouble());
                } else if (choice == 3) {
                    System.out.print("Enter withdrawal amount: ");
                    p.withdraw(sc.nextDouble());
                } else if (choice == 4) {
                    System.out.print("Enter loan amount: ");
                    p.takeLoan(sc.nextDouble());
                } else if (choice == 5) {
                    System.out.print("Enter repayment amount: ");
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
                    System.out.println("⏭ " + p.name + " skipped their turn.");
                } else if (choice == 11) {
                    running = false;
                    System.out.println("👋 Game ended by " + p.name);
                    break;
                } else {
                    System.out.println("❌ Invalid choice!");
                }

                if (p.balance > maxBalance && enableCondition) {
                    maxBalance = p.balance; // Track highest balance
                    if ((p.balance)*3 < maxBalance) {
                        System.out.println("🚨 " + p.name + " has fallen too far behind! Ending game.");
                        running = false;
                        break;
                }
                }
            }
        }

        // 📊 Final Results
        System.out.println("\n📊 Final Results:");
        for (Player p : players) {
            p.showStatus();
            System.out.println("-----------------");
        }

        // 🏆 Leaderboard
        Leaderboard.showLeaderboard(players);

        sc.close();
    }
}