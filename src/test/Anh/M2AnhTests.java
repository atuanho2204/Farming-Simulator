package test.Anh;


import main.configurationScreen.ConfigSceneController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class M2AnhTests {

    /**
     * Test component: Check if the name of player.
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
        seeds.add("cotton");
        seeds.add("corn");
        ConfigSceneController controller = new ConfigSceneController(1, "Anh Ho", seeds, "fall");
        assertEquals("Anh Ho", controller.getNameForTest());
        assertEquals(1, controller.getDifficultyForTest());
        assertEquals("fall", controller.getSeasonForTest());
        assertEquals(seeds, controller.getSeedForTest());
    }

    /**
     * Test component: Check if the name of player.
     * Reason: We need to make sure that the player select difficulty
     * Method: Create a constructor in which the parameter for difficulty is null.
     *    The unit test expects an exception.  In the game, when this exception occurs,
     *    an error wil pop up with the message
     *    “* You must choose difficulty.”
     */
    @Test (expected = NullPointerException.class)
    public void testDifficulty() {
        List<String> seeds = new ArrayList<>();
        seeds.add("corn");
        ConfigSceneController controller2 = new ConfigSceneController(null, "Anh", seeds, "fall");
        controller2.handleContinueButton();
    }

    /**
     * Test component: Check if the name of player.
     * Reason: We need to make sure that the name of player is not too long (over 25 characters).
     * Method: Create a constructor in which the parameter for name is longer than 25 characters.
     *    The unit test expects an exception.  In the game, when this exception occurs,
     *    an error wil pop up with the message
     *    “Your name is too long  (# characters). Should be less than 25 characters.”
     */
    @Test (expected = NullPointerException.class)
    public void testLongName() {
        List<String> seeds = new ArrayList<>();
        seeds.add("corn");
        String name = "di fhdsifkdsfsdkfnaskdjfnaskgnaoeribnaoenbadfobkndfkbldfvnifv";
        ConfigSceneController controller2 = new ConfigSceneController(2, name, seeds, "fall");
        controller2.handleContinueButton();
    }


}
