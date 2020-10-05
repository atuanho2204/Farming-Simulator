package main.inventory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.gameManager.NewDayListener;
import main.inventory.inventoryItems.InventoryItem;
import main.market.marketListing.MarketListing;
import main.util.crops.CropTypes;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryUIController {
    private Stage primaryStage;
    private GameManager gameManager;

    @FXML
    private VBox inventoryScreen;

    public void construct(Stage primaryStage, GameManager gameManager) {
        this.primaryStage = primaryStage;
        this.gameManager = gameManager;

        setInventoryListings();
    }
    private void setInventoryListings() {
        ArrayList<Node> newListings = new ArrayList<>();
        Platform.runLater(() -> inventoryScreen.getChildren().clear());
        try {
            HashMap<CropTypes, Integer> items = gameManager.getInventory().getListOfProductItems();
            for (CropTypes listing : items.keySet()) {
                System.out.println(listing+ ": " + items.get(listing));
            }
            newListings.add(InventoryListing.getListingUI(gameManager.getInventory()));
            Platform.runLater(() -> {
                inventoryScreen.getChildren().addAll(newListings);
            });
        } catch (Exception e) {
            System.out.println("Error in setting market listings: " + e.getMessage());
            for (StackTraceElement l : e.getStackTrace()) {
                System.out.println(l);
            }
        }
    }
}
