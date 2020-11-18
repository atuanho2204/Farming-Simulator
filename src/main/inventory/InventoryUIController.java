package main.inventory;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
        //please stop moving this line...you can change it...just don't move it
        inventoryScreen.setPadding(new Insets(90, 0, 0, 17));


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

            newListings.addAll(InventoryListing.getInfoUI(
                    GameManager.getInstance().getInventory()));

            //go through the seeds
            HashMap<CropTypes, Integer> seeds =
                    GameManager.getInstance().getInventory().getListOfSeedItems();
            if (seeds.keySet().size() == 0) {
                Text emptySeed = new Text("No seeds?\nHow do you plan to farm??");
                emptySeed.setFill(Color.ORANGE);
                emptySeed.setStyle("-fx-font: 16 chalkduster;");
                newListings.add(emptySeed);
            } else {
                newListings.add(InventoryListing.getHeader("SEEDS"));
                for (CropTypes type : seeds.keySet()) {
                    newListings.add(InventoryListing.getSeedListingUI(
                            type.name().toLowerCase(), seeds.get(type)));
                }
            }
            //go through the products
            ArrayList<HarvestedCrop> products =
                    GameManager.getInstance().getInventory().getProducts();
            TilePane tile = new TilePane();
            tile.setAlignment(Pos.BOTTOM_LEFT);
            tile.setPrefColumns(2);
            tile.setPrefRows(4);
            if (products.size() == 0) {
                Text emptyProduct = new Text("No products?\n"
                        + "You are a failure at farming! :(");
                emptyProduct.setFill(Color.ORANGE);
                emptyProduct.setStyle("-fx-font: 16 chalkduster;");
                newListings.add(emptyProduct);
            } else {
                newListings.add(InventoryListing.getHeader("PRODUCTS"));
                for (HarvestedCrop crop : products) {
                    Button sell = InventoryListing.getProductListingUI(crop);
                    tile.getChildren().add(sell);
                    sell.setOnAction(e -> {
                        GameManager.getInstance().getInventory().sellProduct(crop);
                    });
                }
            }
            newListings.add(tile);
            //Platform.runLater(() -> {
            inventoryScreen.setPadding(new Insets(90, 0, 0, 17));
            inventoryScreen.getChildren().addAll(newListings);
            //});
        } catch (Exception e) {
            System.out.println("Error in setting inventory listings: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
