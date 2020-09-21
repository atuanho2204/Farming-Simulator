package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private int sceneWidth = 1200;
    private int sceneHeight = 800;

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
        // Set up panes and add panes to scenes
        VBox welcomePane = new VBox();
        VBox configPane = new VBox();
        VBox gamePane = new VBox();

        Scene welcomeScene = new Scene(welcomePane, sceneWidth, sceneHeight);
        Scene configScene = new Scene(configPane, sceneWidth, sceneHeight);
        Scene gameScene = new Scene(gamePane, sceneWidth, sceneHeight);

        // Welcome Scene = ws
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");

        Button quitButton1 = new Button("Quit");
        quitButton1.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");

        HBox wsButtons = new HBox();
        wsButtons.getChildren().addAll(startButton, quitButton1);
        wsButtons.setAlignment(Pos.CENTER);
        wsButtons.setSpacing(30);
        welcomePane.getChildren().add(wsButtons);

        startButton.setOnAction(e -> {
            primaryStage.setScene(configScene);
        });
        quitButton1.setOnAction(e -> {
            primaryStage.close();
        });

        // Configuration Scene = cs
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");

        Button quitButton2 = new Button("Quit");
        quitButton2.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");

        HBox csButtons = new HBox();
        csButtons.getChildren().addAll(continueButton, quitButton2);
        csButtons.setAlignment(Pos.CENTER);
        csButtons.setSpacing(30);
        configPane.getChildren().add(csButtons);

        continueButton.setOnAction(e -> {
            primaryStage.setScene(gameScene);
        });
        quitButton2.setOnAction(e -> {
            primaryStage.close();
        });

        // Game Scene = gs
        Button pauseButton = new Button("Pause");
        pauseButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");
        Button quitButton3 = new Button("Quit");
        quitButton3.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");
        HBox gsButtons = new HBox();
        gsButtons.getChildren().addAll(pauseButton, quitButton3);
        gsButtons.setAlignment(Pos.CENTER);
        gsButtons.setSpacing(30);
        gamePane.getChildren().add(gsButtons);
        quitButton3.setOnAction(e -> {
            primaryStage.close();
        });

        primaryStage.setTitle("Welcome");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }
}
