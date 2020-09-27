package test.Anh;


import main.configurationScreen.ConfigSceneController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class M2AnhTests {



    @Test (expected = NullPointerException.class)
    public void testGetSeasonWithNoSeasonSelected() {
        ConfigSceneController configScene2 = new ConfigSceneController();
        configScene2.getSeason();
    }

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

    @Test
    public void testEmptyName() {
        List<String> seeds = new ArrayList<>();
        seeds.add("cotton");
        ConfigSceneController controller = new ConfigSceneController(1, "bc", seeds, "fall");
    }
}
