package main.util.crops;

public class Crop {
    private CropTypes type;
    private CropStage cropStage;

    public Crop(CropTypes type) {
        this.type = type;
        this.cropStage = CropStage.SPROUTING;
    }

    public Crop(CropTypes type, CropStage cropStage) {
        this.type = type;
        this.cropStage = cropStage;
    }

    public CropTypes getType() {
        return type;
    }

    public CropStage getStage() {
        return cropStage;
    }

    public void setCropStage(CropStage cropStage) {
        this.cropStage = cropStage;
    }
}