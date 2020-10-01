package main.market.marketListing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.inventory.InventoryItem;


public class MarketListingController {
    Stage primaryStage;
    InventoryItem listing;

    @FXML
    private Label title;

    public void construct(Stage primaryStage, InventoryItem listing) {
        this.primaryStage = primaryStage;
        this.listing = listing;
        title.setText("Hi " + Math.random() * 10);
    }

    private void setListingUI() {
        //you should set the UI text and
    }

    @FXML
    private void buyAction(ActionEvent event) {
        //this should attempt to buy the listing
    }
}
