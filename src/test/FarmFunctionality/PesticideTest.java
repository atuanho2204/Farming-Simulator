package test.FarmFunctionality;

import com.sun.javafx.application.PlatformImpl;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import main.farm.crops.CropTypes;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;

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

    }
}
