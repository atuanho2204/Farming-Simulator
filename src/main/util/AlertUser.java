package main.util;

import javafx.scene.control.Alert;

public class AlertUser {
    public static void alertUser(String message) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(javafx.scene.control.Alert.AlertType.INFORMATION);
        a.setContentText(message);
        // show the dialog
        a.show();
    }
}
