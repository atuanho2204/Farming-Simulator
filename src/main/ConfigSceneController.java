
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

    @FXML
    private Button continueButton;

    @FXML
    private Button quitButton;

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


    public void handleContinueButton(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = FXMLLoader.load(getClass().getResource("configScene.fxml"));
        try {
            boolean seasonCheck = seasonGroup.getSelectedToggle().isSelected();
            boolean seedCheck = wheat.isSelected()
                    || corn.isSelected() || cotton.isSelected() || lettuce.isSelected();
            if (event.getSource() == continueButton && !playerName.getText().isEmpty()
                    && seasonCheck && seedCheck) {
                stage = (Stage) continueButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
            } else if (event.getSource() == quitButton) {
                stage = (Stage) quitButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            getNameAlert();
        }
    }

    private void getNameAlert() {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("You need to enter your name and pick season and seeds before continue!");
        // show the dialog
        a.show();
    }

    public void handleConfigQuitButton(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    public void getSeason() throws Exception {
        RadioButton selectedRadioButton = (RadioButton) seasonGroup.getSelectedToggle();
        String value = selectedRadioButton.getText();
        //System.out.println(value);
    }
    /*
    @FXML
    public void getSeed() throws Exception {
        if (wheat.isSelected()) {
            //System.out.println(wheat.getText());
        }
        if (corn.isSelected()) {
            //System.out.println(corn.getText());
        }
        if (cotton.isSelected()) {
            //System.out.println(cotton.getText());
        }
        if (lettuce.isSelected()) {
            //System.out.println(lettuce.getText());
        }
    }*/
}