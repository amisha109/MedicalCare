package org.amishakalhan.views.sysAdminViewUiElements;

import com.toedter.calendar.JDateChooser;
import org.amishakalhan.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

public class AddAppointmentDialog extends JDialog {
    JDateChooser appointmentDateValueChooser;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel addAppointmentHeadingLabel;
    private JLabel personCommunityLabel;
    private JLabel personSsnLabel;
    private JLabel doctorCommunityLabel;
    private JLabel doctorHospitalLabel;
    private JLabel doctorNameLabel;
    private JLabel appointmentDateLabel;
    private JLabel appointmentTimeLabel;
    private JLabel appointmentReasonLabel;
    private JTextField appointmentVitals1Label;
    private JTextField appointmentVitals2Label;
    private JTextField appointmentVItals3Label;
    private JLabel appointmentDiagnosis;
    private JLabel appointmentTreatment;
    private JComboBox<String> personCommunityComboBox;
    private JComboBox<String> personSsnComboBox;
    private JComboBox<String> doctorCommunityComboBox;
    private JComboBox<String> doctorHospitalComboBox;
    private JComboBox<String> doctorNameComboBox;
    private JPanel appointmentDateValuePanel;
    private JComboBox<String> appointmentTimeComboBox;
    private JTextArea appointmentReasontextArea;
    private JTextField appointmentVital1textField;
    private JTextField appointmentVital2TextField;
    private JTextField appointmentVital3TextField;
    private JTextArea appointmentDiagnosisTextArea;
    private JTextArea appointmentTreatmentTextArea;

    public AddAppointmentDialog(boolean isUser) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        addAppointmentHeadingLabel.setText("Add Appointment");

        // labels
        personCommunityLabel.setText("Person Community: ");
        personSsnLabel.setText("Person SSN: ");
        doctorCommunityLabel.setText("Doctor Community: ");
        doctorHospitalLabel.setText("Doctor Hospital: ");
        doctorNameLabel.setText("Doctor Name: ");
        appointmentDateLabel.setText("Appointment Date: ");
        appointmentTimeLabel.setText("Appointment Time: ");
        appointmentReasonLabel.setText("Appointment Reason: ");
        appointmentDiagnosis.setText("Diagnosis: ");
        appointmentTreatment.setText("Treatment: ");

        // JDateChooser
        appointmentDateValueChooser = new JDateChooser();
        appointmentDateValueChooser.setDateFormatString("MM-dd-yyyy");
        appointmentDateValuePanel.setLayout(new GridLayout());
        appointmentDateValuePanel.add(appointmentDateValueChooser);

        // combo boxes
        personCommunityComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllCommunities().stream().map(Community::getName).toArray(String[]::new)));
        personCommunityComboBox.addActionListener(e -> {
            String selectedCommunity = (String) personCommunityComboBox.getSelectedItem();
            int communityId = new Database().getAllCommunities().stream().filter(community -> community.getName().equals(selectedCommunity)).findFirst().get().getId();
            personSsnComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllPeople().stream().filter(person -> person.getCommunityId() == communityId).map(Person::getSsNumber).toArray(String[]::new)));
        });
        doctorCommunityComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllCommunities().stream().map(Community::getName).toArray(String[]::new)));
        doctorCommunityComboBox.addActionListener(e -> {
            String selectedCommunity = (String) doctorCommunityComboBox.getSelectedItem();
            int communityId = new Database().getAllCommunities().stream().filter(community -> community.getName().equals(selectedCommunity)).findFirst().get().getId();
            doctorHospitalComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllHospitals().stream().filter(hospital -> hospital.getCommunityId() == communityId).map(Hospital::getName).toArray(String[]::new)));
        });
        doctorHospitalComboBox.addActionListener(e -> {
            String selectedHospital = (String) doctorHospitalComboBox.getSelectedItem();
            int hospitalId = new Database().getAllHospitals().stream().filter(hospital -> hospital.getName().equals(selectedHospital)).findFirst().get().getId();
            doctorNameComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllDoctors().stream().filter(doctor -> doctor.getHospitalId() == hospitalId).map(Doctor::getName).toArray(String[]::new)));
        });
        // get the doctor start and end time for selected doctor
        doctorNameComboBox.addActionListener(e -> {
            String selectedDoctor = (String) doctorNameComboBox.getSelectedItem();
            int doctorId = new Database().getAllDoctors().stream().filter(doctor -> doctor.getName().equals(selectedDoctor)).findFirst().get().getId();
            Doctor doctor = new Database().getAllDoctors().stream().filter(doctor1 -> doctor1.getId() == doctorId).findFirst().get();
            int startTime = doctor.getStartTime().getHour();
            int endTime = doctor.getEndTime().getHour();
            String[] times = new String[endTime - startTime + 1];
            for (int i = 0; i < times.length; i++) {
                times[i] = String.valueOf(startTime + i);
                times[i] = times[i].length() == 1 ? "0" + times[i] + ":00" : times[i] + ":00";
            }
            appointmentTimeComboBox.setModel(new DefaultComboBoxModel<>(times));
        });

        // text areas
        appointmentReasontextArea.setLineWrap(true);
        appointmentReasontextArea.setWrapStyleWord(true);
        appointmentDiagnosisTextArea.setLineWrap(true);
        appointmentDiagnosisTextArea.setWrapStyleWord(true);
        appointmentTreatmentTextArea.setLineWrap(true);
        appointmentTreatmentTextArea.setWrapStyleWord(true);

        // if user is the normal user, disable the doctor fields
        if (isUser) {
            appointmentVitals1Label.setEnabled(false);
            appointmentVitals2Label.setEnabled(false);
            appointmentVItals3Label.setEnabled(false);
            appointmentVital1textField.setEnabled(false);
            appointmentVital2TextField.setEnabled(false);
            appointmentVital3TextField.setEnabled(false);
            appointmentDiagnosis.setEnabled(false);
            appointmentTreatment.setEnabled(false);
            appointmentDiagnosisTextArea.setEnabled(false);
            appointmentTreatmentTextArea.setEnabled(false);
        }

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public AddAppointmentDialog(Appointment appointment, boolean isUser) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        addAppointmentHeadingLabel.setText("Add Appointment");

        // labels
        personCommunityLabel.setText("Person Community: ");
        personSsnLabel.setText("Person SSN: ");
        doctorCommunityLabel.setText("Doctor Community: ");
        doctorHospitalLabel.setText("Doctor Hospital: ");
        doctorNameLabel.setText("Doctor Name: ");
        appointmentDateLabel.setText("Appointment Date: ");
        appointmentTimeLabel.setText("Appointment Time: ");
        appointmentReasonLabel.setText("Appointment Reason: ");
        appointmentDiagnosis.setText("Diagnosis: ");
        appointmentTreatment.setText("Treatment: ");

        // JDateChooser
        appointmentDateValueChooser = new JDateChooser();
        appointmentDateValueChooser.setDateFormatString("MM-dd-yyyy");
        appointmentDateValuePanel.setLayout(new GridLayout());
        appointmentDateValuePanel.add(appointmentDateValueChooser);

        // combo boxes
        personCommunityComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllCommunities().stream().map(Community::getName).toArray(String[]::new)));
        personCommunityComboBox.addActionListener(e -> {
            String selectedCommunity = (String) personCommunityComboBox.getSelectedItem();
            int communityId = new Database().getAllCommunities().stream().filter(community -> community.getName().equals(selectedCommunity)).findFirst().get().getId();
            personSsnComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllPeople().stream().filter(person -> person.getCommunityId() == communityId).map(Person::getSsNumber).toArray(String[]::new)));
        });
        doctorCommunityComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllCommunities().stream().map(Community::getName).toArray(String[]::new)));
        doctorCommunityComboBox.addActionListener(e -> {
            String selectedCommunity = (String) doctorCommunityComboBox.getSelectedItem();
            int communityId = new Database().getAllCommunities().stream().filter(community -> community.getName().equals(selectedCommunity)).findFirst().get().getId();
            doctorHospitalComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllHospitals().stream().filter(hospital -> hospital.getCommunityId() == communityId).map(Hospital::getName).toArray(String[]::new)));
        });
        doctorHospitalComboBox.addActionListener(e -> {
            String selectedHospital = (String) doctorHospitalComboBox.getSelectedItem();
            int hospitalId = new Database().getAllHospitals().stream().filter(hospital -> hospital.getName().equals(selectedHospital)).findFirst().get().getId();
            doctorNameComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllDoctors().stream().filter(doctor -> doctor.getHospitalId() == hospitalId).map(Doctor::getName).toArray(String[]::new)));
        });
        // get the doctor start and end time for selected doctor
        doctorNameComboBox.addActionListener(e -> {
            String selectedDoctor = (String) doctorNameComboBox.getSelectedItem();
            int doctorId = new Database().getAllDoctors().stream().filter(doctor -> doctor.getName().equals(selectedDoctor)).findFirst().get().getId();
            Doctor doctor = new Database().getAllDoctors().stream().filter(doctor1 -> doctor1.getId() == doctorId).findFirst().get();
            int startTime = doctor.getStartTime().getHour();
            int endTime = doctor.getEndTime().getHour();
            String[] times = new String[endTime - startTime + 1];
            for (int i = 0; i < times.length; i++) {
                times[i] = String.valueOf(startTime + i);
                times[i] = times[i].length() == 1 ? "0" + times[i] + ":00" : times[i] + ":00";
            }
            appointmentTimeComboBox.setModel(new DefaultComboBoxModel<>(times));
        });

        // text areas
        appointmentReasontextArea.setLineWrap(true);
        appointmentReasontextArea.setWrapStyleWord(true);
        appointmentDiagnosisTextArea.setLineWrap(true);
        appointmentDiagnosisTextArea.setWrapStyleWord(true);
        appointmentTreatmentTextArea.setLineWrap(true);
        appointmentTreatmentTextArea.setWrapStyleWord(true);

        // if user is the normal user, disable the doctor fields
        if (isUser) {
            appointmentVitals1Label.setEnabled(false);
            appointmentVitals2Label.setEnabled(false);
            appointmentVItals3Label.setEnabled(false);
            appointmentVital1textField.setEnabled(false);
            appointmentVital2TextField.setEnabled(false);
            appointmentVital3TextField.setEnabled(false);
            appointmentDiagnosis.setEnabled(false);
            appointmentTreatment.setEnabled(false);
            appointmentDiagnosisTextArea.setEnabled(false);
            appointmentTreatmentTextArea.setEnabled(false);
        }

        // values
        Database db = new Database();
        addAppointmentHeadingLabel.setText("View/Edit Appointment");
        Patient patient = db.getPatient(appointment.getPatientId());
        personCommunityComboBox.setSelectedItem(db.getCommunity(patient.getCommunityId()).getName());
        personSsnComboBox.setSelectedItem(patient.getSsNumber());
        Doctor doctor = db.getDoctor(appointment.getDoctorId());
        Hospital hospital = db.getHospital(doctor.getHospitalId());
        doctorCommunityComboBox.setSelectedItem(db.getCommunity(hospital.getCommunityId()).getName());
        doctorHospitalComboBox.setSelectedItem(hospital.getName());
        doctorNameComboBox.setSelectedItem(doctor.getName());
        Date date = Date.from(appointment.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        appointmentDateValueChooser.setDate(date);
        appointmentTimeComboBox.setSelectedItem(appointment.getTime().toString());
        appointmentReasontextArea.setText(appointment.getReason());
        appointmentDiagnosisTextArea.setText(appointment.getDiagnosis());
        appointmentTreatmentTextArea.setText(appointment.getTreatment());

        // get all key value pairs from the vitals hashmap
        String[] vitals = appointment.getVitals().entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue()).toArray(String[]::new);

        // set the vitals to the text fields
        for (int i = 0; i < vitals.length; i++) {
            switch (i) {
                case 0 -> {
                    appointmentVitals1Label.setText(vitals[i].split(":")[0]);
                    appointmentVital1textField.setText(vitals[i].split(":")[1]);
                }
                case 1 -> {
                    appointmentVitals2Label.setText(vitals[i].split(":")[0]);
                    appointmentVital2TextField.setText(vitals[i].split(":")[1]);
                }
                case 2 -> {
                    appointmentVItals3Label.setText(vitals[i].split(":")[0]);
                    appointmentVital3TextField.setText(vitals[i].split(":")[1]);
                }
            }
        }

        buttonOK.addActionListener(e -> onOK(appointment));

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public AddAppointmentDialog(Patient patient, Doctor doctor, boolean isUser) {
        this(isUser);
        addAppointmentHeadingLabel.setText("Add Appointment");
        personCommunityComboBox.setSelectedItem(new Database().getCommunity(patient.getCommunityId()).getName());
        personCommunityComboBox.setEnabled(false);
        personSsnComboBox.setSelectedItem(patient.getSsNumber());
        personSsnComboBox.setEnabled(false);
        doctorCommunityComboBox.setSelectedItem(new Database().getCommunity(doctor.getHospitalId()).getName());
        doctorCommunityComboBox.setEnabled(false);
        doctorHospitalComboBox.setSelectedItem(new Database().getHospital(doctor.getHospitalId()).getName());
        doctorHospitalComboBox.setEnabled(false);
        doctorNameComboBox.setSelectedItem(doctor.getName());
        doctorNameComboBox.setEnabled(false);
    }

    private void onOK() {
        // get values from the fields
        String personSsn = (String) personSsnComboBox.getSelectedItem();
        if (personSsn == null) {
            JOptionPane.showMessageDialog(null, "Please select a person");
            return;
        }
        String doctorName = (String) doctorNameComboBox.getSelectedItem();
        String hospitalName = (String) doctorHospitalComboBox.getSelectedItem();
        String communityName = (String) doctorCommunityComboBox.getSelectedItem();
        // get community id
        int communityId = new Database().getAllCommunities().stream().filter(community -> community.getName().equals(communityName)).findFirst().get().getId();
        // get hospital id
        int hospitalId = new Database().getAllHospitals().stream().filter(hospital -> hospital.getName().equals(hospitalName) && hospital.getCommunityId() == communityId).findFirst().get().getId();
        // get doctor id
        int doctorId = new Database().getAllDoctors().stream().filter(doctor -> doctor.getName().equals(doctorName) && doctor.getHospitalId() == hospitalId).findFirst().get().getId();
        LocalDate date = appointmentDateValueChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String appointmentTime = (String) appointmentTimeComboBox.getSelectedItem();
        if (appointmentTime == null) {
            JOptionPane.showMessageDialog(null, "Please select a time");
            return;
        }
        // convert the time to local time
        LocalTime time = LocalTime.parse(appointmentTime);
        String appointmentReason = appointmentReasontextArea.getText();
        String appointmentDiagnosis = appointmentDiagnosisTextArea.getText();
        String appointmentTreatment = appointmentTreatmentTextArea.getText();
        HashMap<String, String> vitals = new HashMap<>();
        vitals.put(appointmentVitals1Label.getText(), appointmentVital1textField.getText());
        vitals.put(appointmentVitals2Label.getText(), appointmentVital2TextField.getText());
        vitals.put(appointmentVItals3Label.getText(), appointmentVital3TextField.getText());
        if (new Database().getPatient(personSsn) == null) {
            Person person = new Database().getPerson(personSsn);
            EditPatientDialog editPatientDialog = new EditPatientDialog(person);
            editPatientDialog.pack();
            editPatientDialog.setVisible(true);
        }
        int patientId = new Database().getPatient(personSsn).getId();

        // create the appointment
        Appointment appointment = new Appointment(patientId, hospitalId, date, doctorId, time, appointmentReason, vitals, appointmentDiagnosis, appointmentTreatment);
        new Database().addAppointment(appointment);
        dispose();
    }

    public void onOK(Appointment appointment) {
        // get values from the fields
        String personSsn = (String) personSsnComboBox.getSelectedItem();
        if (personSsn == null) {
            JOptionPane.showMessageDialog(null, "Please select a person");
            return;
        }
        String doctorName = (String) doctorNameComboBox.getSelectedItem();
        String hospitalName = (String) doctorHospitalComboBox.getSelectedItem();
        String communityName = (String) doctorCommunityComboBox.getSelectedItem();
        // get community id
        int communityId = new Database().getAllCommunities().stream().filter(community -> community.getName().equals(communityName)).findFirst().get().getId();
        // get hospital id
        int hospitalId = new Database().getAllHospitals().stream().filter(hospital -> hospital.getName().equals(hospitalName) && hospital.getCommunityId() == communityId).findFirst().get().getId();
        // get doctor id
        int doctorId = new Database().getAllDoctors().stream().filter(doctor -> doctor.getName().equals(doctorName) && doctor.getHospitalId() == hospitalId).findFirst().get().getId();
        String appointmentDate = appointmentDateValueChooser.getDate().toString();
        LocalDate date = LocalDate.parse(appointmentDate);
        String appointmentTime = (String) appointmentTimeComboBox.getSelectedItem();
        if (appointmentTime == null) {
            JOptionPane.showMessageDialog(null, "Please select a time");
            return;
        }
        // convert the time to local time
        LocalTime time = LocalTime.parse(appointmentTime);
        String appointmentReason = appointmentReasontextArea.getText();
        String appointmentDiagnosis = appointmentDiagnosisTextArea.getText();
        String appointmentTreatment = appointmentTreatmentTextArea.getText();
        HashMap<String, String> vitals = new HashMap<>();
        vitals.put(appointmentVitals1Label.getText(), appointmentVital1textField.getText());
        vitals.put(appointmentVitals2Label.getText(), appointmentVital2TextField.getText());
        vitals.put(appointmentVItals3Label.getText(), appointmentVital3TextField.getText());
        if (new Database().getPatient(personSsn) == null) {
            Person person = new Database().getPerson(personSsn);
            EditPatientDialog editPatientDialog = new EditPatientDialog(person);
            editPatientDialog.pack();
            editPatientDialog.setVisible(true);
        }
        int patientId = new Database().getPatient(personSsn).getId();

        // update the appointment
        appointment.setPatientId(patientId);
        appointment.setHospitalId(hospitalId);
        appointment.setDate(date);
        appointment.setDoctorId(doctorId);
        appointment.setTime(time);
        appointment.setReason(appointmentReason);
        appointment.setVitals(vitals);
        appointment.setDiagnosis(appointmentDiagnosis);
        appointment.setTreatment(appointmentTreatment);
        new Database().updateAppointment(appointment);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
