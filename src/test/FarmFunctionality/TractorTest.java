package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.FarmEquipment;
import main.farm.FarmState;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TractorTest {

    private FarmEquipment farmEquipment;

    @Before
    public void setUp() {
        PlatformImpl.startup(() -> {
            //fix TOOLKIT NOT INITIALIZED
        });
        farmEquipment = FarmState.getInstance().getFarmEquipment();
        FarmState.clearFarmStateDangerous();
        GameManager.getInstance().clear();
        GameManager.getInstance().setInventory(new Inventory(true));
    }

    /**
     * Author: Chris
     * Test Component: Testing the Harvest Btn
     * Reason: check if plot get extended
     * Method: Calling addTractor() method
     */
    @Test
    public void testMaxPlots() {
        farmEquipment.addTractor();
        Assert.assertEquals(12, farmEquipment.getMaxHarvestPlots());
    }

    /**
     * Author: Chris
     * Test Component: Testing if Tractor is true
     * Reason: check if tractor is added
     * Method: Calling addTractor() method
     */
    @Test
    public void testHasTractor() {
        farmEquipment.addTractor();
        Assert.assertEquals(true, farmEquipment.hasTractor());
    }
}
