package main.market.marketListing;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class MarketListingController {
    Stage primaryStage;

    @FXML
    private Label listingTitle;

    public void construct(Stage primaryStage) {
        listingTitle.setText("Hi " + Math.random() * 10);
    }
}
