package test.FarmFunctionality;


import main.farm.FarmState;
import main.farm.crops.CropStages;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import main.farm.crops.CropTypes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FertlizeTest {
    private Plot plot;

    @Before
    public void setUp() {
        plot = new Plot();
        FarmState.clearFarmStateDangerous();
        GameManager.getInstance().clear();
        GameManager.getInstance().setInventory(new Inventory(true));
    }

    /**
     * Check if we can apply fertilizer and refill fertilizer on a plot
     * Author: Duy
     */
    @Test
    public void testFertizePlot() {
        GameManager.getInstance().getInventory().setCurrentFertilizer(10);
        plot.fertilizePlot(10);
        Assert.assertEquals(10, plot.getCurrentFertilizer());
        Assert.assertEquals(9, GameManager.getInstance().getInventory().getFertilizer());
        plot.setCurrentFertilizer(4);
        plot.fertilizePlot(10);
        Assert.assertEquals(10, plot.getCurrentFertilizer());
        Assert.assertEquals(8, GameManager.getInstance().getInventory().getFertilizer());

    }

    /**
     * Check the effect of fertilizer on a plot, specifically if it affect growth cycle properly.
     * Author: Duy
     */
    @Test
    public void testGrowthCycleFertilizer() {
        plot.getCurrentCrop().setType(CropTypes.COTTON);
        plot.getCurrentCrop().setCropStage(CropStages.IMMATURE);
        plot.getCurrentCrop().setPlantDay(0);
        GameManager.getInstance().getInventory().setCurrentFertilizer(10);
        plot.fertilizePlot(10);
        FarmState.getInstance().getPlots().add(plot);
        GameManager.getInstance().setDay(1);
        FarmState.getInstance().updateGrowthCycle();
        Assert.assertEquals(CropStages.MATURE, plot.getCurrentCrop().getStage());
        GameManager.getInstance().setDay(2);
        FarmState.getInstance().updateGrowthCycle();
        Assert.assertEquals(CropStages.DEAD, plot.getCurrentCrop().getStage());
    }

    /**
     * Check the effect of fertilizer on a plot if it applies yield bonus properly.
     * Author: Duy
     */
    @Test
    public void testYieldBonus() {
        while (true) {
            plot = new Plot();
            plot.getCurrentCrop().setType(CropTypes.COTTON);
            plot.getCurrentCrop().setCropStage(CropStages.MATURE);
            plot.setCurrentFertilizer(5);
            int products = plot.harvestPlot();
            if (products > 1) {
                Assert.assertNotEquals(1, products);
                break;
            }
        }
    }


}
