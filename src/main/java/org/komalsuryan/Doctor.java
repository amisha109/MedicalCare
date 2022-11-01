package org.komalsuryan;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Doctor {
    private static final AtomicInteger count = new AtomicInteger(new Database().getMaxDoctorId());
    private final int id;
    private String name;
    private String specialization;
    private int hospitalId;
    private LocalTime startTime;
    private LocalTime endTime;
    private int maxPatientsPerHour;
    private DayOfWeek weeklyOffDay;
    private String email;
    private String password;

    public Doctor(String name, String specialization, int hospitalId, LocalTime startTime, LocalTime endTime, int maxPatientsPerHour, DayOfWeek weeklyOffDay, String email, String password) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.specialization = specialization;
        this.hospitalId = hospitalId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxPatientsPerHour = maxPatientsPerHour;
        this.weeklyOffDay = weeklyOffDay;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getMaxPatientsPerHour() {
        return maxPatientsPerHour;
    }

    public void setMaxPatientsPerHour(int maxPatientsPerHour) {
        this.maxPatientsPerHour = maxPatientsPerHour;
    }

    public DayOfWeek getWeeklyOffDay() {
        return weeklyOffDay;
    }

    public void setWeeklyOffDay(DayOfWeek weeklyOffDay) {
        this.weeklyOffDay = weeklyOffDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
