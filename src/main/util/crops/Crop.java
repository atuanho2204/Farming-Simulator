package main.util.crops;

public class Crop {
    private CropTypes cropType;
    private CropStage cropStage;

    public Crop(CropTypes cropType) {
        this.cropType = cropType;
        this.cropStage = CropStage.SPROUTING;
    }

    public Crop(CropTypes cropType, CropStage cropStage) {
        this.cropType = cropType;
        this.cropStage = cropStage;
    }

    public CropTypes getCropType() {
        return cropType;
    }

    public CropStage getStage() {
        return cropStage;
    }

    public void setCropStage(CropStage cropStage) {
        this.cropStage = cropStage;
    }

    public void setCropType(CropTypes cropType) {
        this.cropType = cropType;
    }
}