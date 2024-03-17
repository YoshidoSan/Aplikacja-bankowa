package ApplicationScene;

import Logic.Client;
import Logic.Database_CL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class forgotSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean wasSearch = false;
    String[] questions = {
        "What is your mother's maiden name?",
        "What is the name of your first pet?",
        "What was your first car?",
        "What elementary school did you attend?",
        "What is the name of the town where you were born?"
    };

    @FXML
    TextField enteredClientNumberTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField questionTextField;
    @FXML
    TextField answerTextField;
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
        stage.show();
    }

   public void search(){
       enteredClientNumberTextField.setStyle("");
       answerTextField.setStyle("");
        String clientNumber = enteredClientNumberTextField.getText();
        if(clientNumber.equals("")){
            myLabel.setText("Enter client number!");
            enteredClientNumberTextField.setStyle("-fx-background-color: red;");
            return;
        }
        int question = Database_CL.getQuestion(clientNumber);
        if (question == -1){
            myLabel.setText("Enter valid client number!");
            enteredClientNumberTextField.setStyle("-fx-background-color: red;");
            return;
        }
        if (question==-2) {
            myLabel.setText("Connection failed. Please try again");
        }
        questionTextField.setText(questions[question]);
       wasSearch = true;
    }
    public void retrievePassword(){
        enteredClientNumberTextField.setStyle("");
        answerTextField.setStyle("");
        if(wasSearch) {
            String clientNumber = enteredClientNumberTextField.getText();
            String answer = answerTextField.getText();
            if(answer.equals("")){
                myLabel.setText("Enter answer!");
                answerTextField.setStyle("-fx-background-color: red;");
                return;
            }
            try {
                String[] pass_account = Database_CL.forgotPass(clientNumber, answer);
            passwordTextField.setText(pass_account[0]);
            Client client = Database_CL.getClient(pass_account[1]);
            client.setClientNumber(clientNumber);
            SceneController.setCurrent_client(client);
            }catch (Exception e){
                myLabel.setText("Wrong answer!");
                answerTextField.setStyle("-fx-background-color: red;");
            }
        }else{
            myLabel.setText("You need to correct search first!");
        }
    }
}