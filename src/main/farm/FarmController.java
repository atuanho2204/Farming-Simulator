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
import main.market.Market;
import main.util.UIManager;
import main.util.crops.CropCatalog;
import main.util.crops.CropDetails;
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

        //create market
        Market market = new Market();
        GameManager.getInstance().setMarket(market);
        GameManager.getInstance().getTimeAdvancer().addListener(market);

        //add market and inventoryUI & controllers
        marketHolder.getChildren().add(new Pane(getMarketUI()));
        inventoryHolder.getChildren().add(new Pane(getInventoryUI()));

        initializePlots();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        setHeaderData();
        reduceWaterLevels(GameManager.getInstance().getDifficulty(),
                GameManager.getInstance().getSeason());
        updateGrowthCycle();
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
            plotHolder = getRandomPlots(GameManager.getInstance().getSeeds());
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

    private void reduceWaterLevels(Integer difficulty, String season) {
        if (GameManager.getInstance().getDay() % 2 != 0) {
            return;
        }
        int waterLost = (difficulty < 3) ? 1 : 2;
        if (season.toLowerCase().equals("summer")) {
            ++waterLost;
        }
        try {
            int finalWaterLost = waterLost;
            Platform.runLater(() -> {
                for (Plot plot : plots) {
                    System.out.println("-----");
                    System.out.println("Before: " + plot.getCurrentWater());
                    int maxLevel = plot.getMaxWater();
                    if (plot.getCurrentWater() % maxLevel == 0) {
                        continue;
                    }
                    if (plot.getCurrentWater() > finalWaterLost) {
                        plot.setCurrentWater(plot.getCurrentWater() - finalWaterLost);
                        if (plot.getCurrentWater() < 4) {
                            plot.getWaterBar().setStyle("-fx-accent: #FFD700;"); // yellow
                        } else if (plot.getCurrentWater() < maxLevel - 1) {
                            plot.getWaterBar().setStyle("-fx-accent: #00BFFF;"); // blue
                        }
                    } else {
                        plot.setCurrentWater(0);
                        if (plot.getCurrentCrop() != null) {
                            plot.getCurrentCrop().setCropStage(CropStages.DEAD);
                        }
                    }
                    System.out.println("After: " + plot.getCurrentWater());
                    plot.getWaterBar().setProgress(plot.getCurrentWater() * 1.0 / maxLevel);
                    updatePlotUI(plot);
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

    public List<Plot> getPlots() {
        return plots;
    }


    public void updateGrowthCycle() {
        for (Plot plot: plots) {
            if (plot.getCurrentCrop() == null) {
                continue;
            }
            CropTypes type = plot.getCurrentCrop().getType();
            CropStages stage = plot.getCurrentCrop().getStage();
            int plantDay = plot.getCurrentCrop().getPlantDay();
            CropDetails details = CropCatalog.getInstance().getCropDetails(type);
            int growthTime = details.getGrowthTime();
            int currentDay = GameManager.getInstance().getDay();
            if (currentDay - plantDay > 0 && (currentDay - plantDay) % growthTime == 0) {
                if (stage == CropStages.DEAD) {
                    continue;
                } else if (stage == CropStages.SPROUTING) {
                    plot.getCurrentCrop().setCropStage(CropStages.IMMATURE);
                } else if (stage == CropStages.IMMATURE) {
                    plot.getCurrentCrop().setCropStage(CropStages.MATURE);
                } else if (stage == CropStages.MATURE) {
                    plot.getCurrentCrop().setCropStage(CropStages.DEAD);
                }
            }

        }
    }
}