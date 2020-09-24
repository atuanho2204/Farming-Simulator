package main.welcomeScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import main.configurationScreen.ConfigSceneController;

public class WelcomeSceneController {

    public void construct() {
        //does nothing right now
    }

    @FXML
    public void handleStartButton(ActionEvent event) throws Exception {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../configurationScreen/configScene.fxml"
                    )
            );
            Parent parent = loader.load();
            ConfigSceneController controller = loader.getController();
            controller.construct();

            primaryStage.setTitle("Welcome");
            primaryStage.setScene(new Scene(parent));
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("ConfigScreen not found");
            // show the dialog
            a.show();
        }

        //primaryStage.show();
    }

    private void loadNextScene(Stage stage) {


    }

    public void handleQuitButton(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}





