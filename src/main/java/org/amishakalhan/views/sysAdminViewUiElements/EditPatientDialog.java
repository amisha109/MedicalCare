package org.amishakalhan.views.sysAdminViewUiElements;

import org.amishakalhan.Database;
import org.amishakalhan.Patient;
import org.amishakalhan.Person;

import javax.swing.*;
import java.awt.event.*;

public class EditPatientDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel editPatientLabel;
    private JTextField editPatientIdValueField;
    private JTextField editPatientNameValueField;
    private JTextField editPatientEmailValueField;
    private JPasswordField editPatientPasswordValueField;
    private JLabel editPatientPasswordKeyLabel;
    private JLabel editPatientEmailKeyLabel;
    private JLabel editPatientNameKeyLabel;
    private JLabel editPatientIdKeyLabel;
    private boolean okButtonPressed = false;

    public EditPatientDialog(Person person) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        editPatientLabel.setText("Add/Edit Patient Login Credentials");

        // labels
        editPatientIdKeyLabel.setText("Person ID");
        editPatientNameKeyLabel.setText("Name");
        editPatientEmailKeyLabel.setText("Email");
        editPatientPasswordKeyLabel.setText("Password");

        // value fields
        editPatientIdValueField.setText(person.getSsNumber());
        editPatientNameValueField.setText(person.getName());

        // freezed fields
        editPatientIdValueField.setEditable(false);
        editPatientNameValueField.setEditable(false);

        buttonOK.addActionListener(e -> onOK(person));

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

    public EditPatientDialog(Patient patient) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        editPatientLabel.setText("Edit Patient");

        // labels
        editPatientIdKeyLabel.setText("Person ID");
        editPatientNameKeyLabel.setText("Name");
        editPatientEmailKeyLabel.setText("Email");
        editPatientPasswordKeyLabel.setText("Password");

        // value fields
        editPatientIdValueField.setText(patient.getSsNumber());
        editPatientNameValueField.setText(patient.getName());
        editPatientEmailValueField.setText(patient.getEmail());
        editPatientPasswordValueField.setText(patient.getPassword());

        // freezed fields
        editPatientIdValueField.setEditable(false);
        editPatientNameValueField.setEditable(false);

        buttonOK.addActionListener(e -> onOK(patient));

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
    private void onOK(Person person) {
        // get values from fields
        String email = editPatientEmailValueField.getText();
        String password = new String(editPatientPasswordValueField.getPassword());

        // create new patient object
        new Database().addPatient(new Patient(person, email, password));
        dispose();
    }

    private void onOK(Patient patient) {
        // get values from fields
        String email = editPatientEmailValueField.getText();
        String password = new String(editPatientPasswordValueField.getPassword());

        // update patient object
        patient.setEmail(email);
        patient.setPassword(password);

        // update database
        new Database().updatePatient(patient);
        okButtonPressed = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public boolean isOkButtonPressed() {
        return okButtonPressed;
    }
}
