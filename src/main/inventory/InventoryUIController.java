package main.inventory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.gameManager.GameManager;
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


    public void setInventoryListings() {
        ArrayList<Node> newListings = new ArrayList<>();
        Platform.runLater(() -> inventoryScreen.getChildren().clear());
        try {
            newListings.add(InventoryListing.getInfoUI(gameManager.getInventory()));
            HashMap<CropTypes, Integer> seeds = gameManager.getInventory().getListOfSeedItems();
            if (seeds.keySet().size() == 0) {
                Text emptySeed = new Text("You don't have any seeds. How do you plan to farm??");
                emptySeed.setFill(Color.ORANGE);
                emptySeed.setStyle("-fx-font: 15 arial;");
                newListings.add(emptySeed);
            } else {
                newListings.add(InventoryListing.getHeader("Seeds"));
                for (CropTypes type : seeds.keySet()) {
                    newListings.add(InventoryListing.getSeedListingUI(
                            type.name().toLowerCase(), seeds.get(type)));
                }
            }
            HashMap<CropTypes, Integer> products = gameManager.getInventory().getListOfProductItems();
            if (products.keySet().size() == 0) {
                Text emptyProduct = new Text("You don't have any products. You are a failure at farming! :(");
                emptyProduct.setFill(Color.ORANGE);
                emptyProduct.setStyle("-fx-font: 15 arial;");
                newListings.add(emptyProduct);
            } else {
                newListings.add(InventoryListing.getHeader("Products"));
                for (CropTypes type : products.keySet()) {
                    newListings.add(InventoryListing.getProductListingUI(
                            type.name().toLowerCase(), products.get(type)));
                }
            }
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
