package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import main.configurationScreen.ConfigSceneController;
import main.welcomeScreen.WelcomeSceneController;

import java.io.File;

public class App extends Application {
    private int sceneWidth = 1200;
    private int sceneHeight = 800;

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
     * @throws java.lang.Exception throws Exception if the fxml file is not found
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "welcomeScreen/welcomeScene.fxml"
                    )
            );
            Parent parent = loader.load();
            WelcomeSceneController controller = loader.getController();
            controller.construct();

            primaryStage.setTitle("Welcome");
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Welcome Screen not found");
            // show the dialog
            a.show();
        }
    }
}
