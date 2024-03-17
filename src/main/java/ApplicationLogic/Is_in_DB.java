package ApplicationLogic;

import Logic.Database_CL;

import java.util.ArrayList;

public class Is_in_DB {

    public static boolean is_email_there(String Email){
        ArrayList<String> emails =  Database_CL.getEmails();
        return emails.contains(Email);
    }

    public static boolean is_phone_nr_there(String phone_nr){
        ArrayList<String> phone_nrs = Database_CL.getPhoneNrs();
        return phone_nrs.contains(phone_nr);
    }

    public static boolean is_acc_nr_there(String account_nr){
        ArrayList<String> account_nrs = Database_CL.getAccountNrs();
        return account_nrs.contains(account_nr);
    }

    public static boolean  is_cl_nr_there(String cl_nr){
        ArrayList<String> cl_nrs = Database_CL.getClientNrs();
        return cl_nrs.contains(cl_nr);
    }
}
