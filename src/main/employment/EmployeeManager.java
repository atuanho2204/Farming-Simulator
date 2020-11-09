package main.employment;

import main.gameManager.GameManager;
import javafx.application.Platform;
import main.farm.FarmState;
import main.farm.crops.Crop;
import main.farm.crops.CropStages;
import main.farm.plot.Plot;
import main.inventory.inventoryItems.HarvestedCrop;
import main.notifications.NotificationManager;
import main.util.UIManager;
import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;

import java.util.ArrayList;
import java.util.Random;

public class EmployeeManager implements NewDayListener {
    private final int employeeLimit = 6;
    private static int baseSalary = 2;
    private ArrayList<Employee> employees;

    //Get employee's name
    private ArrayList<String> commonName;
    private int nameIdx = 0;

    public EmployeeManager() {
        employees = new ArrayList<>();
        generateNames();
        baseSalary += GameManager.getInstance().getDifficulty();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {
        employeesManager();
        payWages();
        //System.out.println(GameManager.getInstance().getMoney());
    }

    public void addHarvester(int hireDay) {
        int currentMoney = GameManager.getInstance().getMoney();
        int skillLevel = getRandomSkillLevel();
        if (employees.size() < employeeLimit
                && currentMoney > getSalaryFromSkillLevel(skillLevel)) {
            Employee employee = new Employee(skillLevel, hireDay,
                    EmployeeTypes.HARVESTER, commonName.get(nameIdx++ % 7));
            employees.add(employee);
            UIManager.getInstance().pushUIUpdate();
            NotificationManager.getInstance().addNotification(
                    "You hired " + employee.getEmployeeName() + " with skill: "
                            + employee.getSkillLevel());
        } else {
            NotificationManager.getInstance().addNotification(
                    "You don't have enough money or too much employee");
            System.out.println("Not enough money to hire this employee");
        }
    }

    public void addSeller(int hireDay) {
        int currentMoney = GameManager.getInstance().getMoney();
        int skillLevel = getRandomSkillLevel();
        if (employees.size() < employeeLimit
                && currentMoney > getSalaryFromSkillLevel(skillLevel)) {
            Employee employee = new Employee(skillLevel, hireDay,
                    EmployeeTypes.SELLER, commonName.get(nameIdx++ % 7));

            employees.add(employee);

            UIManager.getInstance().pushUIUpdate();
            NotificationManager.getInstance().addNotification(
                    "You hired " + employee.getEmployeeName() + " with skill: "
                            + employee.getSkillLevel());
        } else {
            NotificationManager.getInstance().addNotification(
                    "You don't have enough money or too much employee");
            System.out.println("Not enough money to hire this employee");
        }
    }

    public void deleteHarvester() {
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

    public void deleteSeller() {
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



    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    private void generateNames() {
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
        for (Employee e : employees) {
            total += getSalaryFromSkillLevel(e.getSkillLevel());
        }
        return total;
    }

    private int getRandomSkillLevel() {
        Random ran = new Random();
        return ran.nextInt(2) + 1;
    }

    public static int getSalaryFromSkillLevel(int skillLevel) {
        return skillLevel + baseSalary;
    }

    public void payWages() {
        //conditionally pays wages if it's the right day of the week
        try {
            for (Employee e : employees) {
                int currentMoney = GameManager.getInstance().getMoney();
                if (currentMoney > getSalaryFromSkillLevel(e.getSkillLevel())) {
                    GameManager.getInstance().setMoney(
                            currentMoney - getSalaryFromSkillLevel(e.getSkillLevel()));
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
        for (Employee e : employees) {
            if (e.getEmployeeType() == EmployeeTypes.HARVESTER) {
                harvesterWork();
            }
            if (e.getEmployeeType() == EmployeeTypes.SELLER) {
                sellerWork();
                UIManager.getInstance().pushUIUpdate();
            }
        }
    }

    private void harvesterWork() {
        Platform.runLater(() -> {
            for (Plot plot : FarmState.getInstance().getPlots()) {
                Crop crop = plot.getCurrentCrop();
                if (crop != null) {
                    CropStages stage = crop.getStage();
                    if (stage == CropStages.MATURE || stage == CropStages.DEAD) {
                        plot.harvestPlot();
                        UIManager.getInstance().pushUIUpdate();
                        break;
                    }
                }
            }
        });
    }

    private void sellerWork() {
        Platform.runLater(() -> {
            for (HarvestedCrop item : GameManager.getInstance().getInventory().getProducts()) {
                //System.out.println(item.getName());
                if (item != null) {
                    GameManager.getInstance().getInventory().sellProduct(item);
                    UIManager.getInstance().pushUIUpdate();
                    //System.out.println("I just sold " + item.getName());
                    break;
                }
            }
        });
    }

    public void fireEmployee(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i) == employee) {
                employees.remove(i);
                UIManager.getInstance().pushUIUpdate();
            }
        }
    }

    public int getEmployeeLimit() {
        return employeeLimit;
    }
}
