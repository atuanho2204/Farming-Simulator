package main.farm.plot;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.farm.FarmController;
import main.farm.FarmEquipment;
import main.farm.FarmState;
import main.farm.crops.Crop;
import main.farm.crops.CropStages;
import main.gameManager.GameManager;
import main.util.AlertUser;
import main.util.UIManager;

public class PlotUI {
    private static int farmPlotWidth = 575;
    private static int farmPlotHeight = 750;
    private static ImageView cropImage;

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
        StackPane sp = new StackPane();

        Crop crop = plot.getCurrentCrop();
        String cropName = "";
        String cropStage = "";
        StringBuilder cropImgPath = new StringBuilder("/main/images/crops/");
        if (crop == null) {
            cropImgPath.append("empty.jpg");//
        } else {
            cropName = crop.getType().toString().toLowerCase();
            cropStage = plot.getCurrentCrop().getStage().toString().toLowerCase();
            cropImgPath.append(cropName);//
            cropImgPath.append(cropStage);//
            cropImgPath.append(".jpg");
        }
        cropImage = new ImageView(new Image(cropImgPath.toString(),
                farmPlotHeight / 4, farmPlotWidth / 3, false, false));
        // Tooltip.install(cropImage, new Tooltip(cropName + " (" + cropStage + ")"));
        sp.getChildren().add(cropImage);

        VBox vBox = new VBox();
        sp.getChildren().add(vBox);

        HBox pl = new HBox(); // pl = pesticide + locust
        vBox.getChildren().add(pl);
        pl.setMinHeight(35);
        pl.setAlignment(Pos.TOP_LEFT);
        pl.setStyle("-fx-background-color: transparent;");

        if (crop != null) {
            //pesticide
            if (crop.getStage() != CropStages.MATURE
                    && crop.getStage() != CropStages.DEAD
                    && !crop.hasPesticide()) {
                pl.getChildren().add(handlePesticide(plot, controller));
            }
            // locust
            if (crop.hadLocust()) {
                Button hasPest = new Button();
                ImageView iv = new ImageView(new Image("/main/images/locust.png"));
                iv.setFitHeight(25);
                iv.setFitWidth(25);
                hasPest.setGraphic(iv);
                hasPest.setStyle("-fx-background-color: transparent;");
                pl.getChildren().add(hasPest);
            }
        }

        // ToolTip crop name and crop stage
        Button cropInfo = new Button();
        vBox.getChildren().add(cropInfo);
        cropInfo.setTooltip(new Tooltip(cropName + " (" + cropStage + ")"));
        cropInfo.setStyle("-fx-background-color: transparent;"
                + "-fx-min-width: 100; -fx-min-height: 100;");
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(0, 0, 2, 0));
        vBox.setSpacing(2);

        // plant and harvest
        if (crop == null || crop.getStage() == CropStages.MATURE
                || crop.getStage() == CropStages.DEAD) {
            vBox.getChildren().add(handlePlantAndHarvest(plot, controller));
        }

        //water
        if (crop != null && crop.getStage() != CropStages.MATURE
                && crop.getStage() != CropStages.DEAD) {
            vBox.getChildren().add(getWaterUI(plot, controller));
        }

        //fertilize
        if (crop != null && crop.getStage() != CropStages.MATURE
                && crop.getStage() != CropStages.DEAD) {
            vBox.getChildren().add(getFertilizeUI(plot, controller));
        }

        return sp;
    }

    private static HBox getWaterUI(Plot plot, FarmController controller) {
        HBox water = new HBox();

        //button
        Button waterBut = new Button("W");
        waterBut.setStyle("-fx-background-color: #00CED1;"
                + "-fx-text-align: center; -fx-text-fill: white;"
                + "-fx-font-family: Chalkduster;"
                + "-fx-font-size: 8px;");
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

        waterBar.setPrefWidth(100);
        waterBar.setPrefHeight(15);
        water.getChildren().addAll(waterBar, waterBut);
        water.setAlignment(Pos.CENTER);
        water.setSpacing(5);
        return water;
    }

    public static Button handlePlantAndHarvest(Plot plot, FarmController controller) {
        Button button = new Button();
        if (plot.getCurrentCrop() == null) {
            if (!plot.getPurchased()) {
                int price = plot.getPrice();
                Text text = new Text();
                text.setText("$" + price);
                button.setText("Buy $" + price);
                button.setStyle("-fx-background-color: GREY; -fx-text-align: center;"
                        + "-fx-text-fill: white; -fx-font-family: Chalkduster;"
                        + "-fx-font-size: 10px; -fx-min-width: 50px;");
                button.setOnAction(actionEvent -> {
                    int currentMoney = GameManager.getInstance().getMoney();
                    int count = FarmState.getInstance().getPlotCount();
                    if (currentMoney >= price && plot.getOpenIdx() == count ) {
                        plot.setPurchased(true);
                        GameManager.getInstance().setMoney(currentMoney - price);
                        FarmState.getInstance().increasePlotCount();
                        UIManager.getInstance().pushUIUpdate();
                        controller.updatePlotUI(plot);
                    } else if (plot.getOpenIdx() != count) {
                        AlertUser.alertUser("You can't buy this");
                    } else {
                        AlertUser.alertUser("You don't have enough money");
                    }
                });
            } else {
                button.setText("plant");
                button.setOnAction(actionEvent -> {
                    plot.plantSeed();
                    controller.updatePlotUI(plot);
                });
                button.setStyle("-fx-background-color: BROWN; -fx-text-align: center;"
                        + "-fx-text-fill: white; -fx-font-family: Chalkduster;"
                        + "-fx-font-size: 10px; -fx-min-width: 50px;");
            }
        } else if (plot.getCurrentCrop().getStage() == CropStages.DEAD
                || plot.getCurrentCrop().getStage() == CropStages.MATURE) {
            if (plot.getCurrentCrop().getStage() == CropStages.DEAD) {
                button.setText("clear");
                button.setStyle("-fx-background-color: BLACK; -fx-text-align: center;"
                        + "-fx-text-fill: white; -fx-font-family: Chalkduster;"
                        + "-fx-font-size: 10px; -fx-min-width: 50px;");
            } else {
                button.setText("harvest");
                button.setStyle("-fx-background-color: #18a734; -fx-text-align: center;"
                        + "-fx-text-fill: white; -fx-font-family: Chalkduster;"
                        + "-fx-font-size: 10px; -fx-min-width: 50px;");
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
        }
        return button;
    }

    private static HBox getFertilizeUI(Plot plot, FarmController controller) {
        HBox fertilize = new HBox();
        fertilize.setAlignment(Pos.CENTER);
        fertilize.setSpacing(5);

        //the "fertilize" button
        Button fertilizeBut = new Button("F");
        fertilizeBut.setStyle("-fx-background-color: LIGHTPINK;"
                + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                + "-fx-font-size: 8px;");
        fertilizeBut.setOnAction(actionEvent -> {
            plot.fertilizePlot(10);
            controller.updatePlotUI(plot);
        });

        //the progressBar
        ProgressBar fertilizerBar = new ProgressBar(
                plot.getCurrentFertilizer() * 1.0 / plot.getMaxFertilizer());
        fertilizerBar.setStyle("-fx-accent: PALEVIOLETRED;");
        fertilizerBar.setPrefWidth(100);
        fertilizerBar.setPrefHeight(15);

        fertilize.getChildren().addAll(fertilizerBar, fertilizeBut);
        return fertilize;
    }

    private static Button handlePesticide(Plot plot, FarmController controller) {
        Button pestBut = new Button();
        ImageView iv = new ImageView(new Image("/main/images/pesticide.jpg"));
        iv.setFitWidth(25);
        iv.setFitHeight(25);
        pestBut.setGraphic(iv);
        pestBut.setStyle("-fx-background-color: white;");
        pestBut.setOnAction(actionEvent -> {
            plot.pesticidePlot();
            controller.updatePlotUI(plot);
        });
        return pestBut;
    }
}
