import java.util.*;
import utility.*;

public class Randoms {

    private static final java.util.Random rand = new java.util.Random();

    public static int getRandomIntInRange(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static void main(String[] args) {

        List<Integer> Nums = new ArrayList<>();
        List<Double> Probabilities = new ArrayList<>();
        double counter = 0;

        for (int i = 0; i < 1000; i++) {
            Nums.add(getRandomIntInRange(0, 99));
        }

        for (int i = 0; i < 1000; i++) {
            if (Nums.get(i) == i) {
                counter += 1;
            }
            double prob = (counter / (i+1)) * 100;
            Probabilities.add(prob);
        }

        for (int i = 0; i < Probabilities.size(); i++) {
            x.p("Probability of " + i + " is: " + Probabilities.get(i) + "%");
        }

        x.p("Highest probability is: " + Collections.max(Probabilities) + "%" + " at " + (Probabilities.indexOf(Collections.max(Probabilities))) + ".");
        x.p("Lowest probability is: " + Collections.min(Probabilities) + "%" + " at " + (Probabilities.indexOf(Collections.min(Probabilities))) + ".");
    }


}