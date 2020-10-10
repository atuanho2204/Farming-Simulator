package main.farm;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.util.AlertUser;
import main.util.UIManager;
import main.util.customEvents.ForceUIUpdate;
import main.util.customEvents.ForceUIUpdateListener;
import main.util.customEvents.NewDayListener;
import main.util.customEvents.NewDayEvent;
import main.inventory.Inventory;
import main.inventory.InventoryUIController;
import main.market.MarketUIController;
import main.util.crops.CropStage;
import main.util.crops.CropTypes;
import java.util.List;

/**
 * The Controller for the FarmUI fxml screen
 */
public class FarmController implements NewDayListener, ForceUIUpdateListener {
    private Stage primaryStage;
    private GameManager gameManager;
    private final int numOfPlots = 12;
    private List<Plot> plots;

    @FXML
    private Pane farmPlots;
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
     * @param primaryStage ...
     * @param gameManager ...
     */
    public void construct(Stage primaryStage, GameManager gameManager) {
        this.primaryStage = primaryStage;
        this.gameManager = gameManager;

        setHeaderData();
        gameManager.setMoney(40 * gameManager.getDifficulty());
        UIManager.getInstance().addListener(this);
        gameManager.getTimeAdvancer().addListener(this);
        gameManager.getTimeAdvancer().startTime();
        farmPlots.getChildren().add(new Pane(populatePlotsRandomly()));
        marketHolder.getChildren().add(new Pane(getMarketUI()));
        //also sets inventory globally
        inventoryHolder.getChildren().add(new Pane(getInventoryUI()));
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
                            "../inventory/inventoryUI.fxml"
                    )
            );
            Parent parent = loader.load();
            InventoryUIController controller = loader.getController();
            gameManager.setInventory(new Inventory(gameManager, primaryStage, controller));
            //sets the global inventory
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

    private Pane populatePlotsRandomly() {
        plots = gameManager.getPlots();
        TilePane plotGrid = new TilePane();
        plotGrid.setPrefWidth(880);
        plotGrid.setPrefTileHeight(200);
        plotGrid.setPrefTileWidth(220);
        int numOfSeedTypes = gameManager.getSeeds().size();
        int numOfStages = CropStage.values().length;
        for (int i = 0; i < numOfPlots; ++i) {
            int randomCrop = (int) (Math.random() * 100) % numOfSeedTypes;
            int randomStage = (int) (Math.random() * 100) % numOfStages;
            String seed = gameManager.getSeeds().get(randomCrop).toUpperCase();
            //uiPlots.get(i).setPrefSize(20,20);
            /*uiPlots.get(i).setGraphic(
                    new ImageView(new Image("main/images/Untitled_Artwork.jpg")));*/
            Plot currPlot = plots.get(i);
            currPlot.getCurrentCrop().setCropType(CropTypes.valueOf(seed));
            currPlot.getCurrentCrop().setCropStage(CropStage.values()[randomStage]);
            currPlot.getPlotButton().setText(
                    plots.get(i).getCurrentCrop().getCropType().toString()
                            + "\n" + plots.get(i).getCurrentCrop().getStage().toString()
            );
            currPlot.getPlotButton().setOnAction(actionEvent -> {
                if (currPlot.getCurrentCrop().getStage() == CropStage.DEAD) {
                    currPlot.getPlotButton().setText("Empty &\nlonely..");
                    currPlot.getCurrentCrop().setCropType(null);
                    currPlot.getCurrentCrop().setCropStage(null);
                } else if (currPlot.getCurrentCrop().getStage() == CropStage.MATURE) {
                    try {
                        gameManager.getInventory().putProduct(
                                currPlot.getCurrentCrop().getCropType());
                        currPlot.getPlotButton().setText("Empty &\nlonely..");
                        currPlot.getCurrentCrop().setCropType(null);
                        currPlot.getCurrentCrop().setCropStage(null);
                    } catch (Exception e) {
                        AlertUser.alertUser("Storage is full!!!");
                    }
                }
            });
            plotGrid.getChildren().add(currPlot.getPlotButton());
        }
        return plotGrid;
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