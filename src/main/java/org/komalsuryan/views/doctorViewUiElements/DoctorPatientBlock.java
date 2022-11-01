package org.komalsuryan.views.doctorViewUiElements;

import org.komalsuryan.Appointment;
import org.komalsuryan.Database;
import org.komalsuryan.Doctor;
import org.komalsuryan.Patient;
import org.komalsuryan.views.sysAdminViewUiElements.AddAppointmentDialog;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DoctorPatientBlock {
    private JPanel mainPanel;
    private JLabel patientNameLabel;
    private JLabel patientEmailLabel;
    private JLabel patientEmailValue;
    private JButton scheduleAppointmentButton;
    private JLabel patientUpcomingAppointmentLabel;
    private JLabel patientUpcomingAppointmentValue;
    Database db;

    public DoctorPatientBlock(Doctor doctor, Patient patient) {
        db = new Database();

        // labels
        patientEmailLabel.setText("Email: ");
        patientUpcomingAppointmentLabel.setText("Upcoming Appointment: ");

        // values
        patientNameLabel.setText(patient.getName());
        patientEmailValue.setText(patient.getEmail());

        // check if patient has an upcoming appointment
        ArrayList<Appointment> appointments = db.getAppointments(doctor, patient);
        ArrayList<Appointment> upcomingAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorId() == doctor.getId()) {
                // create LocalDateTime object for appointment time from getDate and getTime
                LocalDateTime appointmentTime = LocalDateTime.of(appointment.getDate(), appointment.getTime());
                if (appointmentTime.isAfter(LocalDateTime.now())) {
                    upcomingAppointments.add(appointment);
                }
            }
        }
        // if patient has no upcoming appointment, display so
        if (upcomingAppointments.size() == 0) {
            patientUpcomingAppointmentValue.setText("No upcoming appointment");
        } else {
            // get the appointment with the earliest time
            Appointment earliestAppointment = null;
            for (Appointment appointment : upcomingAppointments) {
                if (earliestAppointment == null) {
                    earliestAppointment = appointment;
                } else {
                    LocalDateTime earliestAppointmentTime = LocalDateTime.of(earliestAppointment.getDate(), earliestAppointment.getTime());
                    LocalDateTime appointmentTime = LocalDateTime.of(appointment.getDate(), appointment.getTime());
                    if (appointmentTime.isBefore(earliestAppointmentTime)) {
                        earliestAppointment = appointment;
                    }
                }
            }
            patientUpcomingAppointmentValue.setText(earliestAppointment.getDate().toString() + " " + earliestAppointment.getTime().toString());
        }

        // buttons
        scheduleAppointmentButton.setText("Schedule an appointment");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getScheduleAppointmentButton() {
        return scheduleAppointmentButton;
    }
}
