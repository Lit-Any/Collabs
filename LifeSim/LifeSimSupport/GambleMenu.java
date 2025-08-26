package LifeSim.LifeSimSupport;

import LifeSim.LifeSimulator;
import utility.*;;

public class GambleMenu {

static java.util.Scanner SC = new java.util.Scanner(System.in);
static java.util.Random rand = new java.util.Random();

/* ---- Gambling Menu (all 20 games preserved, adapted to long) ---- */
    static void gambleMenu(Person p) {
        PrintMethods.pln("\n--- 🎰 Gambling Games Menu ---");
        PrintMethods.pln("1. Dice Guess\n2. Coin Toss\n3. High or Low\n4. Lucky Number\n5. Double or Nothing");
        PrintMethods.pln("6. Spin Wheel\n7. Odd or Even\n8. Pick a Card\n9. Mini Lottery\n10. Rock Paper Scissors");
        PrintMethods.pln("11. Blackjack Lite\n12. Roulette\n13. Slot Machine\n14. Horse Race\n15. Poker Draw");
        PrintMethods.pln("16. High Card War\n17. Cricket Bet\n18. Football Penalty\n19. Stock Bet\n20. Jackpot Lottery");
        PrintMethods.p("Choose a game (1-20): ");
        int choice = Helpers.readInt();

        PrintMethods.p("Enter your bet amount (Rs.): ");
        long bet = Helpers.readLong();

        if (bet <= 0) { PrintMethods.pln("❌ Bet must be positive."); return; }
        if (bet > p.balance) { PrintMethods.pln("❌ Not enough balance!"); return; }

        // Take the bet upfront (at-risk model)
        p.balance -= bet; p.addFlow(bet); p.gamblesPlayed++; p.moneyLost += bet; LifeSimulator.jackpotPool += bet;

        long winnings = 0;
        switch (choice) {
            case 1:  winnings = Games.gambleDiceGuess(bet); break;
            case 2:  winnings = Games.gambleCoinToss(bet); break;
            case 3:  winnings = Games.gambleHighLow(bet); break;
            case 4:  winnings = Games.gambleLuckyNumber(bet); break;
            case 5:  winnings = Games.gambleDoubleOrNothing(bet); break;
            case 6:  winnings = Games.gambleSpinWheel(bet); break;
            case 7:  winnings = Games.gambleOddEven(bet); break;
            case 8:  winnings = Games.gamblePickCard(bet); break;
            case 9:  winnings = Games.gambleMiniLottery(bet); break;
            case 10: winnings = Games.gambleRPS(bet); break;
            case 11: winnings = Games.gambleBlackjack(bet); break;
            case 12: winnings = Games.gambleRoulette(bet); break;
            case 13: winnings = Games.gambleSlotMachine(bet); break;
            case 14: winnings = Games.gambleHorseRace(bet); break;
            case 15: winnings = Games.gamblePoker(bet); break;
            case 16: winnings = Games.gambleHighCardWar(bet); break;
            case 17: winnings = Games.gambleCricket(bet); break;
            case 18: winnings = Games.gambleFootball(bet); break;
            case 19: winnings = Games.gambleStock(bet); break;
            case 20: winnings = Games.gambleJackpotLottery(bet); break;
            default:
                PrintMethods.pln("❌ Invalid choice.");
                return;
        }

        // Push handling (winnings == bet): refund bet, no jackpot, fix stats
        if (winnings == bet) {
            p.balance += bet; p.addFlow(bet);
            LifeSimulator.jackpotPool = Math.max(0, LifeSimulator.jackpotPool - bet);
            p.moneyLost -= bet;
            PrintMethods.pln("↔️  Push! Bet Rs." + bet + " returned. (no jackpot awarded)");
            return;
        }

        if (winnings > 0) {
            long payout = winnings + LifeSimulator.jackpotPool;
            p.balance += payout; p.addFlow(payout);
            p.gamblesWon++; p.moneyWon += payout;
            PrintMethods.pln("🎉 " + p.name + " won Rs." + payout + "! (includes jackpot pool)");
            LifeSimulator.jackpotPool = 0;
        } else {
            p.gamblesLost++;
            PrintMethods.pln("❌ You lost this gamble. (jackpot grows: Rs." + LifeSimulator.jackpotPool + ")");
        }
    }

}
