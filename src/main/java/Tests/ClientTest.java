package Tests;

import Logic.Client;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;

class ClientTest {

    @Test
    void getClientNumber() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        client.setClientNumber("98705654");
        Assertions.assertEquals("98705654", client.getClientNumber());
    }

    @Test
    void getName() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        Assertions.assertEquals("Jan", client.getName());
    }

    @Test
    void getSurname() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        Assertions.assertEquals("Kowalski", client.getSurname());
    }

    @Test
    void getBirthdate() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        LocalDate data = LocalDate.parse("1980-01-01");
        Assertions.assertEquals(data, client.getBirthdate());
    }

    @Test
    void getAge() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        Assertions.assertEquals(42, client.getAge());
    }

    @Test
    void getCreditCardNumber() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        Assertions.assertEquals("98705654", client.getCreditCardNumber());
    }

    @Test
    void getPhoneNumber() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        Assertions.assertEquals("123456789", client.getPhoneNumber());
    }

    @Test
    void getEmailAddress() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        Assertions.assertEquals("jankowalski@gmail.com", client.getEmailAddress());
    }

    @Test
    void getAccountNumber() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        Assertions.assertEquals("73584237941846293654283574023", client.getAccountNumber());
    }

    @Test
    void getAccountBalance() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        Assertions.assertEquals(2000, client.getAccountBalance());
    }

    @Test
    void setAccountBalance() {
        Client client = new Client("Jan","Kowalski","1","1","1980","123456789","jankowalski@gmail.com","98705654","73584237941846293654283574023",2000);
        client.setAccountBalance(1500);
        Assertions.assertEquals(1500, client.getAccountBalance());
    }
}