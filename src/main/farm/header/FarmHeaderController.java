package main.farm.header;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
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
    private Button carrotB;
    @FXML
    private Button organicB;
    @FXML
    private Button harvestB;


    /**
     * Constructs the Header Scene.
     *
     * @param primaryStage the stage
     * @param backgroundMusic current background music for farm scene
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
                    int level =  GameManager.getInstance().getDifficulty();
                    if (level == 1) {
                        difficultyLevel.setText("Level: Easy");
                    } else if (level == 2) {
                        difficultyLevel.setText("Level: Medium");
                    } else {
                        difficultyLevel.setText("Level: Hard");
                    }
                }
                // badges
                if (GameManager.getInstance().getBadgeBookkeeping()[0] >= 5) {
                    setBadge("carrotB");
                }
                if (GameManager.getInstance().getBadgeBookkeeping()[1] >= 5) {
                    setBadge("organicB");
                }
                if (GameManager.getInstance().getBadgeBookkeeping()[2] >= 10) {
                    setBadge("harvestB");
                }
                carrotB.setTooltip(new Tooltip("CARROT-FRENZY: Harvest 5 "
                        + "carrots to earn this good-looking badge!"
                        + "\nCurrent: "
                        + Math.min(GameManager.getInstance().getBadgeBookkeeping()[0], 5)));
                organicB.setTooltip(new Tooltip("ORGANIC-ALIC: Harvest 5 crops with "
                        + "neither fertilizer nor pesticide (:"
                        + "\nCurrent: "
                        + Math.min(GameManager.getInstance().getBadgeBookkeeping()[1], 5)));
                harvestB.setTooltip(new Tooltip("HARVEST-FRENZY: Harvest a total "
                        + "of 10 crops. Let's go!"
                        + "\nCurrent: "
                        + Math.min(GameManager.getInstance().getBadgeBookkeeping()[2], 10)));
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

    private void setBadge(String badge) {
        if (badge.equals("carrotB")) {
            /*NotificationManager.getInstance().addNotification(
                    "You earned the CARROT-FRENZY badge!");*/
            carrotB.setText("");
            ImageView view = new ImageView(new Image("/main/images/carrotB.png"));
            view.setFitHeight(40);
            view.setFitWidth(40);
            carrotB.setGraphic(view);
            carrotB.setStyle("-fx-background-color: transparent;");
        }
        if (badge.equals("organicB")) { // organic badge
            /*NotificationManager.getInstance().addNotification(
                    "You earned the ORGANIC-ALIC badge!");*/
            organicB.setText("");
            ImageView view = new ImageView(new Image("/main/images/organicB.png"));
            view.setFitHeight(40);
            view.setFitWidth(40);
            organicB.setGraphic(view);
            organicB.setStyle("-fx-background-color: transparent;");
        }
        if (badge.equals("harvestB")) { // harvest badge
            /*NotificationManager.getInstance().addNotification(
                    "You earned the HARVEST-FRENZY badge!");*/
            harvestB.setText("");
            ImageView view = new ImageView(new Image("/main/images/harvestB.png"));
            view.setFitHeight(40);
            view.setFitWidth(40);
            harvestB.setGraphic(view);
            harvestB.setStyle("-fx-background-color: transparent;");
        }
    }
}