package org.amishakalhan;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Appointment {
    private final int id;
    private int patientId;
    private int hospitalId;
    private LocalDate date;
    private int doctorId;
    private LocalTime time;
    private String reason;
    private HashMap<String, String> vitals;
    private String diagnosis;
    private String treatment;
    private static final AtomicInteger count = new AtomicInteger(new Database().getMaxAppointmentId());

    public Appointment(int patientId, int hospitalId, LocalDate date, int doctorId, LocalTime time, String reason, HashMap<String, String> vitals, String diagnosis, String treatment) {
        this.id = count.incrementAndGet();
        this.patientId = patientId;
        this.hospitalId = hospitalId;
        this.date = date;
        this.doctorId = doctorId;
        this.time = time;
        this.reason = reason;
        this.vitals = vitals;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public HashMap<String, String> getVitals() {
        return vitals;
    }

    public void setVitals(HashMap<String, String> vitals) {
        this.vitals = vitals;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
