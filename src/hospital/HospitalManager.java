package hospital;

import model.Patient;
import model.Doctor;
import model.Staff;
import exceptions.*;

import java.util.*;
import java.time.LocalDateTime;

public class HospitalManager {
    // Collections Framework - Using ArrayList and HashMap
    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<Staff> staff;
    private List<Appointment> appointments;
    
    // HashMap for fast lookups by ID
    private Map<String, Patient> patientMap;
    private Map<String, Doctor> doctorMap;
    private Map<String, Staff> staffMap;
    private Map<String, Appointment> appointmentMap;
    private Map<String, List<Appointment>> patientAppointmentsMap;

    public HospitalManager() {
        this.patients = new ArrayList<>();
        this.doctors = new ArrayList<>();
        this.staff = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.patientMap = new HashMap<>();
        this.doctorMap = new HashMap<>();
        this.staffMap = new HashMap<>();
        this.appointmentMap = new HashMap<>();
        this.patientAppointmentsMap = new HashMap<>();
    }

    // ===== PATIENT MANAGEMENT =====
    public void addPatient(Patient patient) throws InvalidAgeException {
        // Validate age
        if (patient.getAge() < 0 || patient.getAge() > 120) {
            throw new InvalidAgeException("Patient age must be between 0 and 120");
        }
        patients.add(patient);
        patientMap.put(patient.getId(), patient);
        patientAppointmentsMap.put(patient.getId(), new ArrayList<>());
    }

    public Patient getPatientById(String id) throws PatientNotFoundException {
        Patient patient = patientMap.get(id);
        if (patient == null) {
            throw new PatientNotFoundException("Patient with ID " + id + " not found");
        }
        return patient;
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }

    public List<Patient> searchPatientsByName(String name) {
        List<Patient> results = new ArrayList<>();
        for (Patient p : patients) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    // ===== DOCTOR MANAGEMENT =====
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        doctorMap.put(doctor.getId(), doctor);
    }

    public Doctor getDoctorById(String id) throws DoctorNotFoundException {
        Doctor doctor = doctorMap.get(id);
        if (doctor == null) {
            throw new DoctorNotFoundException("Doctor with ID " + id + " not found");
        }
        return doctor;
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }

    public List<Doctor> getAvailableDoctors() {
        List<Doctor> available = new ArrayList<>();
        for (Doctor d : doctors) {
            if (d.isAvailable()) {
                available.add(d);
            }
        }
        return available;
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> results = new ArrayList<>();
        for (Doctor d : doctors) {
            if (d.getSpecialization().equalsIgnoreCase(specialization)) {
                results.add(d);
            }
        }
        return results;
    }

    // ===== STAFF MANAGEMENT =====
    public void addStaff(Staff staffMember) {
        staff.add(staffMember);
        staffMap.put(staffMember.getId(), staffMember);
    }

    public Staff getStaffById(String id) {
        return staffMap.get(id);
    }

    public List<Staff> getAllStaff() {
        return new ArrayList<>(staff);
    }

    // ===== APPOINTMENT MANAGEMENT =====
    public Appointment scheduleAppointment(String patientId, String doctorId, 
                                          LocalDateTime dateTime, String reason) 
            throws PatientNotFoundException, DoctorNotFoundException, AppointmentConflictException {
        
        Patient patient = getPatientById(patientId);
        Doctor doctor = getDoctorById(doctorId);
        
        // Check if doctor is available
        if (!doctor.isAvailable()) {
            throw new AppointmentConflictException("Doctor is currently not available");
        }
        
        // Check for appointment conflicts
        for (Appointment existing : appointments) {
            if (existing.getDoctor().getId().equals(doctorId) &&
                existing.getAppointmentDateTime().equals(dateTime) &&
                !existing.getStatus().equals("CANCELLED")) {
                throw new AppointmentConflictException("Doctor already has an appointment at this time");
            }
            
            if (existing.getPatient().getId().equals(patientId) &&
                existing.getAppointmentDateTime().equals(dateTime) &&
                !existing.getStatus().equals("CANCELLED")) {
                throw new AppointmentConflictException("Patient already has an appointment at this time");
            }
        }
        
        String appointmentId = "APT" + System.currentTimeMillis();
        Appointment appointment = new Appointment(appointmentId, patient, doctor, dateTime, reason);
        
        appointments.add(appointment);
        appointmentMap.put(appointmentId, appointment);
        patientAppointmentsMap.get(patientId).add(appointment);
        
        // Add consultation fee to patient's bill
        patient.addToBill(doctor.getConsultationFee());
        
        return appointment;
    }

    public void cancelAppointment(String appointmentId) 
            throws AppointmentConflictException {
        Appointment appointment = appointmentMap.get(appointmentId);
        if (appointment == null) {
            throw new AppointmentConflictException("Appointment with ID " + appointmentId + " not found");
        }
        
        if (appointment.getStatus().equals("COMPLETED")) {
            throw new AppointmentConflictException("Cannot cancel a completed appointment");
        }
        
        appointment.cancelAppointment();
    }

    public Appointment getAppointmentById(String id) throws AppointmentConflictException {
        Appointment appointment = appointmentMap.get(id);
        if (appointment == null) {
            throw new AppointmentConflictException("Appointment with ID " + id + " not found");
        }
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Appointment> getPatientAppointments(String patientId) 
            throws PatientNotFoundException {
        getPatientById(patientId); // Validate patient exists
        return new ArrayList<>(patientAppointmentsMap.getOrDefault(patientId, new ArrayList<>()));
    }

    public List<Appointment> getDoctorAppointments(String doctorId) 
            throws DoctorNotFoundException {
        getDoctorById(doctorId); // Validate doctor exists
        List<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getDoctor().getId().equals(doctorId)) {
                doctorAppointments.add(a);
            }
        }
        return doctorAppointments;
    }

    // ===== REPORTING =====
    public double getTotalRevenue() {
        double total = 0;
        for (Appointment a : appointments) {
            if (a.getStatus().equals("COMPLETED")) {
                total += a.getDoctor().getConsultationFee();
            }
        }
        return total;
    }

    public Map<String, Integer> getDoctorAppointmentCount() {
        Map<String, Integer> countMap = new HashMap<>();
        for (Doctor d : doctors) {
            countMap.put(d.getName(), 0);
        }
        for (Appointment a : appointments) {
            String doctorName = a.getDoctor().getName();
            countMap.put(doctorName, countMap.getOrDefault(doctorName, 0) + 1);
        }
        return countMap;
    }

    public void displayAllAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        System.out.println("\n=== ALL APPOINTMENTS ===");
        for (Appointment a : appointments) {
            System.out.println(a);
            System.out.println("----------------------------------------");
        }
        System.out.println("Total Revenue: $" + getTotalRevenue());
    }

    public void displayPatientSummary(String patientId) throws PatientNotFoundException {
        Patient patient = getPatientById(patientId);
        List<Appointment> patientAppointments = getPatientAppointments(patientId);
        
        System.out.println("\n=== PATIENT SUMMARY ===");
        System.out.println(patient);
        System.out.println("\nMedical History: " + patient.getMedicalHistory());
        System.out.println("Allergies: " + (patient.getAllergies().isEmpty() ? "None" : patient.getAllergies()));
        System.out.println("Total Medical Bill: $" + patient.getMedicalBill());
        System.out.println("\nAppointments: " + patientAppointments.size());
        for (Appointment a : patientAppointments) {
            System.out.println("  - " + a);
        }
    }
}