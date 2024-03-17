package ApplicationScene;

import Logic.Client;
import Logic.Deposit;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class depositSceneController extends SwitchScene implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean isCheck = false;
    private int amountNumber = -1;
    private int depositNumber = -1;

    private int amount = -1;
    private int numberOfMounts = -1;
    private double interest = -1;
    private int chooseDeposit = -1;

    @FXML
    Label userNameLabel;

    @FXML
    TextArea mainTextArea;

    @FXML
    TextField amountTextField;

    @FXML
    TextArea errorTextArea;

    @FXML
    ChoiceBox<String> deposits;

    String[] depositsToChoose = {"Deposit for 3 months with 1% interest, from 0 to 10.000",
            "Deposit for 6 months with 2% interest, from 10.000 to 20.000",
            "Deposit for 12 months with 2% interest, from 20.000 to 100.000",
            "Deposit for 5 years with 5% interest, from to 100.000 1.000.000",
            "Deposit for 10 years with 10% interest, from 1.000.000 to 10.000.000"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deposits.getItems().addAll(depositsToChoose);
        Client client = SceneController.getCurrent_client();
        setUserName(client.getName(), client.getSurname());
        setTextError("Choose deposit and enter amount!");
        String text = "";
        text += "Current deposits:\n";
        ArrayList<Deposit> deposits = client.deposits;
        if (deposits==null){text += "\nNo current deposits\n";}
        else if (deposits.size()==0){text += "\nNo current deposits\n";}
        else{
            for (Deposit deposit : deposits) {
                text += "\ndate: "+deposit.getDate()+"\namount: "+(double)deposit.getDepositAmount()/100+" PLN\ninterest: "+deposit.getInterest()*100+"%\nnumber of months: "+deposit.getNumberOfMonths()+"\n";
            }
        }
        setText(text);
    }

    public void setUserName(String userName, String userSurname){
        userNameLabel.setText(userName + " " + userSurname);
    }

    public void getInformation(int Number , int Amount, double Interest, int chooseDeposit){
        numberOfMounts = Number;
        amount = Amount;
        interest = Interest;
        setData(Amount, chooseDeposit);
    }

    public void setData(int Amount, int chooseDepositNumber){
        amountTextField.setText(String.valueOf(Amount));
        deposits.setValue(depositsToChoose[chooseDepositNumber]);
    }

    public void makeDeposit(ActionEvent event) throws IOException{
        amountTextField.setStyle("");
        Client client = SceneController.getCurrent_client();
        boolean goood = true;
        int amountIns = 0;
        String chooseDepositS = deposits.getValue();
        int chooseDepositNumber = lookingForAnswerId(chooseDepositS);
        String amountS = amountTextField.getText();
        deposits.setStyle("");
        amountTextField.setStyle("");
        setTextError("");

        if(chooseDepositNumber < 0 || chooseDepositNumber > 4){
            addText("Choose deposit!");
            deposits.setStyle("-fx-background-color: red;");
            goood = false;
        }
        if(amountS.equals("")){
            addText("Enter amount!");
            amountTextField.setStyle("-fx-background-color: red;");
            goood = false;
        }
        if(!goood){
            return;
        }
        if(!amountS.equals("null")) {
            try {
                amountIns = Integer.parseInt(amountS);
                if (amountIns <= 0) {
                    addText("Value must be positive!!");
                    amountTextField.setStyle("-fx-background-color: red;");
                    return;
                }
                if (amountIns > client.getAccountBalance()) {
                    addText("You don't have enough money!");
                    amountTextField.setStyle("-fx-background-color: red;");
                    return;
                }
                if (chooseDepositNumber == 0) {
                    if (amountIns <= 10000 && amountIns > 0) {
                        numberOfMounts = 3;
                        interest = 0.1;
                    } else {
                        addText("Enter correct amount!");
                        amountTextField.setStyle("-fx-background-color: red;");
                        goood = false;
                    }
                } else if (chooseDepositNumber == 1) {
                    if (amountIns <= 20000 && amountIns > 10000) {
                        numberOfMounts = 6;
                        interest = 0.05;
                    } else {
                        addText("Enter correct amount!");
                        amountTextField.setStyle("-fx-background-color: red;");
                        goood = false;
                    }
                } else if (chooseDepositNumber == 2) {
                    if (amountIns <= 100000 && amountIns > 20000) {
                        numberOfMounts = 12;
                        interest = 0.05;
                    } else {
                        addText("Enter correct amount!");
                        amountTextField.setStyle("-fx-background-color: red;");
                        goood = false;
                    }
                } else if (chooseDepositNumber == 3) {
                    if (amountIns <= 1000000 && amountIns > 100000) {
                        numberOfMounts = 60;
                        interest = 0.02;
                    } else {
                        addText("Enter correct amount!");
                        amountTextField.setStyle("-fx-background-color: red;");
                        goood = false;
                    }
                } else if (chooseDepositNumber == 4) {
                    if (amountIns <= 10000000 && amountIns > 1000000) {
                        numberOfMounts = 120;
                        interest = 0.01;
                    } else {
                        addText("Enter correct amount!");
                        amountTextField.setStyle("-fx-background-color: red;");
                        goood = false;
                    }
                }
            } catch (Exception e) {
                addText("We accept only full amounts!");
                amountTextField.setStyle("-fx-background-color: red;");
                goood = false;
            }
            if (goood) {
                amount = amountIns;
                chooseDeposit = chooseDepositNumber;
                execute(event);
            }
        }else {
            addText("Choose loan!");
            deposits.setStyle("-fx-background-color: red;");
            goood = false;
        }

    }

    public void setText(String text){
        mainTextArea.setText(text);
    }

    public void setTextError(String text){errorTextArea.setText(text);}

    public void addText(String newText){
        String text = errorTextArea.getText();
        errorTextArea.setText(text + newText + "\n");
    }

    public int lookingForAnswerId(String answer){
        for(int i = 0; i < depositsToChoose.length; i++){
            if(depositsToChoose[i].equals(answer)){
                return i;
            }
        }
        return -1;
    }

    public void execute(ActionEvent event) throws IOException{
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("confirmDepositWindow.fxml"));
        root = loader.load();
        confirmDepositSceneController confirmDeposit = loader.getController();
        confirmDeposit.getInformation(numberOfMounts, amount, interest, chooseDeposit);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
