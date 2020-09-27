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

    private String alertMessage = "";

    public ConfigSceneController() {
    }

    public ConfigSceneController(Integer difficulty, String name,
                                  List<String> seeds, String season) {
        this.difficulty = difficulty;
        this.startingSeason = season;
        for (String seed : seeds) {
            this.seeds.add(seed);
        }
        try {
            if (name.trim() == "") {
                throw new IllegalArgumentException();
            }
            this.farmerName = name;
        } catch (IllegalArgumentException e) {
            getAlert("Your name must have at least 1 character.");
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
            getAlert(alertMessage);
            alertMessage = "";
        }
    }

    public void handleConfigQuitButton() {
        stage = (Stage) quitButtonCS.getScene().getWindow();
        stage.close();
    }

    public boolean validateData() {
        boolean nameCheck = validName();
        boolean difficultyCheck = validDifficulty();
        boolean seasonCheck = validSeason();
        boolean seedCheck = validSeed();

        if (nameCheck && seasonCheck && seedCheck && difficultyCheck) {
            return true;
        }
        return false;
    }

    public boolean validSeed() {
        if (wheat.isSelected() || corn.isSelected()
                || cotton.isSelected() || lettuce.isSelected()) {
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
            return true;
        } else {
            alertMessage += "* You must select at least one seed \n";
        }
        return false;
    }

    public boolean validName() {
        String name = playerName.getText().trim();
        if (name.equals("")) {
            alertMessage += "* Your name must have at least 1 character. \n";
        } else if (name.length() > 25) {
            alertMessage += "* Your name is too long (" + name.length()
                    + " character). Should be less than 25 character. \n";
        } else {
            farmerName = playerName.getText();
            return true;
        }
        return false;
    }

    public boolean validDifficulty() {
        if (difficultyGroup.getSelectedToggle() != null) {
            getDifficulty();
            return true;
        } else {
            alertMessage += "* You must choose difficulty \n";
        }
        return false;
    }

    public boolean validSeason() {
        if (seasonGroup.getSelectedToggle() != null) {
            RadioButton selectedRadioButton = (RadioButton) seasonGroup.getSelectedToggle();
            this.startingSeason = selectedRadioButton.getText();
            return true;
        } else {
            alertMessage += "* You must select season \n";
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

    @FXML
    public void getName() {
        farmerName = playerName.getText();
    }

    public String getNameForTest() {
        return farmerName;
    }

    public List<String> getSeedForTest() {
        return seeds;
    }

    public String getSeasonForTest() {
        return startingSeason;
    }

    public int getDifficultyForTest() {
        return difficulty;
    }

    @FXML
    public void getSeason() {
        RadioButton selectedRadioButton = (RadioButton) seasonGroup.getSelectedToggle();
        this.startingSeason = selectedRadioButton.getText();
    }
}