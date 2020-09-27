package test.Quynh;

import main.configurationScreen.ConfigSceneController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class M2QuynhTests {

    /*@Test (expected = ExceptionInInitializerError.class)
    public void testFxmlPath() {
        String path = "farm/farmUI.fxml";
        ConfigSceneController configScene1 = new ConfigSceneController();
        configScene1.loadNextScene(path);
    }*/

    /**
     *
     * Test Component: player’s name
     * Reason: We need to check that the name entered by the player has at least one character.
     *    In other words, the player’s name is not allowed to be an empty String.
     * Method: Create a constructor in which the parameter for name is an empty String.
     *    The unit test expects an exception.  In the game, when this exception occurs,
     *    an error will pop up with the message “Your name must have at least 1 character.”
     *    
     */
    @Test (expected = NullPointerException.class)
    public void testGetSeasonWithNoSeasonSelected() {
        ConfigSceneController configScene2 = new ConfigSceneController();
        configScene2.getSeason();
    }

    /**
     *
     * Test Component: one of the ToggleGroup object--season
     * Reason: We need to make sure that the player chooses a starting season in order to begin the game.
     * Method: Create a constructor in which the parameter for season is null.
     *    The unit test expects an exception.  In the game, when this exception occurs, an error will
     *    pop up with the message “Please enter your name and select difficulty, season, seed before start!!!”
     *
     */
    @Test (expected = ExceptionInInitializerError.class)
    public void testNameOfEmptyString() {
        List<String> seeds =  new ArrayList<>();
        seeds.add("cotton");
        ConfigSceneController configScene1 = new ConfigSceneController(1, "", seeds, "fall");
    }
}
