package main.notifications;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class NotificationPopup {
    public static HBox getNotificationPopupUI(String s) {
        HBox hbox = new HBox();
        Text t = new Text(s);
        if (s.contains("~")) {
            t.setFill(Color.WHITE);
            t.setTextAlignment(TextAlignment.CENTER);
        } else if (s.contains("ALERT!! ")) {
            t.setFill(Color.DARKRED);
            t.setStyle("-fx-font-weight: bold;");
        } else {
            // other styling for other stuff?
            // Below is just a placeholder for the sake of checkstyle
            t.setFill(Color.WHITE);
        }
        hbox.getChildren().add(t);

        return hbox;
    }
}
