package main.farm.plot;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.farm.FarmController;
import main.util.crops.CropStages;

public class PlotUI {
    public static TilePane getPlotHolderUI() {
        //setup the plotGrid
        TilePane plotGrid = new TilePane();
        plotGrid.setPrefWidth(880);
        plotGrid.setPrefTileHeight(200);
        plotGrid.setPrefTileWidth(220);

        return plotGrid;
    }

    /**
     * The javaFx code for the plot UI
     *
     * @param plot the plot for this UI to be based on
     * @param controller the creator of this plot
     * @return VBox (or whatever it's changed to) with the plot UI
     */
    public static VBox getPlotUI(Plot plot, FarmController controller) {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: gray;-fx-border-color: black;");

        Text text1 = new Text();
        if (plot.getCurrentCrop() == null) {
            text1.setText("empty & lonely..");
        } else if (plot.getCurrentCrop().getStage() == CropStages.DEAD) {
            text1.setText("dead");
        } else if (plot.getCurrentCrop().getStage() == CropStages.SPROUTING) {
            text1.setText("sprouting");
        } else if (plot.getCurrentCrop().getStage() == CropStages.MATURE) {
            text1.setText("mature");
        } else { // CropStages.IMMATURE
            text1.setText("growing");
        }
        Text text2 = new Text();
        if (plot.getCurrentCrop() == null) {
            text2.setText("dirt");
        } else {
            text2.setText(plot.getCurrentCrop().getType().toString().toLowerCase());
        }
        text1.setFill(Color.WHITE);
        text2.setFill(Color.WHITE);
        text1.setStyle("-fx-font: 16 chalkduster;");
        text2.setStyle("-fx-font: 16 chalkduster;");
        vBox.getChildren().add(text2);
        vBox.getChildren().add(text1);

        HBox buttons = new HBox();
        // water
        Button waterBut = new Button("water");
        waterBut.setOnAction(actionEvent -> {
            //onButtonClick
            plot.harvestPlot();
            controller.updatePlotUI(plot);
        });
        waterBut.setStyle("-fx-background-color: #00CED1;"
                + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                + "-fx-font-size: 13px; -fx-min-width: 50px;");
        buttons.getChildren().add(waterBut);

        // harvest
        Button harvestBut = new Button("harvest");
        harvestBut.setOnAction(actionEvent -> {
            //onButtonClick
            plot.harvestPlot();
            controller.updatePlotUI(plot);
        });
        harvestBut.setStyle("-fx-background-color: #18a734;"
                + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                + "-fx-font-size: 13px; -fx-min-width: 50px;");
        buttons.getChildren().add(harvestBut);

        // plant
        Button plantBut = new Button("plant");
        plantBut.setOnAction(actionEvent -> {
            //onButtonClick
        });
        plantBut.setStyle("-fx-background-color: #A0522D;"
                + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                + "-fx-font-size: 13px; -fx-min-width: 50px;");
        buttons.getChildren().add(plantBut);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(5);

        //ProgressIndicator pi = new ProgressIndicator(0.6);
        ProgressBar pb = new ProgressBar(0.6);

        vBox.getChildren().addAll(buttons, pb);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        return vBox;
    }
}
