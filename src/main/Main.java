package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * main method to help launch the program
     * @param args unused parameter
     * @throws java.lang.Exception throws exception
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /**
     * start method to place UI controls in a scene and display the scene in a stage
     * @param primaryStage primary stage of the JavaFX program
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("farmUI.xml"));
        Parent root = new StackPane();
        primaryStage.setTitle("Scene 1");
        primaryStage.setScene(new Scene(root, 800, 1200));
        primaryStage.show();

        /*Thread.sleep(5000);
        StackPane newRoot = new StackPane();
        primaryStage.setTitle("Scene 2");
        primaryStage.setScene(new Scene(newRoot, 1200, 800));*/
    }
}
