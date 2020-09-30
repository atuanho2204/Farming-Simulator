package main.farm;

import com.sun.javafx.geom.Vec2d;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayListener;
import main.gameManager.NewDayEvent;
import main.util.Crop;
import main.util.CropStage;
import main.util.CropTypes;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * The Controller for the FarmUI fxml screen
 */
public class FarmController implements NewDayListener {
    private Stage primaryStage;
    private GameManager gameManager;
    private final int NUM_OF_PLOTS = 12;
    //plots will be initialized with random crop at construct
    private ArrayList<Plot> plots = new ArrayList<>(NUM_OF_PLOTS);
    private ArrayList<Button> uiPlots = new ArrayList<>(NUM_OF_PLOTS);


    @FXML
    private Label difficultyLevel;
    @FXML
    private Label startingMoney;
    @FXML
    private Label currentDate;
    @FXML
    private Button plot00;
    @FXML
    private Button plot01;
    @FXML
    private Button plot02;
    @FXML
    private Button plot03;
    @FXML
    private Button plot10;
    @FXML
    private Button plot11;
    @FXML
    private Button plot12;
    @FXML
    private Button plot13;
    @FXML
    private Button plot20;
    @FXML
    private Button plot21;
    @FXML
    private Button plot22;
    @FXML
    private Button plot23;


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
            System.out.println(e.getMessage());
        }
    }

    private void populatePlotsRandomly() {
        //replace this
        int numOfSeedTypes = gameManager.getSeeds().size();
        for (int i = 0; i < NUM_OF_PLOTS; ++i) {
            int randomCrop = (int) (Math.random() * 10) % numOfSeedTypes;
            int randomStage = (int) (Math.random() * 10) % CropStage.values().length;
            String seed = gameManager.getSeeds().get(randomCrop).toUpperCase();
            plots.add(new Plot(new Crop(CropTypes.valueOf(seed), CropStage.values()[randomStage])));

            //plots.add(new Plot(new Vec2d(0,0), new Crop(CropTypes.valueOf(seed))));
        }
        showPlots();
    }

    @FXML
    private void showPlots() {
        uiPlots.add(plot00);
        uiPlots.add(plot01);
        uiPlots.add(plot02);
        uiPlots.add(plot03);
        uiPlots.add(plot10);
        uiPlots.add(plot11);
        uiPlots.add(plot12);
        uiPlots.add(plot13);
        uiPlots.add(plot20);
        uiPlots.add(plot21);
        uiPlots.add(plot22);
        uiPlots.add(plot23);
        for (int i = 0; i < NUM_OF_PLOTS; ++i) {
            //uiPlots.get(i).setPrefSize(20,20);
            //uiPlots.get(i).setGraphic(new ImageView(new Image("main/images/Untitled_Artwork.jpg")));
            uiPlots.get(i).setText(plots.get(i).getCurrentCrop().getType().toString()
                    + "\n" + plots.get(i).getCurrentCrop().getStage().toString());
        }
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