package Logic;

import ApplicationScene.SceneController;
import org.json.JSONObject;
import java.time.LocalDate;

import static java.lang.Math.ceil;

public class Deposit {
    public int depositAmount;
    public int numberOfMonths;
    public double interest;
    public LocalDate date;

    public enum Operation {Create, Finish, End}

    public Deposit(int depositAmount, int numberOfMonths, double interest){
        this.date = LocalDate.now();
        this.depositAmount = depositAmount;
        this.numberOfMonths = numberOfMonths;
        this.interest = interest;
    }

    public JSONObject JSON_deposit(){
        JSONObject jo = new JSONObject();
        jo.put("number_of_months", numberOfMonths);
        jo.put("date", date);
        jo.put("deposit_amount", depositAmount);
        jo.put("interest", interest);
        return jo;
    }

    public LocalDate calculateEnd(){
        return date.plusMonths(numberOfMonths);
    }

    public void addToHistory(Client client, Operation operation){
        String title = "";
        int amount = 0;
        JSONObject jo = new JSONObject();

        if (operation == Operation.Create){
            title = "Making a deposit";
            amount = -depositAmount;
            jo.put("date", date.toString());
        }
        else if (operation == Operation.Finish){
            title = "Finally, yeah money!";
            amount = getGainedMoney();
            jo.put("date", date.plusMonths(numberOfMonths).toString());
        }
        jo.put("amount", amount);
        jo.put("title", title);
        jo.put("source", Client.Source.Deposit);
        client.historyJSON.put(jo);
        client.historyJSON = Json_Parser.sortHistory(client.historyJSON);
    }

    private void addToDeposits(Client client){
        client.deposits.add(this);
    }

    public void finishDeposit(){
        Client client = SceneController.getCurrent_client();
        client.setAccountBalance(client.getAccountBalance() + getGainedMoney());
        addToHistory(client,Operation.Finish);
    }

    public void makeDeposit(Client client){
        client.setAccountBalance(client.getAccountBalance() - depositAmount);
        addToHistory(client, Operation.Create);
        addToDeposits(client);
    }

    public boolean checkIfToFinish() {
        return (calculateEnd().isBefore(LocalDate.now()) || calculateEnd().isEqual(LocalDate.now()));
    }

    public int getDepositAmount() {
        return depositAmount;
    }

    public int getNumberOfMonths() {
        return numberOfMonths;
    }

    public double getInterest() {
        return interest;
    }

    public int getGainedMoney() {
        return (int) ceil((double)depositAmount*(1 + interest*((double)numberOfMonths/12)));
    }

    public LocalDate getDate() {
        return date;
    }
}
