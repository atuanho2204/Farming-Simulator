package main.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class SceneLoader {
    public static final int PREF_WIDTH = 1200;
    public static final int PREF_HEIGHT = 800;

    public static void loadScene(String path, Stage primaryStage,
                                 AudioClip backgroundMusic, String titleName) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(path));
            Parent parent = loader.load();
            UIController controller = loader.getController();

            backgroundMusic.stop();
            controller.construct(primaryStage, backgroundMusic);
            primaryStage.setTitle(titleName);
            primaryStage.setScene(new Scene(parent,PREF_WIDTH, PREF_HEIGHT));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            AlertUser.alertUser("FarmUI not found");
        }
    }
}
