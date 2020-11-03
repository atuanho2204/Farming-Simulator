package main.inventory;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.gameManager.GameManager;
import main.inventory.inventoryItems.HarvestedCrop;

import java.util.ArrayList;

public class InventoryListing {
    public static ArrayList<Text> getInfoUI(Inventory inventory) {
        ArrayList<Text> texts = new ArrayList<>();

        Text header = new Text("Inventory:");
        header.setFill(Color.ANTIQUEWHITE);
        header.setStyle("-fx-font: 20 chalkduster;");

        // Inventory Information
        Text current = new Text("Storage: " + inventory.getStorageSize()
                + "/" + inventory.getStorageLimit());
        current.setFill(Color.YELLOW);
        current.setStyle("-fx-font: 16 chalkduster;");

        //fertilizer information
        Text fertilize = new Text("Fertilizer Tank: "
                + GameManager.getInstance().getInventory().getFertilizer() + "/10");
        fertilize.setFill(Color.ANTIQUEWHITE);
        fertilize.setStyle("-fx-font: 16 chalkduster;");

        //pesticide information
        //PUT THE STUFF HERE @CHRIS


        texts.add(header);
        texts.add(current);
        texts.add(fertilize);
        //hBox.setAlignment(Pos.CENTER);
        return texts;
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
}