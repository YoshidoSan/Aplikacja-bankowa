import java.time.LocalDate;
import java.util.Random;
    //tutaj bedzie się zbierało parametry i tworzyło klientów
public class Creation {

    public static String generate_CreditCardNumber(){
        String CreditCardNumber = "";
        Random generator = new Random();
        for (int i=0; i<16; i++) {
            CreditCardNumber += generator.nextInt(10);
            if ((i % 4) == 0){
                CreditCardNumber += "-";
            }
        }
        return CreditCardNumber;
    }

    public static String generate_AccountNumber(){
        String AccountNumber = "7194976654";
        Random generator = new Random();
        for (int i=0; i<16; i++) {
            AccountNumber += generator.nextInt(10);
        }
        return AccountNumber;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println(generate_AccountNumber());
    }

}
