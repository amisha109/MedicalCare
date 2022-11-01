package org.amishakalhan.views;

import org.amishakalhan.*;
import org.amishakalhan.views.personViewUiElements.PersonDoctorBlock;
import org.amishakalhan.views.sysAdminViewUiElements.AddAppointmentDialog;
import org.amishakalhan.views.sysAdminViewUiElements.SysAdminAppointmentBlock;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PersonView {
    private JLabel welcomeLabel;
    private JButton editPersonButton;
    private JButton logoutPersonButton;
    private JPanel personViewMainPanel;
    private JPanel personViewHeadingPanel;
    private JLabel findDoctorsLabel;
    private JScrollPane findDoctorsScrollPane;
    private JPanel findDoctorsPanel;
    private JPanel findDoctorsTopPanel;
    private JPanel findDoctorsSearchPanel;
    private JLabel findDoctorsSearchLabel;
    private JTextField findDoctorsSearchText;
    private JComboBox<String> communitySelectComboBox;
    private JLabel communitySelectLabel;
    private final Database db;

    public PersonView() {
        db = new Database();
        welcomeLabel.setText("Welcome, Find doctors at your location");
        communitySelectLabel.setText("Select Community");
        // populate the community select combo box
        communitySelectComboBox.addItem("Anywhere");
        ArrayList<Community> communities = db.getAllCommunities();
        for (Community community : communities) {
            communitySelectComboBox.addItem(community.getName());
        }
        logoutPersonButton.setText("Logout");
        logoutPersonButton.addActionListener(e -> {
            JFrame jFrame = new JFrame("Hospital Management System");
            jFrame.setContentPane(new LoginView(jFrame).getMainPanel());
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setLocationRelativeTo(null);
            jFrame.pack();
            jFrame.setVisible(true);
            // close the current window
            SwingUtilities.getWindowAncestor(personViewMainPanel).dispose();
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
        communitySelectComboBox.setSelectedItem("Anywhere");

        findDoctorsSearchText.addActionListener(e -> {
            String searchQuery = findDoctorsSearchText.getText();
            if (searchQuery == null || searchQuery.equals("")) {
                createDoctorBlocks(communityId[0]);
                return;
            }
            createDoctorBlocks(communityId[0], searchQuery);
        });
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
        return personViewMainPanel;
    }
}
