package main.util.crops;

public class CropDetails {
    private final int baseBuy;
    private final int baseSell;
    private final int growthTime;

    public CropDetails(int baseBuy, int baseSell, int growthTime) {
        this.baseBuy = baseBuy;
        this.baseSell = baseSell;
        this.growthTime = growthTime;
    }

    public CropDetails(CropDetails c) {
        this.baseBuy = c.getBaseBuy();
        this.baseSell = c.getBaseSell();
        this.growthTime = c.growthTime;
    }

    public int getGrowthTime() {
        return growthTime;
    }

    public int getBaseSell() {
        return baseSell;
    }

    public int getBaseBuy() {
        return baseBuy;
    }
}
