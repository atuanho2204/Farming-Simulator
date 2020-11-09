package main.farm.plot;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.farm.FarmController;
import main.farm.crops.CropStages;

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
    public static VBox getPlotUI(Plot plot, FarmController controller) {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #CD853F; -fx-border-color: black;");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        Text text1 = new Text();
        if (plot.getCurrentCrop() == null) {
            text1.setText("empty & lonely..");
            text1.setFill(Color.BLACK);
        } else if (plot.getCurrentCrop().getStage() == CropStages.DEAD) {
            text1.setText("dead");
            text1.setFill(Color.RED);
        } else if (plot.getCurrentCrop().getStage() == CropStages.SPROUTING) {
            text1.setText("sprouting");
            text1.setFill(Color.ORANGE);
        } else if (plot.getCurrentCrop().getStage() == CropStages.MATURE) {
            text1.setText("mature");
            text1.setFill(Color.GREEN);
        } else { // CropStages.IMMATURE
            text1.setText("growing");
            text1.setFill(Color.ORANGE);
        }

        Text text2 = new Text();
        if (plot.getCurrentCrop() == null) {
            text2.setText("dirt");
            text2.setFill(Color.BLACK);
        } else {
            text2.setText(plot.getCurrentCrop().getType().toString());
            text2.setFill(Color.WHITE);
        }
        text1.setStyle("-fx-font: 16 chalkduster;");
        text2.setStyle("-fx-font: 20 chalkduster;");
        vBox.getChildren().add(text2);
        vBox.getChildren().add(text1);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(5);

        // plant and harvest
        Button button = handlePlantAndHarvest(plot, controller);
        buttons.getChildren().add(button);

        //pesticide
        Button pestBut = handlePesticide(plot, controller);
        Button hasPest = new Button();
        if (plot.getCurrentCrop() != null && plot.getCurrentCrop().hasPesticide()) {
            hasPest.setText("P");
        }
        buttons.getChildren().addAll(pestBut, hasPest);

        //water
        HBox water = new HBox();
        Button waterBut = handleWaterPlot(plot, controller);
        ProgressBar waterBar = new ProgressBar(
                plot.getCurrentWater() * 1.0 / plot.getMaxWater());
        waterBar.setStyle("-fx-accent: #00BFFF;"); // blue
        water.getChildren().addAll(waterBar, waterBut);
        water.setAlignment(Pos.CENTER_LEFT);
        water.setSpacing(5);

        if (plot.getCurrentWater() == plot.getMaxWater()) {
            waterBar.setStyle("-fx-accent: #B22222;"); // red at max
        } else if (plot.getCurrentWater() == plot.getMaxWater() - 1) {
            waterBar.setStyle("-fx-accent: #FA8072;"); // orange at max - 1
        } else if (plot.getCurrentWater() >= 4) {
            waterBar.setStyle("-fx-accent: #00BFFF;"); // back to blue at 4
        } else if (plot.getCurrentWater() >= 1) {
            waterBar.setStyle("-fx-accent: #FFD700;"); // yellow
        }

        HBox fertilize = new HBox();
        fertilize.setAlignment(Pos.CENTER_LEFT);
        fertilize.setSpacing(5);

        Button fertilizeBut = handleFertilize(plot, controller);

        //fertilizer
        ProgressBar fertilizerBar = new ProgressBar(
                plot.getCurrentFertilizer() * 1.0 / plot.getMaxFertilizer());
        fertilizerBar.setStyle("-fx-accent: #00BFFF;"); // blue


        fertilize.getChildren().addAll(fertilizerBar, fertilizeBut);
        vBox.getChildren().addAll(buttons, water, fertilize);
        return vBox;
    }

    private static Button handleWaterPlot(Plot plot, FarmController controller) {
        Button waterBut = new Button("water");
        waterBut.setOnAction(actionEvent -> {
            //onButtonClick
            plot.waterPlot(1);
            controller.updatePlotUI(plot);
        });
        waterBut.setStyle("-fx-background-color: #00CED1;"
                + "-fx-text-align: center; -fx-text-fill: white;"
                + "-fx-font-family: Chalkduster;"
                + "-fx-font-size: 13px; -fx-min-width: 80px;");
        return waterBut;
    }

    public static Button handlePlantAndHarvest(Plot plot, FarmController controller) {
        Button button = new Button();
        if (plot.getCurrentCrop() == null) {
            button.setText("plant");
            button.setOnAction(actionEvent -> {
                plot.plantSeed();
                controller.updatePlotUI(plot);
            });
            button.setStyle("-fx-background-color: #A0522D; -fx-text-align: center;"
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
                plot.harvestPlot();
                controller.updatePlotUI(plot);
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

    private static Button handleFertilize(Plot plot, FarmController controller) {
        Button plantBut = new Button("fertilize");
        plantBut.setOnAction(actionEvent -> {
            plot.fertilizePlot(10);
            controller.updatePlotUI(plot);
        });
        plantBut.setStyle("-fx-background-color: #A0522D;"
                + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                + "-fx-font-size: 13px; -fx-min-width: 70px;");
        return plantBut;
    }

    private static Button handlePesticide(Plot plot, FarmController controller) {
        Button pestBut = new Button("pesticide");
        pestBut.setOnAction(actionEvent -> {
            plot.pesticidePlot();
            controller.updatePlotUI(plot);
        });
        pestBut.setStyle("-fx-background-color: #A0522D;"
                + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                + "-fx-font-size: 13px; -fx-min-width: 50px;");
        return pestBut;
    }
}
