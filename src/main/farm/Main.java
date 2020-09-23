package farm;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class Main extends Application {
    private int sceneWidth = 1200;
    private int sceneHeight = 800;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("farmUI.fxml"));
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        //scene.getStylesheets().add("farm/style.css");
        primaryStage.setTitle("FarmUI");

        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
