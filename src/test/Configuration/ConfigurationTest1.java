package test.Configuration;

import main.configurationScreen.ConfigSceneController;
import org.junit.Test;

/**
 * @author Quynh
 */
public class ConfigurationTest1 {

    /**
     *
     * Test Component: one of the ToggleGroup object--season
     * Reason: We need to make sure that the player chooses a starting season in order
     *    to begin the game.
     * Method: Create a constructor in which the parameter for season is null.
     *    The unit test expects an exception.  In the game, when this exception occurs,
     *    an error will pop up with the message “Please enter your name and select difficulty,
     *    season, seed before start!!!”
     *
     */
    @Test (expected = NullPointerException.class)
    public void testGetSeasonWithNoSeasonSelected() {
        ConfigSceneController configScene2 = new ConfigSceneController();
        configScene2.getSeason();
    }
}
