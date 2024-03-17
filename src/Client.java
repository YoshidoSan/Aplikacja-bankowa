import java.time.LocalDate;
import java.util.Random;

// comment

public class Client {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }

    public String Name;
    public String Surname;
    public int birthday;
    public int birthmonth;
    public int birthyear;
    public LocalDate Birthdate;
    public int Age;
    public String CreditCardNumber;
    public int PhoneNumber;
    public String EmailAddress;
    public String Login;
    public String Password;
    public static String AccountNumber;
    public int AccountBalance;

    public Client(String name,
                  String surname,
                  int birthday, int birthmonth, int birthyear,
                  int phonenumber, String emailaddress,
                  String login, String password,String creditcardnumber, String accountnumber){
        Name = name;
        Surname = surname;
        PhoneNumber = phonenumber;
        EmailAddress = emailaddress;

        Login = login;
        Password = password;

        LocalDate birthdate = new LocalDate(birthyear, birthmonth, birthday);
        Birthdate = birthdate;
        Age = receive_Age(birthdate);

        CreditCardNumber = creditcardnumber;
        AccountNumber = accountnumber;
        AccountBalance = 0;

    }

    int receive_Age(LocalDate birthdate) {
        LocalDate d1 = LocalDate.parse("2018-05-26", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate d2 = LocalDate.parse("2018-05-28", DateTimeFormatter.ISO_LOCAL_DATE);
        Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
        long diffDays = diff.toDays();

        LocalDate today = LocalDate.now();
        int difference = (int)(Duration.between(today.atStartOfDay(), birthdate.atStartOfDay()));
        return difference;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public LocalDate getBirthdate() {
        return Birthdate;
    }

    public int getAge() {
        return Age;
    }

    public String getCreditCardNumber() {
        return CreditCardNumber;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getLogin() {
        return Login;
    }

    public String getPassword() {
        return Password;
    }

    public static String getAccountNumber() {
        return AccountNumber;
    }

    public int getAccountBalance() {
        return AccountBalance;
    }

    public void setAccountBalance(int new_accountBalance) {
        AccountBalance = new_accountBalance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", CreditCardNumber='" + CreditCardNumber + '\'' +
                ", AccountNumber='" + AccountNumber + '\'' +
                ", AccountBalance=" + AccountBalance +
                '}';
    }
}