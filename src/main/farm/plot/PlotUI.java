package main.farm.plot;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.farm.FarmController;
import main.farm.FarmEquipment;
import main.farm.FarmState;
import main.farm.crops.Crop;
import main.farm.crops.CropStages;
import main.util.AlertUser;

public class PlotUI {
    private static int farmPlotWidth = 575;
    private static int farmPlotHeight = 750;

    public static TilePane getPlotHolderUI() {
        //setup the plotGrid
        TilePane plotGrid = new TilePane();
        plotGrid.setPrefWidth(farmPlotWidth);
        plotGrid.setPrefHeight(farmPlotHeight);

        plotGrid.setPrefTileHeight(farmPlotHeight / 4);
        plotGrid.setPrefTileWidth(farmPlotWidth / 3);

        return plotGrid;
    }

    /**
     * The javaFx code for the plot UI
     *
     * @param plot       the plot for this UI to be based on
     * @param controller the creator of this plot
     * @return VBox (or whatever it's changed to) with the plot UI
     */
    public static StackPane getPlotUI(Plot plot, FarmController controller) {
        Crop crop = plot.getCurrentCrop();
        StackPane sp = new StackPane();
        ImageView backgroundImage = new ImageView(
                new Image("/main/images/plot_dark.png",
                        farmPlotHeight / 4, farmPlotWidth / 3, false, false));
        sp.getChildren().add(backgroundImage);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        vBox.getChildren().add(hbox);
        sp.getChildren().add(vBox);


        Text cropName = new Text();
        if (plot.getCurrentCrop() == null) {
            cropName.setText("Dirt");
            cropName.setFill(Color.BLACK);
        } else {
            String name = plot.getCurrentCrop().getType().toString();
            cropName.setText(name.charAt(0)
                    + name.substring(1, name.length()).toLowerCase()
            );
            cropName.setFill(Color.WHITE);
        }
        cropName.setStyle("-fx-font: 20 chalkduster;");
        vBox.getChildren().add(0, cropName);

        Text cropStage = new Text();
        cropStage.setStyle("-fx-font: 16 chalkduster;");
        if (crop == null) {
            cropStage.setText("Empty");
            cropStage.setFill(Color.BLACK);
        } else {
            if (crop.getStage() == CropStages.DEAD) {
                cropStage.setFill(Color.RED);
            } else if (crop.getStage() == CropStages.SPROUTING) {
                cropStage.setFill(Color.ORANGE);
            } else if (crop.getStage() == CropStages.MATURE) {
                cropStage.setFill(Color.GREEN);
            } else { // CropStages.IMMATURE
                cropStage.setFill(Color.ORANGE);
            }
            cropStage.setText(crop.getStage().toString().toLowerCase());
        }
        hbox.getChildren().add(cropStage);


        //locust check
        if (crop != null && (crop.hasPesticide() || crop.hadLocust())) {
            Button hasPest = new Button();
            ImageView iv;
            if (plot.getCurrentCrop().hasPesticide()) {
                iv = new ImageView(new Image("/main/images/pesticide.jpg"));
            } else {
                iv = new ImageView(new Image("/main/images/locust.png"));
            }
            iv.setFitHeight(20);
            iv.setFitWidth(20);
            hasPest.setGraphic(iv);
            hasPest.setStyle("-fx-background-color: white;");
            hbox.getChildren().addAll(hasPest);
        }


        HBox buttonHolder = new HBox();
        buttonHolder.setAlignment(Pos.CENTER);
        buttonHolder.setSpacing(5);

        // plant and harvest
        if (crop == null || crop.getStage() == CropStages.MATURE
                || crop.getStage() == CropStages.DEAD) {
            Button button = handlePlantAndHarvest(plot, controller);
            buttonHolder.getChildren().add(button);
        }


        //pesticide
        if (crop != null && crop.getStage() != CropStages.MATURE
                && crop.getStage() != CropStages.DEAD) {
            Button pestBut = handlePesticide(plot, controller);
            buttonHolder.getChildren().add(pestBut);
        }
        vBox.getChildren().add(buttonHolder);


        //water
        if (crop != null && crop.getStage() != CropStages.MATURE
                && crop.getStage() != CropStages.DEAD) {
            HBox water = getWaterUI(plot, controller);
            vBox.getChildren().add(water);
        }

        //fertilize button & bar
        if (crop != null && crop.getStage() != CropStages.MATURE
                && crop.getStage() != CropStages.DEAD) {
            HBox fertilize = getFertilizeUI(plot, controller);
            vBox.getChildren().add(fertilize);
        }

        return sp;
    }

    private static HBox getWaterUI(Plot plot, FarmController controller) {
        HBox water = new HBox();

        //button
        Button waterBut = new Button("water");
        waterBut.setOnAction(actionEvent -> {
            //onButtonClick
            //check if we are over the water limit
            FarmEquipment farmEquipment = FarmState.getInstance().getFarmEquipment();
            if (farmEquipment.getCurrentWaterPlots() >= farmEquipment.getMaxWaterPlots()) {
                AlertUser.alertUser("There is no more water left in the tank. You may wait a day or buy irrigation");
                return;
            }
            plot.waterPlot(1);
            controller.updatePlotUI(plot);
            //add 1 to the current farm equipment water level
            farmEquipment.setCurrentWaterPlots(farmEquipment.getCurrentWaterPlots() + 1);
        });
        waterBut.setStyle("-fx-background-color: #00CED1;"
                + "-fx-text-align: center; -fx-text-fill: white;"
                + "-fx-font-family: Chalkduster;"
                + "-fx-font-size: 13px; -fx-min-width: 80px;");


        //progress bar
        ProgressBar waterBar = new ProgressBar(
                plot.getCurrentWater() * 1.0 / plot.getMaxWater());
        if (plot.getCurrentWater() == plot.getMaxWater()) {
            waterBar.setStyle("-fx-accent: #B22222;"); // red at max
        } else if (plot.getCurrentWater() == plot.getMaxWater() - 1) {
            waterBar.setStyle("-fx-accent: #FA8072;"); // orange at max - 1
        } else if (plot.getCurrentWater() >= 4) {
            waterBar.setStyle("-fx-accent: #00BFFF;"); // back to blue at 4
        } else if (plot.getCurrentWater() >= 1) {
            waterBar.setStyle("-fx-accent: #FFD700;"); // yellow
        }


        water.getChildren().addAll(waterBar, waterBut);
        water.setAlignment(Pos.CENTER_LEFT);
        water.setSpacing(5);

        return water;
    }

    public static Button handlePlantAndHarvest(Plot plot, FarmController controller) {
        Button button = new Button();
        if (plot.getCurrentCrop() == null) {
            button.setText("plant");
            button.setOnAction(actionEvent -> {
                plot.plantSeed();
                controller.updatePlotUI(plot);
            });
            button.setStyle("-fx-background-color: BURLYWOOD; -fx-text-align: center;"
                    + "-fx-text-fill: white; -fx-font-family: Chalkduster;"
                    + "-fx-font-size: 13px; -fx-min-width: 50px;");
        } else if (plot.getCurrentCrop().getStage() == CropStages.DEAD
                || plot.getCurrentCrop().getStage() == CropStages.MATURE) {
            if (plot.getCurrentCrop().getStage() == CropStages.DEAD) {
                button.setText("clear");
            } else {
                button.setText("harvest");
            }
            button.setOnAction(actionEvent -> {
                FarmEquipment farmEquipment = FarmState.getInstance().getFarmEquipment();
                if (farmEquipment.getCurrentHarvestPlots() >= farmEquipment.getMaxHarvestPlots()) {
                    AlertUser.alertUser("There is no more plots left to harvest. You may wait a day or buy a tractor");
                    return;
                }
                plot.harvestPlot();
                controller.updatePlotUI(plot);
                farmEquipment.setCurrentHarvestPlots(farmEquipment.getMaxHarvestPlots() + 1);
            });
            button.setStyle("-fx-background-color: #18a734; -fx-text-align: center;"
                    + "-fx-text-fill: white; -fx-font-family: Chalkduster;"
                    + "-fx-font-size: 13px; -fx-min-width: 50px;");
        } else {
            button.setText("harvest");
            button.setStyle("-fx-background-color: #C0C0C0; -fx-text-align: center;"
                    + "-fx-text-fill: white; -fx-font-family: Chalkduster;"
                    + "-fx-font-size: 13px; -fx-min-width: 50px;");
        }
        return button;
    }

    private static HBox getFertilizeUI(Plot plot, FarmController controller) {
        HBox fertilize = new HBox();
        fertilize.setAlignment(Pos.CENTER_LEFT);
        fertilize.setSpacing(5);

        //the "fertilize" button
        Button fertilizeBut = new Button("fertilize");
        fertilizeBut.setOnAction(actionEvent -> {
            plot.fertilizePlot(10);
            controller.updatePlotUI(plot);
        });
        fertilizeBut.setStyle("-fx-background-color: LIGHTPINK;"
                + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                + "-fx-font-size: 13px; -fx-min-width: 70px;");

        //the progressBar
        ProgressBar fertilizerBar = new ProgressBar(
                plot.getCurrentFertilizer() * 1.0 / plot.getMaxFertilizer());
        fertilizerBar.setStyle("-fx-accent: PALEVIOLETRED;");


        fertilize.getChildren().addAll(fertilizerBar, fertilizeBut);
        return fertilize;
    }

    private static Button handlePesticide(Plot plot, FarmController controller) {
        Button pestBut = new Button("pesticide");
        pestBut.setOnAction(actionEvent -> {
            plot.pesticidePlot();
            controller.updatePlotUI(plot);
        });
        pestBut.setStyle("-fx-background-color: BURLYWOOD;"
                + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                + "-fx-font-size: 13px; -fx-min-width: 50px;");
        return pestBut;
    }
}
