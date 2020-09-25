package main.farm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


/**
 * The Controller for the FarmUI fxml screen
 */
public class FarmController {
    private Stage stage;
    private Integer difficulty = 1;
    private String name = "";
    private List<String> seeds = new ArrayList<>(0);
    private String season = "";
    private Integer day = 0;
    private Integer money = 0;

    /**
     * Constructs the Farm Scene.
     *
     * @param difficulty The difficulty of the game
     * @param name       the name of the player
     * @param seeds      the list of seeds
     * @param season     the season name
     * @param day        the current day
     */
    public void construct(
            Integer difficulty, String name, List<String> seeds, String season, Integer day) {
        this.difficulty = difficulty;
        this.name = name;
        this.seeds = seeds;
        this.season = season;
        this.day = day;
        this.money = difficulty * 10;

        difficultyLevel.setText("Name: " + name);
        currentDate.setText("Day: " + day.toString());
        startingMoney.setText("Money: " + money.toString());
    }

    @FXML
    private Button quitButtonGS;
    @FXML
    private Label difficultyLevel;
    @FXML
    private Label startingMoney;
    @FXML
    private Label currentDate;


    private void setData() {
        quitButtonGS.setText("");
    }

    @FXML
    public void closeAction(ActionEvent actionEvent) {
        quitButtonGS.setText("Quitting");
        Stage stage = (Stage) quitButtonGS.getScene().getWindow();
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