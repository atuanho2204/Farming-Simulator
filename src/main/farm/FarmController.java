package main.farm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class FarmController {
    private Stage stage;
    private int difficulty = 1;
    private String name = "";
    private List<String> seeds = new ArrayList<>(0);
    private String season = "";
    private int day = 0;

    public void construct(int difficulty, String name, List<String> seeds, String season, int day) {
        this.difficulty = difficulty;
        this.name = name;
        this.seeds = seeds;
        this.season = season;
        this.day = day;
    }

    @FXML
    private Button closeButton;
    @FXML
    private Label Low;
    @FXML
    private Label Medium;
    @FXML
    private Label High;

    private void setData() {
        closeButton.setText("");
    }

    @FXML
    public void closeAction(ActionEvent actionEvent) {
        closeButton.setText("Quitting");
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handlePauseButton(ActionEvent event) {
    }

    @FXML
    public void handleQuitButton(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}