package Logic;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Json_Parser {


    public static ArrayList<Deposit> JSONDepositsToArray(JSONArray JSON_deposits){
        ArrayList<Deposit> deposits = new ArrayList<>();
        int array_length = JSON_deposits.length();
        for (int i = 0; i < array_length; i++){
            JSONObject JSON_elem = (JSONObject)(JSON_deposits.get(i));
            int deposit_value = (int)(JSON_elem.get("deposit_amount"));
            int number_of_months = (int)(JSON_elem.get("number_of_months"));
            double interest = (JSON_elem.getDouble("interest"));
            LocalDate date = LocalDate.parse(JSON_elem.getString("date"));

            Deposit deposit = new Deposit(deposit_value, number_of_months, interest);
            deposit.date = date;
            deposits.add(deposit);
        }
        return deposits;
    }

    public static ArrayList<Loan> JSONLoansToArray(JSONArray JSON_loans){
        ArrayList<Loan> loans = new ArrayList<>();
        int array_length = JSON_loans.length();
        for (int i = 0; i < array_length; i++){
            JSONObject JSON_elem = (JSONObject)(JSON_loans.get(i));
            int loan_amount = (int)(JSON_elem.get("loan_amount"));
            int number_of_months = (int)(JSON_elem.get("number_of_months"));
            double interest = (JSON_elem.getDouble("interest"));
            LocalDate date = LocalDate.parse(JSON_elem.getString("date"));
            int months_paid = (int)(JSON_elem.get("months_paid"));
            Loan loan = new Loan(loan_amount, number_of_months, interest);
            loan.date = date;
            loan.monthsAlreadyPaid=months_paid;
            loans.add(loan);
        }
        return loans;
    }

    public static JSONArray arrayToJSONLoans(ArrayList<Loan> loans){
        JSONArray array = new JSONArray();
        for (Loan loan : loans){
            JSONObject jo = loan.JSON_loan();
            array.put(jo);
        }
        return array;
    }

    public static JSONArray arrayToJSONDeposits(ArrayList<Deposit> deposits){
        JSONArray array = new JSONArray();
        for (Deposit deposit : deposits){
            JSONObject jo = deposit.JSON_deposit();
            array.put(jo);
        }
        return array;
    }

    public static JSONArray sortHistory(JSONArray history){
        // Extract the JSONObjects
        JSONObject[] objects = new JSONObject[history.length()];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = history.getJSONObject(i);
        }
        // Sort the array of JSONObjects
        Arrays.sort(
                objects,
                Comparator.comparing((JSONObject o) -> (LocalDate.parse(o.getString("date"))))
        );
        Collections.reverse(Arrays.asList(objects));
        // Build a new JSONArray from the sorted array
        JSONArray array2 = new JSONArray();
        for (JSONObject o : objects) {
            array2.put(o);
        }
        return array2;
    }
}
