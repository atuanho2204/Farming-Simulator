package farm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class FarmController {
    private int difficulty = 1;
    private String name = "";
    private String seedType = "";
    private int season = 0;

    public void construct(int difficulty, String name, String seedType, int season) {
        this.difficulty = difficulty;
        this.name = name;
        this.seedType = seedType;
        this.season = season;
    }

    @FXML
    private Button closeButton;
    @FXML
    private Label difficultyLabel;

    private void setData() {
        closeButton.setText("");
    }

    @FXML
    public void closeAction(ActionEvent actionEvent) {
        closeButton.setText("Quitting");
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }
}