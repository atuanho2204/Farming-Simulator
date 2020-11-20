package test.FarmFunctionality;

import main.farm.FarmController;
import main.farm.FarmState;
import main.farm.crops.Crop;
import main.farm.crops.CropStages;
import main.farm.crops.CropTypes;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class UpdateBadgesTest {
    private FarmState farm;
    private FarmController controller;
    private GameManager gm;

    @Before
    public void setUp() {
        gm = GameManager.getInstance();
        farm = FarmState.getInstance();
        gm.clear();
        farm.clearFarmStateDangerous();
        controller = new FarmController();
    }

    @Test
    public void testIncrementPointsAfterHarvestingCarrot() {
        for (Plot plot : farm.getPlots()) {
            plot = new Plot(
                    new Crop(CropTypes.CARROT, CropStages.MATURE, false), 5);
        }
        for (Plot plot : farm.getPlots()) {
            int carrotBPoints = gm.getBadgeBookkeeping()[0];
            plot.harvestPlot();
            Assert.assertEquals(++carrotBPoints, gm.getBadgeBookkeeping()[0]);
        }
    }

    @Test
    public void testNotIncrementPointsAfterHarvestingCropsWithPesticide() {
        Random random = new Random();
        for (Plot plot : farm.getPlots()) {
            int rand = random.nextInt(4);
            plot = new Plot(
                    new Crop(CropTypes.values()[rand], CropStages.MATURE, true),
                    5);
        }
        for (Plot plot : farm.getPlots()) {
            int organicBPoints = gm.getBadgeBookkeeping()[1];
            plot.harvestPlot();
            Assert.assertEquals(organicBPoints, gm.getBadgeBookkeeping()[1]);
        }
    }

    @Test
    public void testIncrementPointsAfterHarvestingCropsWithOrWithoutPesticide() {
        Random random = new Random();
        for (Plot plot : farm.getPlots()) {
            int rand = random.nextInt(4);
            Crop crop = new Crop(CropTypes.values()[rand],
                    CropStages.MATURE,
                    (rand % 2 == 0) ? true : false);
            plot = new Plot(crop, 5);
        }
        for (Plot plot : farm.getPlots()) {
            int harvestBPoints = gm.getBadgeBookkeeping()[2];
            plot.harvestPlot();
            Assert.assertEquals(++harvestBPoints, gm.getBadgeBookkeeping()[2]);
        }
    }
}
