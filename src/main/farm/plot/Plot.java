package main.farm.plot;

import javafx.scene.control.ProgressBar;
import main.gameManager.GameManager;
import main.util.AlertUser;
import main.util.crops.Crop;
import main.util.crops.CropStages;
import main.util.crops.CropTypes;

public class Plot {
    private Crop currentCrop;
    private int currentWater = 5;
    private final int maxWater = 10;
    private ProgressBar waterBar;

    public Plot() {
        // random water level from 4 to 8
        this(new Crop(CropTypes.CORN, CropStages.SPROUTING),
                (int) (Math.random() * (8 - 4 + 1)) + 4);
    }

    public Plot(Crop currentCrop, int currentWater) {
        this.currentCrop = currentCrop;
        this.currentWater = currentWater;
        this.waterBar = new ProgressBar(currentWater * 1.0 / maxWater);
        this.waterBar.setStyle("-fx-accent: #00BFFF;"); // blue
    }

    public void setCurrentCrop(Crop currentCrop) {
        this.currentCrop = currentCrop;
    }

    public Crop getCurrentCrop() {
        return currentCrop;
    }

    /**
     * Water the plot
     */
    public void waterPlot() {
        // cannot water if currentWater == 0 or max
        if (currentWater % maxWater == 0) {
            return;
        }
        currentWater++;
        if (currentWater == maxWater) {
            waterBar.setStyle("-fx-accent: #B22222;"); // red at 10
            currentCrop.setCropStage(CropStages.DEAD);
        } else if (currentWater == maxWater - 1) {
            waterBar.setStyle("-fx-accent: #FA8072;"); // orange at 9
        } else if (currentWater == 3) {
            this.waterBar.setStyle("-fx-accent: #00BFFF;"); // back to blue at 3
        }
        waterBar.setProgress(currentWater  * 1.0 / maxWater);
    }

    /**
     * Harvests the plot
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
                GameManager.getInstance().getInventory().putProduct(
                        currentCrop.getType());
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

    public int getCurrentWater() {
        return currentWater;
    }

    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }

    public ProgressBar getWaterBar() {
        return waterBar;
    }


}