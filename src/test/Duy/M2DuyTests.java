package test.Duy;

import main.configurationScreen.ConfigSceneController;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class M2DuyTests {

    @Test (expected = NullPointerException.class)
    public void testGetSeasonWithNoSeasonSelected() {
        ConfigSceneController configScene2 = new ConfigSceneController();
        configScene2.getSeason();
    }

    @Test (expected = NullPointerException.class)
    public void testGetSeedWithNoSeedSelected() {
        ConfigSceneController configScene2 = new ConfigSceneController();
        configScene2.getSeason();
    }

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
