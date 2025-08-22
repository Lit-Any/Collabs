package MultiplayerCasino.MultiplayerCasinoSupport;

import utility.*;

public class Player {
    public String name;
    public double balance;
    double loan;

    // Gambling leaderboard stats
    public int gamblesPlayed = 0;
    public int gamblesWon = 0;
    public int gamblesLost = 0;
    public double moneyWon = 0;      // gambling-only winnings credited (includes jackpot when won)
    public double moneyLost = 0;     // gambling-only bets deducted (net of pushes)
    public double lifetimeTotal = 0; // TOTAL money handled across the whole game (ALL inflows/outflows)

    // Lottery stats
    public double biggestLotteryWin = 0.0;     // largest single lottery prize ever won
    public double lotteryContribution = 0.0;   // total money spent on lottery tickets

    public Player(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.loan = 0.0;
    }

    // Helper: count all money flow (absolute value) toward lifetimeTotal
    public void addFlow(double amount) {
        lifetimeTotal += Math.abs(amount);
    }

    // ✅ Show player status
    public void showStatus() {
        PrintMethods.pln("👤 " + name + " | Balance: $" + balance + " | Loan: $" + loan);
    }

    // ✅ Deposit
    public void deposit(double amount) {
        if (amount <= 0) { PrintMethods.pln("❌ Amount must be positive."); return; }
        balance += amount;
        addFlow(amount);
        PrintMethods.pln("✅ Deposited $" + amount);
    }

    // ✅ Withdraw
    public void withdraw(double amount) {
        if (amount <= 0) { PrintMethods.pln("❌ Amount must be positive."); return; }
        if (amount <= balance) {
            balance -= amount;
            addFlow(amount);
            PrintMethods.pln("✅ Withdrawn $" + amount);
        } else {
            PrintMethods.pln("❌ Not enough balance!");
        }
    }

    // ✅ Take Loan
    public void takeLoan(double amount) {
        if (amount <= 0) { PrintMethods.pln("❌ Amount must be positive."); return; }
        loan += amount * 1.1; // 10% interest
        balance += amount;
        addFlow(amount); // player received this much spending power
        PrintMethods.pln("🏦 Loan granted: $" + amount + " (10% interest applied)");
    }

    // ✅ Repay Loan
    public void repayLoan(double amount) {
        if (amount <= 0) { PrintMethods.pln("❌ Amount must be positive."); return; }
        if (amount <= balance && amount <= loan) {
            balance -= amount;
            loan -= amount;
            addFlow(amount); // money out
            PrintMethods.pln("✅ Repaid $" + amount);
        } else {
            PrintMethods.pln("❌ Repayment not possible!");
        }
    }
}