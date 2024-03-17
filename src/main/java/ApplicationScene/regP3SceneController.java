package ApplicationScene;

import ApplicationLogic.Register;
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
import java.time.LocalDate;

import Logic.*;

public class regP3SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String emailAddress;
    private String clientNumber;
    private String password;
    private String repeatPassword;
    private int answer;
    private String reply;

    @FXML
    TextField passwordTextField;
    @FXML
    TextField passwordTextField2;
    @FXML
    Label myLabelP3;

    public void getInformationP3(String pName, String pSurname, LocalDate date,
                                 String pPhoneNumber, String pEmailAddress, int pAnswer, String pReply,
                                 String pClientNumber, String pPassword, String pRepeatPassword){
        name = pName;
        surname = pSurname;
        dateOfBirth = date;
        phoneNumber = pPhoneNumber;
        emailAddress = pEmailAddress;
        answer = pAnswer;
        reply = pReply;
        clientNumber = pClientNumber;
        password = pPassword;
        repeatPassword = pRepeatPassword;
        passwordTextField.setText(password);
        passwordTextField2.setText(repeatPassword);
        String text = "Your data:" + "\nClient number: " + clientNumber + "\nName: " + name + "\nSurname: " + surname +
                "\nDate of birth: " + date +
                "\nPhone number: " + phoneNumber + "\nEmail address: " + emailAddress;
        myLabelP3.setText(text);
    }

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

    public void backToRegP2Window(ActionEvent event) throws IOException {
        password = passwordTextField.getText();
        repeatPassword = passwordTextField2.getText();
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("regP2Window.fxml"));
        root = loader.load();
        regP2SceneController regP2SceneController = loader.getController();
        regP2SceneController.getInformationP(name, surname, dateOfBirth,
                phoneNumber, emailAddress, answer, reply, clientNumber, password, repeatPassword);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void Create(ActionEvent event) throws IOException {
        passwordTextField.setStyle("");
        passwordTextField2.setStyle("");
        myLabelP3.setText("");
        boolean isCorrect = true;
        password = passwordTextField.getText();
        repeatPassword = passwordTextField2.getText();

        if(password.equals("")){
            isCorrect = false;
            addText("Enter password!");
            passwordTextField.setStyle("-fx-background-color: red;");
        }
        if(repeatPassword.equals("")){
            isCorrect = false;
            addText("Enter repeated password!");
            passwordTextField2.setStyle("-fx-background-color: red;");
        }
        if(!password.equals(repeatPassword)){
            isCorrect = false;
            addText("Entered and repeated password not matched!");
            passwordTextField.setStyle("-fx-background-color: red;");
            passwordTextField2.setStyle("-fx-background-color: red;");
        }
        if(!isCorrect){
            return;
        }
        Register.register(String.valueOf(dateOfBirth.getDayOfMonth()), String.valueOf(dateOfBirth.getMonthValue()),
                String.valueOf(dateOfBirth.getYear()), phoneNumber, emailAddress,
                password, repeatPassword, name, surname, answer, reply, clientNumber);
        String account_nr = Database_CL.login(clientNumber, password);
        Client client = Database_CL.getClient(account_nr);
        client.setClientNumber(clientNumber);
        SceneController.setCurrent_client(client);
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
    public void addText(String newText){
        String currentText = myLabelP3.getText();
        myLabelP3.setText(currentText + newText + "\n");
    }
}
