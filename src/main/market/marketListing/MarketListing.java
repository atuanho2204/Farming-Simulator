package main.market.marketListing;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import main.inventory.inventoryItems.InventoryItem;
import main.inventory.Inventory;


public class MarketListing {
    public static HBox getListingUI(InventoryItem listing) {
        HBox hBox = new HBox();
        //you need to append a label for the name and price,
        // and a button to buy, and a button to sell
        Text text = new Text(listing.getName() + ":\n");
        text.setUnderline(true);
        Text text1 = new Text("\t$" + listing.getBuyCost() + "\t\t");
        Button buy = new Button("Buy");
        Button sell = new Button("Sell");

        hBox.getChildren().add(text);
        hBox.getChildren().add(text1);
        hBox.getChildren().add(buy);
        hBox.getChildren().add(sell);
        return hBox;
    }
}
