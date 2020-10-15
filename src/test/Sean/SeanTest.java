package test.Sean;

import main.gameManager.GameManager;
import main.util.TimeAdvancer;
import main.util.UIManager;
import main.util.customEvents.ForceUIUpdateListener;
import main.util.customEvents.NewDayListener;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeanTest {
    private GameManager gameManager;
    private final int waitTime = 100; //milliseconds
    private final int day = 0;

    /**
     * setup tests.
     */
    @Before
    public void setup() {
        gameManager = new GameManager(day);
        TimeAdvancer.setNewDayWait(waitTime);
    }

    /**
     * Tests that the day increments every wait period.
     */
    @Test
    public void testDayIncrement() {
        gameManager.getTimeAdvancer().startTime();
        waitDay(1);
        waitDay(2);
        waitDay(3);
    }

    /**
     * Tests that the day increments and can be paused.
     */
    @Test
    public void testStopTime() {
        gameManager.getTimeAdvancer().startTime();
        waitDay(1);
        gameManager.getTimeAdvancer().pauseTime();
        waitDay(1);
        waitDay(1);
    }

    /**
     * Tests that a listener can be added to the newDay functionality.
     */
    @Test
    public void testListenToNewDay() {
        gameManager.getTimeAdvancer().startTime();
        final int[] count = {0};
        NewDayListener newDayListener = e -> count[0]++;
        gameManager.getTimeAdvancer().addListener(newDayListener);
        waitDay(1);
        assertEquals(1, count[0]);
    }

    /**
     * Tests that a listener can be added to the forcedUIUpdate functionality.
     */
    @Test
    public void listenToForceUIUpdate() {
        final int[] count = {0};
        ForceUIUpdateListener forceUIUpdateListener = e -> count[0]++;
        UIManager.getInstance().addListener(forceUIUpdateListener);
        UIManager.getInstance().pushUIUpdate();
        assertEquals(1, count[0]);
    }

    /**
     * Tests that the gameManager maintains proper construction.
     */
    @Test
    public void testGameManagerFields() {
        assertNotNull(gameManager.getTimeAdvancer());
        assertNotNull(gameManager.getMarket());
        assertNotNull(gameManager.getSeeds());
        assertEquals("", gameManager.getName());
        assertEquals(day, gameManager.getDay().intValue());
        assertEquals(0, gameManager.getMoney().intValue());
        assertEquals(1, gameManager.getDifficulty().intValue());
    }


    /**
     * Waits a day and checks that the expected value is now the newDay.
     * @param expect the expected day
     */
    private void waitDay(int expect) {
        long time = System.currentTimeMillis();
        try {
            Thread.sleep(waitTime + 10);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Passed time: " + (System.currentTimeMillis() - time));
        assertEquals(expect, gameManager.getDay().intValue());
    }
}