package main.farm;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayListener;
import main.gameManager.NewDayEvent;

import java.util.ArrayList;
import java.util.List;


/**
 * The Controller for the FarmUI fxml screen
 */
public class FarmController implements NewDayListener {
    private Stage primaryStage;
    private GameManager gameManager;
    private ArrayList<Plot> plots; //this should be populated with random stuff at construct


    @FXML
    private Label difficultyLevel;
    @FXML
    private Label startingMoney;
    @FXML
    private Label currentDate;


    /**
     * Constructs the Farm Scene.
     */
    public void construct(Stage primaryStage, GameManager gameManager) {
        this.primaryStage = primaryStage;
        this.gameManager = gameManager;

        populatePlotsRandomly();
        setHeaderData();
        gameManager.setMoney(10 * gameManager.getDifficulty());
        gameManager.getTimeAdvancer().addListener(this);
        gameManager.getTimeAdvancer().startTime();
    }

    public void handleNewDay(NewDayEvent e) {
        setHeaderData();
    }


    private void setHeaderData() {
        try {
            Platform.runLater(() -> {
                if (difficultyLevel != null) {
                    difficultyLevel.setText("Name: " + gameManager.getName());
                }
            });
            Platform.runLater(() -> {
                if (currentDate != null) {
                    currentDate.setText("Day: " + gameManager.getDay());
                }
            });
            Platform.runLater(() -> {
                if (startingMoney != null) {
                    startingMoney.setText("Money: " + gameManager.getMoney());
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void populatePlotsRandomly() {
        //replace this
        this.plots = new ArrayList<>();
    }

    @FXML
    public void harvestPlot(ActionEvent event) {
        //make code to check the plots instance variable
    }

    @FXML
    public void handlePauseButton(ActionEvent event) {
        gameManager.getTimeAdvancer().pauseTime();
    }

    @FXML
    public void handleQuitButton(ActionEvent event) {
        primaryStage.close();
        gameManager.getTimeAdvancer().pauseTime();
    }
}