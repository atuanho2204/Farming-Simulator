package main.farm;

import main.util.crops.Crop;
import main.util.crops.CropStages;
import main.util.crops.CropTypes;

import javafx.scene.control.Button;

public class Plot {
    private Crop currentCrop;
    private Button plotButton;

    public Plot() {
        this(new Crop(CropTypes.CORN, CropStages.SPROUTING), new Button());
    }

    public Plot(Crop currentCrop, Button plotButton) {
        this.currentCrop = currentCrop;
        this.plotButton = plotButton;
    }

    public void setCurrentCrop(Crop currentCrop) {
        this.currentCrop = currentCrop;
    }

    public void setPlotButton(Button plotButton) {
        this.plotButton = plotButton;
    }

    public Crop getCurrentCrop() {
        return currentCrop;
    }

    public Button getPlotButton() {
        return plotButton;
    }
}