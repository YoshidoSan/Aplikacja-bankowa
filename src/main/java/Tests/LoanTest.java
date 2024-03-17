package Tests;

import ApplicationScene.SceneController;
import Logic.Client;
import Logic.Loan;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
class LoanTest {

    @Test
    void getLoan_amount() {
        Loan lo = new Loan(1000,12,0.10);
        Assertions.assertEquals(1000, lo.getLoan_amount());
    }

    @Test
    void getNumber_of_months() {
        Loan lo = new Loan(1000,12,0.10);
        Assertions.assertEquals(12, lo.getNumber_of_months());
    }

    @Test
    void getInterest() {
        Loan lo = new Loan(1000,12,0.10);
        Assertions.assertEquals(0.10, lo.getInterest());
    }

    @Test
    void getMonthly_payment() {
        Loan lo = new Loan(1564827,12,0.23);
        Assertions.assertEquals(160395, lo.calculate_monthly_payment());
        Loan lo2 = new Loan(1000,3,0.1);
        Assertions.assertEquals(367, lo2.calculate_monthly_payment());
    }
}

