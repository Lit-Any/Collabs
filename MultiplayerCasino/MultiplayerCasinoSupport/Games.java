package MultiplayerCasino.MultiplayerCasinoSupport;

import java.util.*;
import utility.*;

public class Games {

    private static final Random rand = new Random();
    
    // 🔹 Implemented Gambling Games (20)
    public static double gambleDiceGuess(double bet, Scanner sc) {
        PrintMethods.p("Guess dice roll (1-6): ");
        int guess = sc.nextInt();
        if (guess < 1 || guess > 6) {
            PrintMethods.pln("❌ Invalid guess.");
            return 0;
        }
        int roll = rand.nextInt(6) + 1;
        PrintMethods.pln("🎲 Dice rolled: " + roll);
        return (guess == roll) ? bet * 5 : 0;
    }

    public static double gambleCoinToss(double bet, Scanner sc) {
        PrintMethods.p("Guess Heads(1) or Tails(2): ");
        int guess = sc.nextInt();
        if (guess != 1 && guess != 2) {
            PrintMethods.pln("❌ Invalid choice.");
            return 0;
        }
        int toss = rand.nextInt(2) + 1;
        PrintMethods.pln("🪙 Toss result: " + (toss == 1 ? "Heads" : "Tails"));
        return (guess == toss) ? bet * 2 : 0;
    }

    public static double gambleHighLow(double bet, Scanner sc) {
        int number = rand.nextInt(100) + 1;
        PrintMethods.p("Guess if number (1-100) is High (>50) or Low (<=50): ");
        String guess = sc.next();
        boolean high = number > 50;
        PrintMethods.pln("Number: " + number);
        return ((guess.equalsIgnoreCase("High") && high) || (guess.equalsIgnoreCase("Low") && !high)) ? bet * 2 : 0;
    }

    public static double gambleLuckyNumber(double bet, Scanner sc) {
        PrintMethods.p("Pick a number (1-10): ");
        int guess = sc.nextInt();
        if (guess < 1 || guess > 10) {
            PrintMethods.pln("❌ Invalid number.");
            return 0;
        }
        int lucky = rand.nextInt(10) + 1;
        PrintMethods.pln("Lucky number: " + lucky);
        return (guess == lucky) ? bet * 10 : 0;
    }

    public static double gambleDoubleOrNothing(double bet, Scanner sc) {
        return (rand.nextBoolean()) ? bet * 2 : 0;
    }

    public static double gambleSpinWheel(double bet, Scanner sc) {
        int spin = rand.nextInt(100);
        // 0-9: 10x, 10-29: 3x, 30-99: lose
        return (spin < 10) ? bet * 10 : (spin < 30 ? bet * 3 : 0);
    }

    public static double gambleOddEven(double bet, Scanner sc) {
        PrintMethods.p("Guess Odd or Even: ");
        String guess = sc.next();
        int num = rand.nextInt(100);
        PrintMethods.pln("Number: " + num);
        return ((num % 2 == 0 && guess.equalsIgnoreCase("Even")) || (num % 2 == 1 && guess.equalsIgnoreCase("Odd"))) ? bet * 2 : 0;
    }

    public static double gamblePickCard(double bet, Scanner sc) {
        int card = rand.nextInt(13) + 1; // 1..13
        PrintMethods.pln("Card value drawn: " + card);
        return (card == 1 || card == 13) ? bet * 5 : (card >= 11 ? bet * 3 : 0);
    }

    public static double gambleMiniLottery(double bet) {
        int draw = rand.nextInt(100);
        PrintMethods.pln("Mini-lottery roll: " + draw + " (win on 0)");
        return (draw == 0) ? bet * 50 : 0;
    }

    public static double gambleRPS(double bet, Scanner sc) {
        PrintMethods.p("Choose Rock(1), Paper(2), Scissors(3): ");
        int user = sc.nextInt();
        if (user < 1 || user > 3) {
            PrintMethods.pln("❌ Invalid move.");
            return 0;
        }
        int comp = rand.nextInt(3) + 1;
        PrintMethods.pln("Computer chose: " + comp);
        if (user == comp) return bet; // push
        if ((user == 1 && comp == 3) || (user == 2 && comp == 1) || (user == 3 && comp == 2)) return bet * 2;
        return 0;
    }

    public static double gambleBlackjack(double bet) {
        int player = rand.nextInt(11) + 10; // 10..20
        int dealer = rand.nextInt(11) + 10; // 10..20
        PrintMethods.pln("🂡 Player: " + player + " | Dealer: " + dealer);
        return (player > dealer) ? bet * 2 : (player == dealer ? bet : 0); // push returns bet
    }

    public static double gambleRoulette(double bet, Scanner sc) {
        PrintMethods.p("Bet on Red or Black: ");
        String guess = sc.next();
        String result = rand.nextBoolean() ? "Red" : "Black";
        PrintMethods.pln("🎡 Roulette: " + result);
        return (guess.equalsIgnoreCase(result)) ? bet * 2 : 0;
    }

    public static double gambleSlotMachine(double bet) {
        int a = rand.nextInt(5), b = rand.nextInt(5), c = rand.nextInt(5);
        PrintMethods.pln("🎰 " + a + " | " + b + " | " + c);
        return (a == b && b == c) ? bet * 10 : (a == b || b == c || a == c ? bet * 3 : 0);
    }

    public static double gambleHorseRace(double bet, Scanner sc) {
        PrintMethods.p("Pick horse (1-5): ");
        int guess = sc.nextInt();
        if (guess < 1 || guess > 5) {
            PrintMethods.pln("❌ Invalid horse.");
            return 0;
        }
        int winner = rand.nextInt(5) + 1;
        PrintMethods.pln("🏇 Winner: Horse " + winner);
        return (guess == winner) ? bet * 4 : 0;
    }

    public static double gamblePoker(double bet) {
        int hand = rand.nextInt(100);
        PrintMethods.pln("🃏 Hand strength roll: " + hand);
        return (hand < 5) ? bet * 20 : (hand < 20 ? bet * 5 : 0);
    }

    public static double gambleHighCardWar(double bet) {
        int player = rand.nextInt(13) + 1;
        int dealer = rand.nextInt(13) + 1;
        PrintMethods.pln("War — You: " + player + " vs Dealer: " + dealer);
        return (player > dealer) ? bet * 2 : (player == dealer ? bet : 0); // push returns bet
    }

    public static double gambleCricket(double bet, Scanner sc) {
        PrintMethods.p("Guess runs (1-6): ");
        int guess = sc.nextInt();
        if (guess < 1 || guess > 6) {
            PrintMethods.pln("❌ Invalid runs.");
            return 0;
        }
        int run = rand.nextInt(6) + 1;
        PrintMethods.pln("🏏 Batsman scored: " + run);
        return (guess == run) ? bet * 6 : 0;
    }

    public static double gambleFootball(double bet, Scanner sc) {
        PrintMethods.p("Choose Left(1), Center(2), Right(3): ");
        int guess = sc.nextInt();
        if (guess < 1 || guess > 3) {
            PrintMethods.pln("❌ Invalid direction.");
            return 0;
        }
        int goalie = rand.nextInt(3) + 1;
        PrintMethods.pln("⚽ Goalie defended: " + goalie);
        return (guess != goalie) ? bet * 3 : 0;
    }

    public static double gambleStock(double bet) {
        boolean up = rand.nextBoolean();
        double mult = up ? (1.2 + rand.nextDouble() * 0.8) : (0.5 + rand.nextDouble() * 0.5); // up:1.2..2.0, down:0.5..1.0
        double payout = Math.round(bet * mult);
        PrintMethods.pln("📈 Stock " + (up ? "UP" : "DOWN") + " → multiplier ~" + String.format("%.2f", mult));
        return up ? payout : 0; // down → lose bet
    }

    public static double gambleJackpotLottery(double bet) {
        int draw = rand.nextInt(1000);
        PrintMethods.pln("🎟 Jackpot draw: " + draw + " (win on 0)");
        return (draw == 0) ? bet * 500 : 0;
    }
}
