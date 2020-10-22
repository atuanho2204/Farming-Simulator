package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.plot.Plot;
import main.util.crops.CropStages;
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

    // Quynh
    @Test
    public void testWaterLevelAtGameStart() {
        // at game start, each plot starts out with a random water level that is neither 0 nor max
        // test on 10 new plots
        for (int i = 0; i < 10; ++i) {
            Plot newPlot = new Plot();
            Assert.assertTrue(newPlot.getCurrentWater() % newPlot.getMaxWater() != 0);
        }
    }

    // Quynh
    @Test
    public void testWaterPlot() {
        // watering a plot increments water level by 1
        plot.setCurrentWater(5);
        plot.waterPlot();
        Assert.assertEquals(6, (long) plot.getCurrentWater());
    }

    // Quynh
    @Test
    public void testCropStageWhenOverwatering() {
        // currentWater == max -> too much
        plot.setCurrentWater(plot.getMaxWater() - 1);
        plot.waterPlot();
        Assert.assertEquals(CropStages.DEAD, plot.getCurrentCrop().getStage());
    }

    // Quynh
    @Test
    public void testCannotWaterWhenWaterAtMin() {
        plot.setCurrentWater(0);
        plot.waterPlot();
        Assert.assertEquals(0, plot.getCurrentWater());
    }

    // Quynh
    @Test
    public void testCannotWaterWhenWaterAtMax() {
        plot.setCurrentWater(plot.getMaxWater());
        plot.waterPlot();
        Assert.assertEquals(plot.getMaxWater(), plot.getCurrentWater());
    }
}
