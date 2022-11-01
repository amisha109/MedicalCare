package org.amishakalhan.views;

import org.amishakalhan.Community;
import org.amishakalhan.Database;
import org.amishakalhan.Doctor;
import org.amishakalhan.Patient;
import org.amishakalhan.views.personViewUiElements.PersonDoctorBlock;

import javax.swing.*;
import java.awt.*;
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
    private JLabel upcomingAppointmentsSearchLabel;
    private JTextField upcomingAppointmentsSearchText;
    private JPanel mainPanel;
    private JPanel doctorsParentPanel;
    private JPanel upcomingAppointmentsParentPanel;
    private JPanel pastAppointmentsParentPanel;
    private JPanel pastAppointmentsHeadingPanel;
    private JLabel pastAppointmentsLabel;
    private JLabel pastAppointmentsSearchLabel;
    private JTextField pastAppointmentsSearchText;
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

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
