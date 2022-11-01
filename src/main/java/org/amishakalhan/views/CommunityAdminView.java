package org.amishakalhan.views;

import org.amishakalhan.*;
import org.amishakalhan.views.sysAdminViewUiElements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CommunityAdminView {
    private JPanel communityAdminViewMainPanel;
    private JPanel communityAdminViewHeadingPanel;
    private JLabel welcomeLabel;
    private JButton editCommunityAdminButton;
    private JButton logoutCommunityAdminButton;
    private JButton editCommunity;
    private JScrollPane communityAdminViewContentScrollPane;
    private JPanel communityAdminViewContentPanel;
    private JPanel doctorsPanel;
    private JScrollPane doctorsScrollPane;
    private JPanel findDoctorsPanel;
    private JPanel doctorsTopPanel;
    private JLabel findDoctorsLabel;
    private JPanel findDoctorsSearchPanel;
    private JLabel findDoctorsSearchLabel;
    private JTextField findDoctorsSearchText;
    private JButton addDoctorButton;
    private JPanel hospitalsPanel;
    private JScrollPane hospitalsScrollPane;
    private JPanel findHospitalsPanel;
    private JPanel hospitalsTopPanel;
    private JLabel findHospitalsLabel;
    private JPanel findHospitalsSearchPanel;
    private JLabel findHospitalsSearchLabel;
    private JTextField findHospitalsSearchText;
    private JButton addHospitalButton;
    private CommunityAdmin communityAdmin;
    private Community community;
    private final Database db;

    public CommunityAdminView(CommunityAdmin admin) {
        db = new Database();
        communityAdmin = admin;
        community = db.getCommunity(admin.getCommunityId());

        // top panel
        welcomeLabel.setText("Welcome, " + admin.getName() + "(" + community.getName() + ")");
        editCommunityAdminButton.setText("Edit your details");
        editCommunityAdminButton.addActionListener(e -> {
            AddCommunityAdminDialog editCommunityAdminDialog = new AddCommunityAdminDialog(communityAdmin);
            editCommunityAdminDialog.pack();
            editCommunityAdminDialog.setLocationRelativeTo(null);
            editCommunityAdminDialog.setVisible(true);
            communityAdmin = db.getCommunityAdmin(communityAdmin.getId());
            welcomeLabel.setText("Welcome, " + communityAdmin.getName() + "(" + community.getName() + ")");
        });
        logoutCommunityAdminButton.setText("Logout");
        editCommunity.setText("Edit Community");
        editCommunity.addActionListener(e -> {
            EditCommunityDialog dialog = new EditCommunityDialog(community);
            dialog.pack();
            dialog.setVisible(true);
            community = db.getCommunity(admin.getCommunityId());
            welcomeLabel.setText("Welcome, " + admin.getName() + "(" + community.getName() + ")");
            createHospitalBlocks();
            createDoctorBlocks();
        });

        // hospitals panel
        findHospitalsLabel.setText("Hospitals in your community");
        findHospitalsSearchLabel.setText("Search");
        addHospitalButton.setText("Add Hospital");
        addHospitalButton.addActionListener(e -> {
            AddHospitalDialog addHospitalDialog = new AddHospitalDialog();
            addHospitalDialog.setAddHospitalCommunityValueComboBox(new DefaultComboBoxModel<>(new String[]{community.getName()}));
            addHospitalDialog.pack();
            addHospitalDialog.setVisible(true);
            createHospitalBlocks();
        });
        findHospitalsSearchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                String search = findHospitalsSearchText.getText();
                if (search.equals("")) {
                    createHospitalBlocks();
                } else {
                    createHospitalBlocks(search);
                }
            }
        });
        createHospitalBlocks();

        // doctors panel
        findDoctorsLabel.setText("Doctors in your community");
        findDoctorsSearchLabel.setText("Search");
        addDoctorButton.setText("Add Doctor");
        addDoctorButton.addActionListener(e -> {
            AddDoctorDialog addDoctorDialog = new AddDoctorDialog();
            addDoctorDialog.setDoctorHospitalValueComboBox(new DefaultComboBoxModel<>(db.getAllHospitals().stream().filter(hospital -> hospital.getCommunityId() == community.getId()).map(Hospital::getName).toArray(String[]::new)));
            addDoctorDialog.pack();
            addDoctorDialog.setVisible(true);
            createDoctorBlocks();
        });
        findDoctorsSearchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                String search = findDoctorsSearchText.getText();
                if (search.equals("")) {
                    createDoctorBlocks();
                } else {
                    createDoctorBlocks(search);
                }
            }
        });
        createDoctorBlocks();
    }

    private void createHospitalBlocks() {
        findHospitalsPanel.removeAll();
        ArrayList<Hospital> hospitals = db.getAllHospitals();
        hospitals.removeIf(hospital -> hospital.getCommunityId() != community.getId());
        if (hospitals.size() == 0) {
            findHospitalsPanel.setLayout(new GridLayout());
            findHospitalsPanel.add(new JLabel("No hospitals found in the community. Add a hospital to begin"));
        } else {
            findHospitalsPanel.setLayout(new BoxLayout(findHospitalsPanel, BoxLayout.X_AXIS));
            for (Hospital hospital : hospitals) {
                SysAdminHospitalBlock hospitalBlock = new SysAdminHospitalBlock(hospital);
                findHospitalsPanel.add(hospitalBlock.getMainPanel());
                hospitalBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this hospital?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            db.removeHospital(hospital.getId());
                            createHospitalBlocks();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });
                hospitalBlock.getEditButton().addActionListener(e -> {
                    EditHospitalDialog editHospitalDialog = new EditHospitalDialog(hospital);
                    editHospitalDialog.setEditHospitalCommunityValueComboBox(new DefaultComboBoxModel<>(new String[]{community.getName()}));
                    editHospitalDialog.pack();
                    editHospitalDialog.setVisible(true);
                    createHospitalBlocks();
                });
            }
        }
        findHospitalsPanel.revalidate();
        findHospitalsPanel.repaint();
    }

    public void createHospitalBlocks(String search) {
        ArrayList<Hospital> hospitals = db.getHospitals(search);
        hospitals.removeIf(hospital -> hospital.getCommunityId() != community.getId());
        findHospitalsPanel.removeAll();
        if (hospitals.size() == 0) {
            findHospitalsPanel.setLayout(new GridLayout());
            findHospitalsPanel.add(new JLabel("No hospitals found for the search term"));
        } else {
            findHospitalsPanel.setLayout(new BoxLayout(findHospitalsPanel, BoxLayout.X_AXIS));
            for (Hospital hospital : hospitals) {
                SysAdminHospitalBlock hospitalBlock = new SysAdminHospitalBlock(hospital);
                findHospitalsPanel.add(hospitalBlock.getMainPanel());
                hospitalBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this hospital?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            db.removeHospital(hospital.getId());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                            return;
                        }
                        createHospitalBlocks(search);
                    }
                });
                hospitalBlock.getEditButton().addActionListener(e -> {
                    EditHospitalDialog editHospitalDialog = new EditHospitalDialog(hospital);
                    editHospitalDialog.setEditHospitalCommunityValueComboBox(new DefaultComboBoxModel<>(new String[]{community.getName()}));
                    editHospitalDialog.pack();
                    editHospitalDialog.setVisible(true);
                    createHospitalBlocks(search);
                });
            }
        }
        findHospitalsPanel.revalidate();
        findHospitalsPanel.repaint();
    }

    private void createDoctorBlocks() {
        findDoctorsPanel.removeAll();
        // get ArrayList of ID of all hospitals in the community
        ArrayList<Integer> hospitalIds = db.getAllHospitals().stream().filter(hospital -> hospital.getCommunityId() == community.getId()).map(Hospital::getId).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Doctor> doctors = db.getAllDoctors();
        doctors.removeIf(doctor -> !hospitalIds.contains(doctor.getHospitalId()));
        if (doctors.size() == 0) {
            findDoctorsPanel.setLayout(new GridLayout());
            findDoctorsPanel.add(new JLabel("No doctors found"));
        } else {
            findDoctorsPanel.setLayout(new BoxLayout(findDoctorsPanel, BoxLayout.X_AXIS));
            for (Doctor doctor : doctors) {
                SysAdminDoctorBlock doctorBlock = new SysAdminDoctorBlock(doctor);
                findDoctorsPanel.add(doctorBlock.getMainPanel());
                doctorBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this doctor?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            db.removeDoctor(doctor.getId());
                            createDoctorBlocks();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });
                doctorBlock.getEditButton().addActionListener(e -> {
                    EditDoctorDialog editDoctorDialog = new EditDoctorDialog(doctor);
                    editDoctorDialog.setDoctorHospitalValueComboBox(new DefaultComboBoxModel<>(db.getAllHospitals().stream().filter(hospital -> hospital.getCommunityId() == communityAdmin.getCommunityId()).map(Hospital::getName).toArray(String[]::new)));
                    editDoctorDialog.pack();
                    editDoctorDialog.setVisible(true);
                    createDoctorBlocks();
                });
            }
        }
        findDoctorsPanel.revalidate();
        findDoctorsPanel.repaint();
    }

    public void createDoctorBlocks(String search) {
        ArrayList<Integer> hospitalIds = db.getAllHospitals().stream().filter(hospital -> hospital.getCommunityId() == community.getId()).map(Hospital::getId).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Doctor> doctors = db.getDoctors(search);
        doctors.removeIf(doctor -> !hospitalIds.contains(doctor.getHospitalId()));
        findDoctorsPanel.removeAll();
        if (doctors.size() == 0) {
            findDoctorsPanel.setLayout(new GridLayout());
            findDoctorsPanel.add(new JLabel("No doctors found for the search term"));
        } else {
            findDoctorsPanel.setLayout(new BoxLayout(findDoctorsPanel, BoxLayout.X_AXIS));
            for (Doctor doctor : doctors) {
                SysAdminDoctorBlock doctorBlock = new SysAdminDoctorBlock(doctor);
                findDoctorsPanel.add(doctorBlock.getMainPanel());
                doctorBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this doctor?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            db.removeDoctor(doctor.getId());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                            return;
                        }
                        createDoctorBlocks(search);
                    }
                });
                doctorBlock.getEditButton().addActionListener(e -> {
                    EditDoctorDialog editDoctorDialog = new EditDoctorDialog(doctor);
                    editDoctorDialog.setDoctorHospitalValueComboBox(new DefaultComboBoxModel<>(db.getAllHospitals().stream().filter(hospital -> hospital.getCommunityId() == communityAdmin.getCommunityId()).map(Hospital::getName).toArray(String[]::new)));
                    editDoctorDialog.pack();
                    editDoctorDialog.setVisible(true);
                    createDoctorBlocks(search);
                });
            }
        }
        findDoctorsPanel.revalidate();
        findDoctorsPanel.repaint();
    }

    public JPanel getMainPanel() {
        return communityAdminViewMainPanel;
    }
}
