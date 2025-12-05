package LifeSim.LifeSimSupport;

import java.util.*;
import utility.*;

/* ===============================
       ========== ECONOMY ============
       =============================== */

public class Economy {
    static Random rand = new Random();
    static java.util.Scanner SC = new java.util.Scanner(System.in);
    static PrintMethods PrintMethods = new PrintMethods();

    /* ---- Unified Economic Menu ---- */
    public static void economicMenu(Person p, List<Person> allPlayers) {
        boolean back = false;
        while (!back) {
            PrintMethods.pln("\n--- 💼 Economic Actions for " + p.name + " ---");
            PrintMethods.pln("Balance: Rs." + p.balance + " | Loan: Rs." + p.loan + " | Jackpot: Rs." + LifeSim.LifeSimulator.jackpotPool + " | Lottery Bonus: Rs." + LifeSim.LifeSimulator.lotteryBonusPool);
            PrintMethods.pln(
                "1) Deposit\n" +
                "2) Withdraw\n" +
                "3) Take Loan\n" +
                "4) Repay Loan\n" +
                "5) Gamble (20 games)\n" +
                "6) Lottery\n" +
                "7) Start Card Games (Rummy)\n" +
                "8) Back"
            );
            PrintMethods.p("Choose: ");
            int c = Helpers.readInt();
            switch (c) {
                case 1: PrintMethods.p("Amount to deposit: "); p.deposit(Helpers.readLong()); break;
                case 2: PrintMethods.p("Amount to withdraw: "); p.withdraw(Helpers.readLong()); break;
                case 3: PrintMethods.p("Loan amount: "); p.takeLoan(Helpers.readLong()); break;
                case 4: PrintMethods.p("Repay amount: "); p.repayLoan(Helpers.readLong()); break;
                case 5: GambleMenu.gambleMenu(p); break;
                case 6: Lottery.lotterySystem(allPlayers); break;
                case 7: Cards.startCardPoll(allPlayers); break;
                case 8: back = true; p.backPressed = true; break;
                default: PrintMethods.pln("❌ Invalid choice.");
            }
        }
    }
}