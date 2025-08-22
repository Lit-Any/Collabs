package MultiplayerCasino.MultiplayerCasinoSupport;

import java.util.Random;
import utility.*;

public class EconEvent {
    // ✅ Random economic events (now count toward lifetimeTotal)
    public static void economicEvent(Player p) {
        PrintMethods.pln("🌍 Economic Event Happening...");
        Random rand = new Random();
        int event = rand.nextInt(5); // random 0-4
        switch (event) {
            case 0:
                PrintMethods.pln("📈 Stock market boom! You earn $500.");
                p.balance += 500;
                p.addFlow(500);
                break;
            case 1:
                PrintMethods.pln("📉 Inflation hits! You lose $200.");
                p.balance -= 200;
                p.addFlow(200);
                break;
            case 2:
                PrintMethods.pln("💸 You won a small lottery! +$1000.");
                p.balance += 1000;
                p.addFlow(1000);
                break;
            case 3:
                PrintMethods.pln("⚠️ Tax imposed! -$300.");
                p.balance -= 300;
                p.addFlow(300);
                break;
            case 4:
                PrintMethods.pln("💼 Business profit! +$700.");
                p.balance += 700;
                p.addFlow(700);
                break;
        }
    }
}
