package LifeSim.LifeSimSupport;

public class Games {

    static java.util.Scanner SC = new java.util.Scanner(System.in);
    static java.util.Random rand = new java.util.Random();

/* ---- Games (adapted to long & console prompts) ---- */

    static int askInt(String prompt) { System.out.print(prompt); return Helpers.readInt(); }
    static String askStr(String prompt) { System.out.print(prompt); return SC.next(); }

    static long gambleDiceGuess(long bet) {
        int guess = askInt("Guess dice roll (1-6): ");
        if (guess < 1 || guess > 6) { System.out.println("❌ Invalid guess."); return 0; }
        int roll = rand.nextInt(6) + 1;
        System.out.println("🎲 Dice rolled: " + roll);
        return (guess == roll) ? bet * 5 : 0;
    }

    static long gambleCoinToss(long bet) {
        int guess = askInt("Guess Heads(1) or Tails(2): ");
        if (guess != 1 && guess != 2) { System.out.println("❌ Invalid choice."); return 0; }
        int toss = rand.nextInt(2) + 1;
        System.out.println("🪙 Toss result: " + (toss == 1 ? "Heads" : "Tails"));
        return (guess == toss) ? bet * 2 : 0;
    }

    static long gambleHighLow(long bet) {
        int number = rand.nextInt(100) + 1;
        String guess = askStr("Guess High (>50) or Low (<=50): ");
        boolean high = number > 50;
        System.out.println("Number: " + number);
        return ((guess.equalsIgnoreCase("High") && high) || (guess.equalsIgnoreCase("Low") && !high)) ? bet * 2 : 0;
    }

    static long gambleLuckyNumber(long bet) {
        int guess = askInt("Pick a number (1-10): ");
        if (guess < 1 || guess > 10) { System.out.println("❌ Invalid number."); return 0; }
        int lucky = rand.nextInt(10) + 1;
        System.out.println("Lucky number: " + lucky);
        return (guess == lucky) ? bet * 10 : 0;
    }

    static long gambleDoubleOrNothing(long bet) { return rand.nextBoolean() ? bet * 2 : 0; }

    static long gambleSpinWheel(long bet) {
        int spin = rand.nextInt(100);
        // 0-9: 10x, 10-29: 3x, 30-99: lose
        return (spin < 10) ? bet * 10 : (spin < 30 ? bet * 3 : 0);
    }

    static long gambleOddEven(long bet) {
        String guess = askStr("Guess Odd or Even: ");
        int num = rand.nextInt(100);
        System.out.println("Number: " + num);
        boolean even = (num % 2 == 0);
        return ((even && guess.equalsIgnoreCase("Even")) || (!even && guess.equalsIgnoreCase("Odd"))) ? bet * 2 : 0;
    }

    static long gamblePickCard(long bet) {
        int card = rand.nextInt(13) + 1; // 1..13
        System.out.println("Card value drawn: " + card);
        return (card == 1 || card == 13) ? bet * 5 : (card >= 11 ? bet * 3 : 0);
    }

    static long gambleMiniLottery(long bet) {
        int draw = rand.nextInt(100);
        System.out.println("Mini-lottery roll: " + draw + " (win on 0)");
        return (draw == 0) ? bet * 50 : 0;
    }

    static long gambleRPS(long bet) {
        int user = askInt("Choose Rock(1), Paper(2), Scissors(3): ");
        if (user < 1 || user > 3) { System.out.println("❌ Invalid move."); return 0; }
        int comp = rand.nextInt(3) + 1;
        System.out.println("Computer chose: " + comp);
        if (user == comp) return bet; // push
        if ((user == 1 && comp == 3) || (user == 2 && comp == 1) || (user == 3 && comp == 2)) return bet * 2;
        return 0;
    }

    static long gambleBlackjack(long bet) {
        int player = rand.nextInt(11) + 10; // 10..20
        int dealer = rand.nextInt(11) + 10; // 10..20
        System.out.println("🂡 Player: " + player + " | Dealer: " + dealer);
        return (player > dealer) ? bet * 2 : (player == dealer ? bet : 0);
    }

    static long gambleRoulette(long bet) {
        String guess = askStr("Bet on Red or Black: ");
        String result = rand.nextBoolean() ? "Red" : "Black";
        System.out.println("🎡 Roulette: " + result);
        return (guess.equalsIgnoreCase(result)) ? bet * 2 : 0;
    }

    static long gambleSlotMachine(long bet) {
        int a = rand.nextInt(5), b = rand.nextInt(5), c = rand.nextInt(5);
        System.out.println("🎰 " + a + " | " + b + " | " + c);
        return (a == b && b == c) ? bet * 10 : (a == b || b == c || a == c ? bet * 3 : 0);
    }

    static long gambleHorseRace(long bet) {
        int guess = askInt("Pick horse (1-5): ");
        if (guess < 1 || guess > 5) { System.out.println("❌ Invalid horse."); return 0; }
        int winner = rand.nextInt(5) + 1;
        System.out.println("🏇 Winner: Horse " + winner);
        return (guess == winner) ? bet * 4 : 0;
    }

    static long gamblePoker(long bet) {
        int hand = rand.nextInt(100);
        System.out.println("🃏 Hand strength roll: " + hand);
        return (hand < 5) ? bet * 20 : (hand < 20 ? bet * 5 : 0);
    }

    static long gambleHighCardWar(long bet) {
        int player = rand.nextInt(13) + 1;
        int dealer = rand.nextInt(13) + 1;
        System.out.println("War — You: " + player + " vs Dealer: " + dealer);
        return (player > dealer) ? bet * 2 : (player == dealer ? bet : 0);
    }

    static long gambleCricket(long bet) {
        int guess = askInt("Guess runs (1-6): ");
        if (guess < 1 || guess > 6) { System.out.println("❌ Invalid runs."); return 0; }
        int run = rand.nextInt(6) + 1;
        System.out.println("🏏 Batsman scored: " + run);
        return (guess == run) ? bet * 6 : 0;
    }

    static long gambleFootball(long bet) {
        int guess = askInt("Choose Left(1), Center(2), Right(3): ");
        if (guess < 1 || guess > 3) { System.out.println("❌ Invalid direction."); return 0; }
        int goalie = rand.nextInt(3) + 1;
        System.out.println("⚽ Goalie defended: " + goalie);
        return (guess != goalie) ? bet * 3 : 0;
    }

    static long gambleStock(long bet) {
        boolean up = rand.nextBoolean();
        double mult = up ? (1.2 + rand.nextDouble() * 0.8) : (0.5 + rand.nextDouble() * 0.5); // up:1.2..2.0, down:0.5..1.0
        long payout = Math.round(bet * mult);
        System.out.println("📈 Stock " + (up ? "UP" : "DOWN") + " → multiplier ~" + String.format("%.2f", mult));
        return up ? payout : 0; // down → lose bet
    }

    static long gambleJackpotLottery(long bet) {
        int draw = rand.nextInt(1000);
        System.out.println("🎟 Jackpot draw: " + draw + " (win on 0)");
        return (draw == 0) ? bet * 500 : 0;
    }
}