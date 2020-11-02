package main.inventory;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.gameManager.GameManager;
import main.inventory.inventoryItems.HarvestedCrop;

public class InventoryListing {
    public static HBox getInfoUI(Inventory inventory) {
        HBox hBox = new HBox();

        // Label for Inventory
        //Text text = new Text("Inventory: ");

        // Inventory Information
        Text current = new Text("Current: " + inventory.getStorageSize() + "   ");

        current.setFill(Color.YELLOW);
        current.setStyle("-fx-font: 16 chalkduster;");
        int storageRemain = inventory.getStorageLimit() - inventory.getStorageSize();
        Text remain = new Text("Remaining: " + storageRemain + "   ");
        remain.setFill(Color.YELLOW);
        remain.setStyle("-fx-font: 16 chalkduster;");
        Text capacity = new Text("Capacity: " + inventory.getStorageLimit());
        capacity.setFill(Color.YELLOW);
        capacity.setStyle("-fx-font: 16 chalkduster;");
        hBox.getChildren().addAll(current, remain, capacity);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public static HBox getHeader(String t) {
        HBox hBox = new HBox();

        Text text = new Text("\n" + t + ": ");
        text.setFill(Color.LIGHTSKYBLUE);
        text.setStyle("-fx-font: 16 chalkduster;");
        hBox.getChildren().addAll(text);


        return hBox;
    }

    public static HBox getSeedListingUI(String seedName, int quantity) {
        HBox hBox = new HBox();

        // Display the storage items

        Text seed = new Text("\t" + seedName + ": " + quantity);
        seed.setFill(Color.WHITE);
        seed.setStyle("-fx-font: 16 chalkduster;");
        hBox.getChildren().add(seed);


        return hBox;
    }

    public static HBox getProductListingUI(HarvestedCrop crop) {
        HBox hBox = new HBox();

        Text product = new Text("\t" + crop.getName() + ": " + crop.getSellCost());
        product.setFill(Color.WHITE);
        product.setStyle("-fx-font: 16 chalkduster;");
        hBox.getChildren().add(product);

        //ADD BUTTON TO SELL
        Button sell = new Button("Sell");
        sell.setTextFill(Color.RED);
        sell.setOnAction(e -> {
            GameManager.getInstance().getInventory().sellProduct(crop);
        });
        hBox.getChildren().add(sell);

        return hBox;
    }

    public static HBox getPesticideUI() {
        HBox hBox = new HBox();
        Text pesticide = new Text("Pesticide:\t");
        Text pesticide1 = new Text(GameManager.getInstance().getInventory().getPesticide() + "/10");
        pesticide.setFill(Color.LIGHTSKYBLUE);
        pesticide1.setFill(Color.RED);
        pesticide.setStyle("-fx-font: 16 chalkduster;");
        pesticide1.setStyle("-fx-font: 16 chalkduster;");
        hBox.getChildren().add(pesticide);
        hBox.getChildren().add(pesticide1);

        return hBox;
    }
}