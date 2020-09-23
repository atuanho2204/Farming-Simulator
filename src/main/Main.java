package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        FXMLLoader root = new FXMLLoader(getClass().getResource("configScene.fxml"));
        Parent configPane = root.load();
        Scene configScene = new Scene(configPane);

        primaryStage.setTitle("Totally Accurate Farming Simulator");
        primaryStage.setScene(configScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    /*Parent root = FXMLLoader.load(getClass().getResource("configScene.fxml"));

        // Set up panes and add panes to scenes
        StackPane welcomePane = new StackPane();
        VBox configPane = new VBox();
        VBox gamePane = new VBox();

        Scene welcomeScene = new Scene(welcomePane, sceneWidth, sceneHeight);
        Scene configScene = new Scene(root, sceneWidth, sceneHeight);
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
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");
        Button quitButton2 = new Button("Quit");
        quitButton2.setStyle("-fx-background-color: green; -fx-text-fill: white;"
                + "-fx-font: 50 arial; -fx-border-radius: 20; -fx-background-radius: 20");

        // Add player's name
        HBox hboxOfName = new HBox();
        hboxOfName.setSpacing(10);
        hboxOfName.setPadding(new Insets(50, 100, 10, 200));

        Label nameLable = new Label("Player's Name:");
        nameLable.setFont(new Font("Western", 35));

        TextField playerName = new TextField ();
        playerName.setAlignment(Pos.CENTER);
        playerName.setPrefWidth(500);
        playerName.setPrefHeight(60);

        hboxOfName.getChildren().addAll(nameLable, playerName);

        // End Add Player's name

        // Select the diffculty
        HBox difficulty = new HBox();

        Label dif = new Label("Select Difficulty: ");
        dif.setFont(new Font("Western", 35));

        Button easy = new Button("Easy");
        easy.setMaxWidth(Double.MAX_VALUE);
        easy.setStyle("-fx-background-color: #00ff00;"
                + "-fx-font: 24 arial; -fx-border-radius: 20; -fx-background-radius: 20");

        Button normal = new Button("Normal");
        normal.setMaxWidth(Double.MAX_VALUE);
        normal.setStyle("-fx-background-color: #FFA500;"
                + "-fx-font: 24 arial; -fx-border-radius: 20; -fx-background-radius: 20");

        Button hard = new Button ("Hard");
        hard.setMaxWidth(Double.MAX_VALUE);
        hard.setStyle("-fx-background-color: #FF0000;"
                + "-fx-font: 24 arial; -fx-border-radius: 20; -fx-background-radius: 20");

        difficulty.setPadding(new Insets(50, 100, 10, 200));

        difficulty.setSpacing(10);
        difficulty.setMargin(dif, new Insets(20, 20, 20, 0));
        difficulty.setMargin(easy, new Insets(20, 20, 20, 20));
        difficulty.setMargin(normal, new Insets(20, 20, 20, 20));
        difficulty.setMargin(hard, new Insets(20, 20, 20, 20));

        difficulty.getChildren().addAll(dif, easy, normal, hard);

        // end select difficulty

        // select season
        VBox season = new VBox();
        season.setSpacing(10);
        season.setPadding(new Insets(50, 300, 200, 200));

        Label selectSeason = new Label("Select Starting Season");
        selectSeason.setFont(new Font("Arial", 35));

        ToggleGroup seasonGroup = new ToggleGroup();

        RadioButton spring = new RadioButton("Spring");
        spring.setToggleGroup(seasonGroup);
        spring.setStyle("-fx-font: 24 arial;");

        RadioButton summer = new RadioButton("Summer");
        summer.setToggleGroup(seasonGroup);
        summer.setStyle("-fx-font: 24 arial;");

        RadioButton fall = new RadioButton("Fall");
        fall.setToggleGroup(seasonGroup);
        fall.setStyle("-fx-font: 24 arial;");

        RadioButton winter = new RadioButton("Winter");
        winter.setToggleGroup(seasonGroup);
        winter.setStyle("-fx-font: 24 arial;");

        season.getChildren().addAll(selectSeason, spring, summer, fall, winter);

        // end select season

        HBox csButtons = new HBox();
        csButtons.getChildren().addAll(continueButton, quitButton2);
        csButtons.setAlignment(Pos.CENTER);
        csButtons.setSpacing(30);
        configPane.getChildren().addAll(csButtons, hboxOfName, difficulty, season);

        continueButton.setOnAction(e -> {
            boolean nameCheck = !playerName.getText().trim().isEmpty();
            boolean seasonCheck = seasonGroup.getSelectedToggle() != null;
            // boolean seedCheck = seedGroup.getSelectedToggle() != null; ??
            if(nameCheck && seasonCheck) {
                primaryStage.setScene(gameScene);
            }
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
        */
}
