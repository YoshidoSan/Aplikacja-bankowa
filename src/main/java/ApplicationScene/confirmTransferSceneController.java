package ApplicationScene;

import Logic.Client;
import Logic.Database_CL;
import Logic.Transfer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;


public class confirmTransferSceneController extends SwitchScene{
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String userAccNumber = "";
    private String recAccNumber = "";
    private String reciverName = "";
    private String amount = "";
    private String title = "";
    private String date = "";

    @FXML
    TextField userAccTextField;

    @FXML
    TextField recAccNumTextField;

    @FXML
    TextField recNameTextField;

    @FXML
    TextField amountTextField;

    @FXML
    TextField titleTextField;

    @FXML
    TextField exeDateTextFiled;

    public void getInformation(String userAcc, String recAcc, String recName, String amountS, String titleS, String dateS){
        userAccNumber = userAcc;
        recAccNumber = recAcc;
        reciverName = recName;
        amount = amountS;
        title = titleS;
        date = dateS;
        setData(userAcc, recAcc, recName, amountS, titleS, dateS);
    }

    public void setData(String userAcc, String recAcc, String recName, String amount, String title, String date){
        userAccTextField.setText(userAcc);
        recAccNumTextField.setText(recAcc);
        recNameTextField.setText(recName);
        amountTextField.setText(amount);
        titleTextField.setText(title);
        exeDateTextFiled.setText(date);
    }

    public void back(ActionEvent event) throws IOException{
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("transferWindow.fxml"));
        root = loader.load();
        transferSceneController transfer = loader.getController();
        transfer.getInformation(recAccNumber, reciverName, amount, title, date);
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
        Client targclient = Database_CL.getClient(recAccNumber);
        Transfer trans = new Transfer(targclient,(int) (100*Double.valueOf(amount)),title);
        trans.transferMoney(SceneController.getCurrent_client());
        Database_CL.updateHistory(targclient);
        Database_CL.updateHistory(SceneController.getCurrent_client());
        Database_CL.updateBalance(targclient);
        Database_CL.updateBalance(SceneController.getCurrent_client());
        switchToMainWindow(event);

    }
}
