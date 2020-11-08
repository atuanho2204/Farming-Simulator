package main.farm.plot;

import main.gameManager.GameManager;
import main.inventory.inventoryItems.HarvestedCrop;
import main.notifications.NotificationManager;
import main.util.AlertUser;
import main.farm.crops.Crop;
import main.farm.crops.CropStages;
import main.farm.crops.CropTypes;

import java.util.Map;
import java.util.Random;

public class Plot {
    private Crop currentCrop;
    private int currentWater = 5;
    private final int maxWater = 10;
    private int currentFertilizer = 0;
    private final int maxFertilizer = 10;
    private int currentPesticide = 0;
    private final int maxPesticide = 10;
    public Plot() {
        // random crop, random stage, random water level from 4 to 6
        this(new Crop(CropTypes.values()[(int) (Math.random() * 4)],
                        CropStages.values()[(int) (Math.random() * 4)], false),
                (int) (Math.random() * (6 - 4 + 1)) + 4);
    }

    public Plot(Crop currentCrop, int currentWater) {
        this.currentCrop = currentCrop;
        this.currentWater = currentWater;
    }

    public Crop getCurrentCrop() {
        return currentCrop;
    }


    public void waterPlot(int increment) {
        currentWater += increment;
        if (currentWater >= maxWater || currentWater <= 0) {
            if (currentWater > maxWater) {
                currentWater = maxWater;
            }
            if (currentWater < 0) {
                currentWater = 0;
            }
            if (currentCrop != null) {
                currentCrop.setCropStage(CropStages.DEAD);
            }
        }
    }

    public void fertilizePlot(int increment) {
        try {
            if (increment == 10) {
                if (currentFertilizer == maxFertilizer) {
                    throw new Exception("This plot's fertilize level is at maximum");
                }
                GameManager.getInstance().getInventory().putFertilizer(-1);
            }
            currentFertilizer += increment;
            if (currentFertilizer >= maxFertilizer || currentFertilizer <= 0) {
                if (currentFertilizer > maxFertilizer) {
                    currentFertilizer = maxFertilizer;
                }
                if (currentFertilizer < 0) {
                    currentFertilizer = 0;
                }
            }
        } catch (Exception e) {
            AlertUser.alertUser(e.getMessage());
        }
    }

    public void pesticidePlot(int increment) {
        try {
            if (increment == 10) {
                if (currentFertilizer == maxFertilizer) {
                    throw new Exception("This plot's pesticide level is at maximum");
                }
                GameManager.getInstance().getInventory().putPesticide(-1);
            }
            currentPesticide += increment;
            if (currentPesticide >= maxPesticide || currentPesticide <= 0) {
                if (currentPesticide > maxPesticide) {
                    currentPesticide = maxPesticide;
                }
                if (currentPesticide < 0) {
                    currentPesticide = 0;
                }
            }
        } catch (Exception e) {
            AlertUser.alertUser("You don't have enough pesticide");
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
                int yieldBonus = 1;
                if (currentFertilizer > 0) {
                    Random random = new Random();
                    yieldBonus = random.nextInt(3) + 1;
                }
                for (int i = 0; i < yieldBonus; i++) {
                    GameManager.getInstance().getInventory().putProduct(
                            new HarvestedCrop(currentCrop.getType()));
                }
                NotificationManager.getInstance().addNotification(
                        "Harvested " + yieldBonus + " "
                                + currentCrop.getType().toString().toLowerCase() + "!!");
                currentCrop = null;
                return;
            } catch (Exception e) {
                AlertUser.alertUser(e.getMessage());
            }
            try {
                if(currentPesticide > 0) {

                }
            } catch (Exception e) {
                AlertUser.alertUser("Price change");
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
                        GameManager.getInstance().getInventory()
                                .getListOfSeedItems();
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

    public int getMaxFertilizer() {
        return maxFertilizer;
    }

    public int getCurrentFertilizer() {
        return currentFertilizer;
    }

    public int getMaxPesticide() {
        return maxPesticide;
    }

    public int getCurrentPesticide() {
        return currentPesticide;
    }

    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }
}