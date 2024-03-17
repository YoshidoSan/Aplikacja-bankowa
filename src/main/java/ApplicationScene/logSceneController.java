package ApplicationScene;

import ApplicationLogic.Is_in_DB;
import Logic.Client;
import Logic.Database_CL;
import Logic.Json_Parser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class logSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField loginTextFiled;
    @FXML
    TextField passwordTextFiled;
    @FXML
    Label myLabel;

    public void switchToStartWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("startWindow.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchToForgotPassword(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("forgotPasswordWindow.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void InLogInWindow(ActionEvent event) throws  IOException {
        loginTextFiled.setStyle("");
        passwordTextFiled.setStyle("");
        String clientNumber = loginTextFiled.getText();
        String password1 = passwordTextFiled.getText();
        myLabel.setText("");
        boolean is_correct = true;

        if(clientNumber.equals("")){
            addText("Please enter username!\n");
            loginTextFiled.setStyle("-fx-background-color: red;");
            is_correct = false;
        }
        else if(!Is_in_DB.is_cl_nr_there(clientNumber)){
            addText("Please enter correct username!\n");
            loginTextFiled.setStyle("-fx-background-color: red;");
            is_correct = false;
        }
        if(password1.equals("")){
            addText("Please enter password!\n");
            passwordTextFiled.setStyle("-fx-background-color: red;");
            is_correct = false;
        }
        if(!is_correct){
            return;
        }
        try {
            String account_nr = Database_CL.login(clientNumber, password1);
            if (account_nr.equals("xd")){
                addText("Wrong password\n");
                passwordTextFiled.setStyle("-fx-background-color: red;");
                return;
            }
            if (account_nr.equals("")){
                addText("Check internet connection\n");
                passwordTextFiled.setStyle("-fx-background-color: red;");
                return;
            }
            Client client = Database_CL.getClient(account_nr);
            client.setClientNumber(clientNumber);
            client.isInDebt =Database_CL.isInDebit(account_nr);
            client.latestDebtDate = Database_CL.LastDebitDate(account_nr);
            SceneController.setCurrent_client(client);
            client.executeDeposits();
            client.executeLoans();
            client.debtOperation();
            client.historyJSON = Json_Parser.sortHistory(client.historyJSON);
            Database_CL.updateDebit(client);
            Database_CL.updateBalance(client);
            Database_CL.updateHistory(client);
            Database_CL.updateLoans(client);
            Database_CL.updateDeposits(client);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void addText(String text){
        myLabel.setText(myLabel.getText() +text);
    }
}