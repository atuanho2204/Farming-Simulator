package main.employment.employmentData;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.employment.Employee;
import main.inventory.inventoryItems.InventoryItem;

import java.util.Collection;

public class EmployeeDataListing {
    public static Text getListingUI(Employee employee) {
        Text text = new Text();
        text.setText(employee.getEmployeeName() +  ": "
                + employee.getEmployeeType() + " " + employee.getSalary());

        text.setStyle("-fx-font: 16 chalkduster;" +
                "-fx-padding-left: 15;");
        text.setFill(Color.WHITE);
        return text;
    }
}
