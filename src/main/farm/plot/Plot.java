package main.farm.plot;

import main.farm.crops.CropCatalog;
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
    private boolean purchased = false;
    private int price = 0;
    private int openIdx = 0;

    public Plot() {
        // random crop, random stage, random water level from 4 to 6
        this(new Crop(CropTypes.values()[(int) (Math.random() * 4)],
                        CropStages.values()[(int) (Math.random() * 4)], false),
                (int) (Math.random() * (6 - 4 + 1)) + 4);
    }

    public Plot(Crop currentCrop, int currentWater) {
        this.currentCrop = currentCrop;
        this.currentWater = currentWater;
        this.purchased = false;
    }

    public Crop getCurrentCrop() {
        return currentCrop;
    }

    public void setCurrentCrop(Crop crop) {
        this.currentCrop = crop;
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

    public void pesticidePlot() {
        if (currentCrop != null && !currentCrop.hasPesticide()) {
            try {
                GameManager.getInstance().getInventory().putPesticide(-1);
                currentCrop.setPesticide(true);
            } catch (Exception e) {
                AlertUser.alertUser("You don't have enough pesticide.");
            }
        }
    }

    public int harvestPlot() {
        if (currentCrop == null) {
            //the plot is empty
            return 0;
        } else if (currentCrop.getStage() == CropStages.DEAD) {
            currentCrop = null;
            return 0;
        } else if (currentCrop.getStage() == CropStages.MATURE) {
            //try to harvest the mature crop
            try {
                int yieldBonus = 1;
                if (currentFertilizer > 0) {
                    Random random = new Random();
                    yieldBonus = random.nextInt(3) + 1;
                }
                for (int i = 0; i < yieldBonus; i++) {
                    if (currentCrop.hasPesticide()) {
                        int adjustedPrice = CropCatalog.getInstance().getCropDetails(
                                currentCrop.getType()).getBaseSell() * 2 - 2;
                        GameManager.getInstance().getInventory().putProduct(
                                new HarvestedCrop(0, adjustedPrice,
                                        currentCrop.getType().toString().toLowerCase() + "(P)",
                                        currentCrop.getType()));
                    } else {
                        GameManager.getInstance().getInventory().putProduct(
                                new HarvestedCrop(currentCrop.getType()));
                        GameManager.getInstance().getBadgeBookkeeping()[1] =
                                GameManager.getInstance().getBadgeBookkeeping()[1] + 1;
                    }
                }
                if (currentCrop.getType() == CropTypes.CARROT) {
                    GameManager.getInstance().getBadgeBookkeeping()[0] =
                            GameManager.getInstance().getBadgeBookkeeping()[0] + 1;
                }
                GameManager.getInstance().getBadgeBookkeeping()[2] =
                        GameManager.getInstance().getBadgeBookkeeping()[2] + 1;
                NotificationManager.getInstance().addNotification(
                        "Harvested " + yieldBonus + " "
                                + currentCrop.getType().toString().toLowerCase() + "!!");
                currentCrop = null;
                return yieldBonus;
            } catch (Exception e) {
                AlertUser.alertUser(e.getMessage());
            }
        }
        return 0;
        //its not time to harvest yet
    }

    public void plantSeed() {
        if (currentCrop != null || !purchased) {
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

    public void setCurrentFertilizer(int amount) {
        this.currentFertilizer = amount;
    }

    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }

    public void setPurchased(boolean value) {
        this.purchased = value;
    }

    public boolean getPurchased() {
        return purchased;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setOpenIdx(int index) {
        this.openIdx = index;
    }

    public int getOpenIdx() {
        return openIdx;
    }

}
