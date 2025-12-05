import java.util.*;
public class Calculator_Pavit
{
    public static void main (String[] args)
    {
        Scanner in=new Scanner(System.in);
        System.out.println("What operation would you like to perform? Addition, Subtraction, Multiplication, or Division?");
        String strinput=in.nextLine();
       
        switch(strinput){
            case("Addition"):
                System.out.println("Input the two values you want to add");
                double a=in.nextDouble();
                double b=in.nextDouble();
                double sum= a + b ;
                System.out.println("Desired output = "+ sum);
                break;
            case("Subtraction"):
                System.out.println("Input the two values you would like to subtract");
                double c=in.nextDouble();
                double d=in.nextDouble();
                double diff= c - d;
                System.out.println("Desired output = "+ diff);
                break;
            case("Multiplication"):
                System.out.println("Input the values you would like to multiply");
                double e=in.nextDouble();
                double f=in.nextDouble();
                double prod= e * f;
                System.out.println("The desired output = "+ prod );
                break;
            case("Division"):
                System.out.println("Input the alues you would like to divide");
                double g=in.nextDouble();
                double i=in.nextDouble();
                double quotient= g / i;
                System.out.println("The desired output = "+ quotient);
                break;
            default:
                System.out.println("Please enter a valid operation from the ones given above only.");
        }
        in.close();
    }
}