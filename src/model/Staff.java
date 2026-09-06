package model;

public class Staff extends Person {
    private static final long serialVersionUID = 1L;
    
    private String department;
    private String position;
    private double salary;
    private String shiftTiming;

    public Staff() {}

    public Staff(String id, String name, int age, String gender, 
                 String phoneNumber, String email, String address,
                 String department, String position, double salary, String shiftTiming) {
        super(id, name, age, gender, phoneNumber, email, address);
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.shiftTiming = shiftTiming;
    }

    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getShiftTiming() { return shiftTiming; }
    public void setShiftTiming(String shiftTiming) { this.shiftTiming = shiftTiming; }

    @Override
    public String getRole() {
        return "Staff";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Department: %-12s | Position: %-10s | Salary: $%-8.2f",
                department, position, salary);
    }
}