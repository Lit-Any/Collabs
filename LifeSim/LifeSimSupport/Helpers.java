package LifeSim.LifeSimSupport;

import java.util.List;
import utility.*;

public class Helpers {

    static java.util.Scanner SC = new java.util.Scanner(System.in);

    public static void spend(Person p, long amt, List<String> log, String what) {

        if (p.balance >= amt) {

            if (p.loan > 0) {
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.PINK + "\nTransaction blocked - cannot spend while having a loan. Repay first." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                return;
            } else {
                p.balance -= amt;
                log.add(p.name + " spent Rs." + amt + " on " + what);
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.PINK + "\n" + p.name + " spent Rs." + amt + " on " + what + "." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
            }

        } else {

            if (p.loan > 0) {
                PrintMethods.pln(ConsoleColors.ULTRA_BOLD.PINK + "\nTransaction blocked - cannot spend while having a loan. Repay first." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
                return;
            }

            long deficit = amt - p.balance;
            p.balance = 0;
            p.loan += deficit;
            log.add(p.name + " took a loan of Rs." + deficit + " for " + what);
            PrintMethods.pln(ConsoleColors.ULTRA_BOLD.PINK + "\n" + p.name + " took a loan of Rs." + deficit + " for " + what + "."+ ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
            p.AgeWhenLoanStarts = p.age;

        }
    }

    public static int eduRank(String e) {
        if (e.equals("None")) return 0;
        if (e.equals("HS")) return 1;
        if (e.equals("College")) return 2;
        if (e.equals("Masters")) return 3;
        return 0;
    }
    
    public static void failJob(Person p, String job, List<String> log) {
        p.happiness -= 5;
        log.add(p.name+" failed job application: " + job);

        if (p.nightmareMode) {
            p.passNightmareYear(log);
        } else {
            p.passYear(log);
        }

        PrintMethods.pln(ConsoleColors.ULTRA_BOLD.YELLOW + "\n" + p.name+" failed job application: " + job + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
    }

    public static void showRecentLog(List<String> log) {
        PrintMethods.pln("\n----- Recent Log -----");
        int start = Math.max(0, log.size() - 30);
        for (int i=start; i<log.size(); i++) PrintMethods.pln("• " + log.get(i));
        if (log.isEmpty()) PrintMethods.pln("(empty)");
        PrintMethods.pln("----------------------");
    }

    public static int readInt() {
        while (true) {
            try { return Integer.parseInt(SC.next()); }
            catch (Exception e) { PrintMethods.p("Enter a number: "); }
        }
    }

    public static long readLong() {
        while (true) {
            try { return Long.parseLong(SC.next()); }
            catch (Exception e) { PrintMethods.p("Enter a whole amount: "); }
        }
    }

    public static void CompoundLoan(Person p){
        // Compound loan
        if (p.loan > 0 && p.age > p.AgeWhenLoanStarts) {
            long interest = Math.round(p.loan * 0.10);
            p.loan += interest;
            PrintMethods.pln(ConsoleColors.ULTRA_BOLD.PINK + "\n🏦 Loan interest accrued: Rs." + interest + " (10% of outstanding loan)." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
        }
    }

    public static boolean ForceEmploymentIfInDebt(Person p) {
        if (p.loan > 0 && p.job == "Unemployed") {
            PrintMethods.pln(ConsoleColors.ULTRA_BOLD.PINK + "\n🏦 You have an outstanding loan and must find a job to repay it." + ConsoleColors.RESET + ConsoleColors.REG.WHITE + ConsoleColors.ULTRA_BG.BLACK);
            return true;
        } else {
            return false;
        }
    }
}