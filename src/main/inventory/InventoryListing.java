package main.inventory;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.inventory.inventoryItems.InventoryItem;
import main.util.crops.CropTypes;
import javafx.scene.paint.Color;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InventoryListing {
    public static HBox getInfoUI(Inventory inventory) {
        HBox hBox = new HBox();

        // Label for Inventory
        //Text text = new Text("Inventory: ");

        // Inventory Information
        Text current = new Text("Current: " + inventory.getStorageSize() + "   ");
        current.setFill(Color.WHITE);
        current.setStyle("-fx-font: 20 arial;");

        int storageRemain = inventory.getStorageLimit() - inventory.getStorageSize();
        Text remain = new Text("Remaining: " + storageRemain + "   ");
        remain.setFill(Color.WHITE);
        remain.setStyle("-fx-font: 20 arial;");

        Text capacity = new Text("Capacity: " + inventory.getStorageLimit());
        capacity.setFill(Color.WHITE);
        capacity.setStyle("-fx-font: 20 arial;");

        hBox.getChildren().addAll(current, remain, capacity);

        return hBox;
    }

    public static HBox getHeader(String t) {
        HBox hBox = new HBox();

        Text text = new Text(t + ": ");
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font: 20 arial;");

        hBox.getChildren().addAll(text);


        return hBox;
    }

    public static HBox getSeedListingUI(String seedName, int quantity) {
        HBox hBox = new HBox();

        // Display the storage items
        Text text = new Text(seedName + ": " + quantity);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font: 20 arial;");



        hBox.setAlignment(Pos.CENTER);

        //optional stuff
        hBox.getChildren().add(text);

        return hBox;
    }

    public static HBox getProductListingUI(String productName, int quantity) {
        HBox hBox = new HBox();

        // Display the storage items
        Text text = new Text(productName + ": " + quantity);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font: 20 arial;");

        hBox.getChildren().add(text);
        hBox.setAlignment(Pos.CENTER);
        //optional stuff

        return hBox;
    }
}
