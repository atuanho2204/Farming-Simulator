package main.employment.employmentData;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.employment.Employee;
import main.employment.EmployeeManager;
import main.employment.EmployeeTypes;
import main.gameManager.GameManager;

import javax.swing.*;


public class EmployeeDataListing {
    public static HBox getListingUI(Employee employee) {
        HBox hbox = new HBox(5);
        Text text = new Text();
        EmployeeTypes type = employee.getEmployeeType();
        String t = "";
        if (type == EmployeeTypes.HARVESTER) {
            t = "Harvester";
        }
        if (type == EmployeeTypes.SELLER) {
            t = "Seller";
        }
        int skill = employee.getSkillLevel();
        text.setText(employee.getEmployeeName() +  " (" + "LV " + skill + "): "
                + t + " $" + EmployeeManager.getSalaryFromSkillLevel(employee.getSkillLevel()));

        text.setStyle("-fx-font: 14 chalkduster;"
                + "-fx-padding-left: 15;");
        text.setFill(Color.WHITE);

        Button fire = new Button();
        fire.setText("Fire");
        fire.setStyle("-fx-margin: 0 10 0 0;");
        fire.setTextFill(Color.DARKRED);

        fire.setOnAction(e -> {
            GameManager.getInstance().getEmployees().fireEmployee(employee);
        });
        hbox.getChildren().addAll(fire, text);
        return hbox;
    }
}
