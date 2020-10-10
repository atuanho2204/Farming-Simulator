package main.util.crops;

public class Crop {
    private CropTypes type;
    private CropStages stage;

    public Crop(CropTypes type) {
        this.type = type;
        this.stage = CropStages.SPROUTING;
    }

    public Crop(CropTypes type, CropStages stage) {
        this.type = type;
        this.stage = stage;
    }

    public CropTypes getType() {
        return type;
    }

    public CropStages getStage() {
        return stage;
    }

    public void setCropStage(CropStages cropStages) {
        this.stage = cropStages;
    }

    public void setType(CropTypes type) {
        this.type = type;
    }
}