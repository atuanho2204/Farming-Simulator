package main.farm.plot;

import javafx.scene.control.Button;
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
            text1.setText("Empty & lonely..");
        } else if (plot.getCurrentCrop().getStage() == CropStages.DEAD) {
            text1.setText("Dead");
        } else if (plot.getCurrentCrop().getStage() == CropStages.SPROUTING) {
            text1.setText("growing");
        } else if (plot.getCurrentCrop().getStage() == CropStages.MATURE) {
            text1.setText("mature");
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
        vBox.getChildren().add(text1);
        vBox.getChildren().add(text2);


        Button button1 = new Button("harvest");
        button1.setTextFill(Color.GREEN);
        button1.setOnAction(actionEvent -> {
            //onButtonClick
            plot.harvestPlot();
            controller.updatePlotUI(plot);
        });
        button1.setStyle("-fx-background-color: #18a734;"
                + "-fx-text-align: center; -fx-text-fill: white; -fx-font-family: Chalkduster;"
                + "-fx-font-size: 14px; -fx-min-width: 100px;");
        vBox.getChildren().add(button1);

        return vBox;
    }
}
