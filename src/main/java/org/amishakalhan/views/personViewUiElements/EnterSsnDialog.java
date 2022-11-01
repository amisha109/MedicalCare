package org.amishakalhan.views.personViewUiElements;

import org.amishakalhan.Database;
import org.amishakalhan.Doctor;
import org.amishakalhan.views.sysAdminViewUiElements.AddAppointmentDialog;
import org.amishakalhan.views.sysAdminViewUiElements.AddPersonDialog;
import org.amishakalhan.views.sysAdminViewUiElements.EditPatientDialog;

import javax.swing.*;
import java.awt.event.*;

public class EnterSsnDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextPane confirmationText;
    private JTextField personSsnValueField;
    private JLabel personSsnLabel;

    public EnterSsnDialog(Doctor doctor) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        confirmationText.setText("We require some basic information from users before being able to schedule appointments. There is a chance that your community has already provided us with that information. Enter your identity value to confirm.");
        confirmationText.setEditable(false);
        confirmationText.setOpaque(false);
        personSsnLabel.setText("Enter your identity number: ");

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
        String ssn = personSsnValueField.getText();
        if (ssn.isEmpty()) {
            // show error dialog
            JOptionPane.showMessageDialog(null, "Please enter your identity number.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // if valid, check if the person exists in the database
            Database db = new Database();
            if (db.getPerson(ssn) == null) {
                AddPersonDialog addPersonDialog = new AddPersonDialog();
                addPersonDialog.pack();
                addPersonDialog.setVisible(true);
                JOptionPane.showMessageDialog(null, "Your details have been added to the system", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            // check if the person exists as a patient
            if (db.getPatient(ssn) == null) {
                EditPatientDialog editPatientDialog = new EditPatientDialog(db.getPerson(ssn));
                editPatientDialog.pack();
                editPatientDialog.setVisible(true);
                JOptionPane.showMessageDialog(null, "Your details have been added to the system", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                AddAppointmentDialog addAppointmentDialog = new AddAppointmentDialog(db.getPatient(ssn), doctor, true);
                addAppointmentDialog.pack();
                addAppointmentDialog.setVisible(true);
                JOptionPane.showMessageDialog(null, "Your appointment has been scheduled. You can view it by logging in as patient", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
