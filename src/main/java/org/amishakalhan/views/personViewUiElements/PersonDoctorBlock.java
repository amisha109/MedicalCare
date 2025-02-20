package org.amishakalhan.views.personViewUiElements;

import org.amishakalhan.Database;
import org.amishakalhan.Doctor;
import org.amishakalhan.Patient;

import javax.swing.*;

public class PersonDoctorBlock {
    protected JPanel mainPanel;
    protected JLabel doctorNameLabel;
    protected JPanel doctorInfoPanel;
    protected JButton viewBookDoctorButton;
    protected JPanel doctorInfoKeysPanel;
    protected JPanel doctorInfoValuesPanel;
    protected JLabel doctorInfoSpecializationKeyLabel;
    protected JLabel doctorInfoLocationKeyLabel;
    protected JLabel doctorInfoSpecializationValueLabel;
    protected JLabel doctorInfoLocationValueLabel;
    protected JLabel doctorInfoHoursAvailableKeyLabel;
    protected JLabel doctorInfoHoursAvailableValueLabel;

    public PersonDoctorBlock(Doctor doctor) {
        Database db = new Database();
        doctorNameLabel.setText(doctor.getName());
        doctorInfoSpecializationKeyLabel.setText("Specialization");
        doctorInfoSpecializationValueLabel.setText(doctor.getSpecialization());
        doctorInfoLocationKeyLabel.setText("Where to find");
        doctorInfoLocationValueLabel.setText(db.getHospital(doctor.getHospitalId()).getName());
        doctorInfoHoursAvailableKeyLabel.setText("Hours available");
        doctorInfoHoursAvailableValueLabel.setText(doctor.getStartTime().toString() + " - " + doctor.getEndTime().toString());
        viewBookDoctorButton.setText("View Details/Schedule Appointment");

        viewBookDoctorButton.addActionListener(e -> {
            ViewDoctorDialog viewDoctorDialog = new ViewDoctorDialog(doctor);
            viewDoctorDialog.pack();
            viewDoctorDialog.setVisible(true);
        });
    }

    public PersonDoctorBlock(Patient patient, Doctor doctor) {
        Database db = new Database();
        doctorNameLabel.setText(doctor.getName());
        doctorInfoSpecializationKeyLabel.setText("Specialization");
        doctorInfoSpecializationValueLabel.setText(doctor.getSpecialization());
        doctorInfoLocationKeyLabel.setText("Where to find");
        doctorInfoLocationValueLabel.setText(db.getHospital(doctor.getHospitalId()).getName());
        doctorInfoHoursAvailableKeyLabel.setText("Hours available");
        doctorInfoHoursAvailableValueLabel.setText(doctor.getStartTime().toString() + " - " + doctor.getEndTime().toString());
        viewBookDoctorButton.setText("View Details/Schedule Appointment");

        viewBookDoctorButton.addActionListener(e -> {
            ViewDoctorDialog viewDoctorDialog = new ViewDoctorDialog(patient, doctor);
            viewDoctorDialog.pack();
            viewDoctorDialog.setVisible(true);
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
