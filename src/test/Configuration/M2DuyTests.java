package test.Configuration;


import main.configurationScreen.ConfigSceneController;
import main.gameManager.GameManager;
import main.farm.crops.CropTypes;
import main.util.Seasons;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;


public class M2DuyTests {

    @Before
    public void setup() {
        GameManager.getInstance().clear(); //test only
    }

    /**
     * Test component: Check the season type.
     * Reason: We need to make sure that the player select season
     * Method: Create a constructor in which the parameter for difficulty is null.
     * The unit test expects an exception.  In the game, when this exception occurs,
     * an error wil pop up with the message
     * “* You must choose Season.”
     */
    @Test(expected = NullPointerException.class)
    public void testGetSeasonWithNoSeasonSelected() {
        ConfigSceneController configScene2 = new ConfigSceneController();
        configScene2.getSeason();
        configScene2.handleContinueButton();
    }

    /**
     * Test component: Check the seed type.
     * Reason: We need to make sure that the player select season
     * Method: Create a constructor in which the parameter for difficulty is null.
     * The unit test expects an exception.  In the game, when this exception occurs,
     * an error wil pop up with the message
     * “* You must choose Seed type.”
     */
    @Test(expected = NullPointerException.class)
    public void testGetSeedWithNoSeedSelected() {
        ConfigSceneController configScene2 = new ConfigSceneController();
        configScene2.getSeason();
        configScene2.handleContinueButton();
    }

    /**
     * Test component: Check the functionality of name field.
     * Reason: We need to make sure that user enter the name, select difficulty, season and seeds.
     * Method: Create a constructor in which the parameter difficulty, name, seeds and season
     * The unit test expects an exception.  In the game, when this exception occurs,
     * an error wil pop up with the message
     * “* Your name must have at least 1 character”
     * "* You must choose difficulty"
     * "* You must select season"
     * "* You must select at least one seed"
     */
    @Test
    public void testConstructor() {
        List<CropTypes> seeds = new ArrayList<>();
        seeds.add(CropTypes.LETTUCE);
        seeds.add(CropTypes.CORN);
        ConfigSceneController controller = new ConfigSceneController();
        controller.construct(null, 2, "Duy", seeds, Seasons.SPRING);
        assertEquals("Duy", controller.getNameForTest());
        assertEquals(2, controller.getDifficultyForTest());
        assertEquals(Seasons.SPRING, GameManager.getInstance().getSeason());
        assertEquals(seeds, controller.getSeedForTest());
    }
}
