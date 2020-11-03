package main.employment;

import main.gameManager.GameManager;
import javafx.application.Platform;
import main.farm.FarmState;
import main.farm.crops.Crop;
import main.farm.crops.CropStages;
import main.farm.plot.Plot;
import main.inventory.inventoryItems.HarvestedCrop;
import main.util.UIManager;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;
import java.util.ArrayList;
import java.util.Random;

public class EmployeeManager implements NewDayListener {
    private final int employeeLimit = 6;

    private int baseHarvestSalary;
    private int baseSellSalary;

    private ArrayList<Employee> employees;

    //Get employee's name
    private ArrayList<String> commonName;
    private int nameIdx = 0;

    public EmployeeManager() {
        employees = new ArrayList<>();
        generateName();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {

        employeesManager();
        payWages();
        updateDailySalary();

        //
        System.out.println(GameManager.getInstance().getMoney());
    }

    public void addHarvester(int hireDay) throws Exception {
        int currentMoney = GameManager.getInstance().getMoney();
        baseHarvestSalary = generateWorkCapacity();
        if (employees.size() < employeeLimit && currentMoney > baseHarvestSalary) {
            Employee employee = new Employee(baseHarvestSalary, hireDay,
                    EmployeeTypes.HARVESTER, commonName.get(nameIdx++ % 7));
            //System.out.println("A");
            employees.add(employee);
            UIManager.getInstance().pushUIUpdate();
            /*System.out.println("You hired " + employee.getEmployeeName()
                    + " " + employee.getEmployeeType());*/
        } else {
            throw new Exception();
        }
    }

    public void addSeller(int hireDay) throws Exception {
        int currentMoney = GameManager.getInstance().getMoney();
        baseSellSalary = generateWorkCapacity();
        if (employees.size() < employeeLimit && currentMoney > baseSellSalary) {
            Employee employee = new Employee(baseSellSalary, hireDay,
                    EmployeeTypes.SELLER, commonName.get(nameIdx++ % 7));

            employees.add(employee);

            UIManager.getInstance().pushUIUpdate();
            /*System.out.println("You hired " + employee.getEmployeeName()
                    + " " + employee.getEmployeeType());*/
        } else {
            throw new Exception();
        }
    }

    public void deleteHarvester() throws Exception {
        try {
            if (employees.size() > 0) {
                for (int i = 0; i < employees.size(); i++) {
                    if (employees.get(i).getEmployeeType() == EmployeeTypes.HARVESTER) {
                        employees.remove(i);
                        /*System.out.println("You fired " +
                                employees.get(i).getEmployeeName());*/
                        UIManager.getInstance().pushUIUpdate();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("You don't have any");
        }
    }
    public void deleteSeller() throws Exception {
        try {
            if (employees.size() > 0) {
                for (int i = 0; i < employees.size(); i++) {
                    if (employees.get(i).getEmployeeType() == EmployeeTypes.SELLER) {
                        employees.remove(i);
                        /*System.out.println("You fired "
                                + employees.get(i).getEmployeeName());*/
                        UIManager.getInstance().pushUIUpdate();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("You don't have any");
        }
    }

    public ArrayList<Employee> getTotalEmployees() {
        return employees;
    }

    public void generateName() {
        commonName = new ArrayList<>();
        commonName.add("Trump");
        commonName.add("Biden");
        commonName.add("Sean");
        commonName.add("Anthony");
        commonName.add("Tony");
        commonName.add("Chris");
        commonName.add("Quynh");
    }

    public int getTotalSalary() {
        int total = 0;
        for (Employee e: employees) {
            total += e.getSalary();
        }
        return total;
    }

    private int generateWorkCapacity() {
        Random ran = new Random();
        int difficulty = GameManager.getInstance().getDifficulty();
        int min = 0;
        int max = 0;

        if (difficulty == 1) {  // Easy: wage 3-5
            min = 3;
            max = 5;
        } else if (difficulty == 2) {   // Medium: wage 6-8
            min = 6;
            max = 8;
        } else if (difficulty == 3) { // Hard: wage 9-11
            min = 9;
            max = 11;
        } else {
            min = 4;
            max = 6;
        }
        //System.out.println(min + " " + max);
        return ran.nextInt(max - min + 1) + min;
    }

    public void updateDailySalary() {
        for (Employee e: employees) {
            e.setSalary(generateWorkCapacity());
        }
    }

    public void payWages() {
        //conditionally pays wages if it's the right day of the week
        try {
            for (Employee e: employees) {
                int currentMoney = GameManager.getInstance().getMoney();
                if (currentMoney > e.getSalary()) {
                    GameManager.getInstance().setMoney(currentMoney - e.getSalary());
                } else {
                    if (e.getEmployeeType() == EmployeeTypes.HARVESTER) {
                        deleteHarvester();
                    }
                    if (e.getEmployeeType() == EmployeeTypes.SELLER) {
                        deleteSeller();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Pay Wage Error");
        }
    }

    public void employeesManager() {
        for (Employee e: employees) {
            if (e.getEmployeeType() == EmployeeTypes.HARVESTER) {
                harvesterWork();
            }
            if (e.getEmployeeType() == EmployeeTypes.SELLER) {
                sellerWork();
                UIManager.getInstance().pushUIUpdate();
            }
        }
    }

    public void harvesterWork() {
        Platform.runLater(() -> {
            for (Plot plot: FarmState.getInstance().getPlots()) {
                Crop crop = plot.getCurrentCrop();
                if (crop != null) {
                    CropStages stage = crop.getStage();
                    if (stage == CropStages.MATURE) {
                        plot.harvestPlot();
                        UIManager.getInstance().pushUIUpdate();
                        break;
                    }
                }
            }
        });
    }

    public void sellerWork() {
        Platform.runLater(() -> {
            for (HarvestedCrop item : GameManager.getInstance().getInventory().getProducts()) {
                System.out.println(item.getName());
                if (item != null) {
                    GameManager.getInstance().getInventory().sellProduct(item);
                    UIManager.getInstance().pushUIUpdate();
                    System.out.println("I just sold " + item.getName());
                    break;
                }
            }
        });
    }


}
