package model;

public class Doctor extends Person {
    private static final long serialVersionUID = 1L;
    
    private String specialization;
    private String licenseNumber;
    private int yearsOfExperience;
    private double consultationFee;
    private String workingHours;
    private boolean available;

    public Doctor() {
        this.available = true;
    }

    public Doctor(String id, String name, int age, String gender, 
                  String phoneNumber, String email, String address,
                  String specialization, String licenseNumber, 
                  int yearsOfExperience, double consultationFee, String workingHours) {
        super(id, name, age, gender, phoneNumber, email, address);
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.consultationFee = consultationFee;
        this.workingHours = workingHours;
        this.available = true;
    }

    // Getters and Setters
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }

    public String getWorkingHours() { return workingHours; }
    public void setWorkingHours(String workingHours) { this.workingHours = workingHours; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String getRole() {
        return "Doctor";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Specialization: %-12s | Experience: %dyrs | Fee: $%-6.2f | Available: %b",
                specialization, yearsOfExperience, consultationFee, available);
    }
}