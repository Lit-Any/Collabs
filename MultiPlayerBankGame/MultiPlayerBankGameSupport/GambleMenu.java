package MultiPlayerBankGame.MultiPlayerBankGameSupport;

import java.util.Scanner;
import MultiPlayerBankGame.MultiPlayerBankGame;

public class GambleMenu {

    // 🎲 Gambling Menu (20 games)
    public static void gambleMenu(Player p, Scanner sc) {
        System.out.println("\n--- 🎰 Gambling Games Menu ---");
        System.out.println("1. Dice Guess\n2. Coin Toss\n3. High or Low\n4. Lucky Number\n5. Double or Nothing");
        System.out.println("6. Spin Wheel\n7. Odd or Even\n8. Pick a Card\n9. Mini Lottery\n10. Rock Paper Scissors");
        System.out.println("11. Blackjack Lite\n12. Roulette\n13. Slot Machine\n14. Horse Race\n15. Poker Draw");
        System.out.println("16. High Card War\n17. Cricket Bet\n18. Football Penalty\n19. Stock Bet\n20. Jackpot Lottery");
        System.out.print("Choose a game (1-20): ");
        int choice = sc.nextInt();

        System.out.print("Enter your bet amount: ");
        double bet = sc.nextDouble();

        if (bet <= 0) {
            System.out.println("❌ Bet must be positive.");
            return;
        }
        if (bet > p.balance) {
            System.out.println("❌ Not enough balance!");
            return;
        }

        // Take the bet upfront (at-risk model)
        p.balance -= bet;
        p.addFlow(bet);      // money out
        p.gamblesPlayed++;
        p.moneyLost += bet;  // will be corrected on push
        MultiPlayerBankGame.jackpotPool += bet;  // add to carryover pool until outcome is known

        double winnings = 0;

        switch (choice) {
            case 1:  winnings = Games.gambleDiceGuess(bet, sc); break;
            case 2:  winnings = Games.gambleCoinToss(bet, sc); break;
            case 3:  winnings = Games.gambleHighLow(bet, sc); break;
            case 4:  winnings = Games.gambleLuckyNumber(bet, sc); break;
            case 5:  winnings = Games.gambleDoubleOrNothing(bet, sc); break;
            case 6:  winnings = Games.gambleSpinWheel(bet, sc); break;
            case 7:  winnings = Games.gambleOddEven(bet, sc); break;
            case 8:  winnings = Games.gamblePickCard(bet, sc); break;
            case 9:  winnings = Games.gambleMiniLottery(bet); break;
            case 10: winnings = Games.gambleRPS(bet, sc); break;           // may return bet for push
            case 11: winnings = Games.gambleBlackjack(bet); break;         // may return bet for push
            case 12: winnings = Games.gambleRoulette(bet, sc); break;
            case 13: winnings = Games.gambleSlotMachine(bet); break;
            case 14: winnings = Games.gambleHorseRace(bet, sc); break;
            case 15: winnings = Games.gamblePoker(bet); break;
            case 16: winnings = Games.gambleHighCardWar(bet); break;       // may return bet for push
            case 17: winnings = Games.gambleCricket(bet, sc); break;
            case 18: winnings = Games.gambleFootball(bet, sc); break;
            case 19: winnings = Games.gambleStock(bet); break;
            case 20: winnings = Games.gambleJackpotLottery(bet); break;
            default:
                System.out.println("❌ Invalid choice.");
                return;
        }

        // Handle pushes (winnings == bet): refund bet, no jackpot, fix stats
        if (winnings == bet) {
            // Refund bet (already deducted)
            p.balance += bet;
            p.addFlow(bet); // money back in
            // Remove this bet from jackpot pool and moneyLost since it's a push
            MultiPlayerBankGame.jackpotPool = Math.max(0, MultiPlayerBankGame.jackpotPool - bet);
            p.moneyLost -= bet;
            System.out.println("↔️  Push! Your bet $" + bet + " was returned. (no jackpot awarded)");
            return;
        }

        if (winnings > 0) {
            // Winner gets winnings + entire jackpot pool
            double payout = winnings + MultiPlayerBankGame.jackpotPool;
            p.balance += payout;
            p.addFlow(payout); // money in
            p.gamblesWon++;
            p.moneyWon += payout;
            System.out.println("🎉 " + p.name + " won $" + payout + "! (includes jackpot pool)");
            MultiPlayerBankGame.jackpotPool = 0; // reset after a win
        } else {
            p.gamblesLost++;
            System.out.println("❌ You lost this gamble. (jackpot grows: $" + MultiPlayerBankGame.jackpotPool + ")");
        }
    }
}