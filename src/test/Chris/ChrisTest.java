package test.Chris;

import com.sun.javafx.application.PlatformImpl;
import main.farm.FarmController;
import main.gameManager.GameManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ChrisTest {
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
        assertEquals(10, gameMan.getSeason());
    }

    @Test
    public void testNameAndSeason() {
        controller.construct(null, gameMan);
        gameMan.setSeason("Spring");
        gameMan.setName("Christ");

        assertEquals("Chris", gameMan.getName());
        assertEquals("Spring", gameMan.getSeason());
    }
}
