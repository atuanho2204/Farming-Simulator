package main.market.marketListing;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import main.inventory.inventoryItems.InventoryItem;
import main.inventory.inventoryItems.Seed;
import main.market.Market;
import javafx.scene.paint.Color;

public class MarketListing {
    public static HBox getListingUI(InventoryItem listing) {
        HBox hBox = new HBox();
        Text text = new Text(listing.getName() + ":");
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font: 16 chalkduster;");
        Text text1 = new Text("\t$" + listing.getBuyCost() + "\t\t");
        text1.setFill(Color.WHITE);
        text1.setStyle("-fx-font: 16 chalkduster;");

        Button sell = new Button("Sell");
        sell.setTextFill(Color.RED);
        hBox.getChildren().add(text);
        hBox.getChildren().add(text1);

        Button buy = new Button("Buy");
        buy.setTextFill(Color.GREEN);
        buy.setOnAction(e -> {
            Market.buySeed(((Seed) listing).getType(), listing.getBuyCost());
        });
        hBox.getChildren().add(buy);
        sell.setOnAction(e -> {
            Market.sellSeed(((Seed) listing).getType(), listing.getSellCost());
        });
        hBox.getChildren().add(sell);

        return hBox;
    }
}
