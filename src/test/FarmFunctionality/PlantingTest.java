package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.util.crops.Crop;
import main.util.crops.CropStages;
import main.util.crops.CropTypes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PlantingTest {
    private Plot plot;

    @Before
    public void setUp() {
        PlatformImpl.startup(() -> {
            //fix TOOLKIT NOT INITIALIZED
        });
        plot = new Plot();
    }

    //Chris
    @Test
    public void testSeedPlot() {
        Map<CropTypes, Integer> seedItems = null;
        seedItems.put(CropTypes.WHEAT, 1);
        seedItems.put(CropTypes.CORN, 1);
        CropTypes type = seedItems.entrySet().iterator().next().getKey();
        GameManager.getInstance().getInventory().removeSeed(type);
        Crop currentCrop = new Crop(type);
        plot.plantSeed();
    }
}
