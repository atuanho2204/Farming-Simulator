package main.util;

import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public abstract class MainController {
    protected Stage primaryStage;
    protected AudioClip backgroundMusic;
    public abstract void construct(Stage primaryStage, AudioClip backgroundMusic);
}
