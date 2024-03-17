package ApplicationScene;


import ApplicationLogic.Is_in_DB;
import ApplicationLogic.Register;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class regP2SceneController implements Initializable {

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
    TextField phoneNumberTextField;
    @FXML
    TextField emailTextField;
    @FXML
    TextField answerTextField;
    @FXML
    Label myLabelP2;
    @FXML
    ChoiceBox<String> question;

    String[] questions = {"What is your mother's maiden name?",
            "What is the name of your first pet?",
            "What was your first car?",
            "What elementary school did you attend?",
            "What is the name of the town where you were born?"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        question.getItems().addAll(questions);
    }

    public void getInformationP(String pName, String pSurname, LocalDate date,
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
        phoneNumberTextField.setText(phoneNumber);
        emailTextField.setText(emailAddress);
        answerTextField.setText(reply);
        if(!phoneNumber.equals("")){
            phoneNumberTextField.setText(phoneNumber);
        }
        if(!emailAddress.equals("")){
            emailTextField.setText(emailAddress);
        }
        if(!reply.equals("")){
            answerTextField.setText(reply);
        }
        if(answer != -1){
            question.setValue(questions[answer]);
        }
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

    public void switchToRegP3Window(ActionEvent event) throws IOException {
        emailTextField.setStyle("");
        phoneNumberTextField.setStyle("");
        answerTextField.setStyle("");
        question.setStyle("");

        myLabelP2.setText("");
        boolean isCorrect = true;
        String phoneNumber = phoneNumberTextField.getText();
        String emailAddress = emailTextField.getText();
        String answerString = question.getValue();
        int answer = lookingForAnswerId(answerString);
        String reply = answerTextField.getText();
        if(emailAddress.equals("")){
            isCorrect = false;
            addText("Enter email address!");
            emailTextField.setStyle("-fx-background-color: red;");
        }else if(!Register.emailIsValid(emailAddress) || Is_in_DB.is_email_there(emailAddress)){
            isCorrect = false;
            addText("Entered email address has not exist or has been taken!");
            emailTextField.setStyle("-fx-background-color: red;");
        }
        if(phoneNumber.equals("")){
            isCorrect = false;
            addText("Enter phone number!");
            phoneNumberTextField.setStyle("-fx-background-color: red;");
        }else if(!Register.phonenumberIsValid(phoneNumber) || Is_in_DB.is_phone_nr_there(phoneNumber)){
            isCorrect = false;
            addText("Entered phone number has not exist or has been taken!");
            phoneNumberTextField.setStyle("-fx-background-color: red;");
        }
        if(!((answer >= 0) && (answer < 10))){
            isCorrect = false;
            addText("Choose question!");
            question.setStyle("-fx-background-color: red;");
        }
        if(reply.equals("")){
            isCorrect = false;
            addText("Reply!");
            answerTextField.setStyle("-fx-background-color: red;");
        }
        if(!isCorrect){
            return;
        }
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("regP3Window.fxml"));
        root = loader.load();
        regP3SceneController regP3SceneController = loader.getController();
        regP3SceneController.getInformationP3(name, surname, dateOfBirth,
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

    public void backToRegP1Window(ActionEvent event) throws IOException {
        phoneNumber = phoneNumberTextField.getText();
        emailAddress = emailTextField.getText();
        reply = answerTextField.getText();
        String answerString = question.getValue();
        answer = lookingForAnswerId(answerString);

        FXMLLoader loader  = new FXMLLoader(getClass().getResource("regP1Window.fxml"));
        root = loader.load();
        regP1SceneController regP1SceneController = loader.getController();
        regP1SceneController.getInformationP2(name, surname, dateOfBirth,
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
    public int lookingForAnswerId(String answer){
        for(int i = 0; i < questions.length; i++){
            if(questions[i].equals(answer)){
                return i;
            }
        }
        return -1;
    }
    public void addText(String newText){
        String currentText = myLabelP2.getText();
        myLabelP2.setText(currentText + newText + "\n");
    }
}
