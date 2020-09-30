/*package main.farm;

import com.sun.javafx.geom.Vec2d;
import main.util.Crop;
import main.util.CropTypes;

public class Plot {
    private Vec2d position;
    private Crop currentCrop;

    public Plot() {
        this(new Vec2d(0,0), new Crop(CropTypes.CORN));
    }

    public Plot(Vec2d position, Crop currentCrop) {
        this.position = position;
        this.currentCrop = currentCrop;
    }

    public void setCurrentCrop(Crop currentCrop) {
        this.currentCrop = currentCrop;
    }

    public Crop getCurrentCrop() {
        return currentCrop;
    }

    public Vec2d getPosition() {
        return position;
    }
}*/

package main.farm;

import main.util.Crop;
import main.util.CropStage;
import main.util.CropTypes;

import javafx.scene.control.Button;

public class Plot {
    private Crop currentCrop;
    private Button plotButton;

    public Plot() {
        this(new Crop(CropTypes.CORN, CropStage.SPROUTING), new Button());
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