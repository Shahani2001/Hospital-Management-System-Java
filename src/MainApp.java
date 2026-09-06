import model.*;
import hospital.*;
import exceptions.*;
import util.FileHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static HospitalManager manager = new HospitalManager();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        System.out.println("=== HOSPITAL MANAGEMENT SYSTEM ===");
        System.out.println("Loading saved data...");
        
        // Load data using File I/O
        FileHandler.loadData(manager);
        
        // Add sample data if empty
        if (manager.getAllPatients().isEmpty()) {
            initializeSampleData();
        }

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        addPatient();
                        break;
                    case 2:
                        addDoctor();
                        break;
                    case 3:
                        addStaff();
                        break;
                    case 4:
                        scheduleAppointment();
                        break;
                    case 5:
                        cancelAppointment();
                        break;
                    case 6:
                        viewAllAppointments();
                        break;
                    case 7:
                        viewPatients();
                        break;
                    case 8:
                        viewDoctors();
                        break;
                    case 9:
                        viewPatientDetails();
                        break;
                    case 10:
                        searchPatients();
                        break;
                    case 11:
                        FileHandler.saveData(manager);
                        System.out.println("Data saved successfully!");
                        break;
                    case 0:
                        System.out.println("Saving data before exit...");
                        FileHandler.saveData(manager);
                        System.out.println("Thank you for using the system. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Register Patient");
        System.out.println("2. Register Doctor");
        System.out.println("3. Register Staff");
        System.out.println("4. Schedule Appointment");
        System.out.println("5. Cancel Appointment");
        System.out.println("6. View All Appointments");
        System.out.println("7. View All Patients");
        System.out.println("8. View All Doctors");
        System.out.println("9. View Patient Details");
        System.out.println("10. Search Patients by Name");
        System.out.println("11. Save Data");
        System.out.println("0. Exit");
    }

    private static void addPatient() {
        System.out.println("\n=== REGISTER NEW PATIENT ===");
        String id = "P" + System.currentTimeMillis();
        System.out.println("Generated ID: " + id);
        
        String name = getStringInput("Enter full name: ");
        int age = getIntInput("Enter age: ");
        
        try {
            if (age < 0 || age > 120) {
                throw new InvalidAgeException("Age must be between 0 and 120");
            }
        } catch (InvalidAgeException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        
        String gender = getStringInput("Enter gender (M/F): ");
        String phone = getStringInput("Enter phone number: ");
        String email = getStringInput("Enter email: ");
        String address = getStringInput("Enter address: ");
        String bloodGroup = getStringInput("Enter blood group: ");
        String medicalHistory = getStringInput("Enter medical history (or 'None'): ");
        String emergencyContact = getStringInput("Enter emergency contact: ");
        
        Patient patient = new Patient(id, name, age, gender, phone, email, address, 
                                      bloodGroup, medicalHistory, emergencyContact);
        
        // Add allergies
        String addAllergies = getStringInput("Add allergies? (y/n): ");
        if (addAllergies.equalsIgnoreCase("y")) {
            while (true) {
                String allergy = getStringInput("Enter allergy (or 'done' to finish): ");
                if (allergy.equalsIgnoreCase("done")) break;
                patient.addAllergy(allergy);
            }
        }
        
        try {
            manager.addPatient(patient);
            System.out.println("Patient registered successfully!");
        } catch (InvalidAgeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addDoctor() {
        System.out.println("\n=== REGISTER NEW DOCTOR ===");
        String id = "D" + System.currentTimeMillis();
        System.out.println("Generated ID: " + id);
        
        String name = getStringInput("Enter full name: ");
        int age = getIntInput("Enter age: ");
        String gender = getStringInput("Enter gender (M/F): ");
        String phone = getStringInput("Enter phone number: ");
        String email = getStringInput("Enter email: ");
        String address = getStringInput("Enter address: ");
        String specialization = getStringInput("Enter specialization: ");
        String license = getStringInput("Enter license number: ");
        int experience = getIntInput("Enter years of experience: ");
        double fee = getDoubleInput("Enter consultation fee ($): ");
        String hours = getStringInput("Enter working hours (e.g., 9AM-5PM): ");
        
        Doctor doctor = new Doctor(id, name, age, gender, phone, email, address,
                                   specialization, license, experience, fee, hours);
        manager.addDoctor(doctor);
        System.out.println("Doctor registered successfully!");
    }

    private static void addStaff() {
        System.out.println("\n=== REGISTER NEW STAFF ===");
        String id = "S" + System.currentTimeMillis();
        System.out.println("Generated ID: " + id);
        
        String name = getStringInput("Enter full name: ");
        int age = getIntInput("Enter age: ");
        String gender = getStringInput("Enter gender (M/F): ");
        String phone = getStringInput("Enter phone number: ");
        String email = getStringInput("Enter email: ");
        String address = getStringInput("Enter address: ");
        String department = getStringInput("Enter department: ");
        String position = getStringInput("Enter position: ");
        double salary = getDoubleInput("Enter salary ($): ");
        String shift = getStringInput("Enter shift timing: ");
        
        Staff staffMember = new Staff(id, name, age, gender, phone, email, address,
                                      department, position, salary, shift);
        manager.addStaff(staffMember);
        System.out.println("Staff registered successfully!");
    }

    private static void scheduleAppointment() {
        System.out.println("\n=== SCHEDULE APPOINTMENT ===");
        
        // Display available doctors
        System.out.println("\nAvailable Doctors:");
        for (Doctor d : manager.getAvailableDoctors()) {
            System.out.println(d);
        }
        
        String patientId = getStringInput("Enter Patient ID: ");
        String doctorId = getStringInput("Enter Doctor ID: ");
        
        // Get date and time
        String dateTimeStr = getStringInput("Enter date and time (yyyy-MM-dd HH:mm): ");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            System.out.println("Invalid date format. Using current time.");
            dateTime = LocalDateTime.now();
        }
        
        String reason = getStringInput("Enter reason for appointment: ");
        
        try {
            Appointment appointment = manager.scheduleAppointment(patientId, doctorId, dateTime, reason);
            System.out.println("Appointment scheduled successfully!");
            System.out.println(appointment);
        } catch (PatientNotFoundException | DoctorNotFoundException | AppointmentConflictException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelAppointment() {
        System.out.println("\n=== CANCEL APPOINTMENT ===");
        String appointmentId = getStringInput("Enter Appointment ID: ");
        
        try {
            manager.cancelAppointment(appointmentId);
            System.out.println("Appointment cancelled successfully!");
        } catch (AppointmentConflictException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllAppointments() {
        manager.displayAllAppointments();
    }

    private static void viewPatients() {
        System.out.println("\n=== ALL PATIENTS ===");
        for (Patient p : manager.getAllPatients()) {
            System.out.println(p);
            System.out.println("----------------------------------------");
        }
    }

    private static void viewDoctors() {
        System.out.println("\n=== ALL DOCTORS ===");
        for (Doctor d : manager.getAllDoctors()) {
            System.out.println(d);
            System.out.println("----------------------------------------");
        }
    }

    private static void viewPatientDetails() {
        System.out.println("\n=== VIEW PATIENT DETAILS ===");
        String patientId = getStringInput("Enter Patient ID: ");
        
        try {
            manager.displayPatientSummary(patientId);
        } catch (PatientNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchPatients() {
        System.out.println("\n=== SEARCH PATIENTS ===");
        String name = getStringInput("Enter patient name (or part of it): ");
        List<Patient> results = manager.searchPatientsByName(name);
        
        if (results.isEmpty()) {
            System.out.println("No patients found with name containing: " + name);
        } else {
            System.out.println("Found " + results.size() + " patient(s):");
            for (Patient p : results) {
                System.out.println(p);
            }
        }
    }

    private static void initializeSampleData() {
        System.out.println("Initializing sample data...");
        
        try {
            // Sample Patients
            Patient p1 = new Patient("P001", "John Smith", 45, "M", "555-1111", 
                                     "john@email.com", "123 Main St", "A+", 
                                     "Diabetes, Hypertension", "555-9999");
            p1.addAllergy("Penicillin");
            manager.addPatient(p1);
            
            Patient p2 = new Patient("P002", "Mary Johnson", 32, "F", "555-2222",
                                     "mary@email.com", "456 Oak Ave", "O-", 
                                     "None", "555-8888");
            manager.addPatient(p2);
            
            Patient p3 = new Patient("P003", "Robert Williams", 67, "M", "555-3333",
                                     "robert@email.com", "789 Pine Rd", "B+", 
                                     "Heart Disease", "555-7777");
            p3.addAllergy("Aspirin");
            p3.addAllergy("Latex");
            manager.addPatient(p3);
            
            // Sample Doctors
            Doctor d1 = new Doctor("D001", "Dr. Sarah Chen", 42, "F", "555-4444",
                                   "sarah@email.com", "100 Medical Plaza", 
                                   "Cardiology", "LIC12345", 15, 200.00, "9AM-5PM");
            manager.addDoctor(d1);
            
            Doctor d2 = new Doctor("D002", "Dr. Michael Patel", 38, "M", "555-5555",
                                   "michael@email.com", "200 Health Tower",
                                   "Neurology", "LIC67890", 10, 250.00, "10AM-6PM");
            manager.addDoctor(d2);
            
            Doctor d3 = new Doctor("D003", "Dr. Emily Brown", 35, "F", "555-6666",
                                   "emily@email.com", "300 Medical Center",
                                   "Pediatrics", "LIC24680", 8, 150.00, "8AM-4PM");
            manager.addDoctor(d3);
            
            // Sample Staff
            Staff s1 = new Staff("S001", "Alice Wilson", 28, "F", "555-7777",
                                 "alice@email.com", "400 Admin Building",
                                 "Administration", "Receptionist", 45000, "8AM-4PM");
            manager.addStaff(s1);
            
            Staff s2 = new Staff("S002", "James Taylor", 34, "M", "555-8888",
                                 "james@email.com", "500 Service Center",
                                 "Maintenance", "Janitor", 32000, "2PM-10PM");
            manager.addStaff(s2);
            
            System.out.println("Sample data initialized!");
        } catch (Exception e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }
    }

    // ===== HELPER METHODS =====
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}