package main.farm;

public class FarmEquipment {
    private boolean hasIrrigation = false;
    private int maxWaterPlots = 6;
    private int currentWaterPlots = 0;
    //add fields here @Chris

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
}
