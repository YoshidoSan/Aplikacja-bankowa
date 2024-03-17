package ApplicationScene;

import javafx.event.ActionEvent;

import java.io.IOException;

public class SwitchScene {

    public void switchToMainWindow(ActionEvent event) throws IOException {
        SceneController.switchToWindow("mainWindow.fxml", event);
    }

    public void switchToStartWindow(ActionEvent event) throws IOException {
        SceneController.current_client=null;
        SceneController.switchToWindow("startWindow.fxml", event);
    }

    public void switchToConverterWindow(ActionEvent event) throws IOException {
        SceneController.switchToWindow("converterWindow.fxml", event);
    }

    public void switchToDepositWindow(ActionEvent event) throws IOException {
        SceneController.switchToWindow("depositWindow.fxml", event);
    }

    public void switchToHistoryWindow(ActionEvent event) throws IOException {
        SceneController.switchToWindow("historyWindow.fxml", event);
    }

    public void switchToLoanWindow(ActionEvent event) throws IOException {
        SceneController.switchToWindow("loanWindow.fxml", event);
    }

    public void switchToMapWindow(ActionEvent event) throws IOException {
        SceneController.switchToWindow("mapWindow.fxml", event);
    }

    public void switchToSettingsWindow(ActionEvent event) throws IOException {
        SceneController.switchToWindow("settingsWindow.fxml", event);
    }

    public void switchToTransferWindow(ActionEvent event) throws IOException {
        SceneController.switchToWindow("transferWindow.fxml", event);
    }
}
