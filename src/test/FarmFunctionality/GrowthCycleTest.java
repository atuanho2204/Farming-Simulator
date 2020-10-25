package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.FarmController;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.util.crops.CropStages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GrowthCycleTest {

    private FarmController controller1;
    private List<Plot> plots = new ArrayList<>(4);


    @Before
    public void setUp() {
        PlatformImpl.startup(() -> {
            //fix TOOLKIT NOT INITIALIZED
        });
        controller1 = new FarmController();
        controller1.initializeTest();
        GameManager.getInstance().clear();
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
        controller1.updateGrowthCycle();
        List<Plot> plots = controller1.getPlots();
        Assert.assertEquals(CropStages.MATURE, plots.get(0).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.SPROUTING, plots.get(1).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.IMMATURE, plots.get(2).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.MATURE, plots.get(3).getCurrentCrop().getStage());

        GameManager.getInstance().setDay(2);
        controller1.updateGrowthCycle();

        Assert.assertEquals(CropStages.MATURE, plots.get(0).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.IMMATURE, plots.get(1).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.IMMATURE, plots.get(2).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.DEAD, plots.get(3).getCurrentCrop().getStage());

        GameManager.getInstance().setDay(3);
        controller1.updateGrowthCycle();

        Assert.assertEquals(CropStages.MATURE, plots.get(0).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.IMMATURE, plots.get(1).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.MATURE, plots.get(2).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.DEAD, plots.get(3).getCurrentCrop().getStage());

        GameManager.getInstance().setDay(4);
        controller1.updateGrowthCycle();

        Assert.assertEquals(CropStages.DEAD, plots.get(0).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.MATURE, plots.get(1).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.MATURE, plots.get(2).getCurrentCrop().getStage());
        Assert.assertEquals(CropStages.DEAD, plots.get(3).getCurrentCrop().getStage());

    }
}
