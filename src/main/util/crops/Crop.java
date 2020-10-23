package main.util.crops;

import main.gameManager.GameManager;

public class Crop {
    private CropTypes type;
    private CropStages stage;
    private int plantDay;

    public Crop(CropTypes type) {
        this(type, CropStages.SPROUTING);
    }

    public Crop(CropTypes type, CropStages stage) {
        this.type = type;
        this.stage = stage;
        this.plantDay = GameManager.getInstance().getDay();
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

    public void setPlantDay(int day) { this.plantDay = day; }

    public int getPlantDay() { return this.plantDay; }
}