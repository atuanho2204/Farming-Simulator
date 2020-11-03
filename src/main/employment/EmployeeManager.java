package main.employment;


import main.gameManager.GameManager;

import javafx.application.Platform;
import main.farm.FarmController;
import main.farm.FarmState;
import main.farm.crops.Crop;
import main.farm.crops.CropStages;
import main.farm.plot.Plot;
import main.farm.plot.PlotUI;
import main.gameManager.GameManager;
import main.inventory.Inventory;
import main.inventory.inventoryItems.HarvestedCrop;

import main.util.customEvents.NewDayEvent;
import main.util.customEvents.NewDayListener;

import java.util.ArrayList;
import java.util.Random;

public class EmployeeManager implements NewDayListener {
    private final int employeeLimit = 3;

    private final int baseHarvestSalary = 2;
    private final int baseSellSalary = 4;
    private int totalHarvestCapacity = 0;
    private int totalSellCapacity = 0;


    private ArrayList<Employee> harvestEmployees;
    private ArrayList<Employee> sellEmployees;

    private ArrayList<String> commonName;
    private int nameIdx = 0;

    public EmployeeManager() {
        harvestEmployees = new ArrayList<>();
        sellEmployees = new ArrayList<>();
        generateName();
    }

    @Override
    public void handleNewDay(NewDayEvent e) {

        harvestEmployeeWork();
        sellEmployeeWork();
        payWages();
        resetEmployeeCapacity();

        //
        System.out.println(GameManager.getInstance().getMoney());
    }

    public void addHarvestEmployee(int hireDay) throws Exception {
        int currentMoney = GameManager.getInstance().getMoney();
        int workCapacity = generateWorkCapacity();
        int wage = workCapacity + baseHarvestSalary;
        if (harvestEmployees.size() < employeeLimit && currentMoney > wage ) {
            Employee employee = new Employee(workCapacity, hireDay,
                    EmployeeTypes.HARVEST, commonName.get(nameIdx++ % 7));
            harvestEmployees.add(employee);
            System.out.println("You hire " + employee.getEmployeeName());
        } else {
            throw new Exception();
        }
    }

    public void addSellEmployee(int hireDay) throws Exception {
        int currentMoney = GameManager.getInstance().getMoney();
        int workCapacity = generateWorkCapacity();
        int wage = workCapacity + baseSellSalary;
        if (sellEmployees.size() < employeeLimit && currentMoney > wage) {
            Employee employee = new Employee(workCapacity, hireDay,
                    EmployeeTypes.SELL, commonName.get(nameIdx++ % 7));
            sellEmployees.add(employee);
            System.out.println("You hire " + employee.getEmployeeName());
        } else {
            throw new Exception();
        }
    }

    public void deleteHarvestEmployee() throws Exception {
        try {
            if (harvestEmployees.size() > 0) {
                Employee e = harvestEmployees.get(0);
                harvestEmployees.remove(0);
                System.out.println("You fire " + e.getEmployeeName());
            }
        } catch (Exception e) {
            System.out.println("You don't have any");
        }
    }
    public void deleteSellEmployee() throws Exception {
        try {
            if (sellEmployees.size() > 0) {
                Employee e = sellEmployees.get(0);
                sellEmployees.remove(0);
                System.out.println("You fire " + e.getEmployeeName());
            }
        } catch (Exception e) {
            System.out.println("You don't have any");
        }
    }

    public ArrayList<Employee> getHarvestEmployees() {
        return harvestEmployees;
    }

    public ArrayList<Employee> getSellEmployees() {
        return sellEmployees;
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

    public int getHarvestEmployeeCapacity() {
        int total = 0;
        for (Employee e: harvestEmployees) {
            total += e.getCapacity();
        }
        return total;
    }

    public int getSellEmployeeCapacity() {
        int total = 0;
        for (Employee e: sellEmployees) {
            total += e.getCapacity();
        }
        return total;
    }

    private int generateWorkCapacity() {
        Random ran = new Random();

        return ran.nextInt(6 - 4 + 1) + 4;
    }

    private void resetEmployeeCapacity() {
        this.totalHarvestCapacity = getHarvestEmployeeCapacity();
        this.totalSellCapacity = getSellEmployeeCapacity();
    }

    public void payWages() {
        //conditionally pays wages if it's the right day of the week
        int currentMoney = GameManager.getInstance().getMoney();
        int harvestWageCount = GameManager.getInstance().getEmployees().getHarvestEmployeeCapacity() * 4;
        int sellWageCount = GameManager.getInstance().getEmployees().getSellEmployeeCapacity() * 6;
        int dailyWage = harvestWageCount + sellWageCount;
        try {
            if (currentMoney > (dailyWage)) {
                GameManager.getInstance().setMoney(currentMoney - dailyWage);
            } else {
                if (currentMoney > harvestWageCount) {
                    GameManager.getInstance().setMoney(currentMoney - harvestWageCount);
                } else {
                    GameManager.getInstance().getEmployees().deleteHarvestEmployee();
                }
                if (currentMoney > sellWageCount) {
                    GameManager.getInstance().setMoney(currentMoney - sellWageCount);
                } else {
                    GameManager.getInstance().getEmployees().deleteSellEmployee();
                }
            }
        } catch (Exception e) {
            System.out.println("Pay Wage Error");
        }
    }

    public void harvestEmployeeWork() {
        Platform.runLater(() -> {
            if (totalHarvestCapacity >= 4) {
                for (Plot plot: FarmState.getInstance().getPlots()) {
                    Crop crop = plot.getCurrentCrop();
                    if (crop != null) {
                        CropStages stage = crop.getStage();
                        if (stage == CropStages.MATURE) {
                            plot.harvestPlot();
                            totalHarvestCapacity -= 4;
                        }
                    }
                }
            }
        });
    }

    public void sellEmployeeWork() {
        Platform.runLater(() -> {
            try {
                System.out.println(totalSellCapacity);
                for (HarvestedCrop item : GameManager.getInstance().getInventory().getProducts()) {
                    System.out.println(item.getName());
                    if (totalSellCapacity >= 3) {
                        GameManager.getInstance().getInventory().sellProduct(item);
                        totalSellCapacity -= 3;
                        System.out.println("I just sold " + item.getName());
                    }
                }
            } catch (Exception e) {
                //System.out.println("ERROR");
            }
        });
    }

}
