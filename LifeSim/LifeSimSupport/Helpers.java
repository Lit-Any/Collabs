package LifeSim.LifeSimSupport;

import java.util.List;

import utility.PrintMethods;

public class Helpers {

    static java.util.Scanner SC = new java.util.Scanner(System.in);

    public static void spend(Person p, long amt, List<String> log, String what) {

        if (p.balance >= amt) {

            if (p.loan > 0) {
                System.out.println("Transaction blocked - cannot spend while having a loan. Repay first.");
                return;
            } else {
                p.balance -= amt;
                log.add(p.name + " spent Rs." + amt + " on " + what);
                PrintMethods.pln(p.name + " spent Rs." + amt + " on " + what);
            }

        } else {

            if (p.loan > 0) {
                System.out.println("Transaction blocked - cannot spend while having a loan. Repay first.");
                return;
            }

            long deficit = amt - p.balance;
            p.balance = 0;
            p.loan += deficit;
            log.add(p.name + " took a loan of Rs." + deficit + " for " + what);
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
        p.passYear(log);
        PrintMethods.pln(p.name+" failed job application: " + job);
    }

    public static void showRecentLog(List<String> log) {
        System.out.println("\n----- Recent Log -----");
        int start = Math.max(0, log.size() - 30);
        for (int i=start; i<log.size(); i++) System.out.println("• " + log.get(i));
        if (log.isEmpty()) System.out.println("(empty)");
        System.out.println("----------------------");
    }

    public static int readInt() {
        while (true) {
            try { return Integer.parseInt(SC.next()); }
            catch (Exception e) { System.out.print("Enter a number: "); }
        }
    }

    public static long readLong() {
        while (true) {
            try { return Long.parseLong(SC.next()); }
            catch (Exception e) { System.out.print("Enter a whole amount: "); }
        }
    }

    public static void CompoundLoan(Person p){

        if (p.age > p.AgeWhenLoanStarts && p.loan > 0) {
        p.loan += Math.rint(p.loan * 0.1);
        }
        
    }
}