package main.farm;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayListener;
import main.gameManager.NewDayEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * The Controller for the FarmUI fxml screen
 */
public class FarmController implements NewDayListener, Initializable {
    private Stage stage;
    private Integer difficulty = 1;
    private String name = "";
    private List<String> seeds = new ArrayList<>(0);
    private String season = "";
    private Integer day = 0;
    private Integer money = 0;
    private GameManager gameManager;

    @FXML
    private Label difficultyLevel;
    @FXML
    private Label startingMoney;
    @FXML
    private Label currentDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Constructs the Farm Scene.
     *
     * @param difficulty The difficulty of the game
     * @param name       the name of the player
     * @param seeds      the list of seeds
     * @param season     the season name
     * @param day        the current day
     */
    public void construct(Integer difficulty, String name,
                          List<String> seeds, String season, Integer day) {
        this.difficulty = difficulty;
        this.name = name;
        this.seeds = seeds;
        this.season = season;
        this.day = day;
        this.money = difficulty * 10;

        setHeaderData();
        gameManager = new GameManager(day);
        gameManager.setListener(this);
        gameManager.startTime();

    }

    public void handleNewDay(NewDayEvent e) {
        this.day = e.getNewDay();
        setHeaderData();
    }

    /**
     * Gets the season of the farm
     *
     * @return the String for seasons name
     */
    public String getSeason() {
        return this.season;
    }
    /**
     * Gets the name of the farmer.
     *
     * @return the String for farmers name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Gets the money of the farm.
     *
     * @return the int with the money
     */
    public Integer getMoney() {
        return this.money;
    }
    /**
     * Gets the day of the farm.
     *
     * @return an int with the current day
     */
    public Integer getDay() {
        return this.day;
    }

    /**
     * Gets the difficulty of the farm.
     *
     * @return the int with the difficulty
     */
    public Integer getDifficulty() {
        return this.difficulty;
    }

    private void setHeaderData() {
        try {
            Platform.runLater(() -> difficultyLevel.setText("Name: " + name));
            Platform.runLater(() -> currentDate.setText("Day: " + day.toString()));
            Platform.runLater(() -> startingMoney.setText("Money: " + money.toString()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void handlePauseButton(ActionEvent event) {
        gameManager.pauseTime();
    }

    @FXML
    public void handleQuitButton(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        gameManager.pauseTime();
    }
}