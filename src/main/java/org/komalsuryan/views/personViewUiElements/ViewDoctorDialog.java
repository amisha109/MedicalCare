package org.komalsuryan.views.personViewUiElements;

import org.komalsuryan.Community;
import org.komalsuryan.Database;
import org.komalsuryan.Doctor;
import org.komalsuryan.Hospital;

import javax.print.Doc;
import javax.swing.*;
import java.awt.event.*;

public class ViewDoctorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel doctorIdLabel;
    private JLabel doctorSpecializationLabel;
    private JLabel hospitalLabel;
    private JLabel hospitalLocationLabel;
    private JLabel hoursAvailableLabel;
    private JLabel offDayLabel;
    private JLabel doctorIdValue;
    private JLabel doctorSpecializationValue;
    private JLabel hospitalValue;
    private JLabel hospitalLocationValue;
    private JLabel hoursAvailableValue;
    private JLabel offDayValue;
    private JLabel doctorNameValue;

    public ViewDoctorDialog(Doctor doctor) {
        Database db = new Database();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        doctorNameValue.setText(doctor.getName());

        // labels
        doctorIdLabel.setText("Doctor ID: ");
        doctorSpecializationLabel.setText("Specialization: ");
        hospitalLabel.setText("Hospital: ");
        hospitalLocationLabel.setText("Hospital Location: ");
        hoursAvailableLabel.setText("Hours Available: ");
        offDayLabel.setText("Off Day: ");

        // values
        doctorIdValue.setText(String.valueOf(doctor.getId()));
        doctorSpecializationValue.setText(doctor.getSpecialization());
        Hospital hospital = db.getHospital(doctor.getHospitalId());
        hospitalValue.setText(hospital.getName());
        Community community = db.getCommunity(hospital.getCommunityId());
        String location = hospital.getLocation() + ", " + community.getName() + ", " + community.getState() + " - " + community.getZipCode();
        hospitalLocationValue.setText(location);
        hoursAvailableValue.setText(doctor.getStartTime().toString() + " - " + doctor.getEndTime().toString());
        offDayValue.setText(doctor.getWeeklyOffDay().toString());

        // buttons
        buttonOK.setText("Schedule Appointment");
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
        // add your code here
        EnterSsnDialog enterSsnDialog = new EnterSsnDialog(doctor);
        enterSsnDialog.pack();
        enterSsnDialog.setVisible(true);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
