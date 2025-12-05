import java.util.*;

public class Factorial {
    private static void p (Object x) {
        System.out.println(x);
    }

    private static long fact (long x) {

        if (x < 0) {

            p("Factorial is not defined for negative numbers.");
            System.exit(0);
            return -1;

        } else {

            return (x<=1) ? 1 : x * fact(x - 1);

        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

        p("How many number do you want to calculate the factorial for?");
        int count = sc.nextInt();

        int x = 0;

        for (int i = 0; i < count; i++) {

            p("Enter a number to calculate its factorial:");
            long n = sc.nextLong();

            long factorial = 1;
            for (x = 1; i <= n; i++) {
                factorial *= i;
            }

            p("The factorial of " + n + " is " + fact(n) + ".");
        }
        sc.close();
    }
}