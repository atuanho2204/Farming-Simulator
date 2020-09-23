package main;

//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
//import javafx.stage.Window;

public class ConfigSceneController {
    @FXML
    private ToggleGroup seasonGroup;

    @FXML
    private CheckBox wheat;
    @FXML
    private CheckBox corn;
    @FXML
    private CheckBox cotton;
    @FXML
    private CheckBox lettuce;

    @FXML
    public void getSeason() throws Exception {
        RadioButton selectedRadioButton = (RadioButton) seasonGroup.getSelectedToggle();
        String value = selectedRadioButton.getText();
        System.out.println(value);
    }

    @FXML
    public void getSeed() throws Exception {
        if (wheat.isSelected()) {
            System.out.println(wheat.getText());
        }
        if (corn.isSelected()) {
            System.out.println(corn.getText());
        }
        if (cotton.isSelected()) {
            System.out.println(cotton.getText());
        }
        if (lettuce.isSelected()) {
            System.out.println(lettuce.getText());
        }
    }
    @FXML
    private void initialize() {
    }


}