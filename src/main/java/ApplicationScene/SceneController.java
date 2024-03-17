package ApplicationScene;

import Logic.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class SceneController {
    private static Stage stage;
    private static Scene scene;
    private Parent root;

    public static Client current_client;

    public static boolean dartTheme = false;

    public static void switchToWindow(String windowName, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(SceneController.class.getResource(windowName));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(dartTheme){
            scene.getStylesheets().add(SceneController.class.getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void setCurrent_client(Client client){
        current_client = client;
    }

    public static Client getCurrent_client(){
        return current_client;
    }
}
