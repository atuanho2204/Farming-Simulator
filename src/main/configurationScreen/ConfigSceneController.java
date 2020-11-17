package main.configurationScreen;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.util.AlertUser;
import main.farm.crops.CropTypes;
import main.util.SceneLoader;
import main.util.Seasons;
import main.util.UIController;

import java.util.ArrayList;
import java.util.List;

public class ConfigSceneController extends UIController {


    @FXML
    private Button continueButtonCS;
    @FXML
    private Button quitButtonCS;
    @FXML
    private Button skipButtonWS;
    @FXML
    private TextField playerName;

    @FXML
    private ToggleGroup difficultyGroup;
    @FXML
    private ToggleGroup seasonGroup;

    @FXML
    private CheckBox wheat;
    @FXML
    private CheckBox carrot;
    @FXML
    private CheckBox cotton;
    @FXML
    private CheckBox lettuce;

    private String alertMessage = "";

    @Override
    public void construct(Stage primaryStage, AudioClip backgroundMusic) {
        construct(primaryStage, 1, "",
                new ArrayList<CropTypes>(), Seasons.FALL, backgroundMusic);
    }

    public void construct(Stage primaryStage, Integer difficulty, String name,
                          List<CropTypes> seeds, Seasons season,
                            AudioClip backgroundMusic) {
        this.primaryStage = primaryStage;
        GameManager.getInstance().setDifficulty(difficulty);
        GameManager.getInstance().setSeason(season);
        GameManager.getInstance().setSeeds(seeds);
        GameManager.getInstance().setName(name);

        this.backgroundMusic = backgroundMusic;
        java.net.URL resource = getClass().getResource(
                "/main/soundtrack/ukulele.mp3");
        this.backgroundMusic = new AudioClip(resource.toExternalForm());
        this.backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        this.backgroundMusic.setVolume(0.2);
        this.backgroundMusic.play();
    }

    public void handleContinueButton() {
        primaryStage = (Stage) continueButtonCS.getScene().getWindow();
        boolean dataIsGood = validateData();
        if (dataIsGood) {
            SceneLoader.loadScene("../farm/farmUI.fxml", primaryStage,
                    backgroundMusic, "FarmUI");
        } else {
            AlertUser.alertUser(alertMessage);
        }
    }

    public void handleConfigQuitButton() {
        primaryStage = (Stage) quitButtonCS.getScene().getWindow();
        primaryStage.close();
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
        if (wheat.isSelected() || carrot.isSelected()
                || cotton.isSelected() || lettuce.isSelected()) {
            GameManager.getInstance().getSeeds().clear();
            if (wheat.isSelected()) {
                GameManager.getInstance().getSeeds().add(CropTypes.WHEAT);
            }
            if (carrot.isSelected()) {
                GameManager.getInstance().getSeeds().add(CropTypes.CARROT);
            }
            if (cotton.isSelected()) {
                GameManager.getInstance().getSeeds().add(CropTypes.TOMATO);
            }
            if (lettuce.isSelected()) {
                GameManager.getInstance().getSeeds().add(CropTypes.LETTUCE);
            }
            if (GameManager.getInstance().getSeeds().size() != 3) {
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
        GameManager.getInstance().setName(playerName.getText());
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
            GameManager.getInstance().setSeason(
                    Seasons.valueOf(selectedRadioButton.getText().toUpperCase()));
            return true;
        } else {
            alertMessage += "* You must select season. \n";
        }
        return false;
    }

    @FXML
    public void getDifficulty() {
        ToggleButton selectedDifficulty = (ToggleButton) difficultyGroup.getSelectedToggle();
        char result = selectedDifficulty.getText().toLowerCase().charAt(0);
        if (result == 'e') {
            GameManager.getInstance().setDifficulty(1);
        } else if (result == 'm') {
            GameManager.getInstance().setDifficulty(2);
        } else if (result == 'h') {
            GameManager.getInstance().setDifficulty(3);
        }
    }

    @FXML
    public void getSeason() {
        RadioButton selectedRadioButton = (RadioButton) seasonGroup.getSelectedToggle();
        GameManager.getInstance().setSeason(
                Seasons.valueOf(selectedRadioButton.getText().toUpperCase()));
    }

    public String getNameForTest() {
        return GameManager.getInstance().getName();
    }

    public List<CropTypes> getSeedForTest() {
        return GameManager.getInstance().getSeeds();
    }

    public int getDifficultyForTest() {
        return GameManager.getInstance().getDifficulty();
    }

    public void handleSkipButton() {
        primaryStage = (Stage) skipButtonWS.getScene().getWindow();
        // set name
        GameManager.getInstance().setName("Super Farmer");
        // set difficulty
        GameManager.getInstance().setDifficulty(3);
        // set season
        GameManager.getInstance().setSeason(Seasons.FALL);
        // set seeds
        GameManager.getInstance().getSeeds().add(CropTypes.values()[0]);
        GameManager.getInstance().getSeeds().add(CropTypes.values()[1]);
        GameManager.getInstance().getSeeds().add(CropTypes.values()[2]);
        //
        SceneLoader.loadScene("../farm/farmUI.fxml", primaryStage,
                backgroundMusic, "FarmUI");
    }
}