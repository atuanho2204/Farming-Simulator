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
    public static HBox getListingUI(Inventory listing) {
        HBox hBox = new HBox();

        // Lable for Inventory
        Text text = new Text("Inventory");
        text.setUnderline(true);

        // Inventory Information
        HBox storage_infor = new HBox();
        Text current = new Text("Current" + listing.getStorageSize());
        int storageRemain = listing.getStorageLimit() - listing.getStorageSize();
        Text remain = new Text("Remaining" + storageRemain);
        Text capacity = new Text("Capacity" + listing.getStorageLimit());
        storage_infor.getChildren().addAll(current, remain, capacity);

        // Display the storage items
        VBox items_infor = new VBox();
        FlowPane display_items = new FlowPane();
        ArrayList<Node> node = new ArrayList<>();
        HashMap<CropTypes, Integer> getSeed = listing.getListOfSeedItems();
        for (CropTypes item: getSeed.keySet()) {
            node.add(new Text(item + ": " + getSeed.get(item)));
        }
        display_items.getChildren().addAll(node);
        items_infor.getChildren().add(display_items);

        hBox.getChildren().addAll(text, storage_infor, items_infor);
        return hBox;
    }
}
