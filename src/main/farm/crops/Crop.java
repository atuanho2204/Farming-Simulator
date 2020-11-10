package main.farm.crops;

import main.gameManager.GameManager;

public class Crop {
    private CropTypes type;
    private CropStages stage;
    private int plantDay;
    private boolean pesticide = false;
    private boolean locust = false;

    public Crop(CropTypes type) {
        this(type, CropStages.SPROUTING, false);
    }

    public Crop(CropTypes type, CropStages stage, boolean pesticide) {
        this.type = type;
        this.stage = stage;
        this.plantDay = GameManager.getInstance().getDay();
        this.pesticide = false;
        this.locust = false;
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

    public void setPlantDay(int day) {
        this.plantDay = day;
    }

    public int getPlantDay() {
        return this.plantDay;
    }

    public boolean hasPesticide() {
        return pesticide;
    }

    public void setPesticide(boolean hasPesticide) {
        this.pesticide = hasPesticide;
    }

    public void setLocust(boolean locust) {
        this.locust = locust;
    }

    public boolean hadLocust() {
        return locust;
    }
}