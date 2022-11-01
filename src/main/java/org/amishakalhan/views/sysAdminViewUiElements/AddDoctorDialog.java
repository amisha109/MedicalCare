package org.amishakalhan.views.sysAdminViewUiElements;

import org.amishakalhan.Database;
import org.amishakalhan.Doctor;
import org.amishakalhan.DoctorSpeciality;
import org.amishakalhan.Hospital;

import javax.swing.*;
import java.awt.event.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

public class AddDoctorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel addDoctorHeadingLabel;
    private JPanel bottomPanel;
    private JPanel buttonPanel;
    private JLabel doctorNameKeyLabel;
    private JLabel doctorSpecializationKeyLabel;
    private JLabel doctorHospitalKeyLabel;
    private JLabel doctorStartTimeKeyLabel;
    private JLabel doctorEndTimeKeyLabel;
    private JLabel doctorDayOffKeyLabel;
    private JTextField doctorNameValueField;
    private JComboBox<String> doctorSpecializationValueComboBox;
    private JComboBox<String> doctorHospitalValueComboBox;
    private JComboBox<String> doctorStartTimeValueComboBox;
    private JComboBox<String> doctorEndTimeValueComboBox;
    private JComboBox<String> doctorDayOffValueComboBox;
    private JSpinner doctorMaxPatientsValueSpinner;
    private JLabel doctorMaxPatientsLabel;
    private JTextField doctorEmailValueField;
    private JPasswordField doctorPasswordValueField;
    private JLabel doctorEmailKeyLabel;
    private JLabel doctorPasswordKeyLabel;

    public AddDoctorDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        addDoctorHeadingLabel.setText("Add New Doctor");

        // key labels
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
        doctorSpecializationValueComboBox.setModel(new DefaultComboBoxModel<>(Arrays.stream(DoctorSpeciality.values()).map(DoctorSpeciality::getSpeciality).toArray(String[]::new)));
        doctorHospitalValueComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllHospitals().stream().map(Hospital::getName).toArray(String[]::new)));
        doctorMaxPatientsValueSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
        String[] timeSlots = new String[24];
        for (int i = 0; i < 24; i++) {
            timeSlots[i] = String.format("%02d:00", i);
        }
        doctorStartTimeValueComboBox.setModel(new DefaultComboBoxModel<>(timeSlots));
        doctorEndTimeValueComboBox.setModel(new DefaultComboBoxModel<>(timeSlots));
        doctorEndTimeValueComboBox.addItem("23:59");
        doctorDayOffValueComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));


        buttonOK.setText("Add");
        buttonCancel.setText("Cancel");

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

    private void onOK() {
        // get all field values
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

            // add doctor to database
            new Database().addDoctor(new Doctor(doctorName, doctorSpecialization, doctorHospitalId, doctorStartTime, doctorEndTime, doctorMaxPatients, doctorDayOff, doctorEmail, doctorPassword));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
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
