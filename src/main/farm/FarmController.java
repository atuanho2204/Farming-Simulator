package main.farm;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.farm.plot.Plot;
import main.farm.plot.PlotUI;
import main.gameManager.GameManager;
import main.util.UIManager;
import main.util.customEvents.ForceUIUpdate;
import main.util.customEvents.ForceUIUpdateListener;
import main.util.customEvents.NewDayListener;
import main.util.customEvents.NewDayEvent;
import main.inventory.Inventory;
import main.inventory.InventoryUIController;
import main.market.MarketUIController;
import main.util.crops.CropStages;
import main.util.crops.CropTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The Controller for the FarmUI fxml screen
 */
public class FarmController implements NewDayListener, ForceUIUpdateListener {
    private Stage primaryStage;
    private final int numOfPlots = 12;
    private List<Plot> plots = new ArrayList<>(numOfPlots);
    private HashMap<Plot, Integer> plotsToUIIndex = new HashMap<>();
    private TilePane plotHolder;

    @FXML
    private HBox farmPlots;
    @FXML
    private Pane marketHolder;
    @FXML
    private Pane inventoryHolder;
    @FXML
    private Label difficultyLevel;
    @FXML
    private Label startingMoney;
    @FXML
    private Label currentDate;


    /**
     * Constructs the Farm Scene.
     *
     * @param primaryStage ...
     */
    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;

        setHeaderData();
        if (GameManager.getInstance().getName().equals("Super Farmer")) {
            GameManager.getInstance().setMoney((1000));
        } else {
            GameManager.getInstance().setMoney(40 * GameManager.getInstance().getDifficulty());
        }
        //listen to forcedUIUpdates
        UIManager.getInstance().addListener(this);
        //listen to newDay
        GameManager.getInstance().getTimeAdvancer().addListener(this);
        GameManager.getInstance().getTimeAdvancer().startTime();

        //create inventory
        GameManager.getInstance().setInventory(new Inventory(true));

        //add market and inventoryUI & controllers
        marketHolder.getChildren().add(new Pane(getMarketUI()));
        inventoryHolder.getChildren().add(new Pane(getInventoryUI()));

        initializePlots();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        setHeaderData();
    }

    @Override
    public void handleForcedUIUpdate(ForceUIUpdate forcedUIUpdate) {
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
            controller.construct(primaryStage);
            return parent;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Parent getInventoryUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../inventory/inventoryUI.fxml"
                    )
            );
            Parent parent = loader.load();
            InventoryUIController controller = loader.getController();
            controller.construct(primaryStage);
            return parent;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void setHeaderData() {
        try {
            Platform.runLater(() -> {
                if (difficultyLevel != null) {
                    difficultyLevel.setText("Name: " + GameManager.getInstance().getName());
                }
                if (currentDate != null) {
                    currentDate.setText("Day: " + GameManager.getInstance().getDay());
                }
                if (startingMoney != null) {
                    startingMoney.setText("Money: " + GameManager.getInstance().getMoney());
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void initializePlots() {
        for (int i = 0; i < numOfPlots; ++i) {
            this.plots.add(new Plot());
        }
        try {
            plotHolder = getRandomPlots(Arrays.asList(CropTypes.values()));
            farmPlots.getChildren().add(plotHolder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public TilePane getRandomPlots(List<CropTypes> seeds) {
        TilePane plotGrid = PlotUI.getPlotHolderUI();
        for (int i = 0; i < plots.size(); i++) {
            Plot plot = plots.get(i);
            int randomCrop = (int) (Math.random() * 100) % seeds.size();
            int randomStage = (int) (Math.random() * 100) % CropStages.values().length;
            plot.getCurrentCrop().setType(seeds.get(randomCrop));
            plot.getCurrentCrop().setCropStage(CropStages.values()[randomStage]);

            VBox uiComponent = PlotUI.getPlotUI(plot, this);

            plotGrid.getChildren().add(uiComponent);
            //now we add it to the hashMap as well
            plotsToUIIndex.put(plot, i);
        }
        return plotGrid;
    }

    /**
     * Updates a given plot's UI.
     * @param plot the plot to render based on
     */
    public void updatePlotUI(Plot plot) {
        int index = plotsToUIIndex.get(plot);
        plotHolder.getChildren().set(index, PlotUI.getPlotUI(plot, this));
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

    public List<Plot> getPlots() {
        return plots;
    }
}