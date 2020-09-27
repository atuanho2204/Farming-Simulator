package test.Duy;

import main.configurationScreen.ConfigSceneController;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class M2DuyTests {

    /**
     * Test component: Check the season type.
     * Reason: We need to make sure that the player select season
     * Method: Create a constructor in which the parameter for difficulty is null.
     *    The unit test expects an exception.  In the game, when this exception occurs,
     *    an error wil pop up with the message
     *    “* You must choose Season.”
     */
    @Test (expected = NullPointerException.class)
    public void testGetSeasonWithNoSeasonSelected() {
        ConfigSceneController configScene2 = new ConfigSceneController();
        configScene2.getSeason();
        configScene2.handleContinueButton();
    }

    /**
     * Test component: Check the seed type.
     * Reason: We need to make sure that the player select season
     * Method: Create a constructor in which the parameter for difficulty is null.
     *    The unit test expects an exception.  In the game, when this exception occurs,
     *    an error wil pop up with the message
     *    “* You must choose Seed type.”
     */
    @Test (expected = NullPointerException.class)
    public void testGetSeedWithNoSeedSelected() {
        ConfigSceneController configScene2 = new ConfigSceneController();
        configScene2.getSeason();
        configScene2.handleContinueButton();
    }

    /**
     * Test component: Check the functionality of name field.
     * Reason: We need to make sure that user enter the name, select difficulty, season and seeds.
     * Method: Create a constructor in which the parameter difficulty, name, seeds and season
     *    The unit test expects an exception.  In the game, when this exception occurs,
     *    an error wil pop up with the message
     *    “* Your name must have at least 1 character”
     *    "* You must choose difficulty"
     *    "* You must select season"
     *    "* You must select at least one seed"
     */
    @Test
    public void testConstructor() {
        List<String> seeds =  new ArrayList<>();
        seeds.add("lettuce");
        seeds.add("corn");
        ConfigSceneController controller = new ConfigSceneController(2, "Duy Nguyen", seeds, "spring");
        assertEquals("Duy Nguyen", controller.getNameForTest());
        assertEquals(2, controller.getDifficultyForTest());
        assertEquals("spring", controller.getSeasonForTest());
        assertEquals(seeds, controller.getSeedForTest());
    }
}
