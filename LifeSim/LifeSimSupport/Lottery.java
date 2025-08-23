package LifeSim.LifeSimSupport;

import java.util.ArrayList;
import java.util.List;
import LifeSim.LifeSimulator;

public class Lottery {

    static java.util.Random rand = new java.util.Random();

        /* ---- Lottery (with accumulating bonus pool) ---- */
    static void lotterySystem(List<Person> players) {
        System.out.println("\n🎰 Lottery System — Each ticket costs Rs.100");
        System.out.println("💡 Winner takes 90% of the pot + accumulated bonus pool!");
        System.out.println("🎁 Current Bonus Pool: Rs." + LifeSimulator.lotteryBonusPool);

        long ticketPrice = 100L;
        long totalPot = 0L;

        ArrayList<Person> ticketPool = new ArrayList<>();

        for (Person p : players) {
            System.out.println("\n" + p.name + ", your balance: Rs." + p.balance);
            System.out.print("Enter number of tickets to buy (0 to skip): ");
            int numTickets = Helpers.readInt();
            if (numTickets < 0) { System.out.println("❌ Invalid number."); continue; }
            long cost = numTickets * ticketPrice;
            if (numTickets > 0 && cost <= p.balance) {
                p.balance -= cost; p.addFlow(cost);
                totalPot += cost;
                p.lotteryContribution += cost;
                for (int t=0;t<numTickets;t++) ticketPool.add(p);
                System.out.println("✅ " + p.name + " bought " + numTickets + " tickets for Rs." + cost);
            } else if (numTickets > 0) {
                System.out.println("❌ Not enough balance! Skipped.");
            } else {
                System.out.println("⏭ " + p.name + " skipped the lottery.");
            }
        }

        if (ticketPool.isEmpty()) { System.out.println("\n⚠️ No tickets bought. Lottery cancelled."); return; }

        // Draw winner
        Person winner = ticketPool.get(rand.nextInt(ticketPool.size()));
        long tax = Math.round(totalPot * 0.10);
        long prize = Math.round(totalPot * 0.90) + LifeSimulator.lotteryBonusPool;

        // Payout winner
        winner.balance += prize; winner.addFlow(prize);
        if (prize > winner.biggestLotteryWin) winner.biggestLotteryWin = prize;

        // Grow the progressive pool by adding this round's tax
        LifeSimulator.lotteryBonusPool += tax;

        System.out.println("\n🏆 Lottery Winner: " + winner.name + "!");
        System.out.println("💰 Prize paid: Rs." + prize + " (includes previous bonus pool)");
        System.out.println("📉 Tax collected this round: Rs." + tax + " → added to bonus pool.");
        System.out.println("🎁 New Bonus Pool (for next round): Rs." + LifeSimulator.lotteryBonusPool);
    }
}
