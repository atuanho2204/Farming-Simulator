package main.farm.plot;

import javafx.scene.control.ProgressBar;
import main.gameManager.GameManager;
import main.util.AlertUser;
import main.util.crops.Crop;
import main.util.crops.CropStages;
import main.util.crops.CropTypes;

import java.util.Map;

public class Plot {
    private Crop currentCrop;
    private int currentWater = 5;
    private final int maxWater = 10;
    private ProgressBar waterBar;

    public Plot() {
        // random water level from 5 to 15
        this(new Crop(CropTypes.CORN, CropStages.SPROUTING),
                (int) (Math.random() * (8 - 5 + 1)) + 5);
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
            waterBar.setStyle("-fx-accent: #B22222;"); // red at max
            if (currentCrop != null) {
                currentCrop.setCropStage(CropStages.DEAD);
            }
        } else if (currentWater == maxWater - 1) {
            waterBar.setStyle("-fx-accent: #FA8072;"); // orange at max - 1
        } else if (currentWater == 4) {
            this.waterBar.setStyle("-fx-accent: #00BFFF;"); // back to blue at 4
        }
        waterBar.setProgress(currentWater * 1.0 / maxWater);
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

    /**
     * Plants the seed in plot
     */
    public void plantSeed() {
        if (currentCrop != null) {
            //the plot is not ready to plant
            return;
        } else {
            //plot is empty and ready
            try {
                //Find first seed in inventory; plant seed & decrease seed inventory
                Map<CropTypes, Integer> seedItems =
                        GameManager.getInstance().getInventory().getListOfSeedItems();
                CropTypes type = seedItems.entrySet().iterator().next().getKey();
                GameManager.getInstance().getInventory().removeSeed(type);
                currentCrop = new Crop(type);
                if (currentWater % maxWater == 0) {
                    currentWater = 5;
                    this.waterBar.setStyle("-fx-accent: #00BFFF;");
                    waterBar.setProgress(currentWater * 1.0 / maxWater);
                }
            } catch (Exception e) {
                AlertUser.alertUser("Seed not available");
            }
        }
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