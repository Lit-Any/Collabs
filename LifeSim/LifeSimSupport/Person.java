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
        public static int stress = jobStress(job);       // 0-100, affected by job, events, causes health/happiness decline
        public static String Accomodation = "Shack"; // Shack, Apartment, House, Mansion
        public static String Vehicle = "None"; // None, Bicycle, Motorbike, Car, Luxury Car
        public static int comfort = comfort();     // 0-100, affected by accomodations and vehicle, offsets stress
        public boolean alive = true;
        public boolean nightmareMode = false; // nightmare mode flag
        public int counterToNightmareMode = 0; // counter to track triggers until nightmare mode activates

        // Vars to track changes in attributes
        public int InitialHealth = health;
        public int InitialHappiness = happiness;
        public int InitialIntelligence = intelligence;
        public int InitialLooks = looks;
        public int InitialStress = stress;
        public int InitialComfort = comfort;
        public int InitialBalance = 0;
        public int InitialLoan = 0;

        public int healthChange = 0;
        public int happinessChange = 0;
        public int intelligenceChange = 0;
        public int looksChange = 0;
        public int stressChange = 0;
        public int comfortChange = 0;
        public int balanceChange = 0;
        public int loanChange = 0;
        public static List<String> statLog = new ArrayList<>(); // log to track yearly stats

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
            stress = clamp(stress);
            comfort = clamp(comfort);
            if (balance < 0) balance = 0;
        }

        public int clamp(int v) { return Math.max(0, Math.min(100, v)); } // clamp to 0-100

        /* ===== Life simulator logic ===== */

        public void passYear(List<String> log) {
            if (!alive) return;
            age++;

            InitialHealth = health;
            InitialHappiness = happiness;
            InitialIntelligence = intelligence;
            InitialLooks = looks;
            InitialStress = stress;
            InitialComfort = comfort;
            InitialBalance = (int) balance;
            InitialLoan = (int) loan;
            comfort = comfort(); // auto-adjust comfort based on current accomodations/vehicle

            // natural decline/gains
            health -= age / 50 + (stress-comfort/20); // slow decline
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

            // Track changes in attributes
            healthChange = health - InitialHealth;
            happinessChange = happiness - InitialHappiness;
            intelligenceChange = intelligence - InitialIntelligence;
            looksChange = looks - InitialLooks;
            stressChange = stress - InitialStress;
            comfortChange = comfort - InitialComfort;
            balanceChange = (int) balance - InitialBalance;
            loanChange = (int) loan - InitialLoan;

            // death checks
            if (health <= 0 || (age >= 100 && RNG.nextInt(100) < 50)) {
                alive = false;
                log.add(age + ": " + name + " has died.");
                PrintMethods.pln(ConsoleColors.ERROR + "\n" + log.get(log.size()-1) + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                Helpers.showRecentLog(log);
            }
        }

        public void passNightmareYear(List<String> log) {
            if (!alive) return;

            age++;

            InitialHealth = health;
            InitialHappiness = happiness;
            InitialIntelligence = intelligence;
            InitialLooks = looks;
            InitialStress = stress;
            InitialComfort = comfort;
            InitialBalance = (int) balance;
            InitialLoan = (int) loan;
            comfort = comfort(); // auto-adjust comfort based on current accomodations/vehicle

            // accelerated decline/gains
            health -= age / 30 + (stress-comfort/10); // faster decline
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

            // Track changes in attributes
            healthChange = health - InitialHealth;
            happinessChange = happiness - InitialHappiness;
            intelligenceChange = intelligence - InitialIntelligence;
            looksChange = looks - InitialLooks;
            stressChange = stress - InitialStress;
            comfortChange = comfort - InitialComfort;
            balanceChange = (int) balance - InitialBalance;
            loanChange = (int) loan - InitialLoan;

            // death checks
            if (health <= 0 || (age >= 90 && RNG.nextInt(100) < 70)) {
                alive = false;
                log.add(age + ": " + name + " has died.");
                PrintMethods.pln(ConsoleColors.ERROR + "\n" + log.get(log.size()-1) + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
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

        public static int jobStress(String job) {
            switch (job) {
                case "Unemployed": return 5;
                case "Retail Worker": return 30;
                case "Teacher": return 40;
                case "Engineer": return 50;
                case "Doctor": return 70;
                case "Artist": return 20;
                case "Criminal": return 60;
                default: return 30;
            }
        }

        public static int comfort() {
            int comfort = 50; // base comfort
            // Accomodation effect
            if (Accomodation.equals("Shack")) comfort -= 20;
            else if (Accomodation.equals("Apartment")) comfort += 0;
            else if (Accomodation.equals("House")) comfort += 10;
            else if (Accomodation.equals("Mansion")) comfort += 20;
            // Vehicle effect
            if (Vehicle.equals("None")) comfort -= 10;
            else if (Vehicle.equals("Bicycle")) comfort += 0;
            else if (Vehicle.equals("Motorbike")) comfort += 5;
            else if (Vehicle.equals("Car")) comfort += 10;
            else if (Vehicle.equals("Luxury Car")) comfort += 15;
            return Math.max(0, Math.min(100, comfort));
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
                    PrintMethods.pln(ConsoleColors.WARNING + "\n😞 " + name + " had a falling out with a friend (Happiness -4)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
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
                PrintMethods.pln(ConsoleColors.WARNING + "\n😞 " + name + " had a falling out with a friend (Happiness -7)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
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

            PrintMethods.pln(ConsoleColors.INFO + "\n👤 " + name + " | Age: " + age +
                               " | Balance: Rs." + balance + "(" + balanceChange + ") | Loan: Rs." + loan + "(" + loanChange + ")" +
                               " | Health: " + health + "(" + healthChange + ") | Happiness: " + happiness + "(" + happinessChange + ")" +
                               " | Int: " + intelligence + "(" + intelligenceChange + ") | Job: " + job + " | Looks: " + looks + "(" + looksChange + ")" +
                               " | Stress: " + stress + "(" + intelligenceChange + ") | Comfort: " + comfort + "(" + comfortChange + ")" +
                               " | Accomodation: " + Accomodation + " | Vehicle: " + Vehicle + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);

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