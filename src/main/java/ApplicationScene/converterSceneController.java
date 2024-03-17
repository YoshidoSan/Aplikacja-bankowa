package ApplicationScene;

import Logic.Client;
import Logic.CurrencyConverter;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class converterSceneController extends SwitchScene implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label userNameLabel;

    @FXML
    TextArea mainTextArea;

    @FXML
    TextField amountTextFieldL;

    @FXML
    TextField amountTextFieldR;

    @FXML
    ChoiceBox<String> valL;

    @FXML
    ChoiceBox<String> valR;

    String[] valNames = {"USD", "CAD", "EUR", "HUF", "CHF", "GBP", "UAH", "JPY", "CZK", "NOK", "TRY", "ILS", "INR", "CNY"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        valL.getItems().addAll(valNames);
        valR.getItems().addAll(valNames);
        setNewText("Please choose currency from and to you want to convert");
        Client client = SceneController.getCurrent_client();
        setUserName(client.getName(), client.getSurname());
    }

    public void setUserName(String userName, String userSurname){
        userNameLabel.setText(userName + " " + userSurname);
    }

    public int celling(double value){
        double val1 = value*100;
        double val2 = Math.floor(val1);
        return (int) val2/100;
    }

    public void ConvertToPLN(){
        valR.setStyle("");
        amountTextFieldR.setStyle("");
        String valName = valR.getValue();
        try {
            if (valName.equals("null")) {
                setNewText("Choose currency!");
                valR.setStyle("-fx-background-color: red;");
                return;
            }
        }catch (Exception e){
            setNewText("Choose currency!");
            valR.setStyle("-fx-background-color: red;");
            return;
        }
        try {
            float amount = Float.parseFloat(amountTextFieldR.getText());
            int corAmount = (int) (100*amount);
            Logic.CurrencyConverter currencyConverter = new CurrencyConverter();
            int newAmount = celling(currencyConverter.exchangeMoney(valName, "PLN", corAmount));
            float showAmount = (float) newAmount/100;
            if(showAmount <= 0){
                setNewText("Amount should be positive!");
                amountTextFieldR.setStyle("-fx-background-color: red;");
                return;
            }
            setNewText(valName + " to " + "PLN" + " -> " + showAmount);
        }
        catch (RuntimeException e){
            setNewText("Please enter amount!");
            amountTextFieldR.setStyle("-fx-background-color: red;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ConvertFromPLN(ActionEvent event) throws Exception{
        valL.setStyle("");
        amountTextFieldL.setStyle("");
        String valName = valL.getValue();
        try {
            if (valName.equals("null")) {
                setNewText("Choose currency!");
                valL.setStyle("-fx-background-color: red;");
                return;
            }
        } catch (Exception e){
            setNewText("Choose currency!");
            valL.setStyle("-fx-background-color: red;");
            return;
        }
        try {
            float amount = Float.parseFloat(amountTextFieldL.getText());
            int corAmount = (int) (100*amount);
            Logic.CurrencyConverter currencyConverter = new CurrencyConverter();
            int newAmount = celling(currencyConverter.exchangeMoney("PLN", valName, corAmount));
            float showAmount = (float) newAmount/100;
            if(showAmount <= 0){
                setNewText("Amount should be positive!");
                amountTextFieldL.setStyle("-fx-background-color: red;");
                return;
            }
            setNewText("PLN" + " to " + valName + " -> " + showAmount);
        }
        catch (RuntimeException e){
            setNewText("Please enter amount!");
            amountTextFieldL.setStyle("-fx-background-color: red;");
        }
    }
    public void setNewText(String text){
        mainTextArea.setText(text);
    }
}
