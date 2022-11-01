package org.komalsuryan.views.sysAdminViewUiElements;

import org.komalsuryan.Database;
import org.komalsuryan.Doctor;
import org.komalsuryan.DoctorSpeciality;
import org.komalsuryan.Hospital;

import javax.swing.*;
import java.awt.event.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

public class EditDoctorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel doctorNameKeyLabel;
    private JLabel doctorSpecializationKeyLabel;
    private JLabel doctorHospitalKeyLabel;
    private JLabel doctorStartTimeKeyLabel;
    private JLabel doctorEndTimeKeyLabel;
    private JLabel doctorDayOffKeyLabel;
    private JLabel doctorMaxPatientsLabel;
    private JTextField doctorNameValueField;
    private JComboBox<String> doctorSpecializationValueComboBox;
    private JComboBox<String> doctorHospitalValueComboBox;
    private JComboBox<String> doctorStartTimeValueComboBox;
    private JComboBox<String> doctorEndTimeValueComboBox;
    private JComboBox<String> doctorDayOffValueComboBox;
    private JSpinner doctorMaxPatientsValueSpinner;
    private JLabel editDoctorHeadingLabel;
    private JTextField doctorEmailValueField;
    private JPasswordField doctorPasswordValueField;
    private JLabel doctorEmailKeyLabel;
    private JLabel doctorPasswordKeyLabel;

    public EditDoctorDialog(Doctor doctor) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        editDoctorHeadingLabel.setText("Edit Doctor");

        // labels
        doctorNameKeyLabel.setText("Name");
        doctorSpecializationKeyLabel.setText("Specialization");
        doctorHospitalKeyLabel.setText("Select Hospital/Clinic");
        doctorStartTimeKeyLabel.setText("Shift Start Time");
        doctorEndTimeKeyLabel.setText("Shift End Time");
        doctorMaxPatientsLabel.setText("Max Patients per Hour");
        doctorDayOffKeyLabel.setText("Select the Day Off");
        doctorEmailKeyLabel.setText("Email");
        doctorPasswordKeyLabel.setText("Password");

        // value fields
        doctorNameValueField.setText(doctor.getName());
        doctorSpecializationValueComboBox.setModel(new DefaultComboBoxModel<>(Arrays.stream(DoctorSpeciality.values()).map(DoctorSpeciality::getSpeciality).toArray(String[]::new)));
        doctorSpecializationValueComboBox.setSelectedItem(doctor.getSpecialization());
        doctorHospitalValueComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllHospitals().stream().map(Hospital::getName).toArray(String[]::new)));
        doctorHospitalValueComboBox.setSelectedItem(new Database().getAllHospitals().stream().filter(hospital -> hospital.getId() == doctor.getHospitalId()).findFirst().get().getName());
        doctorMaxPatientsValueSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
        doctorMaxPatientsValueSpinner.setValue(doctor.getMaxPatientsPerHour());
        String[] timeSlots = new String[24];
        for (int i = 0; i < 24; i++) {
            timeSlots[i] = String.format("%02d:00", i);
        }
        doctorStartTimeValueComboBox.setModel(new DefaultComboBoxModel<>(timeSlots));
        doctorStartTimeValueComboBox.setSelectedItem(doctor.getStartTime().toString());
        doctorEndTimeValueComboBox.setModel(new DefaultComboBoxModel<>(timeSlots));
        doctorEndTimeValueComboBox.addItem("23:59");
        doctorEndTimeValueComboBox.setSelectedItem(doctor.getEndTime().toString());
        doctorDayOffValueComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
        doctorDayOffValueComboBox.setSelectedItem(doctor.getWeeklyOffDay().toString());
        doctorEmailValueField.setText(doctor.getEmail());
        doctorPasswordValueField.setText(doctor.getPassword());

        buttonOK.addActionListener(e -> onOK(doctor));

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

    private void onOK(Doctor doctor) {
        // get the values from the fields
        try {
            String doctorName = doctorNameValueField.getText();
            String doctorSpecialization = (String) doctorSpecializationValueComboBox.getSelectedItem();
            // get hospital ID from name using getAllHospitals()
            String doctorHospitalName = (String) doctorHospitalValueComboBox.getSelectedItem();
            int doctorHospitalId = new Database().getAllHospitals().stream().filter(hospital -> Objects.equals(hospital.getName(), doctorHospitalName)).findFirst().get().getId();
            LocalTime doctorStartTime = LocalTime.parse((String) Objects.requireNonNull(doctorStartTimeValueComboBox.getSelectedItem()));
            LocalTime doctorEndTime = LocalTime.parse((String) Objects.requireNonNull(doctorEndTimeValueComboBox.getSelectedItem()));
            int doctorMaxPatients = (int) doctorMaxPatientsValueSpinner.getValue();
            DayOfWeek doctorDayOff = DayOfWeek.valueOf(((String) Objects.requireNonNull(doctorDayOffValueComboBox.getSelectedItem())).toUpperCase());
            String doctorEmail = doctorEmailValueField.getText();
            String doctorPassword = new String(doctorPasswordValueField.getPassword());

            // update the doctor
            doctor.setName(doctorName);
            doctor.setSpecialization(doctorSpecialization);
            doctor.setHospitalId(doctorHospitalId);
            doctor.setStartTime(doctorStartTime);
            doctor.setEndTime(doctorEndTime);
            doctor.setMaxPatientsPerHour(doctorMaxPatients);
            doctor.setWeeklyOffDay(doctorDayOff);
            doctor.setEmail(doctorEmail);
            doctor.setPassword(doctorPassword);

            // update the doctor in the database
            new Database().updateDoctor(doctor);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void setDoctorHospitalValueComboBox(ComboBoxModel<String> model) {
        doctorHospitalValueComboBox.setModel(model);
    }
}
