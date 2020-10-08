package main.market;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.util.UIManager;
import main.util.customEvents.ForceUIUpdate;
import main.util.customEvents.ForceUIUpdateListener;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;
import main.inventory.inventoryItems.InventoryItem;
import main.market.marketListing.MarketListing;

import java.util.ArrayList;


/**
 * The Controller for the MarketUI fxml screen
 */
public class MarketUIController implements NewDayListener {
    private Stage primaryStage;
    private GameManager gameManager;


    @FXML
    private VBox marketScreen;

    /**
     * Constructs the Market Scene.
     *
     * @param primaryStage the stage
     * @param gameManager  the gameManager
     */
    public void construct(Stage primaryStage, GameManager gameManager) {
        this.primaryStage = primaryStage;
        this.gameManager = gameManager;

        this.gameManager.getTimeAdvancer().addListener(this);

        setMarketListings();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        //update values
        setMarketListings();
    }

    private void setMarketListings() {
        ArrayList<Node> newListings = new ArrayList<>();
        Platform.runLater(() -> marketScreen.getChildren().clear());
        try {
            for (InventoryItem listing : gameManager.getMarket().getMarketListings()) {
                newListings.add(MarketListing.getListingUI(listing, gameManager));
                //System.out.println(listing.getName() + " with price: " + listing.getBuyCost());
            }
            marketScreen.setPadding(new Insets(100, 0, 0, 40));
            Platform.runLater(() -> {
                marketScreen.getChildren().addAll(newListings);
            });
        } catch (Exception e) {
            System.out.println("Error in setting market listings: " + e.getMessage());
            for (StackTraceElement l : e.getStackTrace()) {
                System.out.println(l);
            }
        }
    }
}