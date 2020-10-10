package test.Quynh;

import com.sun.javafx.application.PlatformImpl;
import main.farm.FarmController;
import main.farm.Plot;
import main.util.crops.CropTypes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class M3QuynhTests {
    private FarmController controller1;
    private FarmController controller2;
    private List<Plot> plots1;
    private List<Plot> plots2;
    private List<String> seeds;


    @Before
    public void setUp() {
        controller1 = new FarmController();
        controller2 = new FarmController();
        seeds = new ArrayList<>();
        seeds.add(CropTypes.WHEAT.toString());
        seeds.add(CropTypes.CORN.toString());
        seeds.add(CropTypes.COTTON.toString());
    }

    /**
     *
     * Test Component: initial state of the plots
     * Reason: Check whether the plots List is null; if it is null, the farm screen
     *    will not load as the construct method will invoke NullPointerException
     * Method: Call initializePlots(), get the plots instance of FarmController,
     *    and check whether it is null
     *
     */
    @Test
    public void testInitializedPlotsNotNull() {
        PlatformImpl.startup(() -> {
        });
        controller1.initializePlots();
        Assert.assertNotNull(controller1.getPlots());
    }

    /**
     *
     * Test Component: initial state of each plot
     * Reason: Check whether each plot is null; if any of the plots is null, the farm screen
     *    will not load as the construct method will invoke NullPointerException
     * Method: Call initializePlots(), get the plots instance of FarmController,
     *    and check whether any of the plots in this List is null
     *
     */
    @Test
    public void testInitializedIndividualPlotNotNull() {
        PlatformImpl.startup(() -> {
        });
        controller1.initializePlots();
        List<Plot> plots = controller1.getPlots();
        for (Plot plot : plots) {
            Assert.assertNotNull(plot);
        }
    }

    /**
     *
     * Test Component: CropTypes of the initial plots
     * Reason: Crop types of the initial plots are required to be randomly distributed.
     * Method: Initialize 2 lists of plots and randomly assign to them certain values
     *    which include CropTypes. Compare CropTypes between these two lists at their
     *    corresponding plot position. If every plot of these two lists has the same
     *    CropTypes, the test fails and passes otherwise.
     *
     */
    @Test
    public void testRandomlyPopulatedCropTypes() {
        PlatformImpl.startup(() -> {
        });
        controller1.initializePlots();
        controller1.populatePlotsRandomly(seeds);
        plots1 = controller1.getPlots();
        controller2.initializePlots();
        controller2.populatePlotsRandomly(seeds);
        plots2 = controller2.getPlots();
        boolean same = true;
        int plotSize = plots1.size();
        for (int i = 0; i < plotSize; ++i) {
            if (!plots1.get(i).getCurrentCrop().getType().equals(
                    plots2.get(i).getCurrentCrop().getType())) {
                same = false;
            }
        }
        Assert.assertFalse(same);
    }

    /**
     *
     * Test Component: CropStages of the initial plots
     * Reason: Crop stages of the initial plots are required to be randomly distributed.
     * Method: Initialize 2 lists of plots and randomly assign to them certain values
     *    which include CropStages. Compare CropStages between these two lists at their
     *    corresponding plot position. If every plot of these two lists has the same
     *    CropStages, the test fails and passes otherwise.
     *
     */
    @Test
    public void testRandomlyPopulatedCropStages() {
        PlatformImpl.startup(() -> {
        });
        controller1.initializePlots();
        controller1.populatePlotsRandomly(seeds);
        plots1 = controller1.getPlots();
        controller2.initializePlots();
        controller2.populatePlotsRandomly(seeds);
        plots2 = controller2.getPlots();
        boolean same = true;
        int plotSize = plots1.size();
        for (int i = 0; i < plotSize; ++i) {
            if (!plots1.get(i).getCurrentCrop().getStage().equals(
                    plots2.get(i).getCurrentCrop().getStage())) {
                same = false;
            }
        }
        Assert.assertFalse(same);
    }

    /**
     *
     * Test Component: Text displayed on each plot
     * Reason: Each plot must show its crop and stage at the beginning of the game.
     * Method: Initialize plots and randomly assign values to them. If any of the plots
     *    shows an empty string or textual sign that it is an empty plot, the test fails
     *    and passes otherwise.
     *
     */
    @Test
    public void testPlotsLabels() {
        PlatformImpl.startup(() -> {
        });
        controller1.initializePlots();
        controller1.populatePlotsRandomly(seeds);
        plots1 = controller1.getPlots();
        for (Plot plot : plots1) {
            Assert.assertNotEquals("", plot.getPlotButton().getText());
            Assert.assertNotEquals(
                    "Empty &\nlonely..", plot.getPlotButton().getText());
        }
    }
}
