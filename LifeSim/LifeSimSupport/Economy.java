package LifeSim.LifeSimSupport;

import java.util.*;

/* ===============================
       ========== ECONOMY ============
       =============================== */

public class Economy {
    static Random rand = new Random();
    static java.util.Scanner SC = new java.util.Scanner(System.in);

    /* ---- Unified Economic Menu ---- */
    public static void economicMenu(Person p, List<Person> allPlayers) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- 💼 Economic Actions for " + p.name + " ---");
            System.out.println("Balance: Rs." + p.balance + " | Loan: Rs." + p.loan + " | Jackpot: Rs." + LifeSim.LifeSimulator.jackpotPool + " | Lottery Bonus: Rs." + LifeSim.LifeSimulator.lotteryBonusPool);
            System.out.println(
                "1) Deposit\n" +
                "2) Withdraw\n" +
                "3) Take Loan\n" +
                "4) Repay Loan\n" +
                "5) Gamble (20 games)\n" +
                "6) Lottery\n" +
                "7) Start Card Games (Rummy)\n" +
                "8) Back"
            );
            System.out.print("Choose: ");
            int c = Helpers.readInt();
            switch (c) {
                case 1: System.out.print("Amount to deposit: "); p.deposit(Helpers.readLong()); break;
                case 2: System.out.print("Amount to withdraw: "); p.withdraw(Helpers.readLong()); break;
                case 3: System.out.print("Loan amount: "); p.takeLoan(Helpers.readLong()); break;
                case 4: System.out.print("Repay amount: "); p.repayLoan(Helpers.readLong()); break;
                case 5: GambleMenu.gambleMenu(p); break;
                case 6: Lottery.lotterySystem(allPlayers); break;
                case 7: Cards.startCardPoll(allPlayers); break;
                case 8: back = true; break;
                default: System.out.println("❌ Invalid choice.");
            }
        }
    }

}