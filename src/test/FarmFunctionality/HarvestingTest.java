package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.FarmState;
import main.farm.crops.Crop;
import main.farm.crops.CropStages;
import main.farm.crops.CropTypes;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HarvestingTest {

    @Before
    public void setUp() {
        FarmState.clearFarmStateDangerous();
        GameManager.getInstance().clear();
        GameManager.getInstance().setInventory(new Inventory(false));

        assertEquals(0, GameManager.getInstance().getInventory().getListOfSeedItems().size());
    }

    @Test
    public void test() {
        List<Plot> plots = FarmState.getInstance().getPlots();
        try {
            GameManager.getInstance().getInventory().putSeed(CropTypes.MELON);
            GameManager.getInstance().getInventory().putSeed(CropTypes.TOMATO);
            GameManager.getInstance().getInventory().putSeed(CropTypes.EGGPLANT);
            GameManager.getInstance().getInventory().putSeed(CropTypes.CARROT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(4, GameManager.getInstance().getInventory().getListOfSeedItems().size());


        plantSeed(0);
        plantSeed(1);
        plantSeed(2);
        plantSeed(3);

        System.out.println(FarmState.getInstance().getPlots());

        assertEquals(4, plots.size());
        assertEquals(0, GameManager.getInstance().getInventory().getListOfSeedItems().size());

        harvestPlot(0);
        harvestPlot(1);
        harvestPlot(2);
        harvestPlot(3);

        assertEquals(null, getCrop(0));
        assertEquals(null, getCrop(1));
        assertEquals(null, getCrop(2));
        assertEquals(null, getCrop(3));
    }

    private void harvestPlot(int index) {
        List<Plot> plots = FarmState.getInstance().getPlots();
        plots.get(index).getCurrentCrop().setCropStage(CropStages.MATURE);
        plots.get(index).harvestPlot();
    }

    private void plantSeed(int index) {
        List<Plot> plots = FarmState.getInstance().getPlots();
        Plot plot = new Plot(null, 5);
        plot.setPurchased(true);
        plots.add(index, plot);
        plots.get(index).plantSeed();
    }

    private Crop getCrop(int index) {
        List<Plot> plots = FarmState.getInstance().getPlots();
        return plots.get(index).getCurrentCrop();
    }
}
