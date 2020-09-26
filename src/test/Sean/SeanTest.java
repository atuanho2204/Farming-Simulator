package test.Sean;

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
        assertEquals(true, controller.getDay() == 0);
        try {
            Thread.sleep(4005);
            assertEquals(1, controller.getDay().intValue());
        } catch (Exception e) {
            fail();
        }
    }
}