package main.employment.employmentData;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.employment.Employee;
import main.employment.EmployeeTypes;


public class EmployeeDataListing {
    public static Text getListingUI(Employee employee) {
        Text text = new Text();
        EmployeeTypes type = employee.getEmployeeType();
        String t = "";
        if (type == EmployeeTypes.HARVESTER) {
            t = "Harvester";
        }
        if (type == EmployeeTypes.SELLER) {
            t = "Seller";
        }
        text.setText(employee.getEmployeeName() +  ": "
                + t + " $" + employee.getSalary());

        text.setStyle("-fx-font: 16 chalkduster;"
                + "-fx-padding-left: 15;");
        text.setFill(Color.WHITE);
        return text;
    }
}
