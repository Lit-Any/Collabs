package LifeSim.LifeSimSupport;

import java.util.*;
import utility.ConsoleColors;
import utility.PrintMethods;

public class Person {

    Random RNG = new Random();

        // -------- Life-sim attributes (from Code2, simplified to console) --------
        public String name;
        public int age = 18;
        public int health = 70;       // 0-100
        public static int happiness = 70;    // 0-100
        public static int intelligence = 50; // 0-100
        public static int looks = 50;        // 0-100
        public String education = "None"; // None, HS, College, Masters
        public static String job = "Unemployed";
        public boolean alive = true;
        public boolean nightmareMode = false; // nightmare mode flag
        public int counterToNightmareMode = 0; // counter to track triggers until nightmare mode activates

        // -------- Economic framework fields (from Code1, unified to long Rs.) --------
        public long balance = 1000;     // starting money (Rs.)
        public long loan = 0;
        public int AgeWhenLoanStarts = 0; // track age when loan was taken for interest compounding

        // Gambling leaderboard stats (kept as personal stats; leaderboard UI removed)
        public int gamblesPlayed = 0;
        public int gamblesWon = 0;
        public int gamblesLost = 0;
        public long moneyWon = 0;       // gambling-only winnings credited (includes jackpot when won)
        public long moneyLost = 0;      // gambling-only bets deducted (net of pushes)
        public long lifetimeTotal = 0;  // TOTAL money handled across the whole game (ALL inflows/outflows)

        // Lottery stats
        public long biggestLotteryWin = 0;     // largest single lottery prize ever won
        public long lotteryContribution = 0;   // total money spent on lottery tickets

        //Education attempts
        public int CountOfEducationAttempts = 0; // track attempts at education per player

        public Person(String name) {
            this.name = name;
            // small randomization
            intelligence += RNG.nextInt(21) - 10; // -10..+10
            looks += RNG.nextInt(21) - 10;
            health += RNG.nextInt(11) - 5;
            happiness += RNG.nextInt(11) - 5;
            clampAll();
        }

        public void clampAll() {
            health = clamp(health);
            happiness = clamp(happiness);
            intelligence = clamp(intelligence);
            looks = clamp(looks);
            if (balance < 0) balance = 0;
        }

        public int clamp(int v) { return Math.max(0, Math.min(100, v)); } // clamp to 0-100

        /* ===== Life simulator logic ===== */

        public void passYear(List<String> log) {
            if (!alive) return;
            age++;

            // natural decline/gains
            health -= age / 50; // slow decline
            long revenue = incomePerYear();
            long livingExpenses = (revenue/4 > 5000) ? revenue/4 : 5000; // minimum living expenses of Rs.5000
            long income = 0;
            if (revenue>0) {income = revenue/2 + RNG.nextInt(Integer.valueOf(String.valueOf(revenue/2))) - livingExpenses; } // 50-100% of income
            else {income = 0-livingExpenses;}
            if (livingExpenses > 0) PrintMethods.pln(ConsoleColors.ULTRA_BOLD.ORANGE + "\n🏠 " + name + " paid living expenses of Rs." + livingExpenses + "." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
            balance += income; addFlow(income);
            if (balance<0) {
                loan += Math.abs(balance);
                balance = 0;
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.PINK + "\n🏦 " + name + " went into overdraft! Rs." + loan + ".");
            }
            if (income > 0) {
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.GREEN + "\n💼 " + name + " earned Rs." + revenue + " this year (before tax and living expenses).");
                PrintMethods.pln("💰 After tax and living expenses, Rs." + income + " credited to balance." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                log.add(name + " earned salary Rs." + income + "  at the age of "+ age +" years (Income after taxes and living expenses).");
            }

            // small random stat drift
            intelligence += RNG.nextInt(3) - 1;
            looks += RNG.nextInt(3) - 1;
            happiness += RNG.nextInt(5) - 2;

            // simple random events
            doRandomEvent(log);

            // Compound loan
            if (loan > 0 && age > AgeWhenLoanStarts) {
                long interest = Math.round(loan * 0.10);
                loan += interest;
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.PINK + "\n🏦 Loan interest accrued: Rs." + interest + " (10% of outstanding loan)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
            }

            clampAll();

            // death checks
            if (health <= 0 || (age >= 100 && RNG.nextInt(100) < 50)) {
                alive = false;
                log.add(age + ": " + name + " has died.");
                Helpers.showRecentLog(log);
            }
        }

        public void passNightmareYear(List<String> log) {
            if (!alive) return;

            age++;

            // accelerated decline/gains
            health -= age / 30; // faster decline
            long revenue = incomePerYear();
            long livingExpenses = (revenue/4 > 10000) ? revenue/4 : 10000; // minimum living expenses of Rs. 10000
            long income = 0;
            if (revenue>0) {income = revenue/4 + RNG.nextInt(Integer.valueOf(String.valueOf(revenue/4*2))) - livingExpenses; } // 25-50% of income
            else {income -= livingExpenses;}
            if (livingExpenses > 0) PrintMethods.pln(ConsoleColors.ULTRA_BOLD.ORANGE + "\n🏠 " + name + " paid living expenses of Rs." + livingExpenses + ".");
            balance += income; addFlow(income);
            loan += revenue*RNG.nextInt(25)/100; // random unexpected debt (0-25% of income)
            if (balance<0) {
                loan += Math.abs(balance);
                balance = 0;
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.PINK + "\n🏦 " + name + " went into overdraft! Rs." + loan + ".");
            }
            if (income > 0) {
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.GREEN + "\n💼 " + name + " earned Rs." + revenue + " this year (before tax and living expenses).");
                PrintMethods.pln("💰 After tax and living expenses, Rs." + income + " credited to balance." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                log.add(name + " earned salary Rs." + income + "  at the age of "+ age +" years (Income after taxes and living expenses).");
            }

            // larger random stat drift
            intelligence += RNG.nextInt(5) - 2;
            looks += RNG.nextInt(5) - 2;
            happiness += RNG.nextInt(7) - 3;

            // more frequent random events
            doRandomNightmareEvent(log);
            doRandomNightmareEvent(log);

            // Compound loan
            if (loan > 0 && age > AgeWhenLoanStarts) {
                long interest = Math.round(loan * 0.10);
                loan += interest;
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.PINK + "\n🏦 Loan interest accrued: Rs." + interest + " (+10% of outstanding loan)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
            }

            clampAll();

            // death checks
            if (health <= 0 || (age >= 90 && RNG.nextInt(100) < 70)) {
                alive = false;
                log.add(age + ": " + name + " has died.");
                PrintMethods.pln(ConsoleColors.ERROR + log.get(log.size()-1) + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                Helpers.showRecentLog(log);
            }
        }

        public static long incomePerYear() {
            switch (job) {
                case "Unemployed": return 0;
                case "Retail Worker": return 12000 + intelligence * 10L;
                case "Teacher": return 30000 + intelligence * 50L;
                case "Engineer": return 60000 + intelligence * 100L;
                case "Doctor": return 120000 + intelligence * 200L;
                case "Artist": return 25000 + looks * 30L;
                case "Criminal": return 20000 + (100 - happiness) * 20L;
                default: return 10000;
            }
        }

        public void doRandomEvent(List<String> log) {

            int roll = RNG.nextInt(100);

            if (roll < 8) {
                
                int severity = 5 + RNG.nextInt(5);
                health -= severity;
                happiness -= severity / 2;
                PrintMethods.pln(ConsoleColors.ERROR + "\n⚠️ " + name + " fell ill (-" + severity + " health)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                log.add(age + ": " + name + " fell ill (-" + severity + " health).");

            } else if (roll < 16) {

                long amount = (RNG.nextInt(20) + 5) * 1000L;
                balance += amount; addFlow(amount);
                happiness += 5;
                PrintMethods.pln(ConsoleColors.SUCCESS + "\n🎉 " + name + " received a windfall of Rs." + amount + "! (Happiness +5)" + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                log.add(age + ": " + name + " received windfall Rs." + amount + " (Happiness +5).");

            } else if (roll < 28) {

                if (RNG.nextBoolean()) {

                    happiness += 3;
                    PrintMethods.pln(ConsoleColors.ULTRA_BOLD.CYAN + "\n😊 " + name + " made a great new friend (Happiness +3)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                    log.add(age + ": " + name + " made a great new friend (Happiness +3).");

                } else {

                    happiness -= 4;
                    PrintMethods.pln(ConsoleColors.ULTRA_BOLD.BLUE + "\n😞 " + name + " had a falling out with a friend (Happiness -4)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                    log.add(age + ": " + name + " had a falling out with a friend (Happiness -4).");

                }

            } else if (roll < 34) {
                if (RNG.nextInt(100) < (100 - happiness)) {

                    long loss = 5000;
                    balance = Math.max(0, balance - loss); addFlow(loss);
                    health -= 5;
                    PrintMethods.pln(ConsoleColors.ERROR + "\n🚨 " + name + " got into trouble and lost Rs." + loss + " (-5 health)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                    log.add(age + ": " + name + " got into trouble (-Rs." + loss + ", -5 health).");

                }
            }
        }

        public void doRandomNightmareEvent(List<String> log) {

            int roll = RNG.nextInt(100);

            if (roll < 15) {
                
                int severity = 10 + RNG.nextInt(30);
                health -= severity;
                happiness -= severity / 2;
                PrintMethods.pln(ConsoleColors.ERROR + "\n⚠️ " + name + " suffered a severe illness (-" + severity + " health)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                log.add(age + ": " + name + " suffered a severe illness (-" + severity + " health).");

            } else if (roll < 50) {

                happiness -= 7;
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.BLUE + "\n😞 " + name + " had a falling out with a friend (Happiness -7)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                log.add(age + ": " + name + " had a falling out with a friend (Happiness -7).");

            } else if (roll < 65) {
                if (RNG.nextInt(100) < (100 - happiness)) {

                    long loss = 10000;
                    balance = Math.max(0, balance - loss); addFlow(loss);
                    happiness -= 10;
                    PrintMethods.pln(ConsoleColors.ERROR + "\n🚨 " + name + " got into serious trouble and lost Rs." + loss + " (-10 happiness)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                    log.add(age + ": " + name + " got into serious trouble (-Rs." + loss + ", -10 health).");
                }
            } else if (roll < 75) {

                int severity = 10 + RNG.nextInt(20);
                health -= severity;
                happiness -= severity;
                PrintMethods.pln(ConsoleColors.ERROR + "\n💀 " + name + " was in a major accident (-" + severity + " health, -" + severity + " happiness)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                log.add(age + ": " + name + " was in a major accident (-" + severity + " health, -" + severity + " happiness).");

            } else if (roll < 85) {

                long loss = (RNG.nextInt(50) + 20) * 1000L;
                balance = Math.max(0, balance - loss); addFlow(loss);
                happiness -= 10;
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.ORANGE + "\n💸 " + name + " suffered a major financial loss (-Rs." + loss + ", -10 happiness)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                log.add(age + ": " + name + " suffered a major financial loss (-Rs." + loss + ", -10 happiness).");

            } else {

                long amount = (RNG.nextInt(50) + 10) * 1000L;
                balance += amount; addFlow(amount);
                happiness += 10;
                PrintMethods.pln(ConsoleColors.SUCCESS + "\n🎉 " + name + " received a significant windfall of Rs." + amount + "! (Happiness +10)" + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                log.add(age + ": " + name + " received windfall Rs." + amount + " (Happiness +10).");

            }
        }

        /* ===== Economy methods (from Code1, adapted to long) ===== */

        public void addFlow(long amount) { lifetimeTotal += Math.abs(amount); }

        public void showStatus() {

            PrintMethods.pln("\n👤 " + name + " | Age: " + age +
                               " | Balance: Rs." + balance + " | Loan: Rs." + loan +
                               " | Health: " + health + " | Happiness: " + happiness +
                               " | Int: " + intelligence + " | Job: " + job + " | Looks: " + looks);

        }

        public void deposit(long amount) {

            if (amount <= 0) { PrintMethods.pln("❌ Amount must be positive."); return; }
            balance += amount; addFlow(amount);
            PrintMethods.pln("✅ Deposited Rs." + amount);

        }

        public void withdraw(long amount) {

            if (amount <= 0) { PrintMethods.pln("❌ Amount must be positive."); return; }
            if (amount <= balance) {
                balance -= amount; addFlow(amount);
                PrintMethods.pln("✅ Withdrawn Rs." + amount);
            } else PrintMethods.pln("❌ Not enough balance!");

        }

        public void takeLoan(long amount) {

            if (amount <= 0) { PrintMethods.pln("❌ Amount must be positive."); return; }
            // 10% interest
            long interest = Math.round(amount * 0.10);
            loan += amount + interest;
            balance += amount; addFlow(amount);
            PrintMethods.pln("🏦 Loan granted: Rs." + amount + " (+10% interest added to payable: +" + interest + ")");

        }

        public void repayLoan(long amount) {
            
            if (amount <= 0) { PrintMethods.pln("❌ Amount must be positive."); return; }

            if (amount <= balance && amount <= loan) {

                balance -= amount; loan -= amount; addFlow(amount);
                PrintMethods.pln("✅ Repaid Rs." + amount);

            } else PrintMethods.pln("❌ Repayment not possible!");

        }

        public String brief() {

            return String.format("%s | Age:%d | Balance:Rs.%d | Loan:Rs.%d | H:%d | Happiness:%d | Intelligence:%d | Job:%s | Looks:%d",
                    name, age, balance, loan, health, happiness, intelligence, job, looks);

        }
    }