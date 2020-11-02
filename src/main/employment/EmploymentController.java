package main.employment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;

import java.util.ArrayList;


/**
 * The Controller for the employmentUI fxml screen
 */
public class EmploymentController implements NewDayListener {
    private Stage primaryStage;
    private int numEmployee1;
    private int numEmployee2;
    private final int salary = 5;
    private ArrayList<Employee> employees;

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
        employmentUpdate();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        employmentUpdate();

    }

    private void employmentUpdate() {
        try {

        } catch (Exception e) {

        }
    }

    //BEGIN: methods for hiring, firing, displaying the weekly cost, and next pay day, etc:
    @FXML
    private void fireHarvestEmployee(ActionEvent event) {
        int numHarvestEmployee = GameManager.getInstance().getEmployees().getHarvestEmployees().size();
        try {
            if (numHarvestEmployee > 0) {
                GameManager.getInstance().getEmployees().deleteHarvestEmployee();
            }
        } catch (Exception e) {
            System.out.println("You don't have any harvest employee");
        }
    }

    @FXML
    private void fireSellEmployee(ActionEvent event) {
        int numSellEmployee = GameManager.getInstance().getEmployees().getSellEmployees().size();
        try {
            if (numSellEmployee > 0) {
                GameManager.getInstance().getEmployees().deleteSellEmployee();
            }
        } catch (Exception e) {
            System.out.println("You don't have any sell employee");
        }
    }

    @FXML
    private void hireHarvestEmployee(ActionEvent event) {
        int currentDay = GameManager.getInstance().getDay();
        try {
            GameManager.getInstance().getEmployees().addHarvestEmployee(currentDay);
            //
            System.out.println(GameManager.getInstance().getEmployees().getHarvestEmployees().size() + "A");
            GameManager.getInstance().getEmployees().harvestEmployeeWork();
        } catch (Exception e) {
            System.out.println("You don't have enough money or too much employee");
        }
    }
    @FXML
    private void hireSellEmployee(ActionEvent event) {
        int currentDay = GameManager.getInstance().getDay();
        try {
            GameManager.getInstance().getEmployees().addSellEmployee(currentDay);
            //
            System.out.println(GameManager.getInstance().getEmployees().getSellEmployees().size()
                    + "B");
        } catch (Exception e) {
            System.out.println("You don't have enough money or too much employee");
        }
    }

    private void displayData(/* parameters */) {
        //display the data of the employment stuff
    }



    public void harvestEmployeeWork() {
        //ArrayList<Plot> plots = GameManager.getInstance().get
    }


    //END
}