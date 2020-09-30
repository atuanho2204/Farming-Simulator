package main.welcomeScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import main.configurationScreen.ConfigSceneController;

import java.io.IOException;
import java.util.ArrayList;

public class WelcomeSceneController {
    private Stage primaryStage;

    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void handleStartButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../configurationScreen/configScene.fxml"
                )
        );
        Parent parent = loader.load();
        ConfigSceneController controller = loader.getController();
        controller.construct(1, "", new ArrayList<>(), "");

        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(new Scene(parent));
        /*
        try {
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("ConfigScreen not found");
            System.out.println(e.getCause() + e.getMessage());
            // show the dialog
            a.show();
        }
        */

    }

    public void handleQuitButton(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}





