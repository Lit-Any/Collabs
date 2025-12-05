import utility.*;
import java.util.*;

public class Reverso {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        x.p("Enter a number.");
        int num = sc.nextInt();
        int i=1, countofdigits = 0;
        do{
            if (num%(Math.pow(10, i)) != num) {
                i++;
                countofdigits++;
            } else {
                break;
            }
            
        } while (true);
        int reversednum = 0;
        for (i = 0; i <= countofdigits; i++){
            reversednum = (10*reversednum) + (num%10);
            num /= 10;
        }
        x.p("Reversed number: "+ reversednum);
        sc.close();
    }
}
