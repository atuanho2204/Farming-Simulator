package main;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.util.AlertUser;
import main.util.SceneLoader;

public class App extends Application {
    /**
     * main method to help launch the program
     *
     * @param args unused parameter
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * start method to place UI controls in a scene and display the scene in a stage
     *
     * @param primaryStage primary stage of the JavaFX program
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            java.net.URL resource = getClass().getResource(
                    "/main/soundtrack/buddy.mp3");
            AudioClip backgroundMusic = new AudioClip(resource.toExternalForm());

            primaryStage.setResizable(false);
            SceneLoader.loadScene("../welcomeScreen/welcomeScene.fxml", primaryStage,
                    backgroundMusic, "Welcome");
            primaryStage.show();
        } catch (Exception e) {
            AlertUser.alertUser("Welcome Screen not found");
        }
    }
}
