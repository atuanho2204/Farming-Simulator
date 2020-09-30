package main.util;

public class Crop {
    private CropTypes type;
    private CropStage cropStage;


    public Crop(CropTypes type, CropStage cropStage) {
        this.type = type;
        this.cropStage = cropStage;
    }

    public CropTypes getType() {
        return type;
    }

    public CropStage getStage() { return cropStage; }
}