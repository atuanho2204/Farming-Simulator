package test.Sean;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import main.farm.FarmController;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SeanTest {
    private FarmController controller;

    @Before
    public void setup() {
        PlatformImpl.startup(() -> {
            //this code serves to remove the "Toolkit not found" error
        });
        controller = new FarmController();
    }

    @Test
    public void testConstructController() {
        controller.construct(1, "Sean", new ArrayList<>(0), "Fall", 0);
        assertEquals(0, controller.getDay().intValue());
        assertEquals(1, controller.getDifficulty().intValue());
        assertEquals(10, controller.getMoney().intValue());
    }


    @Test
    public void testDayIncrements() {
        controller.construct(1, "Sean", new ArrayList<>(0), "Fall", 0);
        assertEquals(0, controller.getDay().intValue());
        try {
            Thread.sleep(4050);
            assertEquals(1, controller.getDay().intValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}