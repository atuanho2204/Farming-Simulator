package main.welcomeScreen;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.configurationScreen.ConfigSceneController;
import java.io.IOException;


public class WelcomeSceneController {
    private Stage primaryStage;
    private AudioClip backgroundMusic;

    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;

        java.net.URL resource = getClass().getResource(
                "/main/soundtrack/buddy.mp3");
        backgroundMusic = new AudioClip(resource.toExternalForm());
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        backgroundMusic.play();
    }

    @FXML
    public void handleStartButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../configurationScreen/configScene.fxml"
                )
        );
        Parent parent = loader.load();
        ConfigSceneController controller = loader.getController();
        backgroundMusic.stop();
        controller.construct(primaryStage, backgroundMusic);
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(new Scene(parent));
    }

    @FXML
    public void handleQuitButton(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}





