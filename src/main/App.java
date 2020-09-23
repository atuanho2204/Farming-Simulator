package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private int sceneWidth = 1200;
    private int sceneHeight = 800;

    /**
     * main method to help launch the program
     * @param args unused parameter
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * start method to place UI controls in a scene and display the scene in a stage
     * @param primaryStage primary stage of the JavaFX program
     * @throws java.lang.Exception throws Exception if the fxml file is not found
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent welcomePane = FXMLLoader.load(getClass().getResource("welcomeScene.fxml"));
        Scene welcomeScene = new Scene(welcomePane, sceneWidth, sceneHeight);

        primaryStage.setTitle("Totally Accurate Farming Simulator");
        primaryStage.setScene(welcomeScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
