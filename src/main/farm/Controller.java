package farm;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class Controller {

    @FXML
    private Button closeButton;

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }

}