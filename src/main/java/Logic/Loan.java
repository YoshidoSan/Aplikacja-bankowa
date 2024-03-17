package Logic;

import ApplicationScene.SceneController;
import java.time.LocalDate;
import java.time.Period;
import java.lang.Math;
import org.json.JSONObject;

import static java.lang.Math.ceil;

public class Loan {
    private int loanAmount;
    private int numberOfMonths;
    private double interest;

    public LocalDate date;
    public int monthsAlreadyPaid;

    public Loan(int loanAmount, int numberOfMonths, double interest) {
        this.loanAmount = loanAmount;
        this.numberOfMonths = numberOfMonths;
        this.interest = interest;
        this.date = LocalDate.now();
        this.monthsAlreadyPaid = 0;
    }

    public JSONObject JSON_loan(){
        JSONObject jo = new JSONObject();
        jo.put("number_of_months", numberOfMonths);
        jo.put("date", date);
        jo.put("loan_amount", loanAmount);
        jo.put("interest", interest);
        jo.put("months_paid", monthsAlreadyPaid);
        return jo;
    }
    //boolean create means load has just been created, if(NOT create) then it means the bank draws money back entry
    public void addToHistory(Client client, boolean create){
        String createStatement = "Taking a loan";
        String drawStatement = "You gave money from loan to bank";
        JSONObject jo = new JSONObject();
        jo.put("amount", create ? loanAmount : -calculate_monthly_payment());
        jo.put("date", date.plusMonths(monthsAlreadyPaid).toString());
        jo.put("title", create ? createStatement : drawStatement);
        jo.put("source", Client.Source.Loan);
        client.historyJSON.put(jo);
        client.historyJSON = Json_Parser.sortHistory(client.historyJSON);
    }

    public void executeLoan(){
        Client client = SceneController.getCurrent_client();
        client.setAccountBalance(client.getAccountBalance() - calculate_monthly_payment());
        if(client.getAccountBalance()<0 && !client.isInDebt){
            client.isInDebt = true;
            client.latestDebtDate = date.plusMonths(monthsAlreadyPaid);
        }
        monthsAlreadyPaid ++;
    }

    public int numberOfMonthsToPayFor(){
        LocalDate dateNow = LocalDate.now();
        int numberOfMonthsSinceLastLogIn = Period.between(date, dateNow).getYears()*12 +
                Period.between(date, dateNow).getMonths()-monthsAlreadyPaid;
        return Math.min(numberOfMonthsSinceLastLogIn, numberOfMonths-monthsAlreadyPaid);
    }

    public void makeLoan(Client client){
        client.setAccountBalance(client.getAccountBalance() + loanAmount);
        addToHistory(client, true);
        Add_to_loans(client);
    }

    public boolean checkIfToFinish() {
        return (getMonths_already_paid() >= getNumber_of_months());
    }

    private void Add_to_loans(Client client){
        client.loans.add(this);
    }

    public int calculate_monthly_payment(){
        return (int) ceil(((double)loanAmount * (1+interest))/(double)numberOfMonths);
    }

    public LocalDate getDate() {
        return date;
    }

    public int getLoan_amount() {
        return loanAmount;
    }

    public int getNumber_of_months() {
        return numberOfMonths;
    }

    public double getInterest() {
        return interest;
    }

    public int getMonths_already_paid(){
        return monthsAlreadyPaid;
    }
}
