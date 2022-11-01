package org.komalsuryan.views.sysAdminViewUiElements;

import org.komalsuryan.Appointment;
import org.komalsuryan.Database;

import javax.swing.*;

public class SysAdminAppointmentBlock {
    private JPanel mainPanel;
    private JButton editAppointmentButton;
    private JButton deleteAppointmentButton;
    private JLabel personNameLabel;
    private JLabel doctorNameLabel;
    private JLabel hospitalNameLabel;
    private JLabel dateTimeLabel;
    private JLabel reasonLabel;
    private JLabel personNameValueLabel;
    private JLabel doctorNameValueLabel;
    private JLabel hospitalNameValueLabel;
    private JLabel dateTimeValueLabel;
    private JLabel reasonValueLabel;

    public SysAdminAppointmentBlock(Appointment appointment) {
        // labels
        personNameLabel.setText("Person Name: ");
        doctorNameLabel.setText("Doctor Name: ");
        hospitalNameLabel.setText("Hospital Name: ");
        dateTimeLabel.setText("Date and Time: ");
        reasonLabel.setText("Reason: ");

        Database db = new Database();
        // values
        personNameValueLabel.setText(db.getPatient(appointment.getPatientId()).getName());
        doctorNameValueLabel.setText(db.getDoctor(appointment.getDoctorId()).getName());
        hospitalNameValueLabel.setText(db.getHospital(appointment.getHospitalId()).getName());
        String dateTime = appointment.getDate() + " " + appointment.getTime();
        dateTimeValueLabel.setText(dateTime);
        reasonValueLabel.setText(appointment.getReason());

        // buttons
        editAppointmentButton.setText("Edit");
        deleteAppointmentButton.setText("Delete");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getEditButton() {
        return editAppointmentButton;
    }

    public JButton getDeleteButton() {
        return deleteAppointmentButton;
    }
}
