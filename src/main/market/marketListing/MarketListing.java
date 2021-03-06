package main.market.marketListing;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import main.farm.FarmState;
import main.farm.crops.CropStages;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.inventory.inventoryItems.InventoryItem;
import main.inventory.inventoryItems.Seed;
import main.market.Market;
import javafx.scene.paint.Color;



public class MarketListing {

    private static int plotPrice = 40 + 10 * (GameManager.getInstance().getDifficulty());
    public static GridPane getListingUI(InventoryItem listing) {
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(140)); // column 0
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 1
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 2
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 3
        grid.setAlignment(Pos.CENTER_LEFT);
        int col = 0;
        Text name = new Text(listing.getName() + ":");
        name.setFill(Color.WHITE);
        name.setStyle("-fx-font: 16 chalkduster;");
        grid.add(name, col, 0);
        Text cost = new Text("$" + listing.getBuyCost());
        cost.setFill(Color.WHITE);
        cost.setStyle("-fx-font: 16 chalkduster;");

        Button sell = new Button("Sell");
        sell.setStyle("-fx-font: 11 chalkduster; -fx-text-fill: RED;");
        grid.add(cost, ++col, 0);

        Button buy = new Button("Buy");
        buy.setStyle("-fx-font: 11 chalkduster; -fx-text-fill: GREEN;");
        buy.setOnAction(e -> {
            Market.buySeed(((Seed) listing).getType(), listing.getBuyCost());
        });
        grid.add(buy, ++col, 0);
        sell.setOnAction(e -> {
            Market.sellSeed(((Seed) listing).getType(), listing.getSellCost());
        });
        grid.add(sell, ++col, 0);
        grid.setPadding(new Insets(2, 0, 2, 0));
        return grid;
    }

    public static GridPane getPesticideUI() {
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(140)); // column 0
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 1
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 2
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 3
        int col = 0;
        int difficultySupplement = GameManager.getInstance().getDifficulty();
        int price = 10 + 5 * (difficultySupplement);
        Text name = new Text("Pesticide:");
        name.setFill(Color.WHITE);
        name.setStyle("-fx-font: 16 chalkduster;");
        grid.add(name, col, 0);
        Text cost = new Text("$" + price);
        cost.setFill(Color.WHITE);
        cost.setStyle("-fx-font: 16 chalkduster;");
        grid.add(cost, ++col, 0);
        Button buy = new Button("Buy");
        buy.setStyle("-fx-font: 11 chalkduster; -fx-text-fill: GREEN;");
        buy.setOnAction(e -> {
            Market.buyPesticide(20);
            GameManager.getInstance().getInventory().getPesticide();
        });
        grid.add(buy, ++col, 0);
        // Secret button to kill all crops for testing purposes
        Button kill = new Button();
        kill.setStyle("-fx-background-color: transparent");
        kill.setOnAction(e -> {
            for (Plot plot : FarmState.getInstance().getPlots()) {
                if (plot.getCurrentCrop() != null) {
                    plot.getCurrentCrop().setCropStage(CropStages.DEAD);
                    FarmState.getInstance().forcePlotUpdate("Test game over.");
                }
            }
        });
        grid.add(kill, ++col, 0);
        grid.setPadding(new Insets(2, 0, 2, 0));
        return grid;
    }

    public static GridPane getFertilizeUI() {
        // text and button to fill Pesticide tank
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(140)); // column 0
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 1
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 2
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 3
        int col = 0;
        int difficultySupplement = GameManager.getInstance().getDifficulty();
        int price = 10 + 5 * (difficultySupplement);
        Text name = new Text("Fertilizer:");
        name.setFill(Color.WHITE);
        name.setStyle("-fx-font: 16 chalkduster;");
        grid.add(name, col, 0);
        Text cost = new Text("$" + price);
        cost.setFill(Color.WHITE);
        cost.setStyle("-fx-font: 16 chalkduster;");
        grid.add(cost, ++col, 0);
        Button buy = new Button("Buy");
        buy.setStyle("-fx-font: 11 chalkduster; -fx-text-fill: GREEN;");
        buy.setOnAction(e -> {
            Market.buyFertilizer(price);
        });
        grid.add(buy, ++col, 0);
        grid.setPadding(new Insets(2, 0, 2, 0));
        return grid;
    }

    public static GridPane getIrrigationUI() {
        // text and button to buy irrigation
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(140)); // column 0
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 1
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 2
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 3
        int col = 0;
        int price = 40 + 10 * (GameManager.getInstance().getDifficulty());
        Text name = new Text("Irrigation:");
        name.setFill(Color.WHITE);
        name.setStyle("-fx-font: 16 chalkduster;");
        grid.add(name, col, 0);
        Text cost = new Text("$" + price);
        cost.setFill(Color.WHITE);
        cost.setStyle("-fx-font: 16 chalkduster;");
        grid.add(cost, ++col, 0);
        Button buy = new Button("Buy");
        buy.setStyle("-fx-font: 11 chalkduster; -fx-text-fill: GREEN;");
        buy.setOnAction(e -> {
            Market.buyIrrigation(price);
        });
        grid.add(buy, ++col, 0);
        grid.setPadding(new Insets(2, 0, 2, 0));
        return grid;
    }

    public static GridPane getTractorUI() {
        // text and button to buy Tractor
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(140)); // column 0
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 1
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 2
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 3
        int col = 0;
        int price = 40 + 10 * (GameManager.getInstance().getDifficulty());
        Text name = new Text("Tractor:");
        name.setFill(Color.WHITE);
        name.setStyle("-fx-font: 16 chalkduster;");
        grid.add(name, col, 0);
        Text cost = new Text("$" + price);
        cost.setFill(Color.WHITE);
        cost.setStyle("-fx-font: 16 chalkduster;");
        grid.add(cost, ++col, 0);
        Button buy = new Button("Buy");
        buy.setStyle("-fx-font: 11 chalkduster; -fx-text-fill: GREEN;");
        buy.setOnAction(e -> {
            Market.buyTractor(price);
        });
        grid.add(buy, ++col, 0);
        grid.setPadding(new Insets(2, 0, 2, 0));
        return grid;
    }

    public static GridPane getPlotUI(int price) {
        // text and button to buy Plot
        GridPane grid = new GridPane();
        grid.getColumnConstraints().add(new ColumnConstraints(140)); // column 0
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 1
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 2
        grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 3
        int col = 0;
        //int price = 40 + 10 * (GameManager.getInstance().getDifficulty());
        Text name = new Text("New Plot:");
        name.setFill(Color.WHITE);
        name.setStyle("-fx-font: 16 chalkduster;");
        grid.add(name, col, 0);
        Text cost = new Text("$" + price);
        cost.setFill(Color.WHITE);
        cost.setStyle("-fx-font: 16 chalkduster;");
        grid.add(cost, ++col, 0);
        Button buy = new Button("Buy");
        buy.setStyle("-fx-font: 11 chalkduster; -fx-text-fill: GREEN;");
        buy.setOnAction(e -> {
            //Market.buyPlot(price);
        });
        grid.add(buy, ++col, 0);
        grid.setPadding(new Insets(2, 0, 2, 0));
        return grid;
    }
}
