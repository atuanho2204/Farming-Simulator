package main.notifications;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * The Controller for the NotificationUI fxml screen
 */
public class NotificationController implements PropertyChangeListener {
    private Stage primaryStage;

    @FXML
    private VBox notificationScreen;

    /**
     * Constructs the Market Scene.
     *
     * @param primaryStage the stage
     */
    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;
        NotificationManager.getInstance().subscribeToNotifications(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //called when there is a new notification
        Platform.runLater(() -> {
            notificationScreen.getChildren().add(0,
                    NotificationPopup.getNotificationPopupUI((String) evt.getNewValue())
            );
        });
    }
}