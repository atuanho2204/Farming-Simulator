package test.Quynh;

import main.configurationScreen.ConfigSceneController;
import org.junit.Before;
import org.junit.Test;

public class M2QuynhTests {
    private ConfigSceneController configScene;

    @Before
    public void setUp() {
        configScene = new ConfigSceneController();
    }

    @Test (expected = ExceptionInInitializerError.class)
    public void testPath() {
        String path = "farm/farmUI.fxml";
        configScene.loadNextScene(path);
    }
}
