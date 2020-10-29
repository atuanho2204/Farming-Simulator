package main.notifications;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NotificationPopup {
    public static HBox getNotificationPopupUI(String s) {
        HBox hbox = new HBox();

        Text t = new Text("Someone please change this UI..." + s);
        t.setFill(Color.WHITE);
        hbox.getChildren().add(t);

        return hbox;
    }
}
