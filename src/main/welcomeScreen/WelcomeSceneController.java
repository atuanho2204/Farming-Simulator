package main.welcomeScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.util.SceneLoader;
import main.util.MainController;

import java.io.IOException;

public class WelcomeSceneController extends MainController {
    private Stage primaryStage;
    private AudioClip backgroundMusic;

    @Override
    public void construct(Stage primaryStage, AudioClip backgroundMusic) {
        this.primaryStage = primaryStage;
        this.backgroundMusic = backgroundMusic;

        java.net.URL resource = getClass().getResource(
                "/main/soundtrack/buddy.mp3");
        this.backgroundMusic = new AudioClip(resource.toExternalForm());
        this.backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        this.backgroundMusic.setVolume(0.2);
        this.backgroundMusic.play();
    }

    @FXML
    public void handleStartButton(ActionEvent event) throws IOException {
        SceneLoader.loadScene("../configurationScreen/configScene.fxml",
                primaryStage, backgroundMusic, "Welcome!");
    }

    @FXML
    public void handleQuitButton(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}





