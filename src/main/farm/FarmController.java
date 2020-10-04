package main.farm;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayListener;
import main.gameManager.NewDayEvent;
import main.market.MarketUIController;
import main.util.crops.Crop;
import main.util.crops.CropStage;
import main.util.crops.CropTypes;

import java.util.ArrayList;

/**
 * The Controller for the FarmUI fxml screen
 */
public class FarmController implements NewDayListener {
    private Stage primaryStage;
    private GameManager gameManager;
    private final int numOfPlots = 12;
    private ArrayList<Plot> plots = new ArrayList<>(numOfPlots);
    private ArrayList<Button> uiPlots = new ArrayList<>(numOfPlots);


    @FXML
    private Pane marketHolder;
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
     * @param primaryStage ...
     * @param gameManager ...
     */
    public void construct(Stage primaryStage, GameManager gameManager) {
        this.primaryStage = primaryStage;
        this.gameManager = gameManager;

        populatePlotsRandomly();
        setHeaderData();
        gameManager.setMoney(10 * gameManager.getDifficulty());
        gameManager.getTimeAdvancer().addListener(this);
        gameManager.getTimeAdvancer().startTime();

        marketHolder.getChildren().add(new Pane(getMarketUI()));
    }

    public void handleNewDay(NewDayEvent e) {
        setHeaderData();
    }

    private Parent getMarketUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../market/marketUI.fxml"
                    )
            );
            Parent parent = loader.load();
            MarketUIController controller = loader.getController();
            controller.construct(primaryStage, gameManager);
            return parent;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return null;
        }
    }
    private Parent getInventoryUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../market/marketUI.fxml"
                    )
            );
            Parent parent = loader.load();
            MarketUIController controller = loader.getController();
            controller.construct(primaryStage, gameManager);
            return parent;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return null;
        }
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
        initializeUIPlots();
        int numOfSeedTypes = gameManager.getSeeds().size();
        int numOfStages = CropStage.values().length;
        for (int i = 0; i < numOfPlots; ++i) {
            int randomCrop = (int) (Math.random() * 100) % numOfSeedTypes;
            int randomStage = (int) (Math.random() * 100) % numOfStages;
            String seed = gameManager.getSeeds().get(randomCrop).toUpperCase();
            //uiPlots.get(i).setPrefSize(20,20);
            /*uiPlots.get(i).setGraphic(
                    new ImageView(new Image("main/images/Untitled_Artwork.jpg")));*/
            plots.add(new Plot(
                    new Crop(CropTypes.valueOf(seed), CropStage.values()[randomStage]),
                    uiPlots.get(i)));
            plots.get(i).getPlotButton().setText(plots.get(i).getCurrentCrop().getType().toString()
                    + "\n" + plots.get(i).getCurrentCrop().getStage().toString());
        }
    }

    private void initializeUIPlots() {
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
    }

    private void harvestPlot(Plot plot) {
        if (plot.getCurrentCrop().getStage() == CropStage.DEAD) {
            plot.getPlotButton().setText("");
        }
        if (plot.getCurrentCrop().getStage() == CropStage.MATURE) {
            try {
                gameManager.getInventory().putProduct(plot.getCurrentCrop().getType());
                plot.getPlotButton().setText("");
            } catch (Exception e) {
                getAlert("Storage is full!!!");
            }
        }
    }

    private void getAlert(String message) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText(message);
        // show the dialog
        a.show();
    }

    @FXML
    public void harvest00() {

    }

    @FXML
    public void harvest01() {
        harvestPlot(plots.get(1));
    }

    @FXML
    public void harvest02() {
        harvestPlot(plots.get(2));
    }

    @FXML
    public void harvest03() {
        harvestPlot(plots.get(3));
    }

    @FXML
    public void harvest10() {
        harvestPlot(plots.get(4));
    }

    @FXML
    public void harvest11() {
        harvestPlot(plots.get(5));
    }

    @FXML
    public void harvest12() {
        harvestPlot(plots.get(6));
    }

    @FXML
    public void harvest13() {
        harvestPlot(plots.get(7));
    }

    @FXML
    public void harvest20() {
        harvestPlot(plots.get(8));
    }

    @FXML
    public void harvest21() {
        harvestPlot(plots.get(9));
    }

    @FXML
    public void harvest22() {
        harvestPlot(plots.get(10));
    }

    @FXML
    public void harvest23() {
        harvestPlot(plots.get(11));
    }

    @FXML
    public void handlePauseButton() {
        gameManager.getTimeAdvancer().pauseTime();
    }

    @FXML
    public void handleQuitButton() {
        primaryStage.close();
        gameManager.getTimeAdvancer().pauseTime();
    }
}