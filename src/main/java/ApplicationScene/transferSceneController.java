package ApplicationScene;

import ApplicationLogic.Is_in_DB;
import Logic.Client;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

public class transferSceneController extends SwitchScene implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String userAccNumberM = "";
    private String recNameM = "";
    private String recAccNumberM = "";
    private String titleM = "";
    private String amountM = "";
    private String exeDateM = "";

    @FXML
    Label userNameLabel;

    @FXML
    TextField userAccTextField;

    @FXML
    TextField accFundsTextField;

    @FXML
    TextField recNameTextField;

    @FXML
    TextField recAccNumTextField;

    @FXML
    TextField titleTextField;

    @FXML
    TextField amountTextField;

    @FXML
    Label myLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client client = SceneController.getCurrent_client();
        setUserName(client.getName(), client.getSurname());
        float funds = (float) SceneController.getCurrent_client().getAccountBalance()/100;
        accFundsTextField.setText("Accessible funds: " + funds + " PLN");
        userAccTextField.setText(client.getAccountNumber());
    }

    public void getInformation(String recAcc, String recName, String amountS, String titleS, String dateS){
        recAccNumberM = recAcc;
        recNameM = recName;
        amountM = amountS;
        titleM = titleS;
        exeDateM = dateS;
        setData(recAcc, recName, amountS, titleS, dateS);
    }

    public void setData(String recAcc, String recName, String amount, String title, String date){
        recAccNumTextField.setText(recAcc);
        recNameTextField.setText(recName);
        amountTextField.setText(amount);
        titleTextField.setText(title);
    }

    public void setUserName(String userName, String userSurname){
        userNameLabel.setText(userName + " " + userSurname);
    }

    public void addText(String newText){
        String text = myLabel.getText();
        myLabel.setText(text + newText + "\n");
    }

    public void makeTransfer(ActionEvent event) throws IOException{
        Client client = SceneController.getCurrent_client();
        myLabel.setText("");
        String recName = recNameTextField.getText();
        String recAccNumber = recAccNumTextField.getText();
        String title = titleTextField.getText();
        String amount = amountTextField.getText();
        String  exeDate = String.valueOf(NOW_LOCAL_DATE());
        amountTextField.setStyle("");
        recAccNumTextField.setStyle("");
        titleTextField.setStyle("");
        recNameTextField.setStyle("");
        accFundsTextField.setStyle("");

        boolean isGood = true;
        if(client.getAccountBalance() <= 0){
            addText("No money!");
            accFundsTextField.setStyle("-fx-background-color: red;");
            isGood = false;
        }
        if(recName.equals("")){
            recNameTextField.setStyle("-fx-background-color: red;");
            addText("Enter receiver name!");
            isGood = false;
        }
        try{
            if(recAccNumber.equals("")) {
                recAccNumTextField.setStyle("-fx-background-color: red;");
                addText("Enter receiver account number!");
                isGood = false;
            }else if (Integer.parseInt(recAccNumber) <= 0){
                recAccNumTextField.setStyle("-fx-background-color: red;");
                addText("Enter receiver account number should be positive!");
                isGood = false;
            } else if(!Is_in_DB.is_acc_nr_there(recAccNumber)) {
                recAccNumTextField.setStyle("-fx-background-color: red;");
                addText("Enter receiver account number in our bank!");
                isGood = false;
            }
        }catch (Exception e){
            addText("Receiver account number should be number!");
            recAccNumTextField.setStyle("-fx-background-color: red;");
            isGood = false;
        }
        if(title.equals("")){
            titleTextField.setStyle("-fx-background-color: red;");
            addText("Enter title!");
            isGood = false;
        }
        try {
            if (amount.equals("")) {
                amountTextField.setStyle("-fx-background-color: red;");
                addText("Enter amount!");
                isGood = false;
            } else {
                double amountS = Double.parseDouble(amount);
                if (amountS <= 0) {
                    amountTextField.setStyle("-fx-background-color: red;");
                    addText("Amount should be positive!");
                    isGood = false;
                }
            }
        }catch(Exception e){
            addText("Amount should be a number!");
            amountTextField.setStyle("-fx-background-color: red;");
            isGood = false;
        }
        if(!isGood){
            return;
        }

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("confirmTransferWindow.fxml"));
        root = loader.load();
        confirmTransferSceneController confirmTransfer = loader.getController();
        confirmTransfer.getInformation(client.getAccountNumber(), recAccNumber, recName, amount, title, exeDate);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static final LocalDate NOW_LOCAL_DATE (){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }
}
