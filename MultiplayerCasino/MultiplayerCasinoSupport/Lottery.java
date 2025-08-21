package MultiplayerCasino.MultiplayerCasinoSupport;

import java.util.*;

import MultiplayerCasino.MultiplayerCasino;

public class Lottery {
    // 🎰 Lottery System with Accumulating Bonus Pool (now counts flows)
    public static void lotterySystem(Player[] players, Scanner sc) {
        Random rand = new Random();

        System.out.println("\n🎰 Welcome to the Lottery System!");
        System.out.println("Each ticket costs $100. The more tickets you buy, the higher your chance of winning!");
        System.out.println("💡 Winner takes 90% of the pot + the accumulated bonus pool (tax from previous rounds)!");
        System.out.println("🎁 Current Bonus Pool: $" + MultiplayerCasino.lotteryBonusPool);

        double ticketPrice = 100.0;
        double totalPot = 0.0;

        ArrayList<Player> ticketPool = new ArrayList<>();

        // Each player chooses tickets
        for (Player p : players) {
            System.out.println("\n" + p.name + ", your balance: $" + p.balance);
            System.out.print("Enter number of tickets to buy (0 to skip): ");
            int numTickets = sc.nextInt();
            if (numTickets < 0) {
                System.out.println("❌ Invalid number of tickets.");
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

                System.out.println("✅ " + p.name + " bought " + numTickets + " tickets for $" + cost);
            } else if (numTickets > 0) {
                System.out.println("❌ Not enough balance! Skipped.");
            } else {
                System.out.println("⏭ " + p.name + " skipped the lottery.");
            }
        }

        if (ticketPool.isEmpty()) {
            System.out.println("\n⚠️ No tickets bought. Lottery cancelled.");
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

        System.out.println("\n🏆 Lottery Winner: " + winner.name + "!");
        System.out.println("💰 Prize paid: $" + prize + " (includes previous bonus pool)");
        System.out.println("📉 Tax collected this round: $" + tax + " → added to bonus pool.");
        System.out.println("🎁 New Bonus Pool (for next round): $" + MultiplayerCasino.lotteryBonusPool);
    }
}
