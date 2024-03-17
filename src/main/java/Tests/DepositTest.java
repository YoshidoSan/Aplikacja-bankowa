package Tests;

import ApplicationScene.SceneController;
import Logic.Client;
import Logic.Deposit;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

class DepositTest {

    @Test
    void getDeposit_amount() {
        Client clientNasz = new Client("Adam","Duklas","2","2","1990","987654321","adamduklas@gmail.com","13627824","63394230940846493624213571012",2000);
        SceneController.setCurrent_client(clientNasz);
        Deposit dep = new Deposit(1000, 12, 0.022);
        Assertions.assertEquals(1000, dep.getDepositAmount());
    }

    @Test
    void getNumber_of_months() {
        Client clientNasz = new Client("Adam","Duklas","2","2","1990","987654321","adamduklas@gmail.com","13627824","63394230940846493624213571012",2000);
        SceneController.setCurrent_client(clientNasz);
        Deposit dep = new Deposit(1000, 12, 0.022);
        Assertions.assertEquals(12, dep.getNumberOfMonths());
    }

    @Test
    void getInterest() {
        Client clientNasz = new Client("Adam","Duklas","2","2","1990","987654321","adamduklas@gmail.com","13627824","63394230940846493624213571012",2000);
        SceneController.setCurrent_client(clientNasz);
        Deposit dep = new Deposit(1000, 12, 0.022);
        Assertions.assertEquals(0.022, dep.getInterest());
    }

    @Test
    void getGained_money() {
        Client clientNasz = new Client("Adam","Duklas","2","2","1990","987654321","adamduklas@gmail.com","13627824","63394230940846493624213571012",2000);
        SceneController.setCurrent_client(clientNasz);
        Deposit dep = new Deposit(102529, 12, 0.022);
        Assertions.assertEquals(104785, dep.getGainedMoney());
    }

}

