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
    private int difficulty = 1;
    private String name = "";
    private String seedType = "";
    private int season = 0;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("farmUI.fxml"));
        Scene scene = loader.load();
        FarmController controller = loader.getController();
        controller.construct(difficulty, name, seedType, season);
        primaryStage.setTitle("FarmUI");

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
