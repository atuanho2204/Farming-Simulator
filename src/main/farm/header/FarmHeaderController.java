package main.farm.header;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.util.UIManager;
import main.util.customEvents.ForceUIUpdate;
import main.util.customEvents.ForceUIUpdateListener;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;


/**
 * The Controller for the employmentUI fxml screen
 */
public class FarmHeaderController implements NewDayListener, ForceUIUpdateListener {
    private Stage primaryStage;

    @FXML
    private Label currentSeason;
    @FXML
    private Label difficultyLevel;
    @FXML
    private Label startingMoney;
    @FXML
    private Label currentDate;
    @FXML
    private Label farmerName;


    /**
     * Constructs the Header Scene.
     *
     * @param primaryStage the stage
     */
    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //listen to the time
        GameManager.getInstance().getTimeAdvancer().addListener(this);
        UIManager.getInstance().addListener(this);
        setHeaderData();
    }

    @Override
    public void handleForcedUIUpdate(ForceUIUpdate forcedUIUpdate) {
        setHeaderData();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        setHeaderData();
    }

    private void setHeaderData() {
        try {
            Platform.runLater(() -> {
                if (farmerName != null) {
                    farmerName.setText("Name: " + GameManager.getInstance().getName());
                }
                if (currentSeason != null) {
                    currentSeason.setText("Season: "
                            + GameManager.getInstance().getSeason().toString().toLowerCase());
                }
                if (currentDate != null) {
                    currentDate.setText("Day: " + GameManager.getInstance().getDay());
                }
                if (startingMoney != null) {
                    startingMoney.setText("Money: " + GameManager.getInstance().getMoney());
                }
                if (difficultyLevel != null) {
                    difficultyLevel.setText("Level: " + GameManager.getInstance().getDifficulty());
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void handlePauseButton() {
        GameManager.getInstance().getTimeAdvancer().pauseTime();
    }

    @FXML
    public void handleQuitButton() {
        primaryStage.close();
        GameManager.getInstance().getTimeAdvancer().pauseTime();
    }
}