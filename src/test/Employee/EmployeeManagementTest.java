package test.Employee;

import main.employment.EmployeeManager;
import main.gameManager.GameManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author seank
 */
public class EmployeeManagementTest {
    private EmployeeManager employeeManager;

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
    // test the payEmployees
    // test the harvesting/selling
    // test the employees leaving because of no money
    // try deleting a harvestor/seller when there are none
}