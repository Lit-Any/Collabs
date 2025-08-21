package MultiplayerCasino.MultiplayerCasinoSupport;

import java.util.Random;

public class EconEvent {
    // ✅ Random economic events (now count toward lifetimeTotal)
    public static void economicEvent(Player p) {
        System.out.println("🌍 Economic Event Happening...");
        Random rand = new Random();
        int event = rand.nextInt(5); // random 0-4
        switch (event) {
            case 0:
                System.out.println("📈 Stock market boom! You earn $500.");
                p.balance += 500;
                p.addFlow(500);
                break;
            case 1:
                System.out.println("📉 Inflation hits! You lose $200.");
                p.balance -= 200;
                p.addFlow(200);
                break;
            case 2:
                System.out.println("💸 You won a small lottery! +$1000.");
                p.balance += 1000;
                p.addFlow(1000);
                break;
            case 3:
                System.out.println("⚠️ Tax imposed! -$300.");
                p.balance -= 300;
                p.addFlow(300);
                break;
            case 4:
                System.out.println("💼 Business profit! +$700.");
                p.balance += 700;
                p.addFlow(700);
                break;
        }
    }
}
