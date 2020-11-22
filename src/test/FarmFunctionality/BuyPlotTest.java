package test.FarmFunctionality;

import main.farm.FarmState;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BuyPlotTest {

    @Before
    public void setUp() {
        FarmState.clearFarmStateDangerous();
        GameManager.getInstance().clear();
        GameManager.getInstance().setInventory(new Inventory(true));
    }

    /**
     * Check the initial count of the farm and keep track of the plot counts
     * Author: Duy
     */
    @Test
    public void testFertizePlot() {
        Assert.assertEquals(6, FarmState.getInstance().getPlotCount());
        FarmState.getInstance().increasePlotCount();
        FarmState.getInstance().increasePlotCount();
        Assert.assertEquals(8, FarmState.getInstance().getPlotCount());
    }
}
