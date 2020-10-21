package test.Systems;

import main.gameManager.GameManager;
import main.util.TimeAdvancer;
import main.util.UIManager;
import main.util.customEvents.ForceUIUpdateListener;
import main.util.customEvents.NewDayListener;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UISubscribeTest {

    @Before
    public void setup() {
        TimeAdvancer.setNewDayWait(TimeTest.WAIT_TIME);
    }

    /**
     * Tests that a listener can be added to the newDay functionality. SEAN
     */
    @Test
    public void testListenToNewDay() {
        GameManager.getInstance().setDay(0);
        GameManager.getInstance().getTimeAdvancer().startTime();
        final int[] count = {0};
        NewDayListener newDayListener = e -> count[0]++;
        GameManager.getInstance().getTimeAdvancer().addListener(newDayListener);
        TimeTest.waitDay(1, TimeTest.WAIT_TIME);
        assertEquals(1, count[0]);
        GameManager.getInstance().getTimeAdvancer().pauseTime();
    }

    /**
     * Tests that a listener can be added to the forcedUIUpdate functionality. SEAN
     */
    @Test
    public void listenToForceUIUpdate() {
        final int[] count = {0};
        ForceUIUpdateListener forceUIUpdateListener = e -> count[0]++;
        UIManager.getInstance().addListener(forceUIUpdateListener);
        UIManager.getInstance().pushUIUpdate();
        assertEquals(1, count[0]);
    }
}
