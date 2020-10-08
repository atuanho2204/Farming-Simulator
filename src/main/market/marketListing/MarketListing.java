package main.market.marketListing;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import main.gameManager.GameManager;
import main.inventory.inventoryItems.HarvestedCrop;
import main.inventory.inventoryItems.InventoryItem;
import main.inventory.inventoryItems.Seed;
import main.util.AlertUser;
import main.util.UIManager;
import main.util.crops.CropTypes;
import javafx.scene.paint.Color;

public class MarketListing {
    public static HBox getListingUI(InventoryItem listing, GameManager gameManager) {
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
        if (listing instanceof Seed) {
            Button buy = new Button("Buy");
            buy.setTextFill(Color.GREEN);
            buy.setOnAction(e -> {
                buySeed(((Seed) listing).getType(), gameManager, listing.getBuyCost());
            });
            hBox.getChildren().add(buy);
            sell.setOnAction(e -> {
                sellSeed(((Seed) listing).getType(), gameManager, listing.getSellCost());
            });
        } else if (listing instanceof HarvestedCrop) {
            sell.setOnAction(e -> {
                sellProduct(((HarvestedCrop) listing).getType(), gameManager, listing.getSellCost());
            });
        } else {
            Text warning = new Text("\tThis probably shouldn't be here\t\t");
            warning.setFill(Color.WHITE);
            warning.setStyle("-fx-font: 16 chalkduster;");
            hBox.getChildren().add(warning);
        }
        hBox.getChildren().add(sell);

        return hBox;
    }

    private static void buySeed(CropTypes type, GameManager gameManager, int price) {
        try {
            if (gameManager.getMoney() >= price) {
                gameManager.getInventory().putSeed(type);
                int money = gameManager.getMoney() - price;
                gameManager.setMoney(money);
                UIManager.getInstance().pushUIUpdate();
            } else {
                AlertUser.alertUser("Do not have enough money");
            }
        } catch (Exception e) {
            AlertUser.alertUser("Do not have enough space");
        }
    }

    private static void sellSeed(CropTypes type, GameManager gameManager, int price) {
        try {
            gameManager.getInventory().removeSeed(type);
            int money = gameManager.getMoney() + price;
            gameManager.setMoney(money);
            UIManager.getInstance().pushUIUpdate();
        } catch (Exception e) {
            AlertUser.alertUser("Do not have seed");
        }
    }

    private static void sellProduct(CropTypes type, GameManager gameManager, int price) {
        try {
            gameManager.getInventory().removeProduct(type);
            int newMoney = gameManager.getMoney() + price;
            gameManager.setMoney(newMoney);
            UIManager.getInstance().pushUIUpdate();
        } catch (Exception e) {
            AlertUser.alertUser("You do not have that product in your inventory");
        }
    }
}
