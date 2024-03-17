package Logic;

import java.time.LocalDate;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class Client {
    public Creation c = new Creation();
    private String name;
    private String surname;
    private LocalDate birthdate;
    private int age;
    private String creditCardNumber;
    private String phoneNumber;
    private String emailAddress;
    private String clientNumber;
    private String accountNumber;
    private int accountBalance;
    public ArrayList<Deposit> deposits;
    public ArrayList<Loan> loans;
    public JSONArray historyJSON;
    public boolean isInDebt;
    public LocalDate latestDebtDate;

    public enum Source {Loan, Deposit, Transfer};
    public Client(String name,
                  String surname,
                  String birthday, String birthMonth, String birthYear,
                  String phoneNumber, String emailAddress, String creditCardNumber, String accountNumber,
                  int accountBalance){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.birthdate = LocalDate.of(Integer.parseInt(birthYear), Integer.parseInt(birthMonth), Integer.parseInt(birthday));
        this.age = receive_Age(birthdate);
        this.creditCardNumber = creditCardNumber;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.isInDebt = false;
    }


    //wywołać przy logowaniu
    public void executeDeposits() {
        try {
            for (var deposit : deposits) {
                if (deposit.checkIfToFinish()) {
                    deposit.finishDeposit();
                    deposits.remove(deposit);
                }
            }
        } catch(Exception e){
            deposits.clear();
        }
    }


    public void executeLoans(){
        try {
            for (var loan : loans) {
                int number = loan.numberOfMonthsToPayFor();
                for (int months_to_pay = 0; months_to_pay < number; months_to_pay++) {
                    loan.executeLoan();
                    loan.addToHistory(this, false);
                    if (loan.checkIfToFinish()) {
                        loans.remove(loan);
                    }
                }
            }
        }catch(Exception e){
            loans.clear();
        }
    }

    public void addDebtToHistory(Client client, int amount){
        String createStatement = "Debt increases";
        JSONObject jo = new JSONObject();
        jo.put("amount", amount);
        jo.put("date", latestDebtDate.toString());
        jo.put("title", createStatement);
        jo.put("source", "Debt");
        client.historyJSON.put(jo);
        client.historyJSON = Json_Parser.sortHistory(client.historyJSON);
    }

    public void debtOperation(){
        LocalDate today = LocalDate.now();
        if(getAccountBalance()>=0 && isInDebt){
            isInDebt=false;
        }
        else if(getAccountBalance()<0 && isInDebt && (today.isAfter(latestDebtDate.plusMonths(1)) || today.isEqual(latestDebtDate.plusMonths(1)))){
            int monthsPassed = today.getMonthValue() - latestDebtDate.getMonthValue() + 12*(today.getYear()-latestDebtDate.getYear());
            for(int monthsToPayDebt=0;monthsToPayDebt<monthsPassed;monthsToPayDebt++) {
                latestDebtDate = latestDebtDate.plusMonths(1);
                int addDebt = (int)((double)accountBalance*0.1);
                accountBalance = (int) ((double)accountBalance +  addDebt);
                addDebtToHistory(this,addDebt);
            }

        }
    }

    int receive_Age(LocalDate birthdate) {
        LocalDate today = LocalDate.now();
        int diff=today.getYear()-birthdate.getYear();
        if((today.getMonthValue()>birthdate.getMonthValue()) || ((today.getMonthValue() == birthdate.getMonthValue()) && (today.getDayOfMonth() > birthdate.getDayOfMonth()))){
            return diff;
        }
        else{
            return diff-1;
        }
    }

    public String getClientNumber() {return clientNumber; }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public int getAge() {
        return age;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int newAccountBalance) {
        accountBalance = newAccountBalance;
    }

    public void setClientNumber(String newClientNumber) {
        clientNumber = newClientNumber;
    }

}
