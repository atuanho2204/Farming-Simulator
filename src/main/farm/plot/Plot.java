package main.farm.plot;

import javafx.scene.control.ProgressBar;
import main.farm.FarmState;
import main.gameManager.GameManager;
import main.inventory.inventoryItems.HarvestedCrop;
import main.util.AlertUser;
import main.farm.crops.Crop;
import main.farm.crops.CropStages;
import main.farm.crops.CropTypes;

import java.util.Map;

public class Plot {
    private Crop currentCrop;
    private int currentWater = 5;
    private final int maxWater = 10;

    public Plot() {
        // random water level from 5 to 15
        this(new Crop(CropTypes.CORN, CropStages.SPROUTING),
                (int) (Math.random() * (8 - 5 + 1)) + 5);
    }

    public Plot(Crop currentCrop, int currentWater) {
        this.currentCrop = currentCrop;
        this.currentWater = currentWater;
    }

    public Crop getCurrentCrop() {
        return currentCrop;
    }


    public void waterPlot() {
        // cannot water if currentWater == 0 or max
        if (currentWater % maxWater == 0) {
            return;
        }
        currentWater++;
        if (currentWater == maxWater) {
            if (currentCrop != null) {
                currentCrop.setCropStage(CropStages.DEAD);
            }
        }
    }

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
                        new HarvestedCrop(currentCrop.getType()));
                currentCrop = null;
                return;
            } catch (Exception e) {
                AlertUser.alertUser(e.getMessage());
            }
        }
        //its not time to harvest yet
    }

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
                if (currentWater < (maxWater / 2) || currentWater == maxWater) {
                    currentWater = maxWater / 2;
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
}