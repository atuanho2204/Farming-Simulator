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

    @Test(expected = NullPointerException.class)
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
