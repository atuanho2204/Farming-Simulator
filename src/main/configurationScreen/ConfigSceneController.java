package main.configurationScreen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.farm.FarmController;
import main.gameManager.GameManager;
import java.util.ArrayList;
import java.util.List;

public class ConfigSceneController {
    private Stage stage;
    private GameManager gameManager;

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
        this(1, "No Name", new ArrayList<>(), "No Season");
    }

    public ConfigSceneController(Integer difficulty, String name,
                                 List<String> seeds, String season) {
        this.gameManager = new GameManager(0);
        gameManager.setDifficulty(difficulty);
        gameManager.setSeason(season);
        gameManager.setName(name);
        for (String seed : seeds) {
            gameManager.getSeeds().add(seed);
        }
    }

    public void construct(Integer difficulty, String name,
                          List<String> seeds, String season) {
        this.gameManager = new GameManager(0);
        gameManager.setDifficulty(difficulty);
        gameManager.setSeason(season);
        for (String seed : seeds) {
            gameManager.getSeeds().add(seed);
        }
        gameManager.setName(name);
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

        return nameCheck && seasonCheck && seedCheck && difficultyCheck;
    }

    @FXML
    public boolean validSeed() {
        if (wheat.isSelected() || corn.isSelected()
                || cotton.isSelected() || lettuce.isSelected()) {
            gameManager.getSeeds().clear();
            if (wheat.isSelected()) {
                gameManager.getSeeds().add("wheat");
            }
            if (corn.isSelected()) {
                gameManager.getSeeds().add("corn");
            }
            if (cotton.isSelected()) {
                gameManager.getSeeds().add("cotton");
            }
            if (lettuce.isSelected()) {
                gameManager.getSeeds().add("lettuce");
            }
            if (gameManager.getSeeds().size() != 3) {
                alertMessage += "* You must select three seed types. \n";
                return false;
            }
            return true;
        } else {
            alertMessage += "* You must select three seed types. \n";
        }
        return false;
    }

    @FXML
    public boolean validName() {
        String name = playerName.getText().trim();
        gameManager.setName(playerName.getText());
        if (name.equals("")) {
            alertMessage += "* Your name must have at least 1 character. \n";
        } else if (name.length() > 25) {
            alertMessage += "* Your name is too long (" + name.length()
                    + " characters). Should be less than 25 characters. \n";
        } else {
            return true;
        }
        return false;
    }

    @FXML
    public boolean validDifficulty() {
        if (difficultyGroup.getSelectedToggle() != null) {
            getDifficulty();
            return true;
        } else {
            alertMessage += "* You must select difficulty. \n";
        }
        return false;
    }

    @FXML
    public boolean validSeason() {
        if (seasonGroup.getSelectedToggle() != null) {
            RadioButton selectedRadioButton = (RadioButton) seasonGroup.getSelectedToggle();
            gameManager.setSeason(selectedRadioButton.getText());
            return true;
        } else {
            alertMessage += "* You must select season. \n";
        }
        return false;
    }

    public void loadNextScene(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent parent = loader.load();
            FarmController controller = loader.getController();
            controller.construct(stage, gameManager);

            stage.setTitle("FarmUI");
            stage.setScene(new Scene(parent));
        } catch (Exception e) {
            System.out.println(e.getMessage() + ": ");
            for (StackTraceElement l : e.getStackTrace()) {
                System.out.println(l);
            }
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("FarmUI not found");
            // show the dialog
            a.show();
        }
    }

    @FXML
    public void getDifficulty() {
        ToggleButton selectedDifficulty = (ToggleButton) difficultyGroup.getSelectedToggle();
        char result = selectedDifficulty.getText().toLowerCase().charAt(0);
        if (result == 'e') {
            gameManager.setDifficulty(1);
        } else if (result == 'm') {
            gameManager.setDifficulty(2);
        } else if (result == 'h') {
            gameManager.setDifficulty(3);
        }
    }

    @FXML
    public void getSeason() {
        RadioButton selectedRadioButton = (RadioButton) seasonGroup.getSelectedToggle();
        gameManager.setSeason(selectedRadioButton.getText());
    }

    public void getAlert(String message) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText(message);
        // show the dialog
        a.show();
    }

    public String getNameForTest() {
        return gameManager.getName();
    }

    public List<String> getSeedForTest() {
        return gameManager.getSeeds();
    }

    public String getSeasonForTest() {
        return gameManager.getSeason();
    }

    public int getDifficultyForTest() {
        return gameManager.getDifficulty();
    }
}