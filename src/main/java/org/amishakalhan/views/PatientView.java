package org.amishakalhan.views;

import org.amishakalhan.*;
import org.amishakalhan.views.personViewUiElements.PersonDoctorBlock;
import org.amishakalhan.views.sysAdminViewUiElements.AddAppointmentDialog;
import org.amishakalhan.views.sysAdminViewUiElements.SysAdminAppointmentBlock;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PatientView {
    private JPanel patientViewHeadingPanel;
    private JLabel welcomeLabel;
    private JButton logoutPatientButton;
    private JLabel communitySelectLabel;
    private JComboBox<String> communitySelectComboBox;
    private JButton editDetailsButton;
    private JButton editLoginButton;
    private JPanel findDoctorsTopPanel;
    private JLabel findDoctorsLabel;
    private JPanel findDoctorsSearchPanel;
    private JLabel findDoctorsSearchLabel;
    private JTextField findDoctorsSearchText;
    private JScrollPane findDoctorsScrollPane;
    private JPanel findDoctorsPanel;
    private JPanel upcomingAppointmentsHeadingPanel;
    private JPanel upcomingAppointmentsPanel;
    private JLabel upcomingAppointmentsLabel;
    private JPanel mainPanel;
    private JPanel doctorsParentPanel;
    private JPanel upcomingAppointmentsParentPanel;
    private JPanel pastAppointmentsParentPanel;
    private JPanel pastAppointmentsHeadingPanel;
    private JLabel pastAppointmentsLabel;
    private JPanel pastAppointmentsPanel;
    private final Database db;

    public PatientView(Patient patient) {
        db = new Database();
        welcomeLabel.setText("Welcome, Find doctors at your location");
        communitySelectLabel.setText("Select Community");
        // populate the community select combo box
        communitySelectComboBox.addItem("Anywhere");
        ArrayList<Community> communities = db.getAllCommunities();
        for (Community community : communities) {
            communitySelectComboBox.addItem(community.getName());
        }
        logoutPatientButton.setText("Logout");
        logoutPatientButton.addActionListener(e -> {
            JFrame jFrame = new JFrame("Hospital Management System");
            jFrame.setContentPane(new LoginView(jFrame).getMainPanel());
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setLocationRelativeTo(null);
            jFrame.pack();
            jFrame.setVisible(true);
            // close the current window
            ((JFrame) SwingUtilities.getWindowAncestor(mainPanel)).dispose();
        });

        findDoctorsLabel.setText("Find Doctors");
        findDoctorsSearchLabel.setText("Search");

        final int[] communityId = {0};
        communitySelectComboBox.addActionListener(e -> {
            findDoctorsSearchText.setText("");
            String communityName = (String) communitySelectComboBox.getSelectedItem();
            if (communityName == null || communityName.equals("Anywhere")) {
                createDoctorBlocks(0);
                return;
            }
            communityId[0] = db.getAllCommunities().stream().filter(community -> community.getName().equals(communityName)).findFirst().get().getId();
            createDoctorBlocks(communityId[0]);
        });
        communitySelectComboBox.setSelectedItem(db.getCommunity(patient.getCommunityId()).getName());

        findDoctorsSearchText.addActionListener(e -> {
            String searchQuery = findDoctorsSearchText.getText();
            if (searchQuery == null || searchQuery.equals("")) {
                createDoctorBlocks(communityId[0]);
                return;
            }
            createDoctorBlocks(communityId[0], searchQuery);
        });

        upcomingAppointmentsLabel.setText("Upcoming Appointments");
        pastAppointmentsLabel.setText("Past Appointments");

        createDoctorBlocks(communityId[0]);
        createUpcomingAppointmentBlocks(patient);
        createPastAppointmentBlocks(patient);

    }

    private void createDoctorBlocks(int communityId) {
        findDoctorsPanel.removeAll();
        findDoctorsPanel.revalidate();
        findDoctorsPanel.repaint();
        ArrayList<Doctor> doctors = db.getAllDoctors();
        if (communityId != 0) {
            doctors.removeIf(doctor -> db.getHospital(doctor.getHospitalId()).getCommunityId() != communityId);
        }
        if (doctors.size() == 0) {
            findDoctorsPanel.setLayout(new GridLayout(0, 1));
            findDoctorsPanel.add(new JLabel("No doctors found"));
            findDoctorsPanel.revalidate();
            findDoctorsPanel.repaint();
            return;
        }
        findDoctorsPanel.setLayout(new GridLayout(0, 3));
        for (Doctor doctor : doctors) {
            PersonDoctorBlock doctorBlock = new PersonDoctorBlock(doctor);
            findDoctorsPanel.add(doctorBlock.getMainPanel());
        }
        findDoctorsPanel.revalidate();
        findDoctorsPanel.repaint();
    }

    private void createDoctorBlocks(int communityId, String search) {
        findDoctorsPanel.removeAll();
        findDoctorsPanel.revalidate();
        findDoctorsPanel.repaint();
        ArrayList<Doctor> doctors = db.getDoctors(search);
        if (communityId != 0) {
            doctors.removeIf(doctor -> db.getHospital(doctor.getHospitalId()).getCommunityId() != communityId);
        }
        if (doctors.size() == 0) {
            findDoctorsPanel.setLayout(new GridLayout(0, 1));
            findDoctorsPanel.add(new JLabel("No doctors found"));
            findDoctorsPanel.revalidate();
            findDoctorsPanel.repaint();
            return;
        }
        findDoctorsPanel.setLayout(new GridLayout(0, 3));
        for (Doctor doctor : doctors) {
            PersonDoctorBlock doctorBlock = new PersonDoctorBlock(doctor);
            findDoctorsPanel.add(doctorBlock.getMainPanel());
        }
        findDoctorsPanel.revalidate();
        findDoctorsPanel.repaint();
    }

    private void createUpcomingAppointmentBlocks(Patient patient) {
        upcomingAppointmentsPanel.removeAll();
        upcomingAppointmentsPanel.revalidate();
        upcomingAppointmentsPanel.repaint();
        ArrayList<Appointment> appointments = db.getAllAppointments();
        ArrayList<Appointment> upcomingAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientId() == patient.getId()) {
                // create LocalDateTime object for appointment time from getDate and getTime
                LocalDateTime appointmentTime = LocalDateTime.of(appointment.getDate(), appointment.getTime());
                if (appointmentTime.isAfter(LocalDateTime.now())) {
                    upcomingAppointments.add(appointment);
                }
            }
        }
        if (upcomingAppointments.size() == 0) {
            JLabel noUpcomingAppointmentsLabel = new JLabel("No upcoming appointments");
            upcomingAppointmentsPanel.setLayout(new GridLayout());
            upcomingAppointmentsPanel.add(noUpcomingAppointmentsLabel);
        } else {
            for (Appointment appointment : upcomingAppointments) {
                Doctor doctor = db.getDoctor(appointment.getDoctorId());
                SysAdminAppointmentBlock appointmentBlock = new SysAdminAppointmentBlock(appointment);
                upcomingAppointmentsPanel.add(appointmentBlock.getMainPanel());
                appointmentBlock.getDeleteButton().setVisible(false);
                appointmentBlock.getEditButton().addActionListener(e -> {
                    AddAppointmentDialog editAppointmentDialog = new AddAppointmentDialog(appointment, true);
                    editAppointmentDialog.pack();
                    editAppointmentDialog.setVisible(true);
                    createUpcomingAppointmentBlocks(patient);
                    createPastAppointmentBlocks(patient);
                });
            }
        }
        pastAppointmentsPanel.revalidate();
        pastAppointmentsPanel.repaint();
        upcomingAppointmentsPanel.revalidate();
        upcomingAppointmentsPanel.repaint();
    }

    public void createPastAppointmentBlocks(Patient patient) {
        pastAppointmentsPanel.removeAll();
        pastAppointmentsPanel.revalidate();
        pastAppointmentsPanel.repaint();
        ArrayList<Appointment> appointments = db.getAllAppointments();
        ArrayList<Appointment> pastAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientId() == patient.getId()) {
                // create LocalDateTime object for appointment time from getDate and getTime
                LocalDateTime appointmentTime = LocalDateTime.of(appointment.getDate(), appointment.getTime());
                if (appointmentTime.isBefore(LocalDateTime.now())) {
                    pastAppointments.add(appointment);
                }
            }
        }
        if (pastAppointments.size() == 0) {
            JLabel noPastAppointmentsLabel = new JLabel("No past appointments");
            pastAppointmentsPanel.setLayout(new GridLayout());
            pastAppointmentsPanel.add(noPastAppointmentsLabel);
        } else {
            for (Appointment appointment : pastAppointments) {
                Doctor doctor = db.getDoctor(appointment.getDoctorId());
                SysAdminAppointmentBlock appointmentBlock = new SysAdminAppointmentBlock(appointment);
                pastAppointmentsPanel.add(appointmentBlock.getMainPanel());
                appointmentBlock.getDeleteButton().setVisible(false);
                appointmentBlock.getEditButton().addActionListener(e -> {
                    AddAppointmentDialog editAppointmentDialog = new AddAppointmentDialog(appointment, false);
                    editAppointmentDialog.pack();
                    editAppointmentDialog.setVisible(true);
                    createUpcomingAppointmentBlocks(patient);
                    createPastAppointmentBlocks(patient);
                });
            }
        }
        pastAppointmentsPanel.revalidate();
        pastAppointmentsPanel.repaint();
        upcomingAppointmentsPanel.revalidate();
        upcomingAppointmentsPanel.repaint();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
