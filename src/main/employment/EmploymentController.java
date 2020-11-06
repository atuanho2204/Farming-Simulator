package main.employment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.employment.employmentData.EmploymentDataController;
import main.gameManager.GameManager;

import main.util.AlertUser;
import main.util.UIManager;
import main.util.customEvents.ForceUIUpdate;
import main.util.customEvents.ForceUIUpdateListener;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;


/**
 * The Controller for the employmentUI fxml screen
 */
public class EmploymentController implements NewDayListener, ForceUIUpdateListener {
    private Stage primaryStage;

    private final int salary = 5;

    @FXML
    private VBox employmentData;

    @FXML
    private Text dailyWage;

    @FXML
    private Text totalEmployees;

    /**
     * Constructs the Employment Scene.
     *
     * @param primaryStage the stage
     */
    public void construct(Stage primaryStage) {
        this.primaryStage = primaryStage;

        GameManager.getInstance().getTimeAdvancer().addListener(this);
        UIManager.getInstance().addListener(this);


        employmentUpdate();
        setEmploymentDataUI();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        employmentUpdate();
    }

    @Override
    public void handleForcedUIUpdate(ForceUIUpdate forcedUIUpdate) {
        employmentUpdate();
    }

    private void employmentUpdate() {
        try {
            int wage = GameManager.getInstance().getEmployees().getTotalSalary();
            int totalEmp = GameManager.getInstance().getEmployees().getEmployees().size();
            dailyWage.setText(": $" + wage);
            totalEmployees.setText(": " + totalEmp);
            dailyWage.setStyle("-fx-font: 16 chalkduster;");
            dailyWage.setFill(Color.WHITE);
            totalEmployees.setStyle("-fx-font: 16 chalkduster;");
            totalEmployees.setFill(Color.WHITE);

        } catch (Exception e) {

        }
    }

    @FXML
    private void fireHarvestEmployee(ActionEvent event) {
        try {
            for (Employee e : GameManager.getInstance().getEmployees().getEmployees()) {
                if (e.getEmployeeType() == EmployeeTypes.HARVESTER) {
                    GameManager.getInstance().getEmployees().deleteHarvester();
                    //System.out.println("You fired " + e.getEmployeeName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("You don't have any harvest employee");
        }
    }

    @FXML
    private void fireSellEmployee(ActionEvent event) {
        try {
            for (Employee e : GameManager.getInstance().getEmployees().getEmployees()) {
                if (e.getEmployeeType() == EmployeeTypes.SELLER) {
                    GameManager.getInstance().getEmployees().deleteSeller();
                    //System.out.println("You fired " + e.getEmployeeName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("You don't have any sell employee");
        }
    }

    @FXML
    private void hireHarvestEmployee(ActionEvent event) {
        int currentDay = GameManager.getInstance().getDay();
        GameManager.getInstance().getEmployees().addHarvester(currentDay);
    }

    @FXML
    private void hireSellEmployee(ActionEvent event) {
        int currentDay = GameManager.getInstance().getDay();
        GameManager.getInstance().getEmployees().addSeller(currentDay);
    }


    public void harvestEmployeeWork() {
        //ArrayList<Plot> plots = GameManager.getInstance().get
    }

    private void setEmploymentDataUI() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "../employment/employmentData/employmentDataUI.fxml"
                    )
            );
            Parent parent = loader.load();
            EmploymentDataController controller = loader.getController();
            controller.construct(primaryStage);
            employmentData.getChildren().add(new Pane(parent));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            AlertUser.alertUser("There was an error loading in the headerUI");
        }
    }
}