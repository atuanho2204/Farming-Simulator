package test.Quynh;

import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import main.configurationScreen.ConfigSceneController;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class M2QuynhTests {
    ConfigSceneController configScene;

    @Before
    public void setUp() {
        configScene = new ConfigSceneController();
    }

    @Test (expected = ExceptionInInitializerError.class)
    public void testPath() {
        String path = "farm/farmUI.fxml";
        configScene.loadNextScene(path);
    }

    @Test (expected = Exception.class)
    public void testValidateData() {
        configScene.validateData();
    }

    /*@Test
    public void testGetToggleGroupOutput() {
        TextField name = new TextField();
        name.setUserData("Quynh");
        configScene.getFarmerName();
        assertTrue(configScene.validateName());
    }*/
}
