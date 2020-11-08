package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.inventory.Inventory;
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
     * Test Component: Testing the pesticide Btn
     * Reason: check if plot gets pesticide
     * Method: Calling pesticidePlot() method
     */
    @Test
    public void testPesticidePlot() {
        GameManager.getInstance().getInventory().setCurrentPesticide(10);
        plot.pesticidePlot(10);
        Assert.assertEquals(10, plot.getCurrentPesticide());
        Assert.assertEquals(9, GameManager.getInstance().getInventory().getPesticide());
    }
}
