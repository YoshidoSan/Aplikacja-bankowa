package ApplicationScene;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class startSceneController{

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void setDarkTheme(ActionEvent event) throws IOException{
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("startWindow.fxml"));
        root = loader.load();
        scene = new Scene(root);
        startSceneController startSceneController = loader.getController();
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

    public void switchToLogInWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginWindow.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchToRegP1Window(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("regP1Window.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void switchToMapWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("startMapWindow.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        if(SceneController.dartTheme){
            scene.getStylesheets().add(getClass().getResource("darkTheme.css").toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
