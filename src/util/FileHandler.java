package util;

import hospital.HospitalManager;
import model.Patient;
import model.Doctor;
import model.Staff;
import hospital.Appointment;

import java.io.*;
import java.util.List;

public class FileHandler {
    private static final String PATIENTS_FILE = "patients_data.ser";
    private static final String DOCTORS_FILE = "doctors_data.ser";
    private static final String STAFF_FILE = "staff_data.ser";
    private static final String APPOINTMENTS_FILE = "appointments_data.ser";
    private static final String REPORT_FILE = "hospital_report.txt";

    // Save all data using Serialization
    public static void saveData(HospitalManager manager) {
        try {
            // Save patients
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(PATIENTS_FILE))) {
                oos.writeObject(manager.getAllPatients());
                System.out.println("Patients saved successfully.");
            }

            // Save doctors
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(DOCTORS_FILE))) {
                oos.writeObject(manager.getAllDoctors());
                System.out.println("Doctors saved successfully.");
            }

            // Save staff
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(STAFF_FILE))) {
                oos.writeObject(manager.getAllStaff());
                System.out.println("Staff saved successfully.");
            }

            // Save appointments
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(APPOINTMENTS_FILE))) {
                oos.writeObject(manager.getAllAppointments());
                System.out.println("Appointments saved successfully.");
            }

            // Generate report
            generateReport(manager);

        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // Load data from files
    @SuppressWarnings("unchecked")
    public static void loadData(HospitalManager manager) {
        try {
            // Load patients
            File patientsFile = new File(PATIENTS_FILE);
            if (patientsFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(patientsFile))) {
                    List<Patient> patients = (List<Patient>) ois.readObject();
                    for (Patient p : patients) {
                        try {
                            manager.addPatient(p);
                        } catch (Exception e) {
                            System.err.println("Error adding patient: " + e.getMessage());
                        }
                    }
                    System.out.println("Patients loaded successfully.");
                }
            }

            // Load doctors
            File doctorsFile = new File(DOCTORS_FILE);
            if (doctorsFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(doctorsFile))) {
                    List<Doctor> doctors = (List<Doctor>) ois.readObject();
                    for (Doctor d : doctors) {
                        manager.addDoctor(d);
                    }
                    System.out.println("Doctors loaded successfully.");
                }
            }

            // Load staff
            File staffFile = new File(STAFF_FILE);
            if (staffFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(staffFile))) {
                    List<Staff> staffList = (List<Staff>) ois.readObject();
                    for (Staff s : staffList) {
                        manager.addStaff(s);
                    }
                    System.out.println("Staff loaded successfully.");
                }
            }

            // Load appointments (simplified - you'd need to handle references properly)
            // Note: In a real app, you'd reconstruct references to patients and doctors

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    // Generate text report using FileWriter and BufferedWriter
    public static void generateReport(HospitalManager manager) {
        // Using try-with-resources for automatic resource management
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE))) {
            writer.write("=== HOSPITAL MANAGEMENT SYSTEM REPORT ===\n");
            writer.write("Generated: " + new java.util.Date() + "\n\n");
            
            writer.write("=== PATIENTS ===\n");
            writer.write("Total Patients: " + manager.getAllPatients().size() + "\n\n");
            for (Patient p : manager.getAllPatients()) {
                writer.write(p.toString() + "\n");
                writer.write("  Medical History: " + p.getMedicalHistory() + "\n");
                writer.write("  Allergies: " + (p.getAllergies().isEmpty() ? "None" : p.getAllergies()) + "\n");
                writer.write("  Bill: $" + p.getMedicalBill() + "\n\n");
            }
            
            writer.write("=== DOCTORS ===\n");
            writer.write("Total Doctors: " + manager.getAllDoctors().size() + "\n\n");
            for (Doctor d : manager.getAllDoctors()) {
                writer.write(d.toString() + "\n");
                writer.write("  Working Hours: " + d.getWorkingHours() + "\n\n");
            }
            
            writer.write("=== APPOINTMENTS ===\n");
            writer.write("Total Appointments: " + manager.getAllAppointments().size() + "\n");
            writer.write("Total Revenue: $" + manager.getTotalRevenue() + "\n\n");
            for (Appointment a : manager.getAllAppointments()) {
                writer.write(a.toString() + "\n");
            }
            
            writer.write("\n=== DOCTOR APPOINTMENT STATISTICS ===\n");
            for (var entry : manager.getDoctorAppointmentCount().entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + " appointments\n");
            }
            
            System.out.println("Report generated successfully: " + REPORT_FILE);
            
        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }
}