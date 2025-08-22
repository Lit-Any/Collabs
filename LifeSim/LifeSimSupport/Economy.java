package LifeSim.LifeSimSupport;

import java.util.*;

/* ===============================
       ========== ECONOMY ============
       =============================== */

    public class Economy {
        static Random rand = new Random();
        static java.util.Scanner SC = new java.util.Scanner(System.in);

        // Pools (unified to long)
        static long jackpotPool = 0L;       // gambling: all lost bets accumulate here until next win
        static long lotteryBonusPool = 0L;  // lottery: 10% tax accumulates across rounds

        /* ---- Unified Economic Menu ---- */
        public static void economicMenu(Person p, List<Person> allPlayers) {
            boolean back = false;
            while (!back) {
                System.out.println("\n--- 💼 Economic Actions for " + p.name + " ---");
                System.out.println("Balance: Rs." + p.balance + " | Loan: Rs." + p.loan + " | Jackpot: Rs." + jackpotPool + " | Lottery Bonus: Rs." + lotteryBonusPool);
                System.out.println(
                    "1) Deposit\n" +
                    "2) Withdraw\n" +
                    "3) Take Loan\n" +
                    "4) Repay Loan\n" +
                    "5) Gamble (20 games)\n" +
                    "6) Lottery\n" +
                    "7) Start Card Games (Rummy)\n" +
                    "8) Back"
                );
                System.out.print("Choose: ");
                int c = Helpers.readInt();
                switch (c) {
                    case 1: System.out.print("Amount to deposit: "); p.deposit(Helpers.readLong()); break;
                    case 2: System.out.print("Amount to withdraw: "); p.withdraw(Helpers.readLong()); break;
                    case 3: System.out.print("Loan amount: "); p.takeLoan(Helpers.readLong()); break;
                    case 4: System.out.print("Repay amount: "); p.repayLoan(Helpers.readLong()); break;
                    case 5: gambleMenu(p); break;
                    case 6: lotterySystem(allPlayers); break;
                    case 7: startCardPoll(allPlayers); break;
                    case 8: back = true; break;
                    default: System.out.println("❌ Invalid choice.");
                }
            }
        }

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
            p.balance -= bet; p.addFlow(bet); p.gamblesPlayed++; p.moneyLost += bet; jackpotPool += bet;

            long winnings = 0;
            switch (choice) {
                case 1:  winnings = gambleDiceGuess(bet); break;
                case 2:  winnings = gambleCoinToss(bet); break;
                case 3:  winnings = gambleHighLow(bet); break;
                case 4:  winnings = gambleLuckyNumber(bet); break;
                case 5:  winnings = gambleDoubleOrNothing(bet); break;
                case 6:  winnings = gambleSpinWheel(bet); break;
                case 7:  winnings = gambleOddEven(bet); break;
                case 8:  winnings = gamblePickCard(bet); break;
                case 9:  winnings = gambleMiniLottery(bet); break;
                case 10: winnings = gambleRPS(bet); break;
                case 11: winnings = gambleBlackjack(bet); break;
                case 12: winnings = gambleRoulette(bet); break;
                case 13: winnings = gambleSlotMachine(bet); break;
                case 14: winnings = gambleHorseRace(bet); break;
                case 15: winnings = gamblePoker(bet); break;
                case 16: winnings = gambleHighCardWar(bet); break;
                case 17: winnings = gambleCricket(bet); break;
                case 18: winnings = gambleFootball(bet); break;
                case 19: winnings = gambleStock(bet); break;
                case 20: winnings = gambleJackpotLottery(bet); break;
                default:
                    System.out.println("❌ Invalid choice.");
                    return;
            }

            // Push handling (winnings == bet): refund bet, no jackpot, fix stats
            if (winnings == bet) {
                p.balance += bet; p.addFlow(bet);
                jackpotPool = Math.max(0, jackpotPool - bet);
                p.moneyLost -= bet;
                System.out.println("↔️  Push! Bet Rs." + bet + " returned. (no jackpot awarded)");
                return;
            }

            if (winnings > 0) {
                long payout = winnings + jackpotPool;
                p.balance += payout; p.addFlow(payout);
                p.gamblesWon++; p.moneyWon += payout;
                System.out.println("🎉 " + p.name + " won Rs." + payout + "! (includes jackpot pool)");
                jackpotPool = 0;
            } else {
                p.gamblesLost++;
                System.out.println("❌ You lost this gamble. (jackpot grows: Rs." + jackpotPool + ")");
            }
        }

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

        /* ---- Lottery (with accumulating bonus pool) ---- */
        static void lotterySystem(List<Person> players) {
            System.out.println("\n🎰 Lottery System — Each ticket costs Rs.100");
            System.out.println("💡 Winner takes 90% of the pot + accumulated bonus pool!");
            System.out.println("🎁 Current Bonus Pool: Rs." + lotteryBonusPool);

            long ticketPrice = 100L;
            long totalPot = 0L;

            ArrayList<Person> ticketPool = new ArrayList<>();

            for (Person p : players) {
                System.out.println("\n" + p.name + ", your balance: Rs." + p.balance);
                System.out.print("Enter number of tickets to buy (0 to skip): ");
                int numTickets = Helpers.readInt();
                if (numTickets < 0) { System.out.println("❌ Invalid number."); continue; }
                long cost = numTickets * ticketPrice;
                if (numTickets > 0 && cost <= p.balance) {
                    p.balance -= cost; p.addFlow(cost);
                    totalPot += cost;
                    p.lotteryContribution += cost;
                    for (int t=0;t<numTickets;t++) ticketPool.add(p);
                    System.out.println("✅ " + p.name + " bought " + numTickets + " tickets for Rs." + cost);
                } else if (numTickets > 0) {
                    System.out.println("❌ Not enough balance! Skipped.");
                } else {
                    System.out.println("⏭ " + p.name + " skipped the lottery.");
                }
            }

            if (ticketPool.isEmpty()) { System.out.println("\n⚠️ No tickets bought. Lottery cancelled."); return; }

            // Draw winner
            Person winner = ticketPool.get(rand.nextInt(ticketPool.size()));
            long tax = Math.round(totalPot * 0.10);
            long prize = Math.round(totalPot * 0.90) + lotteryBonusPool;

            // Payout winner
            winner.balance += prize; winner.addFlow(prize);
            if (prize > winner.biggestLotteryWin) winner.biggestLotteryWin = prize;

            // Grow the progressive pool by adding this round's tax
            lotteryBonusPool += tax;

            System.out.println("\n🏆 Lottery Winner: " + winner.name + "!");
            System.out.println("💰 Prize paid: Rs." + prize + " (includes previous bonus pool)");
            System.out.println("📉 Tax collected this round: Rs." + tax + " → added to bonus pool.");
            System.out.println("🎁 New Bonus Pool (for next round): Rs." + lotteryBonusPool);
        }

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

    /* ===============================
       ========== GAME LOOP ==========
       =============================== */

    public static void main(String[] args) {
        System.out.println("🏦 Welcome to the Unified Life & Economy Simulator!");
        System.out.print("Enter number of players: ");
        int n = Helpers.readInt();
        SC.nextLine(); // consume endline

        List<Person> players = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter player " + (i+1) + " name: ");
            String name = SC.nextLine().trim();
            if (name.isEmpty()) name = "Player_" + (i+1);
            players.add(new Person(name));
        }

        List<String> log = new ArrayList<>();
        boolean running = true;

        while (running) {
            for (Person p : players) {
                if (!p.alive) { System.out.println("\n--- " + p.name + " is deceased. Skipping turn. ---"); continue; }

                System.out.println("\n==============================");
                System.out.println("Turn — " + p.name);
                p.showStatus();
                System.out.println("------------------------------");
                System.out.println(
                    "1) Live year (auto)\n" +
                    "2) Study\n" +
                    "3) Work\n" +
                    "4) Improve stats\n" +
                    "5) Risky (life-side)\n" +
                    "6) Economic Actions (bank, gamble, lottery, rummy)\n" +
                    "7) Show recent log\n" +
                    "8) Exit game"
                );
                System.out.print("Choose: ");
                int choice = Helpers.readInt();

                switch (choice) {
                    case 1: p.passYear(log); break;
                    case 2: LifeActions.doStudyFor(p, log); break;
                    case 3: LifeActions.doWorkFor(p, log); break;
                    case 4: LifeActions.doImproveFor(p, log); break;
                    case 5: LifeActions.doRiskyFor(p, log); break;
                    case 6: Economy.economicMenu(p, players); break;
                    case 7: Helpers.showRecentLog(log); break;
                    case 8:
                        running = false;
                        System.out.println("👋 Game ended by " + p.name);
                        break;
                    default: System.out.println("❌ Invalid choice!");
                }

                if (!running) break;
            }
        }

        // Final Results
        System.out.println("\n📊 Final Results:");
        for (Person p : players) {
            p.showStatus();
            System.out.println("   Gambles: played " + p.gamblesPlayed + ", won " + p.gamblesWon + ", lost " + p.gamblesLost);
            System.out.println("   Money Won: Rs." + p.moneyWon + " | Money Lost: Rs." + p.moneyLost + " | Lifetime Flows: Rs." + p.lifetimeTotal);
            System.out.println("   Biggest Lottery Win: Rs." + p.biggestLotteryWin + " | Lottery Contribution: Rs." + p.lotteryContribution);
            System.out.println("-------------------------------");
        }
    }
}