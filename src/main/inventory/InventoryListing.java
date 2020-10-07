package main.inventory;


import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class InventoryListing {
    public static HBox getInfoUI(Inventory inventory) {
        HBox hBox = new HBox();

        // Label for Inventory
        //Text text = new Text("Inventory: ");

        // Inventory Information
        Text current = new Text("Current: " + inventory.getStorageSize() + "   ");
        current.setFill(Color.YELLOW);
        current.setStyle("-fx-font: 25 arial;");
        int storageRemain = inventory.getStorageLimit() - inventory.getStorageSize();
        Text remain = new Text("Remaining: " + storageRemain + "   ");
        remain.setFill(Color.YELLOW);
        remain.setStyle("-fx-font: 25 arial;");
        Text capacity = new Text("Capacity: " + inventory.getStorageLimit());
        capacity.setFill(Color.YELLOW);
        capacity.setStyle("-fx-font: 25 arial;");
        hBox.getChildren().addAll(current, remain, capacity);

        return hBox;
    }

    public static HBox getHeader(String t) {
        HBox hBox = new HBox();

        Text text = new Text(t + ": ");
        text.setFill(Color.LIGHTSKYBLUE);
        text.setStyle("-fx-font: 20 arial;");
        hBox.getChildren().addAll(text);

        return hBox;
    }

    public static HBox getSeedListingUI(String seedName, int quantity) {
        HBox hBox = new HBox();

        // Display the storage items
        Text seed = new Text(seedName + ": " + quantity);
        seed.setFill(Color.WHITE);
        seed.setStyle("-fx-font: 18 arial;");
        hBox.getChildren().add(seed);

        //optional stuff

        return hBox;
    }

    public static HBox getProductListingUI(String productName, int quantity) {
        HBox hBox = new HBox();

        // Display the storage items
        Text product = new Text(productName + ": " + quantity);
        product.setFill(Color.WHITE);
        product.setStyle("-fx-font: 18 arial;");
        hBox.getChildren().add(product);

        //optional stuff

        return hBox;
    }
}