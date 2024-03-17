package ApplicationScene;

import Logic.Client;
import Logic.Database_CL;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class settingsSceneController extends SwitchScene implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label userNameLabel;

    @FXML
    TextArea mainTextArea;

    @FXML
    TextField oldPasswordArea;

    @FXML
    TextField newPasswordArea;

    @FXML
    TextField repNewPasswordArea;

    @FXML
    Button confirmButton;

    @FXML
    Button themeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client client = SceneController.getCurrent_client();
        setUserName(client.getName(), client.getSurname());
    }

    public void setUserName(String userName, String userSurname){
        userNameLabel.setText(userName + " " + userSurname);
    }

    public void changePassword(){
        mainTextArea.setText("");
        String oldPassword = oldPasswordArea.getText();
        String newPassword = newPasswordArea.getText();
        String repNewPassword = repNewPasswordArea.getText();
        oldPasswordArea.setStyle("");
        newPasswordArea.setStyle("");
        repNewPasswordArea.setStyle("");
        boolean goood = true;
        if(oldPassword.equals("")){
            addText("Enter old password!");
            oldPasswordArea.setStyle("-fx-background-color: red;");
            goood = false;
        }
        if(newPassword.equals("")){
            addText("Enter new password!");
            newPasswordArea.setStyle("-fx-background-color: red;");
            goood = false;
        }
        if(repNewPassword.equals("")){
            addText("Enter repeated new password!");
            repNewPasswordArea.setStyle("-fx-background-color: red;");
            goood = false;
        }
        if(!goood){
            return;
        }
        if(!newPassword.equals(repNewPassword)){
            setNewText("Password not equals repeated password");
            repNewPasswordArea.setStyle("-fx-background-color: red;");
            newPasswordArea.setStyle("-fx-background-color: red;");
            return;
        }
        boolean changed = Database_CL.changePass(SceneController.getCurrent_client().getAccountNumber(), oldPassword, newPassword);
        if (!changed){
            setNewText("Wrong old password!");
            oldPasswordArea.setStyle("-fx-background-color: red;");
            return;
        }
        setNewText("Your password has been changed");
        oldPasswordArea.setText("");
        newPasswordArea.setText("");
        repNewPasswordArea.setText("");
    }
    public void setNewText(String text){
        mainTextArea.setText(text);
    }

    public void addText(String newText){
        String text = mainTextArea.getText();
        mainTextArea.setText(text + "\n" + newText);
    }

    public void setDarkTheme(ActionEvent event) throws IOException{
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("settingsWindow.fxml"));
        root = loader.load();
        scene = new Scene(root);
        settingsSceneController settingsSceneController = loader.getController();
        if(!SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
            SceneController.dartTheme = true;
        }else{
            SceneController.dartTheme = false;
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
