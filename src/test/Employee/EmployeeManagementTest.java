package test.Employee;

import main.employment.Employee;
import main.employment.EmployeeManager;
import main.farm.FarmState;
import main.farm.crops.CropStages;
import main.farm.crops.CropTypes;
import main.farm.plot.Plot;
import main.gameManager.GameManager;
import main.inventory.inventoryItems.HarvestedCrop;
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
    private List<Plot> plots;

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
    @Test (expected = NullPointerException.class)
    public void testHarvesting() {
        employeeManager.addHarvester(GameManager.getInstance().getDay());

        for (int i = 0; i < 4; ++i) {
            FarmState.getInstance().getPlots().add(new Plot());
        }
        plots = FarmState.getInstance().getPlots();
        plots.get(0).getCurrentCrop().setType(CropTypes.CORN);
        plots.get(0).getCurrentCrop().setCropStage(CropStages.MATURE);
        GameManager.getInstance().getEmployees().employeesManager();
        assertEquals(null, plots.get(0));
    }

    // test selling
    @Test (expected = NullPointerException.class)
    public void testSelling() throws Exception {
        employeeManager.addSeller(GameManager.getInstance().getDay());

        GameManager.getInstance().getInventory().putProduct(new HarvestedCrop(CropTypes.WHEAT));
        assertEquals(1, GameManager.getInstance().getInventory().getProducts().size());
        GameManager.getInstance().getEmployees().employeesManager();
        assertEquals(0, GameManager.getInstance().getInventory().getProducts().size());
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
        employeeManager.deleteHarvester();
        employeeManager.deleteHarvester();
        employeeManager.deleteHarvester();
        employeeManager.deleteSeller();
        employeeManager.deleteSeller();
        employeeManager.deleteSeller();
        employeeManager.deleteSeller();
        employeeManager.deleteSeller();
    }
}