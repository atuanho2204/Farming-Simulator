package main.market;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.gameManager.GameManager;
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

    @FXML
    private VBox marketScreen;

    /**
     * Constructs the Market Scene.
     *
     * @param primaryStage the stage
     */
    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;
        GameManager.getInstance().getTimeAdvancer().addListener(this);
        setMarketListings();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        //update values
        setMarketListings();
    }

    private void setMarketListings() {
        ArrayList<Node> newListings = new ArrayList<>();
        try {
            for (InventoryItem listing
                    : GameManager.getInstance().getMarket().getMarketListings()) {
                newListings.add(MarketListing.getListingUI(listing, GameManager.getInstance()));
                //System.out.println(listing.getName() + " with price: " + listing.getBuyCost());
            }
            marketScreen.setPadding(new Insets(100, 0, 0, 40));
            Platform.runLater(() -> {
                marketScreen.getChildren().clear();
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