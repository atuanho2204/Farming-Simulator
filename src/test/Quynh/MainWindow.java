package test.Quynh;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainWindow extends Application {
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
    public void start(Stage primaryStage) {
        VBox vBox = new VBox();

        VBox vBoxForText = new VBox();
        vBoxForText.setMinHeight(500);
        Text welcomeFirstLine = new Text("Welcome to the");
        Text welcomeSecondLine = new Text("Totally Accurate Farming Simulation!");
        welcomeFirstLine.setStyle("-fx-font: 75 arial;");
        welcomeSecondLine.setStyle("-fx-font: 75 arial;");
        vBoxForText.setAlignment(Pos.CENTER);
        vBoxForText.getChildren().addAll(welcomeFirstLine, welcomeSecondLine);

        HBox hBoxForButtons = new HBox();
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");
        Button quitButton = new Button("Quit");
        quitButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");
        /*Button resumeButton = new Button("Resume");
        resumeButton.setStyle("-fx-background-color: green; -fx-text-fill: white;
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");
        hBoxForButtons.getChildren().addAll(startButton, resumeButton, quitButton);*/
        hBoxForButtons.getChildren().addAll(startButton, quitButton);
        hBoxForButtons.setSpacing(100);
        hBoxForButtons.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(vBoxForText, hBoxForButtons);

        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(new Scene(vBox));
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1200);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
