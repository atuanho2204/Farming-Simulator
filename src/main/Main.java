package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
//import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileInputStream;

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

        // Set up panes and add panes to scenes
        StackPane welcomePane = new StackPane();
        Parent configPane = FXMLLoader.load(getClass().getResource("configScene.fxml"));
        VBox gamePane = new VBox();

        Scene welcomeScene = new Scene(welcomePane, sceneWidth, sceneHeight);
        Scene configScene = new Scene(configPane, sceneWidth, sceneHeight);
        Scene gameScene = new Scene(gamePane, sceneWidth, sceneHeight);

        // Welcome Scene = ws
        Image image = new Image(new FileInputStream("src/main/images/welcome_background.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(sceneHeight);
        imageView.setFitWidth(sceneWidth);

        Button startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 40 arial; -fx-border-radius: 20; -fx-background-radius: 20");
        Button quitButton1 = new Button("Quit");
        quitButton1.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 40 arial; -fx-border-radius: 20; -fx-background-radius: 20");

        HBox wsButtons = new HBox();
        wsButtons.getChildren().addAll(startButton, quitButton1);
        wsButtons.setAlignment(Pos.CENTER);
        wsButtons.setSpacing(30);
        wsButtons.setPadding(new Insets(100, 0, 0, 0));

        welcomePane.getChildren().addAll(imageView, wsButtons);

        startButton.setOnAction(e -> {
            primaryStage.setScene(configScene);
        });
        quitButton1.setOnAction(e -> {
            primaryStage.close();
        });

        // Configuration Scene = cs
        /*Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");
        Button quitButton2 = new Button("Quit");
        quitButton2.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");

        HBox csButtons = new HBox();
        //csButtons.setMinSize(1200, 800);
        //csButtons.setHgap(20);
        //csButtons.add(continueButton, 0, 3);
        //csButtons.add(quitButton2,1, 3);
        csButtons.setAlignment(Pos.CENTER);
        csButtons.setSpacing(30);
        csButtons.getChildren().addAll(continueButton,quitButton2);
        configPane.add(csButtons,3,3);

        continueButton.setOnAction(e -> {
            primaryStage.setScene(gameScene);
        });
        quitButton2.setOnAction(e -> {
            primaryStage.close();
        });*/

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





        primaryStage.setTitle("Totally Accurate Farming Simulator");
        primaryStage.setScene(welcomeScene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
