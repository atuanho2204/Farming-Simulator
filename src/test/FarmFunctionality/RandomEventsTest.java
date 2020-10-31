package test.FarmFunctionality;

import main.farm.FarmState;
import main.farm.crops.CropStages;
import main.farm.plot.Plot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class RandomEventsTest {
    private List<Plot> plots;
    private int numOfPlots;

    /**
     * Each plot is instantiated with random crop, random stage, and random water level
     */
    @Before
    public void setUp() {
        plots = FarmState.getInstance().getPlots();
        numOfPlots = FarmState.getInstance().getNumOfPlots();
        for (int i  = 0; i < numOfPlots; ++i) {
            plots.add(new Plot());
        }
    }

    /**
     * When it rains, each plot's water level is incremented by 1 or 2
     * Method: compare water level of each plot before and after a rain.
     * --Quynh--
     */
    @Test
    public void testWaterLevelAfterARain() {
        int[] currentLevels = new int[numOfPlots];
        int[] levelsAfterRain = new int[numOfPlots];
        for (int i = 0; i < numOfPlots; ++i) {
            currentLevels[i] = plots.get(i).getCurrentWater();
        }
        FarmState.getInstance().triggerRain();
        for (int i = 0; i < numOfPlots; ++i) {
            levelsAfterRain[i] = plots.get(i).getCurrentWater();
        }
        for (int i = 0; i < numOfPlots; ++i) {
            Assert.assertTrue(levelsAfterRain[i] > currentLevels[i]
                    && levelsAfterRain[i] <= currentLevels[i] + 2);
        }
    }

    /**
     * After a drought, each plot's water level is decremented at least by 1.
     * Method: compare water level of each plot before and after a drought.
     * --Quynh--
     */
    @Test
    public void testWaterLevelAfterADrought() {
        int[] currentLevels = new int[numOfPlots];
        int[] levelsAfterDrought = new int[numOfPlots];
        for (int i = 0; i < numOfPlots; ++i) {
            currentLevels[i] = plots.get(i).getCurrentWater();
        }
        FarmState.getInstance().triggerDrought();
        for (int i = 0; i < numOfPlots; ++i) {
            levelsAfterDrought[i] = plots.get(i).getCurrentWater();
        }
        for (int i = 0; i < numOfPlots; ++i) {
            Assert.assertTrue(levelsAfterDrought[i] >= 0
                    && levelsAfterDrought[i] < currentLevels[i]);
        }
    }

    /**
     * During a locust swarming, at least 1 crop is killed when not all crops
     * have pesticide.
     * Method: in each plot, set crop's stage to IMMATURE; trigger locust swarming,
     * at least one crop should become DEAD.
     * --Quynh--
     */
    @Test
    public void testCropsWithoutPesticideAfterLocust() {
        boolean hasDeadCrop = false;
        for (Plot plot : plots) {
            plot.getCurrentCrop().setCropStage(CropStages.IMMATURE);
        }
        FarmState.getInstance().triggerLocustSwarms((int) (Math.random() * 3) + 1);
        for (Plot plot : plots) {
            if (plot.getCurrentCrop().getStage() == CropStages.DEAD) {
                hasDeadCrop = true;
                break;
            }
        }
        Assert.assertTrue(hasDeadCrop);
    }

    /**
     * During a locust swarming, no crops are killed if all living crops have pesticide.
     * Method: in each plot, set crop's stage to IMMATURE and plot's pesticide to true;
     * trigger locust swarming, no crops should become DEAD.
     * --Quynh--
     */
    @Test
    public void testAllCropsWithPesticideAfterLocust() {
        boolean hasDeadCrop = false;
        for (Plot plot : plots) {
            plot.getCurrentCrop().setCropStage(CropStages.IMMATURE);
            plot.getCurrentCrop().setPesticide(true);
        }
        FarmState.getInstance().triggerLocustSwarms((int) (Math.random() * 3) + 1);
        for (Plot plot : plots) {
            if (plot.getCurrentCrop().getStage() == CropStages.DEAD) {
                hasDeadCrop = true;
                break;
            }
        }
        Assert.assertFalse(hasDeadCrop);
    }
}
