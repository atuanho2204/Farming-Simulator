package main.farm;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.employment.EmployeeManager;
import main.employment.EmploymentController;
import main.farm.header.FarmHeaderController;
import main.farm.plot.Plot;
import main.farm.plot.PlotUI;
import main.gameManager.GameManager;
import main.market.Market;
import main.notifications.NotificationController;
import main.util.AlertUser;
import main.farm.crops.*;
import main.inventory.Inventory;
import main.inventory.InventoryUIController;
import main.market.MarketUIController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;

/**
 * The Controller for the FarmUI fxml screen
 */
public class FarmController implements PropertyChangeListener {
    private Stage primaryStage;
    private HashMap<Plot, Integer> plotsToUIIndex = new HashMap<>();
    private TilePane plotHolder;
    private FarmState farmState;

    @FXML
    private HBox headerHolder;
    @FXML
    private HBox farmPlots;
    @FXML
    private Pane marketHolder;
    @FXML
    private Pane inventoryHolder;
    @FXML
    private Pane employmentHolder;
    @FXML
    private Pane notificationHolder;


    /**
     * Constructs the Farm Scene.
     *
     * @param primaryStage ...
     */
    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;

        if (GameManager.getInstance().getName().equals("Super Farmer")) {
            GameManager.getInstance().setMoney((1000));
        } else {
            GameManager.getInstance().setMoney(75 * GameManager.getInstance().getDifficulty());
        }
        //listen to the farmState
        farmState = FarmState.getInstance();
        farmState.subscribeToChanges(this);

        //create inventory
        GameManager.getInstance().setInventory(new Inventory(true));

        //create market
        Market market = new Market();
        GameManager.getInstance().setMarket(market);
        GameManager.getInstance().getTimeAdvancer().addListener(market);

        // create employee

        EmployeeManager employeeManager = new EmployeeManager();
        GameManager.getInstance().setEmployees(employeeManager);
        GameManager.getInstance().getTimeAdvancer().addListener(employeeManager);

        //add various UI's & controllers
        setHeaderUI();
        setMarketUI();
        setInventoryUI();
        setEmployeeUI();
        setNotificationUI();

        initializePlots();

        //BEGIN GAME
        GameManager.getInstance().getTimeAdvancer().startTime();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //this happens when the farm state changes from outside this class
        for (Plot plot : farmState.getPlots()) {
            Platform.runLater(() -> {
                updatePlotUI(plot);
            });
        }
    }


    private void setHeaderUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../farm/header/farmHeader.fxml"
                    )
            );
            Parent parent = loader.load();
            FarmHeaderController controller = loader.getController();
            controller.construct(primaryStage);
            headerHolder.getChildren().add(new Pane(parent));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            AlertUser.alertUser("There was an error loading in the headerUI");
        }
    }
    private void setMarketUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../market/marketUI.fxml"
                    )
            );
            Parent parent = loader.load();
            MarketUIController controller = loader.getController();
            controller.construct(primaryStage);
            marketHolder.getChildren().add(new Pane(parent));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            AlertUser.alertUser("There was an error loading in the marketUI");
        }
    }
    private void setInventoryUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../inventory/inventoryUI.fxml"
                    )
            );
            Parent parent = loader.load();
            InventoryUIController controller = loader.getController();
            controller.construct(primaryStage);
            inventoryHolder.getChildren().add(new Pane(parent));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    private void setEmployeeUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../employment/employmentUI.fxml"
                    )
            );
            Parent parent = loader.load();
            EmploymentController controller = loader.getController();
            controller.construct(primaryStage);
            employmentHolder.getChildren().add(new Pane(parent));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    private void setNotificationUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../notifications/notificationUI.fxml"
                    )
            );
            Parent parent = loader.load();
            NotificationController controller = loader.getController();
            controller.construct(primaryStage);
            notificationHolder.getChildren().add(new Pane(parent));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public void initializePlots() {
        for (int i = 0; i < farmState.getNumOfPlots(); ++i) {
            farmState.getPlots().add(new Plot());
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

        //these 2 lines caused a UI bug, because this work was already done in the plot class
        //plotGrid.setPrefTileHeight(187.5);
        //plotGrid.setPrefTileWidth(191.3);
        for (int i = 0; i < farmState.getPlots().size(); i++) {
            Plot plot = farmState.getPlots().get(i);
            int randomCrop = (int) (Math.random() * 100) % seeds.size();
            int randomStage = (int) (Math.random() * 100) % CropStages.values().length;
            plot.getCurrentCrop().setType(seeds.get(randomCrop));
            plot.getCurrentCrop().setCropStage(CropStages.values()[randomStage]);

            StackPane uiComponent = PlotUI.getPlotUI(plot, this);
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
        Platform.runLater(() -> {
            plotHolder.getChildren().set(index, PlotUI.getPlotUI(plot, this));
        });
    }
}