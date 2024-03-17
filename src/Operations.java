//tutaj funkcje związane z operacjami na klientach !!!!! trzeba dodać że targety są pobierane z bazy czy coś!!!!! sama logika funkcji tutaj jest, do poprawy branie
import java.time.LocalDate;
public class Operations {
    Client TargetClient;
    Client UserClient;

    void transfer(String target_account_number, int money){
        if (UserClient.getAccountBalance() < money){
            System.out.println("Insufficient amount of money");
        }
        else if (target_account_number.equals(TargetClient.getAccountNumber())){
            int target_balance = TargetClient.getAccountBalance() + money;
            TargetClient.setAccountBalance( target_balance);
            int user_balance = UserClient.getAccountBalance() - money;
            UserClient.setAccountBalance((user_balance));
        }
        else if(money <=0){
            System.out.println("Wrong amount of money");
        }
        else {
            System.out.println("There is no such client");
        }
    }

    void finances(){
        UserClient.toString();
    }

    void take_loan(int amount, String type, int paymonth, int payyear){
        LocalDate loandate = LocalDate.now();
        LocalDate paydate = LocalDate.of(payyear, paymonth, loandate.getDayOfMonth());
        if(paydate.isAfter(loandate)){
            int months = (paydate.getMonthValue() - loandate.getMonthValue()) + 12*(paydate.getYear()-loandate.getYear());
            int monthly_payment = amount/months;

        }
        else {
            System.out.println("Wrong date");
        }





    }
}
