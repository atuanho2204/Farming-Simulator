package test.Anh;


import main.configurationScreen.ConfigSceneController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class M2AnhTests {

    @Test (expected = NullPointerException.class)
    public void testGetSeasonWithNoSeasonSelected() {
        ConfigSceneController configScene2 = new ConfigSceneController();
        configScene2.getSeason();
    }

    @Test (expected = ExceptionInInitializerError.class)
    public void testNameOfEmptyString() {
        List<String> seeds =  new ArrayList<>();
        seeds.add("cotton");
        ConfigSceneController configScene1 = new ConfigSceneController(1, "", seeds, "fall");
    }
}
