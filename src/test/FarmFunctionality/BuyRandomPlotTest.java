package test.FarmFunctionality;

import main.farm.FarmController;
import main.farm.FarmState;
import main.gameManager.GameManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BuyRandomPlotTest {
    private FarmState farm;
    private FarmController controller;
    private GameManager game;
    @Before
    public void setUp() {
        game = GameManager.getInstance();
        FarmState.clearFarmStateDangerous();
        GameManager.getInstance().clear();
        controller = new FarmController();
    }

    /**
     * Check the total number of plot and check the number is increasing.
     * Author: Anh Ho
     */
    @Test
    public void testAddPlot() {
        Assert.assertEquals(6, FarmState.getInstance().getPlotCount());
        FarmState.getInstance().increasePlotCount();
        FarmState.getInstance().increasePlotCount();
        FarmState.getInstance().increasePlotCount();
        FarmState.getInstance().increasePlotCount();

        Assert.assertEquals(10, FarmState.getInstance().getPlotCount());
    }
}
