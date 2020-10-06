package main.market.marketListing;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import main.gameManager.GameManager;
import main.inventory.inventoryItems.InventoryItem;
import main.inventory.Inventory;
import main.util.AlertUser;
import main.util.crops.CropTypes;
import java.util.NoSuchElementException;
import javafx.scene.paint.Color;


public class MarketListing {
    public static HBox getListingUI(InventoryItem listing, GameManager gamemanager) {
        HBox hBox = new HBox();
        //you need to append a label for the name and price,
        // and a button to buy, and a button to sell
        Text text = new Text(listing.getName() + ":\n");
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font: 20 arial;");
        Text text1 = new Text("\t$" + listing.getBuyCost() + "\t\t");
        text1.setFill(Color.WHITE);
        text1.setStyle("-fx-font: 20 arial;");
        Button buy = new Button("Buy");
        buy.setOnAction(e -> {
            buySeed(CropTypes.getTypeFromString(listing.getName()),gamemanager, listing.getBuyCost());
        });
        buy.setTextFill(Color.GREEN);
        Button sell = new Button("Sell");
        sell.setOnAction(e -> {
            sellSeed(CropTypes.getTypeFromString(listing.getName()),gamemanager, listing.getBuyCost());
        });
        sell.setTextFill(Color.RED);

        hBox.getChildren().add(text);
        hBox.getChildren().add(text1);
        hBox.getChildren().add(buy);
        hBox.getChildren().add(sell);
        return hBox;
    }
    private static void buySeed(CropTypes type, GameManager gameManager, int price) {
        try {
            if (gameManager.getMoney() >= price) {
                gameManager.getInventory().putSeed(type);
            }
            else {
                AlertUser.alertUser("Do not have enough money");
            }
        }
        catch (Exception e) {
            AlertUser.alertUser("Do not have enough space");
        }
    }
    private static void sellSeed(CropTypes type, GameManager gameManager, int price) throws NoSuchElementException {
        try {
            if (gameManager.getMoney() <= price) {
                gameManager.getInventory().removeSeed(type);
            }
            else {
                AlertUser.alertUser("Do not have seed");
            }
        }
        catch (Exception e) {
            AlertUser.alertUser("Do not have enough space");
        }
    }
}
