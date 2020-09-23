package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class ConfigSceneController {
    private int sceneWidth = 1200;
    private int sceneHeight = 800;

    @FXML
    private TextField playerName;
    @FXML
    private ToggleGroup seasonGroup;
    @FXML
    private CheckBox wheat;
    @FXML
    private CheckBox corn;
    @FXML
    private CheckBox cotton;
    @FXML
    private CheckBox lettuce;

    @FXML
    private void initialize() {
    }

    @FXML
    public void getSeason() {
        RadioButton selectedRadioButton = (RadioButton) seasonGroup.getSelectedToggle();
        String value = selectedRadioButton.getText();
        System.out.println(value);
    }

    @FXML
    public void getSeed() {
        if (wheat.isSelected()) {
            System.out.println(wheat.getText());
        }
        if (corn.isSelected()) {
            System.out.println(corn.getText());
        }
        if (cotton.isSelected()) {
            System.out.println(cotton.getText());
        }
        if (lettuce.isSelected()) {
            System.out.println(lettuce.getText());
        }
    }

    public void handleContinueAction(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Parent gamePane = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
            if (!playerName.getText().isEmpty()) {
                Scene gameScene = new Scene(gamePane, sceneWidth, sceneHeight);
                primaryStage.setScene(gameScene);
                primaryStage.show();
                Scene scene = new Scene(gamePane);
                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Name is required.");
        }
    }

    public void handleQuitAction(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}