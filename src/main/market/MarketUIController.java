package main.market;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayEvent;
import main.gameManager.NewDayListener;
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
    private StackPane marketScreen;

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

    private void setMarketListings() {
        ArrayList<Node> list = new ArrayList<>();
        try {
            Platform.runLater(() -> marketScreen.getChildren().clear());
            for (InventoryItem listing : gameManager.getMarket().getMarketListings()) {
                list.add(loadListingUI(listing));
                System.out.println("Loading market item: "
                        + listing.getName()
                        + " with price: "
                        + listing.getBuyCost());
            }
            Platform.runLater(() -> {
                marketScreen.getChildren().addAll(list);
            });
        } catch (Exception e) {
            System.out.println("Error in setting market listing: " + e.getMessage());
            for (StackTraceElement l : e.getStackTrace()) {
                System.out.println(l);
            }
        }
    }


    private Node loadListingUI(InventoryItem listing) {
        try {
            return MarketListing.getListingUI(listing);
        } catch (Exception e) {
            return null;
        }
    }

    public void handleNewDay(NewDayEvent e) {
        //update values
        setMarketListings();
    }
}