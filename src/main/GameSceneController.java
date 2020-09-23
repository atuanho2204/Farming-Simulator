package main;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class GameSceneController {
    private int sceneWidth = 1200;
    private int sceneHeight = 800;

    public void handlePauseButton(ActionEvent event) {
    }

    public void handleQuitButton(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}
