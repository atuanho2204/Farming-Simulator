package main.market;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.farm.FarmState;
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
        //please don't move this line...we only need to set them once
        marketScreen.setPadding(new Insets(100, 0, 0, 20));

        GameManager.getInstance().getTimeAdvancer().addListener(this);
        setMarketListings();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        //update values
        setMarketListings();
    }

    private void setMarketListings() {
        try {
            ArrayList<Node> newListings = new ArrayList<>();
            //add the seeds from the market listings
            for (InventoryItem listing
                    : GameManager.getInstance().getMarket().getMarketListings()) {
                newListings.add(MarketListing.getListingUI(listing));
            }
            //add the fertilizer and pesticide
            newListings.add(MarketListing.getFertilizeUI());
            newListings.add(MarketListing.getPesticideUI());


            //add the tractor and/or irrigation
            if (!FarmState.getInstance().getFarmEquipment().hasIrrigation()) {
                newListings.add(MarketListing.getIrrigationUI());
            }
            //add your market listing here @chris
            if (!FarmState.getInstance().getFarmEquipment().hasTractor()) {
                newListings.add(MarketListing.getTractorUI());
            }

            Platform.runLater(() -> {
                marketScreen.getChildren().clear();
                marketScreen.getChildren().addAll(newListings);
            });
        } catch (Exception e) {
            System.out.println("Error in setting market listings: " + e.getMessage());
            e.printStackTrace();
        }
    }
}