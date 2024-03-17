package ApplicationLogic;

import Logic.Creation;
import Logic.Database_CL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register {


    public static void register(String day, String month, String year, String phonenumber, String emailaddress,
                         String password, String repeatPassword, String name, String surname, int question,
                                String answer, String clientNumber){

        if (registerIsValid(phonenumber, emailaddress, password, repeatPassword)){
            String accountNumber = Creation.generateAccountNumber();
            String creditCardNumber = Creation.generateCreditCardNumber();
            Database_CL.register(name, surname, day, month, year, phonenumber, emailaddress, clientNumber, password, question,
                   answer, creditCardNumber,accountNumber);
        }
        else System.out.println("something is wrong");
    }
    public static boolean registerIsValid(String phonenumber, String emailaddress,
                                   String password, String repeatPassword) {

        return emailIsValid(emailaddress) && passwordAreTheSame(password, repeatPassword) && phonenumberIsValid(phonenumber);
    }

    public static boolean emailIsValid(String emailaddress) {
        return EmailValidator.validate(emailaddress);
    }

    public static boolean passwordAreTheSame(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }

    public static boolean firstNameIsValid(String name) {
        return name.matches("[A-Z][a-z]*");
    }

    public static boolean lastNameIsValid(String surname) {
        return surname.matches("[A-Z]+([a-zA-Z]+)*");
    }

    public static boolean phonenumberIsValid(String number)
    {
        Pattern p = Pattern.compile(
                "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
        Matcher m = p.matcher(number);
        return (m.matches());
    }
}
