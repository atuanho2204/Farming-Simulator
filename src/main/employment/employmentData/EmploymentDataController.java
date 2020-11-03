package main.employment.employmentData;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.employment.Employee;
import main.gameManager.GameManager;
import main.util.UIManager;
import main.util.customEvents.ForceUIUpdate;
import main.util.customEvents.ForceUIUpdateListener;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;

import java.util.ArrayList;

public class EmploymentDataController implements NewDayListener, ForceUIUpdateListener {
    private Stage primaryStage;

    @FXML
    private VBox employmentDataPopUp;

    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;

        GameManager.getInstance().getTimeAdvancer().addListener(this);
        UIManager.getInstance().addListener(this);

    }

    @Override
    public void handleForcedUIUpdate(ForceUIUpdate forcedUIUpdate) {
        setEmploymentData();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        setEmploymentData();
    }

    private void setEmploymentData() {
        try {
            ArrayList<Node> newListings = new ArrayList<>();
            for (Employee e: GameManager.getInstance().getEmployees().getTotalEmployees()) {
                newListings.add(EmployeeDataListing.getListingUI(e));
            }
            Platform.runLater(() -> {
                employmentDataPopUp.getChildren().clear();
                employmentDataPopUp.getChildren().addAll(newListings);
            });
        } catch (Exception e) {

        }
    }
}
