package test.Sean;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import main.farm.FarmController;
import main.gameManager.GameManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SeanTest {
    private FarmController controller;
    GameManager gameMan;

    @Before
    public void setup() {
        PlatformImpl.startup(() -> {
            //this code serves to remove the "Toolkit not found" error
        });
        controller = new FarmController();
        gameMan = new GameManager(0);
    }

    @Test
    public void testConstructController() {
        controller.construct(null, gameMan);
        assertEquals(0, gameMan.getDay().intValue());
        assertEquals(1, gameMan.getDifficulty().intValue());
        assertEquals(10, gameMan.getMoney().intValue());
    }


    @Test
    public void testDayIncrements() {
        controller.construct(null, gameMan);
        assertEquals(0, gameMan.getDay().intValue());
        try {
            Thread.sleep(4050);
            assertEquals(1, gameMan.getDay().intValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}