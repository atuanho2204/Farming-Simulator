package farm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.util.ResourceBundle;


public class Controller {

    @FXML
    private Button closeButton;

    @FXML
    public void closeAction(ActionEvent actionEvent) {
        closeButton.setText("Quitting");
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }
}