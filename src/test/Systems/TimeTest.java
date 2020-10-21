package test.Systems;

import main.gameManager.GameManager;
import main.market.Market;
import main.util.TimeAdvancer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeTest {
    public static final int WAIT_TIME = 100; //milliseconds
    private final int day = 0;

    /**
     * setup tests.
     */
    @Before
    public void setup() {
        TimeAdvancer.setNewDayWait(WAIT_TIME);
        GameManager.getInstance().clear(); //test only
    }

    /**
     * Tests that the day increments every wait period. SEAN
     */
    @Test
    public void testDayIncrement() {
        GameManager.getInstance().setDay(0);
        GameManager.getInstance().getTimeAdvancer().startTime();
        waitDay(1, WAIT_TIME);
        waitDay(2, WAIT_TIME);
        waitDay(3, WAIT_TIME);
        GameManager.getInstance().getTimeAdvancer().pauseTime();
    }

    /**
     * Tests that the day increments and can be paused. SEAN
     */
    @Test
    public void testStopTime() {
        GameManager.getInstance().setDay(0);
        GameManager.getInstance().getTimeAdvancer().startTime();
        waitDay(1, WAIT_TIME);
        GameManager.getInstance().getTimeAdvancer().pauseTime();
        waitDay(1, WAIT_TIME);
        waitDay(1, WAIT_TIME);
    }

    /**
     * Tests that the gameManager maintains proper construction. SEAN
     */
    @Test
    public void testGameManagerFields() {
        GameManager.getInstance().setDay(0);
        assertNotNull(GameManager.getInstance().getTimeAdvancer());
        GameManager.getInstance().setMarket(new Market());
        assertNotNull(GameManager.getInstance().getMarket());
        assertNotNull(GameManager.getInstance().getSeeds());
        assertEquals("", GameManager.getInstance().getName());
        assertEquals(0, GameManager.getInstance().getMoney().intValue());
        assertEquals(1, GameManager.getInstance().getDifficulty().intValue());
        assertEquals(day, GameManager.getInstance().getDay().intValue());
    }

    /**
     * Waits a day and checks that the expected value is now the newDay. SEAN
     * @param expect the expected day
     * @param waitTime the amount of time to wait for
     */
    public static void waitDay(int expect, int waitTime) {
        long time = System.currentTimeMillis();
        try {
            Thread.sleep(waitTime + 20);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Passed time: " + (System.currentTimeMillis() - time));
        assertEquals(expect, GameManager.getInstance().getDay().intValue());
    }
}