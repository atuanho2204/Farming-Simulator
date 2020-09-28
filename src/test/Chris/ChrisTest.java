package test.Chris;

import main.farm.FarmController;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ChrisTest {
    private FarmController controller;

    @Before
    public void setup() {
        controller = new FarmController();
    }

    @Test
    public void testConstructController() {
        controller.construct(1, "Chris", new ArrayList<>(0), "Fall", 0);
        assertEquals(0, controller.getDay().intValue());
        assertEquals(1, controller.getDifficulty().intValue());
        assertEquals(10, controller.getMoney().intValue());
    }

    @Test
    public void testNameAndSeason() {
        controller.construct(2, "Chris", new ArrayList<>(0), "Spring", 1);
        assertEquals("Chris", controller.getName());
        assertEquals("Spring", controller.getSeason());
    }
}
