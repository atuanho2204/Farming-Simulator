package main.farm;

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
}
