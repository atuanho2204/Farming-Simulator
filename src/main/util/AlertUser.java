package main.util;

import javafx.scene.control.Alert;

public class AlertUser {
    public static void alertUser(String message) {
        //this try-catch is here, so that when called from code, it won't error
        try {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText(message);
            // show the dialog
            a.show();
        } catch (ExceptionInInitializerError exceptionInInitializerError) {
            System.out.println("Probably running tests...ignoring initialization error");
        } catch (NoClassDefFoundError e2) {
            System.out.println("Probably running tests...ignoring noClassDef error");
        } catch (IllegalStateException e) {
            System.out.println(
                    "Probably running tests...ignoring IllegalStateException error");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
