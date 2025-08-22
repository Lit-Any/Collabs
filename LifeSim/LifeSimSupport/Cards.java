package LifeSim.LifeSimSupport;

import java.util.ArrayList;
import java.util.List;

public class Cards {
    
    static java.util.Scanner SC = new java.util.Scanner(System.in);
    static java.util.Random RNG = new java.util.Random();

        /* ---- Card Poll + Rummy (from Code1) ---- */
    static void startCardPoll(List<Person> players) {
        int yesVotes = 0;
        ArrayList<Person> consenting = new ArrayList<>();
        System.out.println("\n🃏 A player has requested to start CARD GAMES!");
        for (Person p : players) {
            System.out.print(p.name + ", do you agree? (yes/no): ");
            String vote = SC.next();
            if (vote.equalsIgnoreCase("yes")) {
                yesVotes++;
                consenting.add(p);
            }
        }
        if (yesVotes > players.size() / 2) {
            System.out.println("✅ Majority agreed! Card games starting now...");
            // Map consenting persons to lightweight PlayerAdapter for Rummy
            PlayerAdapter[] arr = new PlayerAdapter[consenting.size()];
            for (int i=0;i<consenting.size();i++) arr[i] = new PlayerAdapter(consenting.get(i).name);

            int[] result = RummyModule.runRummyGame(SC, arr); // {winnerIdxInConsenting, points}
            if (result[0] >= 0) {
                Person winner = consenting.get(result[0]);
                int points = result[1];
                winner.balance += points; winner.addFlow(points);
                System.out.println("🎉 " + winner.name + " received +" + points + " to balance. New balance: Rs." + winner.balance);
            } else {
                System.out.println("No Rummy winner.");
            }
        } else {
            System.out.println("❌ Not enough votes. Card games cancelled.");
        }
    }

    // Minimal adapter to satisfy RummyModule API without bringing Code1's Player directly
    static class PlayerAdapter {
        String name;
        PlayerAdapter(String n) { name = n; }
    }
}
