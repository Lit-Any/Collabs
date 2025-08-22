package LifeSim.LifeSimSupport;

import java.util.List;

public class Helpers {

    static java.util.Scanner SC = new java.util.Scanner(System.in);

    public static void spend(Person p, long amt, List<String> log, String what) {
        long pay = Math.min(amt, p.balance);
        p.balance -= pay; p.addFlow(pay);
        if (pay > 0) log.add(p.name+" spent Rs." + pay + " on " + what + ".");
    }

    public static int eduRank(String e) {
        if (e.equals("None")) return 0;
        if (e.equals("HS")) return 1;
        if (e.equals("College")) return 2;
        if (e.equals("Masters")) return 3;
        return 0;
        }

    
    public    static void failJob(Person p, String job, List<String> log) {
        p.happiness -= 5;
        log.add(p.name+" failed job application: " + job);
        p.passYear(log);
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
}
