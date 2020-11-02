package main.employment;

public class Employee {
    private int capacity;
    private int hireDay;
    private EmployeeTypes type;
    private String employeeName;

    public Employee(int capacity, int hireDay, EmployeeTypes type, String employeeName) {
        this.capacity = capacity;
        this.hireDay = hireDay;
        this.type = type;
        this.employeeName = employeeName;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getHireDay() {
        return this.hireDay;
    }

    public void setHireDay(int hireDay) {
        this.hireDay = hireDay;
    }

    public void setCapacity(int salary) {
        this.capacity = salary;
    }

    public EmployeeTypes getEmployeeType() {
        return type;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String name) {
        this.employeeName = name;
    }
}
