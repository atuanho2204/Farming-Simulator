package main.employment;

public class Employee {
    private int skillLevel;
    private int hiredDay;
    private EmployeeTypes type;
    private String employeeName;

    public Employee(int skillLevel, int hiredDay, EmployeeTypes type, String employeeName) {
        this.skillLevel = skillLevel;
        this.hiredDay = hiredDay;
        this.type = type;
        this.employeeName = employeeName;
    }

    public int getSkillLevel() {
        return this.skillLevel;
    }

    public EmployeeTypes getEmployeeType() {
        return type;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
}
