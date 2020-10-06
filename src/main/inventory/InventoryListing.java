package main.inventory;

import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.inventory.inventoryItems.InventoryItem;
import main.util.crops.CropTypes;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryListing {
    public static HBox getInfoUI(Inventory inventory) {
        HBox hBox = new HBox();

        // Label for Inventory
        //Text text = new Text("Inventory: ");

        // Inventory Information
        Text current = new Text("Current: " + inventory.getStorageSize() + "   ");
        int storageRemain = inventory.getStorageLimit() - inventory.getStorageSize();
        Text remain = new Text("Remaining: " + storageRemain + "   ");
        Text capacity = new Text("Capacity: " + inventory.getStorageLimit());

        hBox.getChildren().addAll(current, remain, capacity);

        return hBox;
    }

    public static HBox getHeader(String t) {
        HBox hBox = new HBox();

        Text text = new Text(t + ": ");
        hBox.getChildren().addAll(text);

        return hBox;
    }

    public static HBox getSeedListingUI(String seedName, int quantity) {
        HBox hBox = new HBox();

        // Display the storage items
        hBox.getChildren().add(new Text(seedName + ": " + quantity));

        //optional stuff

        return hBox;
    }

    public static HBox getProductListingUI(String productName, int quantity) {
        HBox hBox = new HBox();

        // Display the storage items

        hBox.getChildren().add(new Text(productName + ": " + quantity));

        //optional stuff

        return hBox;
    }
}
