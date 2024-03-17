package Tests;

import Logic.Creation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

class CreationTest {

    @Test
    void generate_CreditCardNumber() {
        String testCredit = Creation.generateCreditCardNumber();
        Assertions.assertEquals(19, testCredit.length());
        String onlyNumbers = testCredit.replace("-", "");
        Assertions.assertEquals(16, onlyNumbers.length());
    }

    @Test
    void generate_AccountNumber() {
        String testAccount = Creation.generateAccountNumber();
        Assertions.assertEquals(31, testAccount.length());
        String onlyNumbers = testAccount.replace("-", "");
        Assertions.assertEquals(26, onlyNumbers.length());
    }

    @Test
    void generate_Client_Nr() {
        String testClient = Creation.generateClientNr();
        Assertions.assertEquals(8, testClient.length());
    }
}