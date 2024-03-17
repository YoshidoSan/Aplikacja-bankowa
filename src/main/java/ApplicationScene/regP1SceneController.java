package ApplicationScene;

import ApplicationLogic.Register;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;


import Logic.*;

public class regP1SceneController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String name = "";
    private String surname = "";
    private LocalDate dateOfBirth;
    private String phoneNumber = "";
    private String emailAddress = "";
    private String clientNumber = "";
    private String password = "";
    private String repeatPassword = "";
    private int answer = -1;
    private String reply = "";

    @FXML
    TextField nameTextField;
    @FXML
    TextField surnameTextField;
    @FXML
    Label myLabelP1;
    @FXML
    DatePicker exeDatePicker;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        exeDatePicker.setValue(NOW_LOCAL_DATE().minusYears(18));
    }

    public void getInformationP2(String pName, String pSurname, LocalDate date,
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
        nameTextField.setText(name);
        surnameTextField.setText(surname);
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

    public void switchToRegP2Window(ActionEvent event) throws IOException {
        nameTextField.setStyle("");
        surnameTextField.setStyle("");
        exeDatePicker.setStyle("");

        LocalDate dateToday = NOW_LOCAL_DATE();
        myLabelP1.setText("");
        boolean isCorrect = true;
        name = nameTextField.getText();
        surname = surnameTextField.getText();
        LocalDate date  = exeDatePicker.getValue();
        String dateS = String.valueOf(date);
        if(clientNumber.equals("")) {
            clientNumber = Creation.generateClientNr();
        }
        if(name.equals("")){
            isCorrect = false;
            nameTextField.setStyle("-fx-background-color: red;");
            addText("Enter name!");
        } else if(!Register.firstNameIsValid(name)) {
            isCorrect = false;
            nameTextField.setStyle("-fx-background-color: red;");
            addText("Enter real name!");
        }
        if(surname.equals("")){
            isCorrect = false;
            surnameTextField.setStyle("-fx-background-color: red;");
            addText("Enter surname!");
        } else if(!Register.lastNameIsValid(surname)) {
            isCorrect = false;
            surnameTextField.setStyle("-fx-background-color: red;");
            addText("Enter real surname!");
        }
            if(dateS.equals("")){
            isCorrect = false;
            exeDatePicker.setStyle("-fx-background-color: red;");
            addText("Enter date of birth!");
        }
        if(date.isAfter(dateToday.minusYears(18))){
            isCorrect = false;
            exeDatePicker.setStyle("-fx-background-color: red;");
            addText("You must have 18 years!");
        }
        if(!isCorrect){
            return;
        }
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("regP2Window.fxml"));
        root = loader.load();
        regP2SceneController regP2SceneController = loader.getController();
        regP2SceneController.getInformationP(name, surname, date,
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

    public void addText(String newText){
        String currentText = myLabelP1.getText();
        myLabelP1.setText(currentText + newText + "\n");
    }

    public static final LocalDate NOW_LOCAL_DATE (){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }
}
