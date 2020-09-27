package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import main.welcomeScreen.WelcomeSceneController;

public class App extends Application {
    public static final int PREF_WIDTH = 1200;
    public static final int PREF_HEIGHT = 800;

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
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "welcomeScreen/welcomeScene.fxml"
                    )
            );
            Parent parent = loader.load();
            WelcomeSceneController controller = loader.getController();
            controller.construct();

            primaryStage.setTitle("Welcome");
            primaryStage.setScene(new Scene(parent, PREF_WIDTH, PREF_HEIGHT));
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
