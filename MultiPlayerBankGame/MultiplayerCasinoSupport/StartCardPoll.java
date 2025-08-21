package MultiPlayerBankGame.MultiplayerCasinoSupport;

import java.util.ArrayList;
import java.util.Scanner;

public class StartCardPoll {
    // 🃏 Multiplayer Card Poll (now integrated with RummyModule)
    public static void startCardPoll(Player[] players, Scanner sc) {
        int yesVotes = 0;
        ArrayList<Player> consenting = new ArrayList<>();
        System.out.println("\n🃏 A player has requested to start CARD GAMES!");
        for (Player p : players) {
            System.out.print(p.name + ", do you agree? (yes/no): ");
            String vote = sc.next();
            if (vote.equalsIgnoreCase("yes")) {
                yesVotes++;
                consenting.add(p);
            }
        }
        if (yesVotes > players.length / 2) {
            System.out.println("✅ Majority agreed! Card games starting now...");
            // Start interactive Rummy with consenting players only
            Player[] consentingArr = consenting.toArray(new Player[0]);

            // Run interactive Rummy. It returns winner index relative to consentingArr and points earned.
            int[] result = RummyModule.runRummyGame(sc, consentingArr); // {winnerIdxInConsenting, points}
            if (result[0] >= 0) {
                Player winner = consentingArr[result[0]];
                int points = result[1];

                // Find the original index in the main players array and credit balance
                int originalIndex = -1;
                for (int i = 0; i < players.length; i++) {
                    if (players[i].name.equals(winner.name)) { originalIndex = i; break; }
                }
                if (originalIndex >= 0) {
                    players[originalIndex].balance += points;
                    players[originalIndex].addFlow(points);
                    System.out.println("🎉 " + players[originalIndex].name + " received +" + points + " to balance. New balance: $" + players[originalIndex].balance);
                } else {
                    // Fallback: credit by name match failure (shouldn't happen)
                    System.out.println("⚠️ Winner found but couldn't map to original player list.");
                }
            } else {
                System.out.println("No Rummy winner (game ended without a valid declaration).");
            }
        } else {
            System.out.println("❌ Not enough votes. Card games cancelled.");
        }
    }
}
