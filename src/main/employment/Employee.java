package main.employment;

public class Employee {
    private int salary;
    private int hiredDay;
    private EmployeeTypes type;
    private String employeeName;

    public Employee(int capacity, int hiredDay, EmployeeTypes type, String employeeName) {
        this.salary = capacity;
        this.hiredDay = hiredDay;
        this.type = type;
        this.employeeName = employeeName;
    }

    public int getSalary() {
        return this.salary;
    }

    public int getHireDay() {
        return this.hiredDay;
    }

    public void setHireDay(int hiredDay) {
        this.hiredDay = hiredDay;
    }

    public void setSalary(int salary) {
        this.salary = salary;
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
