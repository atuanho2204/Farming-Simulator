package main.inventory;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.inventory.inventoryItems.HarvestedCrop;
import main.farm.crops.CropTypes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

public class InventoryUIController implements PropertyChangeListener {
    private Stage primaryStage;

    @FXML
    private VBox inventoryScreen;


    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;
        inventoryScreen.setPadding(new Insets(120, 0, 0, 35));

        //subscribe to the inventory changes
        GameManager.getInstance().getInventory().subscribeToChanges(this);

        setInventoryListings();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //System.out.println("Inventory Controller received changed property");
        setInventoryListings();
    }

    public void setInventoryListings() {
        ArrayList<Node> newListings = new ArrayList<>();
        try {
            inventoryScreen.getChildren().clear();

            newListings.add(InventoryListing.getInfoUI(GameManager.getInstance().getInventory()));
            //Fertilizer display
            Text fertilize = new Text("Fertilizer Tank: " + GameManager.getInstance().getInventory().getFertilizer() + "/10");
            fertilize.setFill(Color.ANTIQUEWHITE);
            fertilize.setStyle("-fx-font: 16 chalkduster;");
            newListings.add(fertilize);
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
            ArrayList<HarvestedCrop> products =
                    GameManager.getInstance().getInventory().getProducts();
            if (products.size() == 0) {
                Text emptyProduct = new Text("\nNo products?\n"
                        + "You are a failure at farming! :(");
                emptyProduct.setFill(Color.ORANGE);
                emptyProduct.setStyle("-fx-font: 16 chalkduster;");
                newListings.add(emptyProduct);
            } else {
                newListings.add(InventoryListing.getHeader("Products"));
                for (HarvestedCrop crop : products) {
                    newListings.add(InventoryListing.getProductListingUI(crop));
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