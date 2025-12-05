package LifeSim.LifeSimSupport;

import java.util.*;
import utility.*;
import LifeSim.LifeSimulator;

public class Lottery {

    static java.util.Random rand = new java.util.Random();
    static PrintMethods PrintMethods = new PrintMethods();

    /* ---- Lottery (with accumulating bonus pool) ---- */
    static void lotterySystem(List<Person> players) {
        PrintMethods.pln("\n🎰 Lottery System — Each ticket costs Rs.100");
        PrintMethods.pln("💡 Winner takes 90% of the pot + accumulated bonus pool!");
        PrintMethods.pln("🎁 Current Bonus Pool: Rs." + LifeSimulator.lotteryBonusPool);

        long ticketPrice = 100L;
        long totalPot = 0L;

        ArrayList<Person> ticketPool = new ArrayList<>();

        for (Person p : players) {
            PrintMethods.pln("\n" + p.name + ", your balance: Rs." + p.balance);
            PrintMethods.p("Enter number of tickets to buy (0 to skip): ");
            int numTickets = Helpers.readInt();
            if (numTickets < 0) { PrintMethods.pln("❌ Invalid number."); continue; }
            long cost = numTickets * ticketPrice;
            if (numTickets > 0 && cost <= p.balance) {
                p.balance -= cost; p.addFlow(cost);
                totalPot += cost;
                p.lotteryContribution += cost;
                for (int t=0;t<numTickets;t++) ticketPool.add(p);
                PrintMethods.pln("✅ " + p.name + " bought " + numTickets + " tickets for Rs." + cost);
            } else if (numTickets > 0) {
                PrintMethods.pln("❌ Not enough balance! Skipped.");
            } else {
                PrintMethods.pln("⏭ " + p.name + " skipped the lottery.");
            }
        }

        if (ticketPool.isEmpty()) { PrintMethods.pln("\n⚠️ No tickets bought. Lottery cancelled."); return; }

        // Draw winner
        Person winner = ticketPool.get(rand.nextInt(ticketPool.size()));
        long tax = Math.round(totalPot * 0.10);
        long prize = Math.round(totalPot * 0.90) + LifeSimulator.lotteryBonusPool;

        // Payout winner
        winner.balance += prize; winner.addFlow(prize);
        if (prize > winner.biggestLotteryWin) winner.biggestLotteryWin = prize;

        // Grow the progressive pool by adding this round's tax
        LifeSimulator.lotteryBonusPool += tax;

        PrintMethods.pln("\n🏆 Lottery Winner: " + winner.name + "!");
        PrintMethods.pln("💰 Prize paid: Rs." + prize + " (includes previous bonus pool)");
        PrintMethods.pln("📉 Tax collected this round: Rs." + tax + " → added to bonus pool.");
        PrintMethods.pln("🎁 New Bonus Pool (for next round): Rs." + LifeSimulator.lotteryBonusPool);
    }
}
