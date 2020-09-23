package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigSceneController {

    @FXML
    private Button continueButton;

    @FXML
    private Button quitButton;

    @FXML
    private TextField playerName;

    public void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = FXMLLoader.load(getClass().getResource("configScene.fxml"));
        try {
            if (event.getSource() == continueButton && !playerName.getText().isEmpty()) {
                stage = (Stage) continueButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("testscene.fxml"));
            } else if (event.getSource() == quitButton) {
                stage = (Stage) quitButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("testscene.fxml"));
            }


            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("put your name");
        }
    }
}
