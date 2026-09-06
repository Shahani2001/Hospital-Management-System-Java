package model;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private static final long serialVersionUID = 1L;
    
    private String bloodGroup;
    private String medicalHistory;
    private List<String> allergies;
    private String emergencyContact;
    private double medicalBill;

    public Patient() {
        this.allergies = new ArrayList<>();
        this.medicalBill = 0.0;
    }

    public Patient(String id, String name, int age, String gender, 
                   String phoneNumber, String email, String address,
                   String bloodGroup, String medicalHistory, String emergencyContact) {
        super(id, name, age, gender, phoneNumber, email, address);
        this.bloodGroup = bloodGroup;
        this.medicalHistory = medicalHistory;
        this.allergies = new ArrayList<>();
        this.emergencyContact = emergencyContact;
        this.medicalBill = 0.0;
    }

    // Getters and Setters
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    public List<String> getAllergies() { return allergies; }
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }
    public void addAllergy(String allergy) { this.allergies.add(allergy); }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public double getMedicalBill() { return medicalBill; }
    public void setMedicalBill(double medicalBill) { this.medicalBill = medicalBill; }
    public void addToBill(double amount) { this.medicalBill += amount; }

    @Override
    public String getRole() {
        return "Patient";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Blood: %-5s | Bill: $%-8.2f | Allergies: %d",
                bloodGroup, medicalBill, allergies.size());
    }
}