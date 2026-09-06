package hospital;

import model.Patient;
import model.Doctor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime appointmentDateTime;
    private String reason;
    private String status; // SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
    private String notes;

    public Appointment() {
        this.status = "SCHEDULED";
    }

    public Appointment(String appointmentId, Patient patient, Doctor doctor, 
                       LocalDateTime appointmentDateTime, String reason) {
        this();
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDateTime = appointmentDateTime;
        this.reason = reason;
    }

    // Getters and Setters
    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) { 
        this.appointmentDateTime = appointmentDateTime; 
    }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Business methods
    public void completeAppointment() {
        this.status = "COMPLETED";
    }

    public void cancelAppointment() {
        this.status = "CANCELLED";
    }

    public void markNoShow() {
        this.status = "NO_SHOW";
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("Appointment ID: %-10s | Patient: %-12s | Doctor: %-12s | " +
                           "Date: %-16s | Status: %-10s | Reason: %-20s",
                appointmentId, patient.getName(), doctor.getName(),
                appointmentDateTime.format(formatter), status, reason);
    }
}