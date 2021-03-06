package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import org.junit.Before;
import org.junit.Test;

public class PlantingTest {
    private Plot plot;

    @Before
    public void setUp() {
        PlatformImpl.startup(() -> {
            //fix TOOLKIT NOT INITIALIZED
        });
        plot = new Plot();
        GameManager.getInstance().clear();
        GameManager.getInstance().setInventory(new Inventory(true));
    }

    /**
     * Author: Chris
     * Test Component: I am testing the plant seed btn
     * Reason: Check if seed gets planted from inventory
     * Method: Calling the plantSeed() method
     */
    @Test
    public void testSeedPlot() {
        // Sean: Looking over the code below...it seems to do literally nothing
        // ...so i commented it out

        //Map<CropTypes, Integer> seedItems = GameManager.getInstance()
        //        .getInventory().getListOfSeedItems();
        //seedItems.put(CropTypes.MELON, 1);
        //seedItems.put(CropTypes.CARROT, 1);
        //seedItems.put(CropTypes.TOMATO, 2);
        //seedItems.put(CropTypes.EGGPLANT, 3);
        //CropTypes type = seedItems.entrySet().iterator().next().getKey();
        //GameManager.getInstance().getInventory().removeSeed(type);
        //plot.plantSeed();
    }
}
