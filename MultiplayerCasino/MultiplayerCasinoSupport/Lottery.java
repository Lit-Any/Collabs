package MultiplayerCasino.MultiplayerCasinoSupport;

import java.util.*;
import utility.*;

import MultiplayerCasino.MultiplayerCasino;

public class Lottery {
    // 🎰 Lottery System with Accumulating Bonus Pool (now counts flows)
    public static void lotterySystem(Player[] players, Scanner sc) {
        Random rand = new Random();
        PrintMethods PrintMethods = new PrintMethods();

        PrintMethods.pln("\n🎰 Welcome to the Lottery System!");
        PrintMethods.pln("Each ticket costs $100. The more tickets you buy, the higher your chance of winning!");
        PrintMethods.pln("💡 Winner takes 90% of the pot + the accumulated bonus pool (tax from previous rounds)!");
        PrintMethods.pln("🎁 Current Bonus Pool: $" + MultiplayerCasino.lotteryBonusPool);

        double ticketPrice = 100.0;
        double totalPot = 0.0;

        ArrayList<Player> ticketPool = new ArrayList<>();

        // Each player chooses tickets
        for (Player p : players) {
            PrintMethods.pln("\n" + p.name + ", your balance: $" + p.balance);
            PrintMethods.p("Enter number of tickets to buy (0 to skip): ");
            int numTickets = sc.nextInt();
            if (numTickets < 0) {
                PrintMethods.pln("❌ Invalid number of tickets.");
                continue;
            }

            double cost = numTickets * ticketPrice;

            if (numTickets > 0 && cost <= p.balance) {
                p.balance -= cost;
                p.addFlow(cost); // money out
                totalPot += cost;
                p.lotteryContribution += cost; // track contributions to lottery

                for (int t = 0; t < numTickets; t++) {
                    ticketPool.add(p);
                }

                PrintMethods.pln("✅ " + p.name + " bought " + numTickets + " tickets for $" + cost);
            } else if (numTickets > 0) {
                PrintMethods.pln("❌ Not enough balance! Skipped.");
            } else {
                PrintMethods.pln("⏭ " + p.name + " skipped the lottery.");
            }
        }

        if (ticketPool.isEmpty()) {
            PrintMethods.pln("\n⚠️ No tickets bought. Lottery cancelled.");
            return;
        }

        // Draw winner
        Player winner = ticketPool.get(rand.nextInt(ticketPool.size()));
        double tax = totalPot * 0.1;
        double prize = (totalPot * 0.9) + MultiplayerCasino.lotteryBonusPool;

        // Payout winner
        winner.balance += prize;
        winner.addFlow(prize); // money in
        if (prize > winner.biggestLotteryWin) {
            winner.biggestLotteryWin = prize;
        }

        // Grow the progressive pool by adding this round's tax
        MultiplayerCasino.lotteryBonusPool += tax;

        PrintMethods.pln("\n🏆 Lottery Winner: " + winner.name + "!");
        PrintMethods.pln("💰 Prize paid: $" + prize + " (includes previous bonus pool)");
        PrintMethods.pln("📉 Tax collected this round: $" + tax + " → added to bonus pool.");
        PrintMethods.pln("🎁 New Bonus Pool (for next round): $" + MultiplayerCasino.lotteryBonusPool);
    }
}
