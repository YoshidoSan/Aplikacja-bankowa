package Logic;

import org.json.JSONArray;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class Database_CL {

    //laczenie sie z baza danych
    public static Connection setConnection() throws SQLException{
        // db parameters

        String url       = "jdbc:mysql://eu-cdbr-west-02.cleardb.net:3306/heroku_be4a1b6569228e6";
        String user      = "b8d266390ae0ea";
        String password  = "10d61ab1";

        Connection conn = null;

        try  {
            conn = DriverManager.getConnection(url, user, password);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // tworzy w bazie danych pola dla nowego klienta
    public static void register(String name, String surname
            , String birthday, String birthmonth, String birthyear, String phonenumber,
                                String emailaddress, String clientNumber, String password, int question, String answer,
                                String credit_card_number, String account_number) {

        String credentials_query = "{call heroku_be4a1b6569228e6.create_credential_row(?, ?, ?, ?, ?) }";
        String clients_query = "{call heroku_be4a1b6569228e6.create_client_row(?,?,?,?,?,?,?,?, ?)}";

        try (Connection conn = setConnection();
             CallableStatement log_stmt = conn.prepareCall(credentials_query);
             CallableStatement cl_stmt = conn.prepareCall(clients_query)) {

            log_stmt.setString(1, clientNumber);
            log_stmt.setString(2, password);
            log_stmt.setString(3, account_number);
            log_stmt.setInt(4, question);
            log_stmt.setString(5, answer);

            cl_stmt.setString(1, name);
            cl_stmt.setString(2, surname);
            cl_stmt.setString(3,birthday);
            cl_stmt.setString(4, birthmonth);
            cl_stmt.setString(5, birthyear);
            cl_stmt.setString(9, phonenumber);
            cl_stmt.setString(6, emailaddress);
            cl_stmt.setString(7, credit_card_number);
            cl_stmt.setString(8, account_number);
            log_stmt.execute();
            cl_stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // sciaga z bazy danych informacje o kliencie o podanym numerze konta i wrzuca do klasy klient
    public static Client getClient(String account_number){
        String cl_query = "{call heroku_be4a1b6569228e6.get_client_info(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        Client client =null;
        try (Connection conn = setConnection();
             CallableStatement log_stmt = conn.prepareCall(cl_query)) {
            log_stmt.setString("_account_number", account_number);
            log_stmt.registerOutParameter("_name", Types.CHAR);
            log_stmt.registerOutParameter("_surname", Types.CHAR);
            log_stmt.registerOutParameter("_birthday", Types.CHAR);
            log_stmt.registerOutParameter("_birthmonth", Types.CHAR);
            log_stmt.registerOutParameter("_birthyear", Types.CHAR);
            log_stmt.registerOutParameter("_email", Types.CHAR);
            log_stmt.registerOutParameter("_credit_card_number", Types.CHAR);
            log_stmt.registerOutParameter("_phone_number", Types.CHAR);
            log_stmt.registerOutParameter("_balance", Types.DOUBLE);
            log_stmt.registerOutParameter("_deposits", Types.CHAR);
            log_stmt.registerOutParameter("_loans", Types.CHAR);
            log_stmt.registerOutParameter("_history", Types.CHAR);
            log_stmt.execute();

            String name = log_stmt.getString("_name");
            String surname = log_stmt.getString("_surname");
            String birthday = log_stmt.getString("_birthday");
            String birthmonth = log_stmt.getString("_birthmonth");
            String birthyear = log_stmt.getString("_birthyear");
            String phone_number =  log_stmt.getString("_phone_number");
            String email = log_stmt.getString("_email");
            String credit_card_number = log_stmt.getString("_credit_card_number");
            int balance = log_stmt.getInt("_balance");

            String _deposits = log_stmt.getString("_deposits");
            ArrayList<Deposit> deposits;
            if (!_deposits.equals("")) {
                JSONArray deposit = new JSONArray(_deposits);
                deposits = Json_Parser.JSONDepositsToArray(deposit);
            }else{
                deposits = new ArrayList<>();
            }


            String _history = log_stmt.getString("_history");
            JSONArray history;
            if (!_history.equals("")) {
                history = new JSONArray(_history);
            }else{
                history = new JSONArray();
            }

            client = new Client(name, surname,birthday,birthmonth,birthyear, phone_number, email, credit_card_number,
                    account_number, balance);

            String _loans = log_stmt.getString("_loans");
            ArrayList<Loan> loans;
            if (!_loans.equals("")) {
                JSONArray loan = new JSONArray(_loans);
                loans = Json_Parser.JSONLoansToArray(loan);
            }else{
                loans = new ArrayList<>();
            }

            client.deposits = deposits;
            client.loans = loans;
            client.historyJSON = history;

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return client;
    }

    //zwraca numer konta klienta o danym loginie i hasle
    public static String login(String _clientNumber, String _pass){
        String log_query = "{call heroku_be4a1b6569228e6.login(?,?,?)}";
        try (Connection conn = setConnection();
             CallableStatement log_stmt = conn.prepareCall(log_query)){
            log_stmt.setString("entered_login", _clientNumber);
            log_stmt.setString("entered_password", _pass);
            log_stmt.registerOutParameter("account_num", Types.CHAR);
            log_stmt.execute();
            if (!(log_stmt.getString("account_num").equals("no"))){
                return log_stmt.getString("account_num");
            }
            else{
                return "xd";
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    public static boolean changePass(String account_nr, String old_pass, String new_pass)
    {
        String log_query = "{call heroku_be4a1b6569228e6.change_pass(?,?,?,?)}";
        try (Connection conn = setConnection();
            CallableStatement log_stmt = conn.prepareCall(log_query)){
            log_stmt.setString("account_nr", account_nr);
            log_stmt.setString("old_pass", old_pass);
            log_stmt.setString("new_pass", new_pass);
            log_stmt.registerOutParameter("_changed", Types.BOOLEAN);
            log_stmt.execute();
            return log_stmt.getBoolean("_changed");
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static ArrayList<String> getLogins()
    {
        String sql = "SELECT login FROM heroku_be4a1b6569228e6.credentials";
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection conn = setConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                list.add(rs.getString("login"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public static ArrayList<String> getEmails()
    {
        String sql = "SELECT email FROM heroku_be4a1b6569228e6.clients;";
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection conn = setConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                list.add(rs.getString("email"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }


    public static ArrayList<String> getPhoneNrs()
    {
        String sql = "SELECT phone_number FROM heroku_be4a1b6569228e6.clients;";
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection conn = setConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                list.add(rs.getString("phone_number"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }



    public static ArrayList<String> getAccountNrs()
    {
        String sql = "SELECT account_number FROM heroku_be4a1b6569228e6.credentials;";
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection conn = setConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                list.add(rs.getString("account_number"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public static ArrayList<String> getClientNrs()
    {
        String sql = "SELECT login FROM heroku_be4a1b6569228e6.credentials;";
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection conn = setConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("login"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public static ArrayList<String> getCardNrs()
    {
        String sql = "SELECT credit_card_number FROM heroku_be4a1b6569228e6.clients";
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection conn = setConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                list.add(rs.getString("credit_card_number"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public static int getQuestion(String clientNumber){
        String query = "{call heroku_be4a1b6569228e6.get_question(?,?)}";
        try (Connection conn = setConnection();
             CallableStatement log_stmt = conn.prepareCall(query)){
            log_stmt.setString("entered_login", clientNumber);
            log_stmt.registerOutParameter("p_question", Types.INTEGER);
            log_stmt.execute();
            return log_stmt.getInt("p_question");
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -2;
        }
    }


    public static String[] forgotPass(String clientNumber, String answer){
        String[] ans= new String[2];
        String query = "{call heroku_be4a1b6569228e6.forgot_pass(?,?,?,?)}";
        try (Connection conn = setConnection();
             CallableStatement log_stmt = conn.prepareCall(query)){
            log_stmt.setString("_login", clientNumber);
            log_stmt.setString("_answer", answer);
            log_stmt.registerOutParameter("_pass", Types.CHAR);
            log_stmt.registerOutParameter("_account_nr", Types.CHAR);
            log_stmt.execute();
            ans[0] = log_stmt.getString("_pass");
            ans[1] = log_stmt.getString("_account_nr");
            return ans;
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ans[0]="";
            ans[1]="";
        }
        return ans;
    }

    public static ArrayList<String[]> getBanks() {
        ArrayList<String[]> ans = new ArrayList<>();
        String sql = "SELECT * FROM heroku_be4a1b6569228e6.map_points";
        try {
            Connection conn = setConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                String latitude = rs.getString("latitude");
                String longitude = rs.getString("longitude");
                String city = rs.getString("city");
                String street = rs.getString("street");
                String postal_code = rs.getString("postal_code");
                String working_hours= rs.getString("working_hours");
                String[] smth = {latitude, longitude, city, street, postal_code, working_hours};
                ans.add(smth);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ans;

    }

    public static boolean updateBalance(Client client){
        String query = "{call heroku_be4a1b6569228e6.update_balance(?,?)}";
        String account_nr = client.getAccountNumber();
        double balance = client.getAccountBalance();

        try {
            Connection conn = setConnection();
            CallableStatement log_stmt = conn.prepareCall(query);
            log_stmt.setString("_account_nr", account_nr);
            log_stmt.setDouble("_balance", balance);
            log_stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean updateHistory(Client client){
        String account_nr = client.getAccountNumber();
        String history = client.historyJSON.toString();

        String query = "{call heroku_be4a1b6569228e6.update_history(?,?)}";

        try {
            Connection conn = setConnection();
            CallableStatement log_stmt = conn.prepareCall(query);
            log_stmt.setString("_account_nr", account_nr);
            log_stmt.setString("_history", history);
            log_stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean updateDeposits(Client client){
        String account_nr = client.getAccountNumber();
        String deposits = Json_Parser.arrayToJSONDeposits(client.deposits).toString();
        String query = "{call heroku_be4a1b6569228e6.update_deposits(?,?)}";
        try {
            Connection conn = setConnection();
            CallableStatement log_stmt = conn.prepareCall(query);
            log_stmt.setString("_account_nr", account_nr);
            log_stmt.setString("_deposits", deposits);
            log_stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean updateLoans(Client client) {
        String account_nr = client.getAccountNumber();
        String loans = Json_Parser.arrayToJSONLoans(client.loans).toString();
        String query = "{call heroku_be4a1b6569228e6.update_loans(?,?)}";
        try {
            Connection conn = setConnection();
            CallableStatement log_stmt = conn.prepareCall(query);
            log_stmt.setString("_account_nr", account_nr);
            log_stmt.setString("_loans", loans);
            log_stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean isInDebit(String account_nr){
        String sql = "SELECT is_in_debit FROM heroku_be4a1b6569228e6.clients where account_number = "+ account_nr;
        try {
            Connection conn = setConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            if(rs.next()){return rs.getBoolean("is_in_debit");};
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static LocalDate LastDebitDate(String account_nr){
        String sql = "SELECT debit_date FROM heroku_be4a1b6569228e6.clients where account_number = "+ account_nr;
        try {
            Connection conn = setConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            if(rs.next()){String date =  rs.getString("debit_date");
                if(!date.equals("")){return LocalDate.parse(date);}
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static boolean updateDebit(Client client) {
        String account_nr = client.getAccountNumber();
        String date_str;
        boolean deb = client.isInDebt;
        if (client.latestDebtDate!=null) {
            date_str = client.latestDebtDate.toString();
        }else {
            date_str = "";
        }
        String query = "{call heroku_be4a1b6569228e6.update_debit(?,?,?)}";
        try {
            Connection conn = setConnection();
            CallableStatement log_stmt = conn.prepareCall(query);
            log_stmt.setString("_account_nr", account_nr);
            log_stmt.setBoolean("_is_in_debit", deb);
            log_stmt.setString("_debit_date", date_str);
            log_stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

/*
    public static void main(String[] args){
        String sql = "SELECT *" +
                "FROM heroku_be4a1b6569228e6.clients";

        try {
            Connection conn = setConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("birthday") + "\t" +
                        rs.getString("birthmonth")  + "\t" +
                        rs.getString("birthyear"));
            }

            get_account_nrs();
            String m = login("admin", "admin");
            Client client = get_client(m, "admin");
            client.setAccountBalance(12);
//            client.Loans.add(new Loan(12,32,2, client));
            client.Deposits.add(new Deposit(12,12,0.02));
            client.HistoryJSON.put(1);
            update_balance(client);
            update_deposits(client);
            update_history(client);
            update_loans(client);

            System.out.println(m);
            //register("789","789",789,789,789,789,"789","789", "789", "789", "789");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

 */
}

