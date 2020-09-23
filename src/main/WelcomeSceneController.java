package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class WelcomeSceneController extends App {
    private int sceneWidth = 1200;
    private int sceneHeight = 800;

    @FXML
    public void handleStartButton(ActionEvent event) throws Exception {
        Parent configPane = FXMLLoader.load(getClass().getResource("configScene.fxml"));
        Scene configScene = new Scene(configPane, sceneWidth, sceneHeight);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(configScene);
        primaryStage.show();
    }

    public void handleQuitButton(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}





