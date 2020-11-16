package main.farm.header;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.notifications.NotificationManager;
import main.util.AlertUser;
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
    private AudioClip backgroundMusic;

    @FXML
    private ImageView currentSeason;
    @FXML
    private Label difficultyLevel;
    @FXML
    private Label startingMoney;
    @FXML
    private Label currentDate;
    @FXML
    private Label farmerName;
    @FXML
    private Button season;
    @FXML
    private Button carrotB;
    @FXML
    private Button organicB;
    @FXML
    private Button harvestB;


    /**
     * Constructs the Header Scene.
     *
     * @param primaryStage the stage
     */
    public void construct(Stage primaryStage, AudioClip backgroundMusic) {
        this.primaryStage = primaryStage;
        this.backgroundMusic = backgroundMusic;
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
                    Image img = new Image("/main/images/"
                            + GameManager.getInstance().getSeason().toString().toLowerCase()
                            + ".jpg");
                    currentSeason.setImage(img);
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
                if (GameManager.getInstance().getBadgeBookkeeping()[0] >= 5) { // carrot badge
                    if (GameManager.getInstance().getBadgeBookkeeping()[0] == 5) {
                        NotificationManager.getInstance().addNotification(
                                "You earned the CARROTTING-FRENZY badge!");
                    }
                    carrotB.setText("");
                    Image img = new Image("/main/images/carrotB.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(40);
                    view.setFitWidth(40);
                    carrotB.setGraphic(view);
                    carrotB.setStyle("-fx-background-color: transparent;");
                }
                if (GameManager.getInstance().getBadgeBookkeeping()[1] >=5) { // organic badge
                    if (GameManager.getInstance().getBadgeBookkeeping()[1] == 5) {
                        NotificationManager.getInstance().addNotification(
                                "You earned the ORGANIC-ALIC badge!");
                    }
                    organicB.setText("");
                    Image img = new Image("/main/images/organicB.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(40);
                    view.setFitWidth(40);
                    organicB.setGraphic(view);
                    organicB.setStyle("-fx-background-color: transparent;");
                }
                if (GameManager.getInstance().getBadgeBookkeeping()[2] >=10) { // harvest badge
                    if (GameManager.getInstance().getBadgeBookkeeping()[2] == 10) {
                        NotificationManager.getInstance().addNotification(
                                "You earned the HARVEST-FRENZY badge!");
                    }
                    harvestB.setText("");
                    Image img = new Image("/main/images/harvestB.png");
                    ImageView view = new ImageView(img);
                    view.setFitHeight(40);
                    view.setFitWidth(40);
                    harvestB.setGraphic(view);
                    harvestB.setStyle("-fx-background-color: transparent;");
                }
                carrotB.setTooltip(new Tooltip("CARROTTING-FRENZY: Harvest 5 "
                        + "carrots to earn this good-looking badge!"
                        + "\nCurrent: " + GameManager.getInstance().getBadgeBookkeeping()[0]));
                organicB.setTooltip(new Tooltip("ORGANIC-ALIC: Harvest 5 crops with "
                        + "neither fertilizer nor pesticide (:"
                        + "\nCurrent: " + GameManager.getInstance().getBadgeBookkeeping()[1]));
                harvestB.setTooltip(new Tooltip("HARVEST-FRENZY: Harvest a total "
                        + "of 10 crops. Let's go!"
                        + "\nCurrent: " + GameManager.getInstance().getBadgeBookkeeping()[2]));
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void handleQuitButton() {
        primaryStage.close();
        GameManager.getInstance().getTimeAdvancer().pauseTime();
    }

    @FXML
    public void handleMuteButton() {
        try {
            if (backgroundMusic.getVolume() != 0) {
                backgroundMusic.setVolume(0);

            } else {
                backgroundMusic.setVolume(0.2);
            }
            backgroundMusic.stop();
            backgroundMusic.play();
        } catch (NullPointerException e) {
            System.out.println("backgroundMusic is null.");
        }
    }

    @FXML
    public void handleCarrotB() {
        //        System.out.println("badge 1");
    }

    @FXML
    public void handleOrganicB() {
//        System.out.println("badge 2");
    }

    @FXML
    public void handleHarvestB() {
//        System.out.println("badge 3");
    }
}