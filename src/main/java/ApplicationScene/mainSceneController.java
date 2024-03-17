package ApplicationScene;

import Logic.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class mainSceneController extends SwitchScene implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    Label userNameLabel;

    @FXML
    TextArea mainTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client client = SceneController.getCurrent_client();
        setUserName(client.getName(), client.getSurname());
        setNewText(client.getAccountBalance(), client.getAccountNumber(), client.getCreditCardNumber(), client.getClientNumber());
    }

    public void setNewText(int AccountBalance, String AccountNumber, String CreditCardNumber, String ClientNumber){
        Client client = SceneController.getCurrent_client();
        float showAccountBalance = (float) AccountBalance/100;
        String text = "Your account number: " + AccountNumber + "\n" +
                "Your credit card number: " + CreditCardNumber + "\n" +
                "Your balance: " + showAccountBalance + " PLN" + "\n" +
                "Your client number: " + ClientNumber;
        if (client.isInDebt) {
            text += "\nYour account is in debt!\nUntil you pay it off, you will lose additional 10%";
        }
        mainTextArea.setText(text);
    }
    public void setUserName(String userName, String userSurname){
        userNameLabel.setText(userName + " " + userSurname);
    }
}
