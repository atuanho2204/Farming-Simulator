package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.FarmState;
import main.farm.crops.CropStages;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import main.farm.crops.CropTypes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PesticideTest {
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
     * Test Component:
     * Reason:
     * Method:
     */
    @Test
    public void testPesticidePlot() {
        GameManager.getInstance().getInventory().setCurrentPesticide(5);
        plot.pesticidePlot(5);
        Assert.assertEquals(5, plot.getCurrentPesticide());
        Assert.assertEquals(5, GameManager.getInstance().getInventory().getPesticide());
    }
}
