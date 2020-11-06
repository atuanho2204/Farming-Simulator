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
        Text pesticide = new Text("Pesticide Tank: "
                + GameManager.getInstance().getInventory().getPesticide() + "/10");
        pesticide.setFill(Color.ANTIQUEWHITE);
        pesticide.setStyle("-fx-font: 16 chalkduster;");


        texts.add(header);
        texts.add(current);
        texts.add(fertilize);
        texts.add(pesticide);
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

    public static Button getProductListingUI(HarvestedCrop crop) {
        //ADD BUTTON TO SELL
        Button sell = new Button(crop.getName() + "\n" + crop.getSellCost());
        sell.setTextFill(Color.ORANGE);
        sell.setPrefWidth(63);
        return sell;
    }
}