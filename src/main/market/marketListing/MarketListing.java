package main.market.marketListing;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.inventory.inventoryItems.InventoryItem;


public class MarketListing {
    public static HBox getListingUI(InventoryItem listing) {
        HBox hBox = new HBox();
        //you need to append a label for the name and price,
        // and a button to buy, and a button to sell
        Text text = new Text("Test + " + Math.random() * 5); //change this


        hBox.getChildren().add(text);
        return hBox;
    }
}
