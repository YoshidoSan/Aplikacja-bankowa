package ApplicationScene;

import Logic.Client;
import Logic.Database_CL;
import Logic.Deposit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class confirmDepositSceneController extends SwitchScene{
    private Stage stage;
    private Scene scene;
    private Parent root;

    private int numberOfMounts = -1;
    private double interest = -1;
    private int amount = -1;
    private int chooseLoan = -1;

    @FXML
    TextField numberTextField;
    @FXML
    TextField amountTextField;
    @FXML
    TextField interestTextField;

    public void getInformation(int Number , int Amount, double Interest, int LoanNumber){
        numberOfMounts = Number;
        amount = Amount;
        interest = Interest;
        chooseLoan = LoanNumber;
        setData(Number, Amount, Interest);
    }

    public void setData(int Number, int Amount, double Interest){
        numberTextField.setText(String.valueOf(Number));
        amountTextField.setText(String.valueOf(Amount));
        interestTextField.setText(Interest*100 + "%");
    }

    public void back(ActionEvent event) throws IOException{
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("depositWindow.fxml"));
        root = loader.load();
        depositSceneController deposit = loader.getController();
        deposit.getInformation(numberOfMounts, amount, interest, chooseLoan);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void execute(ActionEvent event) throws IOException{
        Client client = SceneController.getCurrent_client();
        Deposit deposit = new Deposit(100*amount, numberOfMounts,interest);
        deposit.makeDeposit(client);
        Database_CL.updateHistory(SceneController.getCurrent_client());
        Database_CL.updateBalance(client);
        Database_CL.updateDeposits(client);
        switchToMainWindow(event);
    }
}
