package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.ArrayList;

public class MainWindow extends Application {
    /**
     * main method to help launch the program
     * @param args unused parameter
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

        HBox hBox = new HBox();
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-border-radius: 20; -fx-background-radius: 20");
        Button quitButton = new Button("Quit");
        quitButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-border-radius: 20; -fx-background-radius: 20");
        hBox.getChildren().addAll(startButton, quitButton);
        hBox.setPadding(new Insets(10, 20, 10, 20));
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(hBox);

        primaryStage.setTitle("Totally Accurate Farming Simulation");
        primaryStage.setScene(new Scene(vBox));
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1200);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
