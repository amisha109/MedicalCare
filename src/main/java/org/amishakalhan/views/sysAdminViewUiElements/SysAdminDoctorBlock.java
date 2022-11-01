package org.amishakalhan.views.sysAdminViewUiElements;

import org.amishakalhan.Database;
import org.amishakalhan.Doctor;

import javax.swing.*;

public class SysAdminDoctorBlock {
    private JPanel mainPanel;
    private JLabel doctorNameLabel;
    private JButton editDoctorButton;
    private JButton deleteDoctorButton;
    private JLabel doctorSpecializationLabel;
    private JLabel doctorHospitalLabel;
    private JLabel doctorHoursAvailable;
    private JLabel doctorSpecializationValue;
    private JLabel doctorHospitalValue;
    private JLabel doctorHoursAvailableValue;

    public SysAdminDoctorBlock(Doctor doctor) {
        doctorNameLabel.setText(doctor.getName());
        doctorSpecializationLabel.setText("Specialization");
        doctorSpecializationValue.setText(doctor.getSpecialization());
        doctorHospitalLabel.setText("Hospital");
        doctorHospitalValue.setText(new Database().getHospital(doctor.getHospitalId()).getName());
        doctorHoursAvailable.setText("Hours available");
        doctorHoursAvailableValue.setText(doctor.getStartTime().toString() + " - " + doctor.getEndTime().toString());
        editDoctorButton.setText("Edit");
        deleteDoctorButton.setText("Delete");
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getEditButton() {
        return editDoctorButton;
    }

    public JButton getDeleteButton() {
        return deleteDoctorButton;
    }

}
