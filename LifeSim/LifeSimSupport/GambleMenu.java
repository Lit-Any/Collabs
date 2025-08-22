package LifeSim.LifeSimSupport;

import LifeSim.LifeSimulator;

public class GambleMenu {

static java.util.Scanner SC = new java.util.Scanner(System.in);
static java.util.Random rand = new java.util.Random();

/* ---- Gambling Menu (all 20 games preserved, adapted to long) ---- */
    static void gambleMenu(Person p) {
        System.out.println("\n--- 🎰 Gambling Games Menu ---");
        System.out.println("1. Dice Guess\n2. Coin Toss\n3. High or Low\n4. Lucky Number\n5. Double or Nothing");
        System.out.println("6. Spin Wheel\n7. Odd or Even\n8. Pick a Card\n9. Mini Lottery\n10. Rock Paper Scissors");
        System.out.println("11. Blackjack Lite\n12. Roulette\n13. Slot Machine\n14. Horse Race\n15. Poker Draw");
        System.out.println("16. High Card War\n17. Cricket Bet\n18. Football Penalty\n19. Stock Bet\n20. Jackpot Lottery");
        System.out.print("Choose a game (1-20): ");
        int choice = Helpers.readInt();

        System.out.print("Enter your bet amount (Rs.): ");
        long bet = Helpers.readLong();

        if (bet <= 0) { System.out.println("❌ Bet must be positive."); return; }
        if (bet > p.balance) { System.out.println("❌ Not enough balance!"); return; }

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
                System.out.println("❌ Invalid choice.");
                return;
        }

        // Push handling (winnings == bet): refund bet, no jackpot, fix stats
        if (winnings == bet) {
            p.balance += bet; p.addFlow(bet);
            LifeSimulator.jackpotPool = Math.max(0, LifeSimulator.jackpotPool - bet);
            p.moneyLost -= bet;
            System.out.println("↔️  Push! Bet Rs." + bet + " returned. (no jackpot awarded)");
            return;
        }

        if (winnings > 0) {
            long payout = winnings + LifeSimulator.jackpotPool;
            p.balance += payout; p.addFlow(payout);
            p.gamblesWon++; p.moneyWon += payout;
            System.out.println("🎉 " + p.name + " won Rs." + payout + "! (includes jackpot pool)");
            LifeSimulator.jackpotPool = 0;
        } else {
            p.gamblesLost++;
            System.out.println("❌ You lost this gamble. (jackpot grows: Rs." + LifeSimulator.jackpotPool + ")");
        }
    }

}
