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
    public static StackPane getPlotUI(Plot plot, FarmController controller) {
        VBox vBox = new VBox();
        //vBox.setStyle("-fx-background-color: #CD853F; -fx-border-color: black;");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        Text cropName = new Text();
        if (plot.getCurrentCrop() == null) {
            cropName.setText("dirt");
            cropName.setFill(Color.BLACK);
        } else {
            cropName.setText(plot.getCurrentCrop().getType().toString());
            cropName.setFill(Color.WHITE);
        }
        cropName.setStyle("-fx-font: 20 chalkduster;");
        vBox.getChildren().add(cropName);

        Text stage = new Text();
        if (plot.getCurrentCrop() == null) {
            stage.setText("empty & lonely..");
            stage.setFill(Color.BLACK);
        } else if (plot.getCurrentCrop().getStage() == CropStages.DEAD) {
            stage.setText("dead");
            stage.setFill(Color.RED);
        } else if (plot.getCurrentCrop().getStage() == CropStages.SPROUTING) {
            stage.setText("sprouting");
            stage.setFill(Color.ORANGE);
        } else if (plot.getCurrentCrop().getStage() == CropStages.MATURE) {
            stage.setText("mature");
            stage.setFill(Color.GREEN);
        } else { // CropStages.IMMATURE
            stage.setText("growing");
            stage.setFill(Color.ORANGE);
        }
        stage.setStyle("-fx-font: 16 chalkduster;");
        HBox hbox = new HBox();
        hbox.getChildren().addAll(stage);
        if (plot.getCurrentCrop() != null
                && (plot.getCurrentCrop().hasPesticide()
                || plot.getCurrentCrop().hadLocust())) {
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
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        vBox.getChildren().add(hbox);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(5);

        // plant and harvest
        Button button = handlePlantAndHarvest(plot, controller);
        buttons.getChildren().add(button);

        //pesticide
        Button pestBut = handlePesticide(plot, controller);
        buttons.getChildren().add(pestBut);

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
        fertilizerBar.setStyle("-fx-accent: PALEVIOLETRED;");


        fertilize.getChildren().addAll(fertilizerBar, fertilizeBut);
        vBox.getChildren().addAll(buttons, water, fertilize);

        StackPane sp = new StackPane();
        ImageView iv = new ImageView(new Image("/main/images/plot_dark.png"));
        iv.setFitHeight(187.5);
        iv.setFitWidth(191.5);
        sp.getChildren().addAll(iv, vBox);
        return sp;
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
        plantBut.setStyle("-fx-background-color: LIGHTPINK;"
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
        pestBut.setStyle("-fx-background-color: BURLYWOOD;"
                + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                + "-fx-font-size: 13px; -fx-min-width: 50px;");
        return pestBut;
    }
}
