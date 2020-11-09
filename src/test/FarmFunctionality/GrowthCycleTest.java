package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;

import main.farm.FarmState;
import main.farm.crops.CropTypes;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.farm.crops.CropStages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GrowthCycleTest {
    private List<Plot> plots = new ArrayList<>(4);


    @Before
    public void setUp() {
        PlatformImpl.startup(() -> {
            //fix TOOLKIT NOT INITIALIZED
        });
        FarmState.clearFarmStateDangerous();
        GameManager.getInstance().clear();

        for (int i = 0; i < 4; ++i) {
            plots.add(new Plot());

        }
        plots.get(0).getCurrentCrop().setType(CropTypes.CORN);
        plots.get(0).getCurrentCrop().setCropStage(CropStages.MATURE);
        plots.get(0).getCurrentCrop().setPlantDay(0);
        plots.get(1).getCurrentCrop().setType(CropTypes.WHEAT);
        plots.get(1).getCurrentCrop().setCropStage(CropStages.SPROUTING);
        plots.get(1).getCurrentCrop().setPlantDay(0);
        plots.get(2).getCurrentCrop().setType(CropTypes.LETTUCE);
        plots.get(2).getCurrentCrop().setCropStage(CropStages.IMMATURE);
        plots.get(2).getCurrentCrop().setPlantDay(0);
        plots.get(3).getCurrentCrop().setType(CropTypes.COTTON);
        plots.get(3).getCurrentCrop().setCropStage(CropStages.IMMATURE);
        plots.get(3).getCurrentCrop().setPlantDay(0);

        for (int i = 0; i < 4; ++i) {
            FarmState.getInstance().getPlots().add(plots.get(i));
        }
    }

    /**
     *
     * Test Component: CropStage of different plots during its cycle
     * Reason: CropStage of each crop should change accordingly to their growth cycle
     * Method: Initialize 4 plots of 4 crop types with different stages and see
     * the difference in 4 days. Check if their next stages are correct.
     *
     */
    @Test
    public void testUpdateGrowthCycle() {


        GameManager.getInstance().setDay(1);
        FarmState.getInstance().updateGrowthCycle();
        List<Plot> plots = FarmState.getInstance().getPlots();

        Assert.assertEquals(CropStages.MATURE, plots.get(0).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.SPROUTING, plots.get(1).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.IMMATURE, plots.get(2).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.IMMATURE, plots.get(3).getCurrentCrop().getStage());

        GameManager.getInstance().setDay(2);
        FarmState.getInstance().updateGrowthCycle();

        Assert.assertEquals(CropStages.MATURE, plots.get(0).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.SPROUTING, plots.get(1).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.IMMATURE, plots.get(2).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.MATURE, plots.get(3).getCurrentCrop().getStage());

        GameManager.getInstance().setDay(3);
        FarmState.getInstance().updateGrowthCycle();

        Assert.assertEquals(CropStages.MATURE, plots.get(0).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.IMMATURE, plots.get(1).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.IMMATURE, plots.get(2).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.MATURE, plots.get(3).getCurrentCrop().getStage());

        GameManager.getInstance().setDay(4);
        FarmState.getInstance().updateGrowthCycle();

        Assert.assertEquals(CropStages.MATURE, plots.get(0).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.IMMATURE, plots.get(1).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.MATURE, plots.get(2).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.DEAD, plots.get(3).getCurrentCrop().getStage());

    }

}
