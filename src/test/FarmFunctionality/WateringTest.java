package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.plot.Plot;
import main.farm.crops.CropStages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WateringTest {
    private Plot plot;

    @Before
    public void setUp() {
        PlatformImpl.startup(() -> {
            //fix TOOLKIT NOT INITIALIZED
        });
        plot = new Plot();
    }

    /**
     *
     * At game start, each plot starts out with a random water level that is neither 0 nor max.
     * Method: On 10 new plots, return false if all plots have the same level of water and
     * return true otherwise.
     * --Quynh--
     *
     */
    @Test
    public void testWaterLevelAtGameStart() {
        for (int i = 0; i < 10; ++i) {
            Plot newPlot = new Plot();
            Assert.assertTrue(newPlot.getCurrentWater() % newPlot.getMaxWater() != 0);
        }
    }

    /**
     *
     * Water level within normal range [1, 9] should be increased by 1 after watering.
     * Method: test on 2 plots, one is with a preset water level and the other is with
     * a random water level.
     * --Quynh--
     *
     */
    @Test
    public void testWaterPlot() {
        plot.setCurrentWater(5);
        plot.waterPlot();
        Assert.assertEquals(6, (long) plot.getCurrentWater());
        plot = new Plot();
        int levelBeforeWatering = plot.getCurrentWater();
        plot.waterPlot();
        Assert.assertEquals(++levelBeforeWatering, (long) plot.getCurrentWater());
    }

    /**
     *
     * Player cannot water a plot if its water level is 0.
     * --Quynh--
     *
     */
    @Test
    public void testCannotWaterWhenWaterAtMin() {
        plot.setCurrentWater(0);
        plot.waterPlot();
        Assert.assertEquals(0, plot.getCurrentWater());
    }

    /**
     *
     * Player cannot water a plot if its water level is 10.
     * --Quynh--
     *
     */
    @Test
    public void testCannotWaterWhenWaterAtMax() {
        plot.setCurrentWater(plot.getMaxWater());
        plot.waterPlot();
        Assert.assertEquals(plot.getMaxWater(), plot.getCurrentWater());
    }

    /**
     *
     * Overwatering (bringing water bar to max value == 10) causes crop to die.
     * Method: watering a plot whose water level is 9 causes the crop to reach dead stage.
     * --Quynh--
     *
     */
    @Test
    public void testCropStageWhenOverwatering() {
        // from sprouting
        plot = new Plot();
        plot.getCurrentCrop().setCropStage(CropStages.SPROUTING);
        plot.setCurrentWater(plot.getMaxWater() - 1);
        plot.waterPlot();
        Assert.assertEquals(CropStages.DEAD, plot.getCurrentCrop().getStage());

        // from immature
        plot = new Plot();
        plot.getCurrentCrop().setCropStage(CropStages.IMMATURE);
        plot.setCurrentWater(plot.getMaxWater() - 1);
        plot.waterPlot();
        Assert.assertEquals(CropStages.DEAD, plot.getCurrentCrop().getStage());

        // from mature
        plot = new Plot();
        plot.getCurrentCrop().setCropStage(CropStages.MATURE);
        plot.setCurrentWater(plot.getMaxWater() - 1);
        plot.waterPlot();
        Assert.assertEquals(CropStages.DEAD, plot.getCurrentCrop().getStage());
    }
}
