package main.employment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.gameManager.GameManager;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;


/**
 * The Controller for the employmentUI fxml screen
 */
public class EmploymentController implements NewDayListener {
    private Stage primaryStage;

    @FXML
    private VBox employmentScreen;

    /**
     * Constructs the Employment Scene.
     *
     * @param primaryStage the stage
     */
    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //
        GameManager.getInstance().getTimeAdvancer().addListener(this);
    }

    @Override
    public void handleNewDay(NewDayEvent e) {

    }

    //BEGIN: methods for hiring, firing, displaying the weekly cost, and next pay day, etc:
    @FXML
    private void fire(ActionEvent event) {

    }

    @FXML
    private void hire(ActionEvent event) {

    }

    private void displayData(/* parameters */) {
        //display the data of the employment stuff
    }

    private void payWages() {
        //conditionally pays wages if it's the right day of the week
    }
    //END
}