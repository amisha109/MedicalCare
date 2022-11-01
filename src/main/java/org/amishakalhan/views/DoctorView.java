package org.amishakalhan.views;

import org.amishakalhan.Appointment;
import org.amishakalhan.Database;
import org.amishakalhan.Doctor;
import org.amishakalhan.Patient;
import org.amishakalhan.views.doctorViewUiElements.DoctorPatientBlock;
import org.amishakalhan.views.sysAdminViewUiElements.AddAppointmentDialog;
import org.amishakalhan.views.sysAdminViewUiElements.SysAdminAppointmentBlock;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DoctorView {
    private JPanel doctorViewMainPanel;
    private JPanel doctorViewHeadingPanel;
    private JLabel welcomeLabel;
    private JButton logoutPersonButton;
    private JButton editDoctorButton;
    private JLabel upcomingAppointmentsLabel;
    private JPanel findUpcomingAppointmentsPanel;
    private JPanel upcomingAppointmentsPanel;
    private JLabel pastAppointmentsLabel;
    private JPanel findPastAppointments;
    private JPanel pastAppointmentsPanel;
    private JPanel patientsPanel;
    private JLabel viewPatientsLabel;
    private JPanel viewPatientsPanel;
    private Database db;

    public DoctorView(Doctor doctor) {
        db = new Database();

        // heading
        welcomeLabel.setText("Welcome, " + doctor.getName());
        upcomingAppointmentsLabel.setText("Upcoming Appointments");
        pastAppointmentsLabel.setText("Past Appointments");
        viewPatientsLabel.setText("Your Patients");

        // buttons
        logoutPersonButton.setText("Logout");
        logoutPersonButton.addActionListener(e -> {
            JFrame jFrame = new JFrame("Hospital Management System");
            jFrame.setContentPane(new LoginView(jFrame).getMainPanel());
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setLocationRelativeTo(null);
            jFrame.pack();
            jFrame.setVisible(true);
            // close the current window
            SwingUtilities.getWindowAncestor(doctorViewMainPanel).dispose();
        });
        editDoctorButton.setText("Edit Profile");

        // upcoming appointments
        createUpcomingAppointmentBlocks(doctor);
        createPastAppointmentBlocks(doctor);
        createPatientBlocks(doctor);
    }

    private void createUpcomingAppointmentBlocks(Doctor doctor) {
        findUpcomingAppointmentsPanel.removeAll();
        findUpcomingAppointmentsPanel.revalidate();
        findUpcomingAppointmentsPanel.repaint();
        ArrayList<Appointment> appointments = db.getAllAppointments();
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
        if (upcomingAppointments.size() == 0) {
            JLabel noUpcomingAppointmentsLabel = new JLabel("No upcoming appointments");
            findUpcomingAppointmentsPanel.setLayout(new GridLayout());
            findUpcomingAppointmentsPanel.add(noUpcomingAppointmentsLabel);
        } else {
            for (Appointment appointment : upcomingAppointments) {
                Patient patient = db.getPatient(appointment.getPatientId());
                SysAdminAppointmentBlock appointmentBlock = new SysAdminAppointmentBlock(appointment);
                findUpcomingAppointmentsPanel.add(appointmentBlock.getMainPanel());
                appointmentBlock.getDeleteButton().setVisible(false);
                appointmentBlock.getEditButton().addActionListener(e -> {
                    AddAppointmentDialog editAppointmentDialog = new AddAppointmentDialog(appointment, false);
                    editAppointmentDialog.pack();
                    editAppointmentDialog.setVisible(true);
                    createUpcomingAppointmentBlocks(doctor);
                    createPastAppointmentBlocks(doctor);
                });
            }
        }
        findPastAppointments.revalidate();
        findPastAppointments.repaint();
        findUpcomingAppointmentsPanel.revalidate();
        findUpcomingAppointmentsPanel.repaint();
    }

    public void createPastAppointmentBlocks(Doctor doctor) {
        findPastAppointments.removeAll();
        findPastAppointments.revalidate();
        findPastAppointments.repaint();
        ArrayList<Appointment> appointments = db.getAllAppointments();
        ArrayList<Appointment> pastAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorId() == doctor.getId()) {
                // create LocalDateTime object for appointment time from getDate and getTime
                LocalDateTime appointmentTime = LocalDateTime.of(appointment.getDate(), appointment.getTime());
                if (appointmentTime.isBefore(LocalDateTime.now())) {
                    pastAppointments.add(appointment);
                }
            }
        }
        if (pastAppointments.size() == 0) {
            JLabel noPastAppointmentsLabel = new JLabel("No past appointments");
            findPastAppointments.setLayout(new GridLayout());
            findPastAppointments.add(noPastAppointmentsLabel);
        } else {
            for (Appointment appointment : pastAppointments) {
                Patient patient = db.getPatient(appointment.getPatientId());
                SysAdminAppointmentBlock appointmentBlock = new SysAdminAppointmentBlock(appointment);
                findPastAppointments.add(appointmentBlock.getMainPanel());
                appointmentBlock.getDeleteButton().setVisible(false);
                appointmentBlock.getEditButton().addActionListener(e -> {
                    AddAppointmentDialog editAppointmentDialog = new AddAppointmentDialog(appointment, false);
                    editAppointmentDialog.pack();
                    editAppointmentDialog.setVisible(true);
                    createUpcomingAppointmentBlocks(doctor);
                    createPastAppointmentBlocks(doctor);
                });
            }
        }
        findPastAppointments.revalidate();
        findPastAppointments.repaint();
        findUpcomingAppointmentsPanel.revalidate();
        findUpcomingAppointmentsPanel.repaint();
    }

    public void createPatientBlocks(Doctor doctor) {
        viewPatientsPanel.removeAll();
        viewPatientsPanel.revalidate();
        viewPatientsPanel.repaint();
        ArrayList<Appointment> appointments = db.getAppointments(doctor, null);
        ArrayList<Patient> doctorPatients = new ArrayList<>();
        for (Appointment appointment : appointments) {
            Patient patient = db.getPatient(appointment.getPatientId());
            if (!doctorPatients.contains(patient)) {
                doctorPatients.add(patient);
            }
        }
        if (doctorPatients.size() == 0) {
            JLabel noPatientsLabel = new JLabel("No patients");
            viewPatientsPanel.setLayout(new GridLayout());
            viewPatientsPanel.add(noPatientsLabel);
        } else {
            for (Patient patient : doctorPatients) {
                DoctorPatientBlock patientBlock = new DoctorPatientBlock(doctor, patient);
                viewPatientsPanel.add(patientBlock.getMainPanel());
                patientBlock.getScheduleAppointmentButton().addActionListener(e -> {
                    AddAppointmentDialog addAppointmentDialog = new AddAppointmentDialog(patient, doctor, true);
                    addAppointmentDialog.pack();
                    addAppointmentDialog.setVisible(true);
                    createUpcomingAppointmentBlocks(doctor);
                    createPastAppointmentBlocks(doctor);
                });
            }
        }
        viewPatientsPanel.revalidate();
        viewPatientsPanel.repaint();
    }

    public JPanel getMainPanel() {
        return doctorViewMainPanel;
    }
}
