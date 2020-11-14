package main.farm;

public class FarmEquipment {
    private boolean hasIrrigation = false;
    private int maxWaterPlots = 6;
    private int currentWaterPlots = 0;
    private boolean hasTractor = false;
    private int maxHarvestPlots = 6;
    private int currentHarvestPlots = 0;


    public int getMaxWaterPlots() {
        return maxWaterPlots;
    }

    public void setMaxWaterPlots(int maxWaterPlots) {
        this.maxWaterPlots = maxWaterPlots;
    }

    public int getCurrentWaterPlots() {
        return currentWaterPlots;
    }

    public void setCurrentWaterPlots(int currentWaterPlots) {
        this.currentWaterPlots = currentWaterPlots;
    }

    public void addIrrigation() {
        maxWaterPlots *= 2;
        this.hasIrrigation = true;
    }

    public boolean hasIrrigation() {
        return hasIrrigation;
    }

    public int getMaxHarvestPlots() { return maxHarvestPlots;}

    public void setMaxHarvestPlots(int maxHarvestPlots) {
        this.maxHarvestPlots = maxHarvestPlots;
    }

    public int getCurrentHarvestPlots() {
        return currentHarvestPlots;
    }

    public void setCurrentHarvestPlots(int currentHarvestPlots) {
        this.currentHarvestPlots = currentHarvestPlots;
    }

    public void addTractor() {
        maxHarvestPlots *= 2;
        this.hasTractor = true;
    }

    public boolean hasTractor() {
        return hasTractor;
    }
}

