package main.configurationScreen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.farm.FarmController;

import java.util.ArrayList;
import java.util.List;

public class ConfigSceneController {
    private Stage stage;
    private String farmerName;
    private Integer difficulty; // 1 = easy; 2 = medium; 3 = hard
    private List<String> seeds = new ArrayList<>();
    private String startingSeason;
    private final int day = 0;

    @FXML
    private Button continueButtonCS;
    @FXML
    private Button quitButtonCS;
    @FXML
    private TextField playerName;

    @FXML
    private ToggleGroup difficultyGroup;
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

    public ConfigSceneController() {
    }

    public ConfigSceneController(Integer difficulty, String name,
                                  List<String> seeds, String season) {
        this.difficulty = difficulty;
        this.farmerName = name;
        this.startingSeason = season;
        for (String seed : seeds) {
            this.seeds.add(seed);
        }
    }

    public void construct() {
        //this doesn't need to do anything yet
    }

    public void handleContinueButton() {
        stage = (Stage) continueButtonCS.getScene().getWindow();
        boolean dataIsGood = validateData();
        if (dataIsGood) {
            loadNextScene("../farm/farmUI.fxml");
        } else {
            getAlert("Please enter your name and select difficulty, season, seed before start!!!");
        }
    }

    //SEAN ADDED THIS (You might need to modify it further)
    public boolean validateData() {
        try {
            boolean seasonCheck = seasonGroup.getSelectedToggle().isSelected();
            boolean seedCheck = wheat.isSelected() || corn.isSelected()
                    || cotton.isSelected() || lettuce.isSelected();
            boolean difficultyCheck = difficultyGroup.getSelectedToggle().isSelected();
            boolean nameCheck = !playerName.getText().isEmpty();

            if (nameCheck && seasonCheck && seedCheck && difficultyCheck) {
                farmerName = playerName.getText();
                getDifficulty();

                if (wheat.isSelected()) {
                    seeds.add("wheat");
                }
                if (corn.isSelected()) {
                    seeds.add("corn");
                }
                if (cotton.isSelected()) {
                    seeds.add("cotton");
                }
                if (lettuce.isSelected()) {
                    seeds.add("lettuce");
                }
                getSeason();
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void loadNextScene(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent parent = loader.load();
            FarmController controller = loader.getController();
            controller.construct(difficulty, farmerName, seeds, startingSeason, day);
            stage.setTitle("FarmUI");
            stage.setScene(new Scene(parent));
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("FarmUI not found");
            // show the dialog
            a.show();
        }
    }

    public void getDifficulty() {
        ToggleButton selectedDifficulty = (ToggleButton) difficultyGroup.getSelectedToggle();
        char result = selectedDifficulty.getText().toLowerCase().charAt(0);
        if (result == 'e') {
            difficulty = 1;
        }
        if (result == 'm') {
            difficulty = 2;
        }
        if (result == 'h') {
            difficulty = 3;
        }
    }

    public void getAlert(String message) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText(message);
        // show the dialog
        a.show();
    }

    public void handleConfigQuitButton() {
        stage = (Stage) quitButtonCS.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void getSeason() {
        RadioButton selectedRadioButton = (RadioButton) seasonGroup.getSelectedToggle();
        this.startingSeason = selectedRadioButton.getText();
    }

    /*
    @FXML
    public void getSeed() throws Exception {
        if (wheat.isSelected()) {
            //System.out.println(wheat.getText());
        }
        if (corn.isSelected()) {
            //System.out.println(corn.getText());
        }
        if (cotton.isSelected()) {
            //System.out.println(cotton.getText());
        }
        if (lettuce.isSelected()) {
            //System.out.println(lettuce.getText());
        }
    }*/

}