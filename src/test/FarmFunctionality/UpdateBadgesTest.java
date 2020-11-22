package test.FarmFunctionality;

import main.farm.FarmState;
import main.farm.crops.Crop;
import main.farm.crops.CropStages;
import main.farm.crops.CropTypes;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UpdateBadgesTest {
    private FarmState farm;
    private GameManager gm;
    private List<Plot> plots;
    private int numOfPlots;

    @Before
    public void setUp() {
        gm = GameManager.getInstance();
        farm = FarmState.getInstance();
        gm.clear();
        farm.clearFarmStateDangerous();
        numOfPlots = farm.getNumOfPlots();
        plots = new ArrayList<>();
    }


    /**
     *
     * On 12 plots of mature carrots, test points for carrotB checking if they are
     * incremented by 1 each time a carrot is harvested
     * --Quynh--
     *
     */
    @Test
    public void testIncrementPointsAfterHarvestingCarrot() {
        for (Plot plot : plots) {
            plot = new Plot(
                    new Crop(CropTypes.CARROT, CropStages.MATURE, false), 5);
        }
        for (Plot plot : plots) {
            int carrotBPoints = gm.getBadgeBookkeeping()[0];
            plot.harvestPlot();
            Assert.assertEquals(++carrotBPoints, gm.getBadgeBookkeeping()[0]);
        }
    }

    /**
     *
     * On 12 plots of mature crops sprayed with pesticide, test points for organicB
     * checking whether they are incremented by 1 each time such a crop is harvested,
     * which they should not be.
     * --Quynh--
     *
     */
    @Test
    public void testNotIncrementPointsAfterHarvestingCropsWithPesticide() {
        Random random = new Random();
        for (Plot plot : plots) {
            int rand = random.nextInt(4);
            plot = new Plot(
                    new Crop(CropTypes.values()[rand], CropStages.MATURE, true),
                    5);
        }
        for (Plot plot : plots) {
            int organicBPoints = gm.getBadgeBookkeeping()[1];
            plot.harvestPlot();
            Assert.assertEquals(organicBPoints, gm.getBadgeBookkeeping()[1]);
        }
    }

    /**
     *
     * On 12 plots of mature crops with/without pesticide, test points for harvestB
     * checking whether they are incremented by 1 each time such a crop is harvested,
     * which they should be.
     * --Quynh--
     *
     */
    @Test
    public void testIncrementPointsAfterHarvestingCropsWithOrWithoutPesticide() {
        Random random = new Random();
        for (Plot plot : plots) {
            int rand = random.nextInt(4);
            Crop crop = new Crop(CropTypes.values()[rand],
                    CropStages.MATURE,
                    rand % 2 == 0);
            plot = new Plot(crop, 5);
        }
        for (Plot plot : plots) {
            int harvestBPoints = gm.getBadgeBookkeeping()[2];
            plot.harvestPlot();
            Assert.assertEquals(++harvestBPoints, gm.getBadgeBookkeeping()[2]);
        }
    }
}
