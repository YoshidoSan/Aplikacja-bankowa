package Logic;

import org.json.simple.JSONObject;
import java.time.LocalDate;

public class Transfer {
    public Client targetClient;
    public int moneyAmount;
    public LocalDate date;
    public String title;

    public Transfer(Client targetClient, int money, String title) {
        this.targetClient = targetClient;
        this.moneyAmount = money;
        this.date = LocalDate.now();
        this.title = title;
    }

    public void makeEntry(Client client, boolean giver){
        JSONObject jo = new JSONObject();
        jo.put("amount", giver ? -moneyAmount : moneyAmount);
        jo.put("date", date);
        jo.put("title", title);
        jo.put("source", Client.Source.Transfer);
        client.historyJSON.put(jo);
        client.historyJSON = Json_Parser.sortHistory(client.historyJSON);
    }

    public void addToHistory(Client client){
        makeEntry(client, true);
        makeEntry(targetClient, false);
    }

    public void transferMoney(Client client) {
        client.setAccountBalance(client.getAccountBalance() - moneyAmount);
        targetClient.setAccountBalance(targetClient.getAccountBalance() + moneyAmount);
        addToHistory(client);
    }

}
