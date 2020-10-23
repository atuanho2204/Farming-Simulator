package main.inventory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.util.UIManager;
import main.util.crops.CropTypes;
import main.util.customEvents.ForceUIUpdate;
import main.util.customEvents.ForceUIUpdateListener;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryUIController implements ForceUIUpdateListener {
    private Stage primaryStage;

    @FXML
    private VBox inventoryScreen;


    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;
        inventoryScreen.setPadding(new Insets(120, 0, 0, 35));
        setInventoryListings();
        UIManager.getInstance().addListener(this);
    }

    @Override
    public void handleForcedUIUpdate(ForceUIUpdate forcedUIUpdate) {
        setInventoryListings();
    }

    public void setInventoryListings() {
        ArrayList<Node> newListings = new ArrayList<>();
        try {
            inventoryScreen.getChildren().clear();

            newListings.add(InventoryListing.getInfoUI(GameManager.getInstance().getInventory()));
            //go through the seeds
            HashMap<CropTypes, Integer> seeds =
                    GameManager.getInstance().getInventory().getListOfSeedItems();
            if (seeds.keySet().size() == 0) {
                Text emptySeed = new Text("\nNo seeds?\nHow do you plan to farm??");
                emptySeed.setFill(Color.ORANGE);
                emptySeed.setStyle("-fx-font: 16 chalkduster;");
                newListings.add(emptySeed);

            } else {
                newListings.add(InventoryListing.getHeader("Seeds"));
                for (CropTypes type : seeds.keySet()) {
                    newListings.add(InventoryListing.getSeedListingUI(
                            type.name().toLowerCase(), seeds.get(type)));
                }
            }
            //go through the products
            HashMap<CropTypes, Integer> products =
                    GameManager.getInstance().getInventory().getListOfProductItems();
            if (products.keySet().size() == 0) {
                Text emptyProduct = new Text("\nNo products?\n"
                        + "You are a failure at farming! :(");
                emptyProduct.setFill(Color.ORANGE);
                emptyProduct.setStyle("-fx-font: 16 chalkduster;");
                newListings.add(emptyProduct);
            } else {
                newListings.add(InventoryListing.getHeader("Products"));
                for (CropTypes type : products.keySet()) {
                    newListings.add(InventoryListing.getProductListingUI(
                            type.name().toLowerCase(), products.get(type)));
                }
            }
            //Platform.runLater(() -> {
            inventoryScreen.getChildren().addAll(newListings);
            //});
        } catch (Exception e) {
            System.out.println("Error in setting inventory listings: " + e.getMessage());
            e.printStackTrace();
        }
    }
}