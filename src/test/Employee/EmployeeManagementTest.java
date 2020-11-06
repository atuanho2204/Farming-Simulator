package test.Employee;

import main.employment.EmployeeManager;
import main.farm.FarmState;
import main.farm.crops.CropStages;
import main.farm.crops.CropTypes;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author seank
 */
public class EmployeeManagementTest {
    private EmployeeManager employeeManager;
    private List<Plot> plots = new ArrayList<>(4);

    @Before
    public void setup() {
        GameManager.getInstance().clear(); //test only
        GameManager.getInstance().setMoney(10000);
        employeeManager = new EmployeeManager();
    }

    @Test
    public void testHireHarvester() {
        assertEquals(0, employeeManager.getEmployees().size());

        employeeManager.addHarvester(GameManager.getInstance().getDay());
        assertEquals(1, employeeManager.getEmployees().size());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        assertEquals(2, employeeManager.getEmployees().size());
    }

    @Test
    public void testFireHarvester() {
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        assertEquals(2, employeeManager.getEmployees().size());

        employeeManager.deleteHarvester();
        assertEquals(1, employeeManager.getEmployees().size());
        employeeManager.deleteHarvester();
        assertEquals(0, employeeManager.getEmployees().size());
    }

    @Test
    public void testHireSeller() {
        assertEquals(0, employeeManager.getEmployees().size());

        employeeManager.addSeller(GameManager.getInstance().getDay());
        assertEquals(1, employeeManager.getEmployees().size());
        employeeManager.addSeller(GameManager.getInstance().getDay());
        assertEquals(2, employeeManager.getEmployees().size());
    }

    @Test
    public void testFireSeller() {
        employeeManager.addSeller(GameManager.getInstance().getDay());
        employeeManager.addSeller(GameManager.getInstance().getDay());
        assertEquals(2, employeeManager.getEmployees().size());

        employeeManager.deleteSeller();
        assertEquals(1, employeeManager.getEmployees().size());
        employeeManager.deleteSeller();
        assertEquals(0, employeeManager.getEmployees().size());
    }

    // @Tuan
    // test adding too many employees?
    @Test
    public void testHireTooManyEmployee() {
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        assertEquals(6, employeeManager.getEmployees().size());
    }

    // test the payEmployees
    @Test
    public void testPayEmployee() {
        int dailyWage = employeeManager.getTotalSalary();
        employeeManager.payWages();
        int currentMoney = GameManager.getInstance().getMoney();
        assertEquals(10000 - dailyWage, currentMoney);
    }

    // test the harvesting/selling
    @Test
    public void testHarvesting() {
        employeeManager.addHarvester(GameManager.getInstance().getDay());

    }


    // test the employees leaving because of no money
    @Test
    public void testLeavingWithoutMoney() {
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        employeeManager.addHarvester(GameManager.getInstance().getDay());
        assertEquals(6, employeeManager.getEmployees().size());
        GameManager.getInstance().setMoney(0);
        employeeManager.payWages();
        employeeManager.payWages();
        employeeManager.payWages();
        assertEquals(3, employeeManager.getEmployees().size());
    }
    // try deleting a harvestor/seller when there are none
    @Test
    public void testNoneEmployee() {
        employeeManager.deleteHarvester();
        employeeManager.deleteSeller();
    }
}