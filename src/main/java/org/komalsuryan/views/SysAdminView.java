package org.komalsuryan.views;

import org.komalsuryan.*;
import org.komalsuryan.views.sysAdminViewUiElements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SysAdminView {
    private JPanel sysAdminViewMainPanel;
    private JPanel sysAdminViewHeadingPanel;
    private JLabel welcomeLabel;
    private JButton editSysAdminButton;
    private JButton logoutSysAdminButton;
    private JScrollPane doctorsScrollPane;
    private JPanel findDoctorsPanel;
    private JPanel doctorsTopPanel;
    private JLabel findDoctorsLabel;
    private JPanel findDoctorsSearchPanel;
    private JLabel findDoctorsSearchLabel;
    private JTextField findDoctorsSearchText;
    private JButton addDoctorButton;
    private JPanel doctorsPanel;
    private JPanel hospitalsPanel;
    private JScrollPane hospitalsScrollPane;
    private JPanel hospitalsTopPanel;
    private JPanel findHospitalsPanel;
    private JLabel findHospitalsLabel;
    private JPanel findHospitalsSearchPanel;
    private JLabel findHosptalsSearchLabel;
    private JTextField findHospitalsSearchText;
    private JButton addHospitalButton;
    private JPanel communitiesPanel;
    private JScrollPane communitiesScrollPane;
    private JPanel findCommunitiesPanel;
    private JPanel communitiesTopPanel;
    private JLabel findCommunitiesLabel;
    private JPanel findCommunitiesSearchPanel;
    private JLabel findCommunitiesSearchLabel;
    private JTextField findCommunitiesSearchText;
    private JButton addCommunityButton;
    private JPanel sysAdminViewContentPanel;
    private JScrollPane sysAdminViewContentScrollPane;
    private JPanel peoplePanel;
    private JPanel findPeoplePanel;
    private JLabel findPeopleLabel;
    private JPanel findPeopleSearchPanel;
    private JLabel findPeopleSearchLabel;
    private JTextField findPeopleSearchField;
    private JButton addPeopleButton;
    private JPanel communityAdminPanel;
    private JPanel findCommunityAdminsPanel;
    private JLabel findCommunityAdminLabel;
    private JLabel findCommunityAdminSearchLabel;
    private JTextField findCommunityAdminSearchText;
    private JButton addCommunityAdminButton;
    private JPanel patientPanel;
    private JPanel findPatientsPanel;
    private JLabel findPatientHeadingLabel;
    private JLabel findPatientSearchLabel;
    private JTextField findPatientSearchText;
    private JPanel appointmentPanel;
    private JLabel appointmentPanelHeading;
    private JLabel findAppointmentSearchLabel;
    private JTextField findAppointmentSearchText;
    private JButton addAppointment;
    private JPanel findAppointmentsPanel;
    private final Database db;

    public SysAdminView() {
        db = new Database();

        welcomeLabel.setText("Welcome, System Admin");
        editSysAdminButton.setText("Edit your details");
        logoutSysAdminButton.setText("Logout");

        // communities panel
        findCommunitiesLabel.setText("Communities");
        findCommunitiesSearchLabel.setText("Search");
        addCommunityButton.setText("Add Community");
        addCommunityButton.addActionListener(e -> {
            AddCommunityDialog addCommunityDialog = new AddCommunityDialog();
            addCommunityDialog.pack();
            addCommunityDialog.setVisible(true);
            createCommunityBlocks();
        });
        findCommunitiesSearchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                super.keyReleased(e);
                String search = findCommunitiesSearchText.getText();
                if (search.equals("")) {
                    createCommunityBlocks();
                } else {
                    createCommunityBlocks(search);
                }
            }
        });

        // people panel
        findPeopleLabel.setText("People");
        findPeopleSearchLabel.setText("Search");
        addPeopleButton.setText("Add Person");
        addPeopleButton.addActionListener(e -> {
            AddPersonDialog addPersonDialog = new AddPersonDialog();
            addPersonDialog.pack();
            addPersonDialog.setVisible(true);
            createPeopleBlocks();
        });
        findPeopleSearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                super.keyReleased(e);
                String search = findPeopleSearchField.getText();
                if (search.equals("")) {
                    createPeopleBlocks();
                } else {
                    createPeopleBlocks(search);
                }
            }
        });

        // community admin panel
        findCommunityAdminLabel.setText("Community Admins");
        findCommunityAdminSearchLabel.setText("Search");
        addCommunityAdminButton.setText("Add Community Admin");
        addCommunityAdminButton.addActionListener(e -> {
            AddCommunityAdminDialog addCommunityAdminDialog = new AddCommunityAdminDialog();
            addCommunityAdminDialog.pack();
            addCommunityAdminDialog.setVisible(true);
            createCommunityAdminBlocks();
        });
        findCommunityAdminSearchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                super.keyReleased(e);
                String search = findCommunityAdminSearchText.getText();
                if (search.equals("")) {
                    createCommunityAdminBlocks();
                } else {
                    createCommunityAdminBlocks(search);
                }
            }
        });

        // hospitals panel
        findHospitalsLabel.setText("Hospitals");
        findHosptalsSearchLabel.setText("Search");
        addHospitalButton.setText("Add a hospital");
        addHospitalButton.addActionListener(e -> {
            AddHospitalDialog addHospitalDialog = new AddHospitalDialog();
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

        // doctors panel
        findDoctorsLabel.setText("Doctors");
        findDoctorsSearchLabel.setText("Search");
        addDoctorButton.setText("Add a doctor");
        addDoctorButton.addActionListener(e -> {
            AddDoctorDialog addDoctorDialog = new AddDoctorDialog();
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

        // patient panel
        findPatientHeadingLabel.setText("Patients");
        findPatientSearchLabel.setText("Search");
        findPatientSearchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                String search = findPatientSearchText.getText();
                if (search.equals("")) {
                    createPatientBlocks();
                } else {
                    createPatientBlocks(search);
                }
            }
        });

        // appointment panel
        appointmentPanelHeading.setText("Appointments");
        findAppointmentSearchLabel.setText("Search");
        addAppointment.setText("Add Appointment");
        addAppointment.addActionListener(e -> {
            AddAppointmentDialog addAppointmentDialog = new AddAppointmentDialog(false);
            addAppointmentDialog.pack();
            addAppointmentDialog.setVisible(true);
            createAppointmentBlocks();
            createPatientBlocks();
        });
        findAppointmentSearchText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                String search = findAppointmentSearchText.getText();
                if (search.equals("")) {
                    createAppointmentBlocks();
                } else {
                    createAppointmentBlocks(search);
                }
            }
        });

        createCommunityBlocks();
        createPeopleBlocks();
        createCommunityAdminBlocks();
        createHospitalBlocks();
        createDoctorBlocks();
        createPatientBlocks();
        createAppointmentBlocks();
    }

    public void createCommunityBlocks() {
        findCommunitiesPanel.removeAll();
        ArrayList<Community> communities = db.getAllCommunities();
        if (communities.size() == 0) {
            findCommunitiesPanel.setLayout(new GridLayout());
            findCommunitiesPanel.add(new JLabel("No communities found. Add a community to begin"));
        } else {
            findCommunitiesPanel.setLayout(new BoxLayout(findCommunitiesPanel, BoxLayout.X_AXIS));
            for (Community community : communities) {
                SysAdminCommunityBlock communityBlock = new SysAdminCommunityBlock(community);
                communityBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this community?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        db.removeCommunity(community.getId());
                        createCommunityBlocks();
                    }
                });
                communityBlock.getEditButton().addActionListener(e -> {
                    EditCommunityDialog editCommunityDialog = new EditCommunityDialog(community);
                    editCommunityDialog.pack();
                    editCommunityDialog.setVisible(true);
                    createCommunityBlocks();
                });
                findCommunitiesPanel.add(communityBlock.getMainPanel());
            }
        }
        findCommunitiesPanel.revalidate();
        findCommunitiesPanel.repaint();
    }

    public void createCommunityBlocks(String search) {
        ArrayList<Community> communities = new Database().getCommunities(search);
        findCommunitiesPanel.removeAll();
        if (communities.size() == 0) {
            findCommunitiesPanel.setLayout(new GridLayout());
            findCommunitiesPanel.add(new JLabel("No communities found for the search term"));
        } else {
            findCommunitiesPanel.setLayout(new BoxLayout(findCommunitiesPanel, BoxLayout.X_AXIS));
            for (Community community : communities) {
                SysAdminCommunityBlock communityBlock = new SysAdminCommunityBlock(community);
                communityBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this community?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        db.removeCommunity(community.getId());
                        createCommunityBlocks(search);
                    }
                });
                communityBlock.getEditButton().addActionListener(e -> {
                    EditCommunityDialog editCommunityDialog = new EditCommunityDialog(community);
                    editCommunityDialog.pack();
                    editCommunityDialog.setVisible(true);
                    createCommunityBlocks(search);
                });
                findCommunitiesPanel.add(communityBlock.getMainPanel());
            }
        }
        findCommunitiesPanel.revalidate();
        findCommunitiesPanel.repaint();
    }

    public void createPeopleBlocks() {
        findPeoplePanel.removeAll();
        ArrayList<Person> people = db.getAllPeople();
        if (people.size() == 0) {
            findPeoplePanel.setLayout(new GridLayout());
            findPeoplePanel.add(new JLabel("No people found. Add a person to begin"));
        } else {
            findPeoplePanel.setLayout(new BoxLayout(findPeoplePanel, BoxLayout.X_AXIS));
            for (Person person : people) {
                SysAdminPersonBlock personBlock = new SysAdminPersonBlock(person);
                personBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this person?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        db.removePerson(person.getSsNumber());
                        createPeopleBlocks();
                    }
                });
                personBlock.getEditButton().addActionListener(e -> {
                    AddPersonDialog editPersonDialog = new AddPersonDialog(person);
                    editPersonDialog.pack();
                    editPersonDialog.setVisible(true);
                    createPeopleBlocks();
                });
                findPeoplePanel.add(personBlock.getMainPanel());
            }
        }
        findPeoplePanel.revalidate();
        findPeoplePanel.repaint();
    }

    public void createPeopleBlocks(String search) {
        ArrayList<Person> people = new Database().getPeople(search);
        findPeoplePanel.removeAll();
        if (people.size() == 0) {
            findPeoplePanel.setLayout(new GridLayout());
            findPeoplePanel.add(new JLabel("No people found for the search term"));
        } else {
            findPeoplePanel.setLayout(new BoxLayout(findPeoplePanel, BoxLayout.X_AXIS));
            for (Person person : people) {
                SysAdminPersonBlock personBlock = new SysAdminPersonBlock(person);
                personBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this person?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        db.removePerson(person.getSsNumber());
                        createPeopleBlocks(search);
                    }
                });
                personBlock.getEditButton().addActionListener(e -> {
                    AddPersonDialog editPersonDialog = new AddPersonDialog(person);
                    editPersonDialog.pack();
                    editPersonDialog.setVisible(true);
                    createPeopleBlocks(search);
                });
                findPeoplePanel.add(personBlock.getMainPanel());
            }
        }
        findPeoplePanel.revalidate();
        findPeoplePanel.repaint();
    }

    public void createCommunityAdminBlocks() {
        findCommunityAdminsPanel.removeAll();
        ArrayList<CommunityAdmin> communityAdmins = db.getAllCommunityAdmins();
        if (communityAdmins.size() == 0) {
            findCommunityAdminsPanel.setLayout(new GridLayout());
            findCommunityAdminsPanel.add(new JLabel("No community admins found. Add a community admin to begin"));
        } else {
            findCommunityAdminsPanel.setLayout(new BoxLayout(findCommunityAdminsPanel, BoxLayout.X_AXIS));
            for (CommunityAdmin communityAdmin : communityAdmins) {
                SysAdminCommunityAdminBlock communityAdminBlock = new SysAdminCommunityAdminBlock(communityAdmin);
                communityAdminBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this community admin?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        db.deleteCommunityAdmin(communityAdmin.getId());
                        createCommunityAdminBlocks();
                    }
                });
                communityAdminBlock.getEditButton().addActionListener(e -> {
                    AddCommunityAdminDialog editCommunityAdminDialog = new AddCommunityAdminDialog(communityAdmin);
                    editCommunityAdminDialog.pack();
                    editCommunityAdminDialog.setVisible(true);
                    createCommunityAdminBlocks();
                });
                findCommunityAdminsPanel.add(communityAdminBlock.getMainPanel());
            }
        }
        findCommunityAdminsPanel.revalidate();
        findCommunityAdminsPanel.repaint();
    }

    public void createCommunityAdminBlocks(String search) {
        ArrayList<CommunityAdmin> communityAdmins = new Database().getCommunityAdmins(search);
        findCommunityAdminsPanel.removeAll();
        if (communityAdmins.size() == 0) {
            findCommunityAdminsPanel.setLayout(new GridLayout());
            findCommunityAdminsPanel.add(new JLabel("No community admins found for the search term"));
        } else {
            findCommunityAdminsPanel.setLayout(new BoxLayout(findCommunityAdminsPanel, BoxLayout.X_AXIS));
            for (CommunityAdmin communityAdmin : communityAdmins) {
                SysAdminCommunityAdminBlock communityAdminBlock = new SysAdminCommunityAdminBlock(communityAdmin);
                communityAdminBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this community admin?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        db.deleteCommunityAdmin(communityAdmin.getId());
                        createCommunityAdminBlocks(search);
                    }
                });
                communityAdminBlock.getEditButton().addActionListener(e -> {
                    AddCommunityAdminDialog editCommunityAdminDialog = new AddCommunityAdminDialog(communityAdmin);
                    editCommunityAdminDialog.pack();
                    editCommunityAdminDialog.setVisible(true);
                    createCommunityAdminBlocks(search);
                });
                findCommunityAdminsPanel.add(communityAdminBlock.getMainPanel());
            }
        }
        findCommunityAdminsPanel.revalidate();
        findCommunityAdminsPanel.repaint();
    }

    public void createHospitalBlocks() {
        findHospitalsPanel.removeAll();
        ArrayList<Hospital> hospitals = db.getAllHospitals();
        if (hospitals.size() == 0) {
            findHospitalsPanel.setLayout(new GridLayout());
            findHospitalsPanel.add(new JLabel("No hospitals found. Add a hospital to begin"));
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
        ArrayList<Hospital> hospitals = new Database().getHospitals(search);
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
        ArrayList<Doctor> doctors = db.getAllDoctors();
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
        ArrayList<Doctor> doctors = new Database().getDoctors(search);
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
                    editDoctorDialog.pack();
                    editDoctorDialog.setVisible(true);
                    createDoctorBlocks(search);
                });
            }
        }
        findDoctorsPanel.revalidate();
        findDoctorsPanel.repaint();
    }

    public void createPatientBlocks() {
        findPatientsPanel.removeAll();
        ArrayList<Patient> patients = db.getAllPatients();
        if (patients.size() == 0) {
            findPatientsPanel.setLayout(new GridLayout());
            findPatientsPanel.add(new JLabel("No patients found"));
        } else {
            findPatientsPanel.setLayout(new BoxLayout(findPatientsPanel, BoxLayout.X_AXIS));
            for (Patient patient : patients) {
                SysAdminPatientBlock patientBlock = new SysAdminPatientBlock(patient);
                findPatientsPanel.add(patientBlock.getMainPanel());
                patientBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this patient?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            db.removePatient(patient.getId());
                            createPatientBlocks();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });
                patientBlock.getEditButton().addActionListener(e -> {
                    EditPatientDialog editPatientDialog = new EditPatientDialog(patient);
                    editPatientDialog.pack();
                    editPatientDialog.setVisible(true);
                    createPatientBlocks();
                });
            }
        }
        findPatientsPanel.revalidate();
        findPatientsPanel.repaint();
    }

    public void createPatientBlocks(String search) {
        ArrayList<Patient> patients = new Database().getPatients(search);
        findPatientsPanel.removeAll();
        if (patients.size() == 0) {
            findPatientsPanel.setLayout(new GridLayout());
            findPatientsPanel.add(new JLabel("No patients found for the search term"));
        } else {
            findPatientsPanel.setLayout(new BoxLayout(findPatientsPanel, BoxLayout.X_AXIS));
            for (Patient patient : patients) {
                SysAdminPatientBlock patientBlock = new SysAdminPatientBlock(patient);
                findPatientsPanel.add(patientBlock.getMainPanel());
                patientBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this patient?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            db.removePatient(patient.getId());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                            return;
                        }
                        createPatientBlocks(search);
                    }
                });
                patientBlock.getEditButton().addActionListener(e -> {
                    EditPatientDialog editPatientDialog = new EditPatientDialog(patient);
                    editPatientDialog.pack();
                    editPatientDialog.setVisible(true);
                    createPatientBlocks(search);
                });
            }
        }
        findPatientsPanel.revalidate();
        findPatientsPanel.repaint();
    }

    public void createAppointmentBlocks() {
        findAppointmentsPanel.removeAll();
        ArrayList<Appointment> appointments = db.getAllAppointments();
        if (appointments.size() == 0) {
            findAppointmentsPanel.setLayout(new GridLayout());
            findAppointmentsPanel.add(new JLabel("No appointments found"));
        } else {
            findAppointmentsPanel.setLayout(new BoxLayout(findAppointmentsPanel, BoxLayout.X_AXIS));
            for (Appointment appointment : appointments) {
                SysAdminAppointmentBlock appointmentBlock = new SysAdminAppointmentBlock(appointment);
                findAppointmentsPanel.add(appointmentBlock.getMainPanel());
                appointmentBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this appointment?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            db.removeAppointment(appointment.getId());
                            createAppointmentBlocks();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });
                appointmentBlock.getEditButton().addActionListener(e -> {
                    AddAppointmentDialog editAppointmentDialog = new AddAppointmentDialog(appointment, false);
                    editAppointmentDialog.pack();
                    editAppointmentDialog.setVisible(true);
                    createAppointmentBlocks();
                });
            }
        }
        findAppointmentsPanel.revalidate();
        findAppointmentsPanel.repaint();
    }

    public void createAppointmentBlocks(String search) {
        ArrayList<Appointment> appointments = new Database().getAppointments(search);
        findAppointmentsPanel.removeAll();
        if (appointments.size() == 0) {
            findAppointmentsPanel.setLayout(new GridLayout());
            findAppointmentsPanel.add(new JLabel("No appointments found for the search term"));
        } else {
            findAppointmentsPanel.setLayout(new BoxLayout(findAppointmentsPanel, BoxLayout.X_AXIS));
            for (Appointment appointment : appointments) {
                SysAdminAppointmentBlock appointmentBlock = new SysAdminAppointmentBlock(appointment);
                findAppointmentsPanel.add(appointmentBlock.getMainPanel());
                appointmentBlock.getDeleteButton().addActionListener(e -> {
                    // dialog to confirm deletion
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this appointment?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            db.removeAppointment(appointment.getId());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                            return;
                        }
                        createAppointmentBlocks(search);
                    }
                });
                appointmentBlock.getEditButton().addActionListener(e -> {
                    AddAppointmentDialog editAppointmentDialog = new AddAppointmentDialog(appointment, false);
                    editAppointmentDialog.pack();
                    editAppointmentDialog.setVisible(true);
                    createAppointmentBlocks(search);
                });
            }
        }
        findAppointmentsPanel.revalidate();
        findAppointmentsPanel.repaint();
    }

    public JPanel getMainPanel() {
        return sysAdminViewMainPanel;
    }
}
