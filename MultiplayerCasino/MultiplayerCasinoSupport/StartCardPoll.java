package MultiplayerCasino.MultiplayerCasinoSupport;

import java.util.ArrayList;
import java.util.Scanner;
import utility.*;

public class StartCardPoll {

    static PrintMethods PrintMethods = new PrintMethods();
    
    // 🃏 Multiplayer Card Poll (now integrated with RummyModule)
    public static void startCardPoll(Player[] players, Scanner sc) {
        int yesVotes = 0;
        ArrayList<Player> consenting = new ArrayList<>();
        PrintMethods.pln("\n🃏 A player has requested to start CARD GAMES!");
        for (Player p : players) {
            PrintMethods.p(p.name + ", do you agree? (Y/N): ");
            String vote = sc.next();
            if (vote.equalsIgnoreCase("yes") || vote.equalsIgnoreCase("y")) {
                yesVotes++;
                consenting.add(p);
            }
        }
        if (yesVotes > players.length / 2) {
            PrintMethods.pln("✅ Majority agreed! Card games starting now...");
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
                    PrintMethods.pln("🎉 " + players[originalIndex].name + " received +" + points + " to balance. New balance: $" + players[originalIndex].balance);
                } else {
                    // Fallback: credit by name match failure (shouldn't happen)
                    PrintMethods.pln("⚠️ Winner found but couldn't map to original player list.");
                }
            } else {
                PrintMethods.pln("No Rummy winner (game ended without a valid declaration).");
            }
        } else {
            PrintMethods.pln("❌ Not enough votes. Card games cancelled.");
        }
    }
}
