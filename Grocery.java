
import java.util.Scanner;

public class Grocery {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String strAnotherP, strProdName, strCustomer;
        char cCustomer = ' ', cAnotherP = ' ';

        double dQty, dBill, dPrice;
        double dTotal = 0, dPay, dChange = 0;

        do {

            dBill = 0;

            do {

                System.out.println("\nWelcome to the Grocery Store");

                System.out.print("Input Product Name: ");
                strProdName = input.nextLine();

                System.out.print("Input Price: ");
                dPrice = input.nextDouble();
                input.nextLine();

                System.out.print("Input Quantity: ");
                dQty = input.nextDouble();
                input.nextLine();

                dTotal = dPrice * dQty;

                System.out.println("Total: " + dTotal);
                dBill = dBill + dTotal;

                System.out.println("Another product Y/N? ");
                strAnotherP = input.nextLine();
                cAnotherP = strAnotherP.charAt(0);

            } while ((cAnotherP == 'Y') || (cAnotherP == 'y'));

            System.out.println("Bill: " + dBill);

            System.out.println("Payment: ");
            dPay = input.nextDouble();
            input.nextLine();

            if (dPay >= dBill) {
                dChange = dPay - dBill;
                System.out.println("\nChange: " + dChange);
                System.out.println("Thank you for shopping");
            } else {
                System.out.println("Money is not enough!");
            }

            System.out.print("Another customer Y/N? ");
            strAnotherP = input.nextLine();
            cAnotherP = strAnotherP.charAt(0);

        } while ((cAnotherP == 'Y') || (cAnotherP == 'y'));

        System.out.println("Grocery program terminating...");
        input.close();
    }
}
