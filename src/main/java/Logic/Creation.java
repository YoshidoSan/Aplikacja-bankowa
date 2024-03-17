package Logic;

import java.util.ArrayList;
import java.util.Random;

public class Creation {

    public static String generateCreditCardNumber(){
        String CreditCardNumber = "";
        Random generator = new Random();
        boolean contains = false;
        ArrayList<String> list = Database_CL.getCardNrs();
        do {
            for (int i = 0; i < 16; i++) {
                CreditCardNumber += generator.nextInt(10);
                if ((((i+1) % 4) == 0) && i != 15) {
                    CreditCardNumber += "-";
                }
                contains = list.contains(CreditCardNumber);
            }
        }while(contains);
        return CreditCardNumber;
    }

    public static String generateAccountNumber(){
        String AccountNumber = "7194-9766-54";
        Random generator = new Random();
        boolean contains = false;
        ArrayList<String> list = Database_CL.getAccountNrs();
        do {
            for (int i = 0; i < 16; i++) {
                if ((((i+1) % 4) == 0) && i != 15) {
                    AccountNumber += "-";
                }
                AccountNumber += generator.nextInt(10);
            }
            contains = list.contains(AccountNumber);
        }while(contains);
        return AccountNumber;
    }

    public static String generateClientNr(){
        String Client_nr = "";
        Random generator = new Random();
        boolean contains = false;
        ArrayList<String> list = Database_CL.getLogins();
        do {
            for (int i = 0; i < 8; i++) {
                Client_nr += generator.nextInt(10);
            }
            contains = list.contains(Client_nr);
        }while(contains);
        return Client_nr;
    }
}

