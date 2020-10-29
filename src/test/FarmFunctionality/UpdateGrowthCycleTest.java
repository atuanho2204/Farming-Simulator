package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.FarmController;
import main.farm.FarmState;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import main.farm.crops.CropStages;
import main.farm.crops.CropTypes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpdateGrowthCycleTest {
    private List<Plot> plots;

    @Before
    public void setUp() {
        PlatformImpl.startup(() -> {

        });
        GameManager.getInstance().clear();
        FarmState.clearFarmStateDangerous();
        List<CropTypes> seed = new ArrayList<>();
        seed.add(CropTypes.CORN);
        GameManager.getInstance().setSeeds(seed);
        setPlotsForTest(seed);
        plots = FarmState.getInstance().getPlots();
    }

    /**
     *
     * Test Component: check the growth cycle after period of time
     * Reason: check after several days the crop changes its stage.
     * Method: Initialize plots and randomly assign values to them.
     * check their next stage is correct.
     *
     */
    @Test
    public void testLifeCycle() {

        List<CropStages> stage = new ArrayList<>();
        for (Plot plot: plots) {
            CropStages currentStage = plot.getCurrentCrop().getStage();
            if (currentStage == CropStages.SPROUTING) {
                stage.add(CropStages.IMMATURE);
            } else if (currentStage == CropStages.IMMATURE) {
                stage.add(CropStages.MATURE);
            } else if (currentStage == CropStages.MATURE) {
                stage.add(CropStages.DEAD);
            } else {
                stage.add(CropStages.DEAD);
            }
        }

        GameManager.getInstance().setDay(8);
        FarmState.getInstance().updateGrowthCycle();

        for (int i = 0; i < plots.size(); i++) {
            assertEquals(stage.get(i), plots.get(i).getCurrentCrop().getStage());
        }
    }

    private void setPlotsForTest(List<CropTypes> seeds) {
        for (int i = 0; i < FarmState.getInstance().getPlots().size(); ++i) {
            this.plots.add(new Plot());
        }

        for (int i = 0; i < plots.size(); i++) {
            Plot plot = plots.get(i);
            int randomCrop = (int) (Math.random() * 100) % seeds.size();
            int randomStage = (int) (Math.random() * 100) % CropStages.values().length;
            plot.getCurrentCrop().setType(seeds.get(randomCrop));
            plot.getCurrentCrop().setCropStage(CropStages.values()[randomStage]);

        }
    }
}






















