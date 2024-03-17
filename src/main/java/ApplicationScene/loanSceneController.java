package ApplicationScene;

import Logic.Client;
import Logic.Loan;
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

public class loanSceneController extends SwitchScene implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private int loansNumber = -1;
    private int amountNumber = -1;

    private int amount = -1;
    private int numberOfMounts = -1;
    private double interest = -1;
    private int chooseLoan = -1;

    @FXML
    Label userNameLabel;

    @FXML
    TextArea mainTextArea;

    @FXML
    TextField amountTextField;

    @FXML
    TextArea errorTextArea;

    @FXML
    ChoiceBox<String> loans;

    String[] loansToChoose = {
            "Loan for 3 months with 10% interest, from 0 to 10.000 PLN",
            "Loan for 6 months with 5% interest, from 10.000 to 20.000 PLN",
            "Loan for 12 months with 5% interest, from 20.000 to 100.000 PLN",
            "Loan for 5 years with 2% interest, from 100.000 to 1.000.000 PLN",
            "Loan for 10 years with 1% interest, from to 1.000.000 to 10.000.000 PLN"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loans.getItems().addAll(loansToChoose);
        Client client = SceneController.getCurrent_client();
        setUserName(client.getName(), client.getSurname());
        setTextError("Choose loan and enter amount!");
        String text = "";
        text += "Current loans:\n";
        ArrayList<Loan> loans = client.loans;
        if (loans==null){text += "\nNo current loans\n";}
        else if (loans.size()==0){text += "\nNo current loans\n";}
        else{
            for (Loan loan : loans) {
                text += "\ndate: "+loan.getDate()+"\namount: "+(double)loan.getLoan_amount()/100+" PLN\ninterest: "+(loan.getInterest()*100)+"%\nmonthly payment: "+(double)loan.calculate_monthly_payment()/100+" PLN\nnumber of months: "+loan.getNumber_of_months()+"\n";
            }
        }
        setText(text);
    }

    public void getInformation(int Number , int Amount, double Interest, int chooseLoan){
        numberOfMounts = Number;
        amount = Amount;
        interest = Interest;
        setData(Amount, chooseLoan);
    }

    public void setData(int Amount, int chooseLoanNumber){
        amountTextField.setText(String.valueOf(Amount));
        loans.setValue(loansToChoose[chooseLoanNumber]);
    }

    public void setUserName(String userName, String userSurname){
        userNameLabel.setText(userName + " " + userSurname);
    }

    public void makeLoan(ActionEvent event) throws IOException{
        boolean goood = true;
        int amountIns = 0;
        String chooseLoanS = loans.getValue();
        int chooseLoanNumber = lookingForAnswerId(chooseLoanS);
        String amountS = amountTextField.getText();
        amountTextField.setStyle("");
        loans.setStyle("");


        setTextError("");

        if(chooseLoanNumber < 0 || chooseLoanNumber > 4){
            addText("Choose loan!");
            loans.setStyle("-fx-background-color: red;");
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
        if(!amountS.equals("null")){
            try{
                amountIns = Integer.parseInt(amountS);
                if(amountIns <= 0){
                    addText("Value must be positive!");
                    amountTextField.setStyle("-fx-background-color: red;");
                    return;
                }
                if(chooseLoanNumber == 0){
                    if(amountIns <= 10000 && amountIns > 0){
                        numberOfMounts = 3;
                        interest = 0.1;
                    } else{
                        addText("Enter correct amount!");
                        amountTextField.setStyle("-fx-background-color: red;");
                        goood = false;
                    }
                } else if(chooseLoanNumber == 1){
                    if(amountIns <= 20000 && amountIns > 10000){
                        numberOfMounts = 6;
                        interest = 0.05;
                    } else{
                        addText("Enter correct amount!");
                        amountTextField.setStyle("-fx-background-color: red;");
                        goood = false;
                    }
                } else if(chooseLoanNumber == 2){
                    if(amountIns <= 100000 && amountIns > 20000){
                        numberOfMounts = 12;
                        interest = 0.05;
                    } else{
                        addText("Enter correct amount!");
                        amountTextField.setStyle("-fx-background-color: red;");
                        goood = false;
                    }
                } else if(chooseLoanNumber == 3){
                    if(amountIns <= 1000000 && amountIns > 100000){
                        numberOfMounts = 60;
                        interest = 0.02;
                    } else{
                        addText("Enter correct amount!");
                        amountTextField.setStyle("-fx-background-color: red;");
                        goood = false;
                    }
                } else if(chooseLoanNumber == 4){
                    if(amountIns <= 10000000 && amountIns > 1000000){
                        numberOfMounts = 120;
                        interest = 0.01;
                    } else{
                        amountTextField.setStyle("-fx-background-color: red;");
                        addText("Enter correct amount!");
                        goood = false;
                    }
                }
            } catch (Exception e){
                addText("We accept only full amounts!");
                amountTextField.setStyle("-fx-background-color: red;");
                goood = false;
            }
            if(goood){
                amount = amountIns;
                chooseLoan = chooseLoanNumber;
                execute(event);
            }
        }else{
            addText("Choose loan!");
            loans.setStyle("-fx-background-color: red;");
            goood = false;
        }
    }

    public void execute(ActionEvent event) throws IOException{
        Loan loan = new Loan(100*amount, numberOfMounts,interest);
        double monthPayment = (double)loan.calculate_monthly_payment()/100;
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("confirmLoanWindow.fxml"));
        root = loader.load();
        confirmLoanSceneController confirmLoan = loader.getController();
        confirmLoan.getInformation(numberOfMounts, amount, interest, chooseLoan, monthPayment);
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
        String text = errorTextArea.getText();
        errorTextArea.setText(text + newText + "\n");
    }

    public void setText(String text){
        mainTextArea.setText(text);
    }

    public void setTextError(String text) {errorTextArea.setText(text);}

    public int lookingForAnswerId(String answer){
        for(int i = 0; i < loansToChoose.length; i++){
            if(loansToChoose[i].equals(answer)){
                return i;
            }
        }
        return -1;
    }
}
