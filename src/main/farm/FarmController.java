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
import main.util.crops.CropStages;
import main.util.crops.CropTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * The Controller for the FarmUI fxml screen
 */
public class FarmController implements NewDayListener, ForceUIUpdateListener {
    private Stage primaryStage;
    private final int numOfPlots = 12;
    private List<Plot> plots = new ArrayList<>(numOfPlots);

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
     */
    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;

        initializePlots();
        setHeaderData();
        if (GameManager.getInstance().getName().equals("Super Farmer")) {
            GameManager.getInstance().setMoney((1000));
        } else {
            GameManager.getInstance().setMoney(40 * GameManager.getInstance().getDifficulty());
        }
        UIManager.getInstance().addListener(this);
        GameManager.getInstance().getTimeAdvancer().addListener(this);
        GameManager.getInstance().getTimeAdvancer().startTime();
        farmPlots.getChildren().add(
                new Pane(populatePlotsRandomly(GameManager.getInstance().getSeeds())));
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
            controller.construct(primaryStage);
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
            GameManager.getInstance().setInventory(new Inventory(true));
            //sets the global inventory
            controller.construct(primaryStage);
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
            this.plots.get(i).getPlotButton().setStyle("-fx-background-color: #18a734;"
                    + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                    + "-fx-font-size: 14px; -fx-min-width: 100px;");
        }
    }

    public Pane populatePlotsRandomly(List<CropTypes> seeds) {
        TilePane plotGrid = new TilePane();
        plotGrid.setPrefWidth(880);
        plotGrid.setPrefTileHeight(200);
        plotGrid.setPrefTileWidth(220);
        int numOfSeedTypes = seeds.size();
        int numOfStages = CropStages.values().length;
        for (int i = 0; i < numOfPlots; ++i) {
            int randomCrop = (int) (Math.random() * 100) % numOfSeedTypes;
            int randomStage = (int) (Math.random() * 100) % numOfStages;
            String seed = seeds.get(randomCrop).toString();
            //uiPlots.get(i).setPrefSize(20,20);
            /*uiPlots.get(i).setGraphic(
                    new ImageView(new Image("main/images/Untitled_Artwork.jpg")));*/
            Plot currPlot = plots.get(i);
            currPlot.getCurrentCrop().setType(CropTypes.valueOf(seed));
            currPlot.getCurrentCrop().setCropStage(CropStages.values()[randomStage]);
            currPlot.getPlotButton().setText(
                    plots.get(i).getCurrentCrop().getType().toString()
                            + "\n" + plots.get(i).getCurrentCrop().getStage().toString()
            );
            currPlot.getPlotButton().setOnAction(actionEvent -> {
                if (currPlot.getCurrentCrop().getStage() == CropStages.DEAD) {
                    currPlot.getPlotButton().setText("Empty &\nlonely..");
                    currPlot.getCurrentCrop().setType(null);
                    currPlot.getCurrentCrop().setCropStage(null);
                } else if (currPlot.getCurrentCrop().getStage() == CropStages.MATURE) {
                    try {
                        GameManager.getInstance().getInventory().putProduct(
                                currPlot.getCurrentCrop().getType());
                        currPlot.getPlotButton().setText("Empty &\nlonely..");
                        currPlot.getCurrentCrop().setType(null);
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