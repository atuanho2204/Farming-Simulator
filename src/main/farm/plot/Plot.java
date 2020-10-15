package main.farm.plot;

import main.gameManager.GameManager;
import main.util.AlertUser;
import main.util.crops.Crop;
import main.util.crops.CropStages;
import main.util.crops.CropTypes;

public class Plot {
    private Crop currentCrop;
    private int currentWater = 0;
    private int maxWater = 10;

    public Plot() {
        this(new Crop(CropTypes.CORN, CropStages.SPROUTING));
    }

    public Plot(Crop currentCrop) {
        this.currentCrop = currentCrop;
    }

    public void setCurrentCrop(Crop currentCrop) {
        this.currentCrop = currentCrop;
    }

    public Crop getCurrentCrop() {
        return currentCrop;
    }

    /**
     * Harvests the plot.
     */
    public void harvestPlot() {
        if (currentCrop == null) {
            //the plot is empty
            return;
        } else if (currentCrop.getStage() == CropStages.DEAD) {
            currentCrop = null;
            return;
        } else if (currentCrop.getStage() == CropStages.MATURE) {
            //try to harvest the mature crop
            try {
                GameManager.getInstance().getInventory().putProduct(currentCrop.getType());
                currentCrop = null;
                return;
            } catch (Exception e) {
                AlertUser.alertUser("Storage is full!!!");
            }
        }
        //its not time to harvest yet
    }

    public int getMaxWater() {
        return maxWater;
    }

    public void setMaxWater(int maxWater) {
        this.maxWater = maxWater;
    }

    public int getCurrentWater() {
        return currentWater;
    }

    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }
}