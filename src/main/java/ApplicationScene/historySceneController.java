package ApplicationScene;

import Logic.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class historySceneController extends SwitchScene implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label userNameLabel;

    @FXML
    TextArea mainTextArea;

    @FXML
    DatePicker fromPicker;

    @FXML
    DatePicker toPicker;

    @FXML
    TextField titleTextField;

    @FXML
    TextArea filtersTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client client = SceneController.getCurrent_client();
        setUserName(client.getName(), client.getSurname());
        filtersTextArea.setText("No active filters" + "\n" + "The entire history will be displayed");
        showAllHistory();
    }

    public void setUserName(String userName, String userSurname){
        userNameLabel.setText(userName + " " + userSurname);
    }

    public void showAllHistory(){
        String text = "";
        text += "All history:\n";
        filtersTextArea.setText("No active filters" + "\n" + "The entire history will be displayed");
        Client client = SceneController.getCurrent_client();
        JSONArray history = client.historyJSON;
        if(history.length() == 0){
            text += "No data!";
        } else {
            for (Object his : history) {
                JSONObject hist = (JSONObject) his;
                for (Object key : hist.keySet()) {
                    String k = (String) key;
                    Object odp = hist.get(k);
                    if (!key.equals("amount")) {
                        text += "\t" + key + " : " + odp + "\n";
                    } else {
                        int mon_odp = (int) odp;
                        double money_odp = Double.valueOf(mon_odp);
                        money_odp = money_odp / 100;
                        text += "\t" + key + " : " + money_odp + " PLN\n";
                    }
                }
                text += "\n";
            }
        }
        mainTextArea.setText(text);
    }
    public void addFilters(){
        String filters = "";
        LocalDate dateFrom = fromPicker.getValue();
        LocalDate dateTo = toPicker.getValue();
        String dateFromS = String.valueOf(dateFrom);
        String dateToS = String.valueOf(dateTo);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String title = titleTextField.getText();

        String text = "History: \n";
        Client client = SceneController.getCurrent_client();
        JSONArray history = client.historyJSON;
        ArrayList<JSONObject> filtered = new ArrayList<>();
        if(dateFromS.equals("") && dateToS.equals("") && title.equals("")){
            setNewText("Please enter filters");
            return;
        }

        filters += "Active filters:" + "\n";
        if(dateFromS.equals("null")){
            dateFrom = LocalDate.parse("01/01/0001", formatter1);
        }else{
            filters += "From: " + dateFromS + "\n";
        }
        if(dateToS.equals("null")){
            dateTo = LocalDate.parse("30/12/9999", formatter1);
        }else{
            filters += "To: " + dateToS + "\n";
        }
        if(!title.equals("")) {
            filters += "Title: " + title + " \n";
        }
        if(dateFromS.equals("null") && dateToS.equals("null") && title.equals("")){
            showAllHistory();
            return;
        }
        for(Object his : history) {
            JSONObject hist = (JSONObject) his;
            String date = (String) hist.get("date");
            LocalDate dateParsed = LocalDate.parse(date,formatter2);
            String title2 = (String) hist.get("title");
            if((title.equals("") || title.equals(title2)) && (dateToS.equals("null") || (dateParsed.isEqual(dateTo) || dateParsed.isBefore(dateTo)))
                    && (dateFromS.equals("null") || (dateParsed.isEqual(dateFrom) || dateParsed.isAfter(dateFrom)))){
                for(Object key :hist.keySet()){
                    String k = (String) key;
                    Object odp = hist.get(k);
                    if (! key.equals("amount")){
                        text += "\t"+key+" : "+odp+"\n";
                    }else{
                        int mon_odp = (int) odp;
                        double money_odp = Double.valueOf(mon_odp);
                        money_odp = money_odp/100;
                        text += "\t"+key+" : "+money_odp+" PLN\n";
                    }
                }
                text +="\n";
                filtered.add(hist);
            }
        }
        if(filtered.size() == 0){text = "No data!";}
        mainTextArea.setText(text);
        filtersTextArea.setText(filters);
    }
    public void setNewText(String text){
        mainTextArea.setText(text);
    }

    public void addText(String text){
        String oldText =  mainTextArea.getText();
        mainTextArea.setText(oldText + text);
    }
}
